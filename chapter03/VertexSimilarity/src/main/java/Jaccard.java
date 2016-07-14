/**
 * Copyright 2014 Grafos.ml
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import ml.grafos.okapi.common.data.LongArrayListWritable;
import ml.grafos.okapi.common.data.MessageWrapper;

import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.master.DefaultMasterCompute;
import org.apache.hadoop.io.*;

/**
 * 
 * This class computes the Jaccard similarity or distance
 * for each pair of neighbors in an undirected unweighted graph.  
 * 
 * To get the exact Jaccard similarity, run the command:
 * 
 * <pre>
 * hadoop jar $OKAPI_JAR org.apache.giraph.GiraphRunner \
 *   ml.grafos.okapi.graphs.Jaccard\$SendFriendsList  \
 *   -mc  ml.grafos.okapi.graphs.Jaccard\$MasterCompute  \
 *   -eif ml.grafos.okapi.io.formats.LongDoubleZerosTextEdgeInputFormat  \
 *   -eip $INPUT_EDGES \
 *   -eof org.apache.giraph.io.formats.SrcIdDstIdEdgeValueTextOutputFormat \
 *   -op $OUTPUT \
 *   -w $WORKERS \
 *   -ca giraph.oneToAllMsgSending=true \
 *   -ca giraph.outEdgesClass=org.apache.giraph.edge.HashMapEdges \
 *   -ca jaccard.approximation.enabled=false
 *   
 *   Use -ca distance.conversion.enabled=true to get the Jaccard distance instead.
 *
 * 
 * To get the approximate Jaccard similarity, replace the SendFriendsList class
 * in the command with the SendFriendsBloomFilter class and set the 
 * jaccard.approximation.enabled parameter to true.
 * 
 * 
 * @author dl
 *
 */
public class Jaccard {


  /**
   * Implements the first step in the exact jaccard similirity algorithm. Each
   * vertex broadcasts the list with the IDs of al its neighbors.
   * @author dl
   *
   */

  public static class SendFriendsList
          extends BasicComputation<LongWritable,
          NullWritable, DoubleWritable, LongIdFriendsList> {

    @Override
    public void compute(Vertex<LongWritable, NullWritable, DoubleWritable> vertex,
                        Iterable<LongIdFriendsList> messages)
            throws IOException {

      final LongArrayListWritable friends =  new LongArrayListWritable();

      for (Edge edge : vertex.getEdges()) {
          LongWritable id = (LongWritable)edge.getTargetVertexId();
          friends.add(WritableUtils.clone(id, getConf()));
      }

      LongIdFriendsList msg = new LongIdFriendsList();

      msg.setSourceId(vertex.getId());
      msg.setMessage(friends);
      sendMessageToAllEdges(vertex, msg);
    }
  }

  /**
   * This is the message sent in the implementation of the exact jaccard
   * similarity. The message contains the source vertex id and a list of vertex
   * ids representing the neighbors of the source.
   * 
   * @author dl
   *
   */
  public static class LongIdFriendsList extends MessageWrapper<LongWritable, 
    LongArrayListWritable> { 

    @Override
    public Class<LongWritable> getVertexIdClass() {
      return LongWritable.class;
    }

    @Override
    public Class<LongArrayListWritable> getMessageClass() {
      return LongArrayListWritable.class;
    }
  }

  /**
   * Implements the computation of the exact Jaccard vertex similarity. The 
   * vertex Jaccard similarity between u and v is the number of common neighbors 
   * of u and v divided by the number of vertices that are neighbors of u or v.
   * 
   * This computes similarity only between vertices that are connected with 
   * edges, not any pair of vertices in the graph.
   * 
   * @author dl
   *
   */
  public static class JaccardComputation extends BasicComputation<LongWritable,
          NullWritable, DoubleWritable, LongIdFriendsList> {


      @Override
      public void compute(
              Vertex<LongWritable, NullWritable, DoubleWritable> vertex,
              Iterable<LongIdFriendsList> messages) throws IOException {

          for (LongIdFriendsList msg : messages) {
              LongWritable src = msg.getSourceId();
              DoubleWritable edgeValue = vertex.getEdgeValue(src);
              assert(edgeValue!=null);
              long totalFriends = vertex.getNumEdges();
              long commonFriends = 0;
              for (LongWritable id : msg.getMessage()) {
                  if (vertex.getEdgeValue(id)!=null) { // This is a common friend
                      commonFriends++;
                  } else {
                      totalFriends++;
                  }
              }

              vertex.setEdgeValue(src, new DoubleWritable(
                      (double)commonFriends/(double)totalFriends));
          }
          vertex.voteToHalt();
      }
  }
  







  /**
   * Coordinates the execution of the algorithm.
   */
  public static class MasterCompute extends DefaultMasterCompute {

      @Override
      public final void compute() {
          long superstep = getSuperstep();
          if (superstep == 0) {
              setComputation(SendFriendsList.class);
          } else if (superstep == 1) {
              setComputation(JaccardComputation.class);
          }

      }
  }

}