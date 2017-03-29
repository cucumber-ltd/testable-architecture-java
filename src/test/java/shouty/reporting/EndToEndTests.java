package shouty.reporting;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class EndToEndTests {
    @Test
    public void single_sales_person() throws Exception {
        // run report
        run("shouty.reporting.ShoutyReportJob", "test-data/test-case-1-input.csv");

        // check XML report has been generated
        Assert.assertTrue(new File("report.xml").isFile());
        Assert.assertEquals(
                readAllLines("test-data/test-case-1-output.xml"),
                readAllLines("report.xml")
        );
    }

    @Test
    public void multiple_sales_people() throws Exception {
        // run report
        run("shouty.reporting.ShoutyReportJob", "test-data/test-case-2-input.csv");

        // check XML report has been generated
        Assert.assertTrue(new File("report.xml").isFile());
        Assert.assertEquals(
                readAllLines("test-data/test-case-2-output.xml"),
                readAllLines("report.xml")
        );
    }

    private void run(String mainClass, String args) throws Exception {
        String[] command = {"java", "-cp", "target/classes", mainClass, args};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.environment().put("EXAMPLE_ENVIRONMENT_VARIABLE", "ARBITRARY_DATA");
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        process.waitFor();
    }

    private String readAllLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path)).stream().map(l -> l).collect(Collectors.joining("\n"));
    }
}