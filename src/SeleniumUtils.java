import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

	public static WebDriver driver = new ChromeDriver(new ChromeDriverService.Builder().withSilent(true).build());

	// navigate to a parameter web page and wait until it loaded fully
	public static void navigateAndWaitForLoaded(String link) {
		driver.navigate().to(link);
		waitForPageLoaded();
	}

	// wait until web page fully loaded
	public static void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	// save the first x forum link from hup.hu
	public static String[] forumLinkSaver(int firstX) {
		String[] forumLinks = new String[firstX];
		for (int i = 0; i < firstX; i++) {
			int iforums = i + 1;
			WebElement forum = driver
					.findElement(By.xpath("//*[@id=\"block-block-16\"]/div/table/tbody/tr[" + iforums + "]/td[2]/a"));
			forumLinks[i] = forum.getAttribute("href");
		}
		return forumLinks;
	}

	public static List<String> saveFirstXCommentUser(int max, String[] forumLinks) {
		List<String> userList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			SeleniumUtils.navigateAndWaitForLoaded(forumLinks[i]);

			int k = 0;
			int size = driver.findElements(By.xpath("//*[@id=\"comments\"]/div[@class=\"comment\"]")).size();
			for (int j = 1; j <= size; j++) {
				String jClass = driver.findElement(By.xpath("//*[@id=\"comments\"]/div[" + j + "]"))
						.getAttribute("class").trim();

				if (jClass.equals("comment")) {
					String submittedString = driver
							.findElement(By.xpath("//*[@id=\"comments\"]/div[" + j + "]/div[@class=\"submitted\"]"))
							.getText();

					userList.add(submittedString.substring(2, submittedString.indexOf("|")).trim());

					k += 1;
					if (k == max) {
						break;
					}
				}
			}
		}
		return userList;
	}
}
