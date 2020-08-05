package abhi.karateAPI.feature;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.*;

import org.junit.Test;		


// @RunWith(Karate.class)
@KarateOptions(tags = {"~@ignore"})
public class APITestRunner {

	@Test
    public void testParallel() {
        Results results = Runner.parallel(getClass(), 5);	//("classpath:demo").tags("~@ignore").parallel(5);
        generateReport(results.getReportDir());
        assertTrue(results.getErrorMessages(), results.getFailCount() == 0);        
    }
    
    public static void generateReport(String karateOutputPath) {        
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
        final List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
        jsonFiles.forEach(new Consumer<File>() {
            public void accept(File file) {
                jsonPaths.add(file.getAbsolutePath());
            }
        });
        Configuration config = new Configuration(new File("target"), "demo");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();        
    }
}
	