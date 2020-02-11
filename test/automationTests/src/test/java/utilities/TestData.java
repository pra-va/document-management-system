package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestData {

	
	public static List<String> getTestData(String fileName) throws IOException {
		List<String> records = new ArrayList<String>();
		String record;

		BufferedReader buffer = new BufferedReader(new FileReader(fileName));

		while ((record = buffer.readLine()) != null) {
			records.add(record);
		}
		buffer.close();

		return records;
	}
}
