package bookExamples.ch5.dataSharing;

import org.apache.giraph.combiner.MessageCombiner;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;

public class sumDoubleCombiner extends
		MessageCombiner<LongWritable, DoubleWritable> {

	@Override
	public void combine(LongWritable vertexIndex, DoubleWritable originalMessage,
			DoubleWritable messageToCombine) {
			originalMessage.set(originalMessage.get()+messageToCombine.get());
	}

	@Override
	public DoubleWritable createInitialMessage() {
		return new DoubleWritable(0);
	}
}