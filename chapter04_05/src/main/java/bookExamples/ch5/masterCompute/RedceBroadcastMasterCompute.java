//Dummy example of how to use Reduce and Broadcast in MasterCompute

package bookExamples.ch5.masterCompute;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import bookExamples.ch5.dataSharing.SUMReduce;

import org.apache.giraph.master.MasterCompute;
import org.apache.hadoop.io.LongWritable;

public class RedceBroadcastMasterCompute extends MasterCompute {

	@Override
	public void initialize() throws InstantiationException, IllegalAccessException {
		// Initialization phase, used to initialize aggregator/Reduce/Broadcast
		// or to initialize other objects

		broadcast("TotalNodes", new LongWritable(getTotalNumVertices()));
		registerReduce("SumReduce", new SUMReduce());
	}

	@Override
	public void compute() {
		// MasterCompute body
		if (getSuperstep() > 0) {
			// If not first superstep, get Reduced value for "SumReduce",
			// compare it with getTotalNumVertices(), halt only if the Reduced
			// value exceeded getTotalNumVertices()
			long totalNodes = getTotalNumVertices();
			long workerReduce = ((LongWritable) getReduced("SumReduce")).get();
			if (totalNodes <= workerReduce) {
				haltComputation();
			}
		}
	}

	public void readFields(DataInput arg0) throws IOException {
		// To deserialize this class fields (global variables) if any
	}

	public void write(DataOutput arg0) throws IOException {
		// To serialize this class fields (global variables) if any
	}
}
