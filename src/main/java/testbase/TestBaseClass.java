package testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestBaseClass implements ITestListener {

	File file = new File("src/test/resources/prop");
	Properties properties = new Properties();
	private static WebDriver driver;
	private static Map<String, String> driverCap = null;

	public TestBaseClass() {

	}

	public void onStart(ITestContext context) {

		this.loadProperties(file);
		driverCap = context.getCurrentXmlTest().getAllParameters();
		System.out.println(driverCap);

		if (driver == null) {
			setDriver(context.getCurrentXmlTest().getParameter("browserName"));
		}
		driver.manage().deleteAllCookies();
		this.openWebsite();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public void setDriver(String browserName) {
		System.out.println("Browser Name......." + browserName);
		if (browserName.toLowerCase().contains("firefox")) {
			driver = new FirefoxDriver();
		}
		if (browserName.toLowerCase().contains("internet")) {
			driver = new InternetExplorerDriver();
		}
		if (browserName.toLowerCase().contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			driver = new ChromeDriver();
		}
	}

	public void openWebsite() {
		getDriver().get(getKey("baseURL"));
		getDriver().manage().window().maximize();
		WSJUtils.waitForPageLoad();
	}

	public void loadProperties(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				loadProperties(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
				try {
					File file = new File(
							"src/test/resources/prop/" + fileEntry.getName() + "");
					FileInputStream fileInput = new FileInputStream(file);

					properties.load(fileInput);
					fileInput.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// @AfterMethod
	// public void tearDown(ITestResult result) {
	//
	// if (ITestResult.FAILURE == result.getStatus()) {
	// try {
	//
	// TakesScreenshot ts = (TakesScreenshot) driver;
	//
	// File source = ts.getScreenshotAs(OutputType.FILE);
	//
	// FileUtils.copyFile(source,
	// new File("./Screenshots/" + result.getName() + ".png"));
	//
	// System.out.println("Screenshot taken : Failure ");
	// getDriver().close();
	// } catch (Exception e) {
	//
	// System.out.println("Exception while taking screenshot " +
	// e.getMessage());
	// getDriver().close();
	// }
	//
	// } else {
	// getDriver().close();
	// // log.info(result.getName() + " Function is passed");
	//
	// }
	//
	// }

	public String getKey(String key) {

		Enumeration enuKeys = properties.keys();
		while (enuKeys.hasMoreElements()) {
			String keyValue = (String) enuKeys.nextElement();
			if (keyValue.equalsIgnoreCase(key)) {
				return properties.getProperty(keyValue);
			}
			// String value = properties.getProperty(key);
			// System.out.println(key + ": " + value);
		}
		return null;
	}

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
