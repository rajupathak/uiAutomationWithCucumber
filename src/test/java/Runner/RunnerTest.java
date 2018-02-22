package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(features="src/test/resource",glue={"StepDefination"},tags={"@FacebookLogin"},dryRun=false,monochrome=true,format={"pretty","html:test-Output"})
public class RunnerTest {

}
