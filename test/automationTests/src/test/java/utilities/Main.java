package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;

public class Main {

	public static void main(String[] args) {
		XStream xstream = new XStream();
		XStream.setupDefaultSecurity(xstream);
		xstream.allowTypesByWildcard(new String[] {
			    "utilities.User"
			});
		try {
			User user = (User) xstream.fromXML(FileUtils.readFileToString(new File("src/test/resources/user.xml")));
			System.out.println(user.getLastName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
