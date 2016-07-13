//Example of implementing the simplest MasterCompute class

package bookExamples.ch5.masterCompute;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.giraph.master.MasterCompute;

public class simpleMasterCompute extends MasterCompute {

	@Override
	public void initialize() throws InstantiationException, IllegalAccessException {
		// Initialization phase, used to initialize aggregator/Reduce/Broadcast
		// or to initialize other objects
	}

	@Override
	public void compute() {
		// MasterCompute body
		long ssID = getSuperstep();
		long vCnt = getTotalNumVertices();
		if (ssID > vCnt) {
			haltComputation();
		}
	}

	public void readFields(DataInput arg0) throws IOException {
		// To deserialize this class fields (global variables) if any
	}

	public void write(DataOutput arg0) throws IOException {
		// To serialize this class fields (global variables) if any
	}
}
