package bookExamples.ch5.dataSharing;

import org.apache.giraph.combiner.MessageCombiner;
import org.apache.hadoop.io.LongWritable;

public class minLongCombiner extends
		MessageCombiner<LongWritable, LongWritable> {

	@Override
	public void combine(LongWritable vertexIndex, LongWritable originalMessage,
			LongWritable messageToCombine) {
		if (originalMessage.get() > messageToCombine.get()) {
			originalMessage.set(messageToCombine.get());
		}
	}

	@Override
	public LongWritable createInitialMessage() {
		return new LongWritable(Long.MAX_VALUE);
	}
}