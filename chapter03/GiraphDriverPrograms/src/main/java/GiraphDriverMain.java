import org.apache.giraph.conf.GiraphConfiguration;
import org.apache.giraph.examples.SimpleShortestPathsComputation;
import org.apache.giraph.io.formats.GiraphFileInputFormat;
import org.apache.giraph.io.formats.IdWithValueTextOutputFormat;
import org.apache.giraph.io.formats.JsonLongDoubleFloatDoubleVertexInputFormat;
import org.apache.giraph.job.GiraphJob;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class GiraphDriverMain {
    public static void main(String[] args) throws Exception {
        String inputPath = "src/main/resources/input/input.txt";
        String outputPath = "src/main/resources/output";


        GiraphConfiguration giraphConf = new GiraphConfiguration();

        giraphConf.setComputationClass(SimpleShortestPathsComputation.class);

        giraphConf.setVertexInputFormatClass(JsonLongDoubleFloatDoubleVertexInputFormat.class);
        GiraphFileInputFormat.addVertexInputPath(giraphConf, new Path(inputPath));

        giraphConf.setLocalTestMode(true);
        giraphConf.setWorkerConfiguration(1, 1, 100);
        giraphConf.SPLIT_MASTER_WORKER.set(giraphConf, false);

        giraphConf.setMaxNumberOfSupersteps(100);

        giraphConf.setVertexOutputFormatClass(IdWithValueTextOutputFormat.class);

        GiraphJob giraphJob = new GiraphJob(giraphConf,"GiraphDriverMain");

        FileOutputFormat.setOutputPath(giraphJob.getInternalJob(), new Path(outputPath));

        giraphJob.run(true);
    }
}
