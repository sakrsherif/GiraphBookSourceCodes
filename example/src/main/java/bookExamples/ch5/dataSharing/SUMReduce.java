package bookExamples.ch5.dataSharing;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.giraph.reducers.ReduceOperation;
import org.apache.hadoop.io.LongWritable;

public class SUMReduce implements ReduceOperation<Long, LongWritable> {

	public void readFields(DataInput arg0) throws IOException {
		// To deserialize this class fields (global variables) if any
	}

	public void write(DataOutput arg0) throws IOException {
		// To serialize this class fields (global variables) if any
	}

	public LongWritable createInitialValue() {
		return new LongWritable(0);
	}

	public LongWritable reduceSingle(LongWritable curValue, Long valueToReduce) {
		return new LongWritable(curValue.get() + valueToReduce);
	}

	public LongWritable reducePartial(LongWritable curValue, LongWritable valueToReduce) {
		return new LongWritable(curValue.get() + valueToReduce.get());
	}

}
