package StepDefination;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Libraries.TestBase;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FacebookLoginWithDataTable extends TestBase {
	
	TestBase obj = new TestBase();
	

	@Given("^User is on Home Page$")
	public void user_is_on_Home_Page() throws Throwable {
	    obj.selectBrowser("chrome");
	    obj.getURL("https://www.facebook.com/");
	    
	}

	@When("^User Navigate to LogIn Page$")
	public void user_Navigate_to_LogIn_Page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("User navigated to login page successfully");
	    
	}

	@When("^User enters Credentials to LogIn$")
	public void user_enters_Credentials_to_LogIn(DataTable arg1) throws Throwable {
	List<List<String>> list = arg1.raw();
		WebElement userName = driver.findElement(By.id("email"));
		userName.sendKeys(list.get(0).get(0));

		WebElement password = driver.findElement(By.id("pass"));

		password.sendKeys(list.get(0).get(1));
		
	    
	}

	@Then("^Message displayed Login Successfully$")
	public void message_displayed_Login_Successfully() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("User logged in succesfully");
	}



}
