package bookExamples.ch5.dataSharing;

import org.apache.giraph.aggregators.Aggregator;
import org.apache.hadoop.io.LongWritable;

public class simpleSUMAggregator implements Aggregator<LongWritable> {

	LongWritable myValue;

	public void aggregate(LongWritable value) {
		myValue.set(myValue.get() + value.get());
	}

	public LongWritable createInitialValue() {
		return new LongWritable(0);
	}

	public LongWritable getAggregatedValue() {
		// TODO Auto-generated method stub
		return myValue;
	}

	public void setAggregatedValue(LongWritable value) {
		myValue.set(value.get());
	}

	public void reset() {
		if (myValue == null) {
			myValue = new LongWritable(0);
		} else {
			myValue.set(0);
		}
	}

}
