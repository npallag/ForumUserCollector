import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;

public class Collector {

	public static void main(String[] args) {

		final String hupAddress = "https://hup.hu/";
		
		// navigate to hup.hu
		SeleniumUtils.navigateAndWaitForLoaded(hupAddress);
		// save first 10 forum link
		String[] firstTenForumLinks = SeleniumUtils.forumLinkSaver(10);
		// get the maximum first ten user names from comments (only top level)
		List<String> userList = SeleniumUtils.saveFirstXCommentUser(10, firstTenForumLinks);
		// close browser
		SeleniumUtils.driver.quit();
		// eliminate duplicates
		userList = new ArrayList<>(new HashSet<>(userList));
		// sort list in alphabetical order
		Collections.sort(userList, String.CASE_INSENSITIVE_ORDER);
		// print all user name to the console
		for (String element : userList) {
			System.out.println(element);
		}
	}

}
