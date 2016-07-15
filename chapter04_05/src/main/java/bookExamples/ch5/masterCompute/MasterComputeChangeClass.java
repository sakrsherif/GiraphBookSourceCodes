//Example of implementing the simplest MasterCompute class

package bookExamples.ch5.masterCompute;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.giraph.master.MasterCompute;

import bookExamples.ch4.algorithms.ConnectedComponentsVertex;
import bookExamples.ch4.algorithms.PageRankVertexComputation;
import bookExamples.ch5.dataSharing.minLongCombiner;
import bookExamples.ch5.dataSharing.sumDoubleCombiner;

public class MasterComputeChangeClass extends MasterCompute {

	@Override
	public void initialize() throws InstantiationException, IllegalAccessException {
		// Initialization phase, used to initialize aggregator/Reduce/Broadcast
		// or to initialize other objects
	}

	@Override
	public void compute() {
		// MasterCompute body
		long ssID = getSuperstep();
		if(ssID == 0){
			//At superstep 0, start with PageRank
			setComputation(PageRankVertexComputation.class);	
			/*
			 * If the number of edges 10 times larger than
			 * the number of vertices, use a PageRank combiner
			*/
			long vCnt = getTotalNumVertices();
			long eCnt = getTotalNumEdges();
			if(eCnt > 10 * vCnt){
				setMessageCombiner(sumDoubleCombiner.class);
			}
		}
		else if (ssID == 10) {
			//change the compute class in superstep 10
			setMessageCombiner(minLongCombiner.class);
			setComputation(ConnectedComponentsVertex.class);
		}
	}

	public void readFields(DataInput arg0) throws IOException {
		// To deserialize this class fields (global variables) if any
	}

	public void write(DataOutput arg0) throws IOException {
		// To serialize this class fields (global variables) if any
	}
}
