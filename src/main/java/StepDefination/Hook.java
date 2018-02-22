package StepDefination;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hook {

	@Before
	public void launchBrowserTest(){
		
		System.out.println("Browser Started Successfully");
	}
	
	
	@After
	public void TearDownTest(){
		System.out.println("Browser Closed Successfully");
	}
	
}
