//Dummy example of how to use aggregator and persistent aggregator in MasterCompute

package bookExamples.ch5.masterCompute;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import bookExamples.ch5.dataSharing.SUMAggregator;
import org.apache.giraph.master.MasterCompute;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class AggregatorMasterCompute extends MasterCompute {

	// Using Hadoop's Text object for easier serialization/deserialization
	Text agg1;
	Text agg2;

	public AggregatorMasterCompute() {
		this.agg1 = new Text("SumAgg");
		this.agg2 = new Text("PersSumAgg");
	}

	@Override
	public void initialize() throws InstantiationException, IllegalAccessException {
		// Initialization phase, used to initialize aggregator/Reduce/Broadcast
		// or to initialize other objects

		// Normal Aggregator
		registerAggregator(agg1.toString(), SUMAggregator.class);
		// Persistent Aggregator
		registerPersistentAggregator(agg2.toString(), SUMAggregator.class);
	}

	@Override
	public void compute() {
		// MasterCompute body
		if (getSuperstep() == 0) {
			// If this is the first superstep, set aggregator variables
			setAggregatedValue(agg1.toString(), new LongWritable(0));
			setAggregatedValue(agg2.toString(), new LongWritable(getTotalNumVertices()));
		} else {
			// If not first superstep, get aggregator value for "SumAgg",
			// compare it with "PersSumAgg", halt if all aggregators used
			// aggregator "SumAgg" or otherwise assign a new value for aggregaor
			// "PersSumAgg"
			long workerAgg = ((LongWritable) getAggregatedValue(agg1.toString())).get();
			long lastPerAgg = ((LongWritable) getAggregatedValue(agg2.toString())).get();
			if (lastPerAgg <= workerAgg) {
				haltComputation();
			} else {
				setAggregatedValue(agg2.toString(), new LongWritable(lastPerAgg - workerAgg));
			}
		}
	}

	public void readFields(DataInput arg0) throws IOException {
		// To deserialize this class fields (global variables) if any
		agg1.readFields(arg0);
		agg2.readFields(arg0);

	}

	public void write(DataOutput arg0) throws IOException {
		// To serialize this class fields (global variables) if any
		agg1.write(arg0);
		agg2.write(arg0);
	}
}
