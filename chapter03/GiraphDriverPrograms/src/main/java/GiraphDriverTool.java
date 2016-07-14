import org.apache.giraph.conf.GiraphConfiguration;
import org.apache.giraph.examples.SimpleShortestPathsComputation;
import org.apache.giraph.job.GiraphJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class GiraphDriverTool implements Tool{
    private Configuration conf;
    public Configuration getConf() {
        return conf;
    }

    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    public int run(String[] args) throws Exception {
        GiraphConfiguration giraphConf = new GiraphConfiguration(getConf());
        giraphConf.setComputationClass(SimpleShortestPathsComputation.class);
        GiraphJob giraphJob = new GiraphJob(giraphConf,"GiraphDriverTool");
        giraphJob.run(true);
        return 0;
    }

    public static void main(String[] args) throws Exception{
        ToolRunner.run(new GiraphDriverTool(), args);
    }
}
