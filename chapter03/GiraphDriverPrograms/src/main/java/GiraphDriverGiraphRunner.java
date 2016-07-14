import org.apache.giraph.GiraphRunner;
import org.apache.giraph.conf.GiraphConfiguration;
import org.apache.hadoop.util.ToolRunner;

public class GiraphDriverGiraphRunner {
    public static void main(String[] args) throws Exception {
        GiraphRunner giraphRunner = new GiraphRunner();
        giraphRunner.setConf(new GiraphConfiguration());
        ((GiraphConfiguration)giraphRunner.getConf()).setMaxNumberOfSupersteps(100);
        ToolRunner.run(giraphRunner,args);
    }
}
