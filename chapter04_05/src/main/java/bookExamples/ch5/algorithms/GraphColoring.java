package bookExamples.ch5.algorithms;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.apache.giraph.Algorithm;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

@Algorithm(name = "Graph Coloring", description = "Assign different colors to neighbouring vertices")
public class GraphColoring extends
		BasicComputation<LongWritable, Text, Text, Text> {
	int myColor = -1;
	int lastColor = 100;
	int lastBO = 20;
	boolean backoff = false;
	int backOffCnt = 0;
	boolean IamDone = false;
	HashSet<Integer> usedColors = new HashSet<Integer>();
	Random rand;

	@Override
	public void compute(Vertex<LongWritable, Text, Text> vertex,
			Iterable<Text> messages) throws IOException {
		long ss = super.getSuperstep();

		// send to all nbrs my ID
		LongWritable myVertexID = vertex.getId();
		if (ss == 0) {
			Text outMessage = new Text(myVertexID.get() + "");
			super.sendMessageToAllEdges(vertex, outMessage);
		}
		// store nbr IDs
		else if (ss == 1) {		
			Iterator<Text> msgIterator = messages.iterator();
			while (msgIterator.hasNext()) {
				LongWritable removeVertexID = new LongWritable(
						Long.parseLong(msgIterator.next().toString()));
				super.addEdgeRequest(removeVertexID,EdgeFactory.create(myVertexID, new Text()));
			}
			myColor = 0;
			super.sendMessageToAllEdges(vertex, new Text(myColor + ""));
		} else {
			Iterator<Text> msgIterator = messages.iterator();
			// check incoming messages
			if (IamDone) {
				while (msgIterator.hasNext()) {
					msgIterator.next();
				}
			} else if (msgIterator.hasNext()) {
				backOffCnt = 0;
				backoff = false;
				while (msgIterator.hasNext()) {
					int recvColor = Integer.parseInt(msgIterator.next()
							.toString());
					if (recvColor != myColor) {
						usedColors.add(recvColor);
					}
					// activate backoff
					else {
						backoff = true;
						backOffCnt = (rand.nextInt(lastBO));
					}
				}
			} else if (backOffCnt > 0) {
				// decrement backoff
				backOffCnt--;
			}
			// reset color if conflict
			if (backoff) {
				backoff = false;
				myColor = -1;
			}
			// process if no backoff
			if (backOffCnt == 0) {
				if (myColor == -1) {
					for (int i = 0; i < lastColor; i++) {
						if (!usedColors.contains(i)) {
							myColor = i;
							super.sendMessageToAllEdges(vertex, new Text(myColor + ""));
							break;
						}
					}
				} else {
					IamDone = true;
					vertex.setValue(new Text(myColor + ""));
					vertex.voteToHalt();
				}
			}
		}
	}
}
