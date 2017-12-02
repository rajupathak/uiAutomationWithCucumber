package StepDefination;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import Libraries.TestBase;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginPage extends TestBase {

	TestBase obj = new TestBase();
	String title;

	@Given("^Open \"(.*?)\" and launch the Home Page URL$")
	public void open_and_launch_the_Home_Page_URL(String browserName)
			throws Throwable {
		System.out.println(browserName);
		obj.selectBrowser(browserName);
		obj.getURL("http://www.vodafone.co.uk/myvodafone");
	}

	@When("^user enter the valid \"(.*?)\" and \"(.*?)\"$")
	public void user_enter_the_valid_and(String useName, String password)
			throws Throwable {
		driver.findElement(By.id("userId")).sendKeys(useName);
		driver.findElement(By.id("passwordLogin")).sendKeys(password);
	}

	@When("^click on Login CTA$")
	public void click_on_Login_CTA() throws Throwable {
		driver.findElement(
				By.xpath("//div[@class='formButtonContainer']/input[@value='Log in now']"))
				.click();
	}

	@Then("^User is navigated to account summary Taskflow$")
	public void user_is_navigated_to_account_summary_Taskfloe()
			throws Throwable {
		System.out.println("User is successfully navigated to Account summary");
		title = driver.getTitle();
	}

	@Then("^verify the account summary title$")
	public void verify_the_account_summary_title() throws Throwable {
		Assert.assertEquals(title, "Account summary");
		System.out.println("title verified successsfully");
		driver.close();
	}

}
