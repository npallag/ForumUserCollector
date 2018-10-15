import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class CollectorTest {

	@Before
	public void navigateToHup() {
		SeleniumUtils.navigateAndWaitForLoaded("https://hup.hu/");
	}

	@Test
	public void assertNameAndPassTextFieldIsDisplayed() {
		assertTrue(SeleniumUtils.driver.findElement(By.cssSelector("#edit-name")).isDisplayed());
		assertTrue(SeleniumUtils.driver.findElement(By.cssSelector("#edit-pass")).isDisplayed());
	}

	@Test
	public void assertMinMaxUserListSize() {
		String[] firstTenForumLinks = SeleniumUtils.forumLinkSaver(10);
		List<String> userList = SeleniumUtils.saveFirstXCommentUser(10, firstTenForumLinks);
		userList = new ArrayList<String>(new HashSet<String>(userList));

		assertTrue(userList.size() > 10);
		assertTrue(userList.size() < 100);
	}

	@Test
	public void assertTheUserListNotContainsUserTray() {
		String[] firstTenForumLinks = SeleniumUtils.forumLinkSaver(10);
		List<String> userList = SeleniumUtils.saveFirstXCommentUser(10, firstTenForumLinks);
		userList = new ArrayList<String>(new HashSet<String>(userList));

		assertFalse(userList.contains("Trey"));
	}

	@AfterClass
	public static void driverQuit() {
		SeleniumUtils.driver.quit();
	}
}
