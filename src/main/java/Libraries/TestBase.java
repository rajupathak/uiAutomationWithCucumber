package Libraries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	public static WebDriver driver;
	JavascriptExecutor js = (JavascriptExecutor) driver;
	public static Alert alert = null;
	public static Properties prop;
	static FileReader fin;
	static File file;
	ExcelReader excelObject;
	public static ExtentReports report;
	public static ExtentTest logger;
	// String url = "http://si9.vodafone.co.uk/myvodafone";
	// String url = System.getProperty("url");
	String browsername;
	// String browsername = System.getProperty("browserName");

	String log4jConfigPath = "log4j.properties";
	
	public void init() {
		PropertyConfigurator.configure(log4jConfigPath);
		selectBrowser(browsername);
		// lis = new Listener(driver);

	}

	public void selectBrowser(String browsername) {
		log.info("Creating object of " + browsername);
		if (browsername.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}

		else if (browsername.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir")
							+ "\\Drivers1\\chromedriver.exe");

			driver = new ChromeDriver();
		}

		else if (browsername.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir")
							+ "\\Drivers1\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		else if (browsername.equalsIgnoreCase("HTML")) {
			driver = new HtmlUnitDriver(true);
		}

	}

	public void getURL(String url) {
		log.info("Navigating to :" + url);
		driver.get(url);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public String[][] getData(String excelName, String sheetName) {
		// Since one Excel can have multiple sheet
		// Path contains the Excel Name
		String path = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\data\\"
				+ excelName;
		excelObject = new ExcelReader(path);
		String data[][] = excelObject.getDataFromSheet(sheetName, excelName);
		return data;
	}

	public String getDatafromSheet(String excelName, String sheetName,
			String colName, int rowNum) {
		// Since one Excel can have multiple sheet
		// Path contains the Excel Name
		String path = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\data\\"
				+ excelName;
		excelObject = new ExcelReader(path);
		String data = excelObject.getCellData(sheetName, colName, rowNum);
		return data;
	}

	public String getScreenShot(String screenshotName) {
		if (screenshotName.equals("")) {
			screenshotName = "blank";
		}

		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		TakesScreenshot tc = (TakesScreenshot) driver;

		File sourceFile = tc.getScreenshotAs(OutputType.FILE);
		System.out.println(sourceFile);

		try {

			String reportDirectory = new File(System.getProperty("user.dir"))
					.getAbsolutePath()
					+ "/src/main/java/com/test/automation/uiAutomation/screenShots/";
			System.out.println("Path:=" + reportDirectory);
			String actaulImageName = reportDirectory + screenshotName + "_"
					+ formater.format(calender.getTime()) + ".png";

			File desFile = new File(actaulImageName);
			FileUtils.copyFile(sourceFile, desFile);
			Reporter.log("<a href='" + desFile.getAbsolutePath()
					+ "'><img src='" + desFile.getAbsolutePath()
					+ "' height='100' width='100'/></a>");
			return actaulImageName;
		} catch (Exception e) {
			System.out.println("Exception is throwing during screen shot"
					+ e.getMessage());
		}
		return screenshotName;

	}

	public Iterator<String> getAllWindows() {

		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		return itr;

	}

	public void mouseHover(WebElement element, WebDriver driver) {

		Actions action = new Actions(driver);

		action.moveToElement(element).perform();
	}

	public WebElement waitForTheWebElement(WebElement element,
			WebDriver driver, long time) {

		WebDriverWait wait = new WebDriverWait(driver, time);

		return wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public WebElement waitForTheWebElementWithPoolingInterval(
			WebElement element, WebDriver driver, long time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);

		return wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void implicitWait(long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void highLighteBackground(WebDriver driver, WebElement element) {

		js.executeScript("arguments[0].style.border='6px groove yellow'",
				element);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		js.executeScript("arguments[0].style.border=''", element);

	}

	public void scroolTheWidnow() {
		js.executeScript("scroll(0,1200)");
	}

	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}

	}

	public void triggerClickOnWebElement(WebDriver driver, WebElement webElement) {
		if (isAlertPresent(driver)) {
			alert.dismiss();
			waitForTheWebElement(webElement, driver, 10);
			webElement.click();
		} else {
			waitForTheWebElement(webElement, driver, 10);
			webElement.click();
		}
	}

	public void getcookie() throws Exception {

		FileReader file = new FileReader("Cookies.data");
		BufferedReader br = new BufferedReader(file);
		String line;
		while ((line = br.readLine()) != null) {
			StringTokenizer str = new StringTokenizer(line, ";");
			while (str.hasMoreTokens()) {
				String name = str.nextToken();
				String value = str.nextToken();
				String domain = str.nextToken();
				String path = str.nextToken();
				Date expiry = null;
				String dt;
				if (!(dt = str.nextToken()).equals("null")) {

					expiry = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy")
							.parse(dt);
				}
				boolean isSecure = Boolean.valueOf(str.nextToken())
						.booleanValue();
				Cookie ck = new Cookie(name, value, domain, path, expiry,
						isSecure);

				if (name.equalsIgnoreCase("x-vodafone-solo")) {
					System.out.println("Cookie Name is :" + name);
					driver.get("http://si9.vodafone.co.uk/myvodafone");
					driver.manage().window().maximize();
					// Thread.sleep(1000);
					driver.manage().addCookie(ck);
					driver.get("http://si9.vodafone.co.uk/myvodafone");
					break;
				}

			}

		}
	}

	static {
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy_HH_MM_SS");
		// I don't want to retain the previous record
		report = new ExtentReports(
				System.getProperty("user.dir")
						+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\reports\\test"
						+ "-" + format.format(calendar.getTime()) + ".html",
				false);

	}

	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {

			logger.log(LogStatus.PASS, result.getName() + "test is passed");

		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP,
					result.getName() + "test is skipped and skip reason is"
							+ result.getThrowable());
		}

		else if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, result.getName() + "test is failed "
					+ result.getThrowable());

			String name = getScreenShot(result.getName());

			logger.log(LogStatus.FAIL,
					result.getName() + logger.addScreenCapture(name));

		}
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		getResult(result);
	}

	@BeforeMethod
	public void beforeMethod(Method name) {
		logger = report.startTest(name.getName());
		logger.log(LogStatus.INFO, name.getName() + "test Started");

	}

	@AfterClass
	public void afterClass() {

		report.endTest(logger);
		report.flush();
		driver.get(System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\reports");
		// driver.close();
	}

	public static WebElement getLocator(String locator) throws Exception {
		String []	split = locator.split(":");
		String locatorType = split[0];

		String locatorValue = split[1];
		
		System.out.println(locatorType);
		System.out.println(locatorValue);

		if (locatorType.toLowerCase().equals("id")) {
			return driver.findElement(By.id(locatorValue));
		} else if (locatorType.toLowerCase().equals("name")) {
			return driver.findElement(By.name(locatorValue));
		}

		else if (locatorType.toLowerCase().equals("classname")
				|| locatorType.toLowerCase().equals("class")) {
			return driver.findElement(By.className(locatorValue));
		}

		else if (locatorType.toLowerCase().equals("tagname")
				|| locatorType.toLowerCase().equals("tag")) {
			return driver.findElement(By.tagName(locatorValue));
		} else if (locatorType.toLowerCase().equals("linketext")
				|| locatorType.toLowerCase().equals("text")) {
			return driver.findElement(By.linkText(locatorValue));
		} else if (locatorType.toLowerCase().equals("partiallinktext")) {
			return driver.findElement(By.partialLinkText(locatorValue));
		}

		else if (locatorType.toLowerCase().equals("cssselector")
				|| locatorType.toLowerCase().equals("css")) {
			return driver.findElement(By.cssSelector(locatorValue));
		} else if (locatorType.toLowerCase().equals("xpath")) {
			return driver.findElement(By.xpath(locatorValue));
		} else {
			throw new Exception("Unknown locator type" + locatorType + " ");
		}
	}

	public static List<WebElement> getLocators(String locator) throws Exception {
		String split[] = locator.split(":");
		String locatorType = split[0];

		String locatorValue = split[1];

		if (locatorType.toLowerCase().equals("id")) {
			return driver.findElements(By.id(locatorValue));
		} else if (locatorType.toLowerCase().equals("name")) {
			return driver.findElements(By.name(locatorValue));
		}

		else if (locatorType.toLowerCase().equals("classname")
				|| locatorType.toLowerCase().equals("class")) {
			return driver.findElements(By.className(locatorValue));
		}

		else if (locatorType.toLowerCase().equals("tagname")
				|| locatorType.toLowerCase().equals("tag")) {
			return driver.findElements(By.tagName(locatorValue));
		} else if (locatorType.toLowerCase().equals("linketext")
				|| locatorType.toLowerCase().equals("text")) {
			return driver.findElements(By.linkText(locatorValue));
		} else if (locatorType.toLowerCase().equals("partiallinktext")) {
			return driver.findElements(By.partialLinkText(locatorValue));
		}

		else if (locatorType.toLowerCase().equals("cssselector")
				|| locatorType.toLowerCase().equals("css")) {
			return driver.findElements(By.cssSelector(locatorValue));
		} else if (locatorType.toLowerCase().equals("xpath")) {
			return driver.findElements(By.xpath(locatorValue));
		} else {
			throw new Exception("Unknown locator type" + locatorType + " ");
		}

	}

	public static void loadProperties() throws IOException {
		prop = new Properties();
		file = new File(
				System.getProperty("user.dir")
						+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\config\\credential.properties");

		fin = new FileReader(file);
		prop.load(fin);

		file = new File(
				System.getProperty("user.dir")
						+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\config\\LoginPageElement.properties");
		fin = new FileReader(file);

		prop.load(fin);
	}

	public static String getProperties(String data) throws IOException {
		loadProperties();
		String Data = prop.getProperty(data);
		return Data;

	}

	
}

