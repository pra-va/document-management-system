package lt.vtmc.files.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

/**
 * This service class is used to zip files.
 * 
 * @author pra-va
 *
 */
@Service
public class ZipService {

	@Autowired
	private FileService fileService;

	/**
	 * This method will turn files provided as byteArrayResource in a map to a byte
	 * array of a zip file.
	 * 
	 * @param files
	 * @param username
	 * @return byte array of zip file
	 * @throws IOException
	 */
	public byte[] zipFiles(Map<String, ByteArrayResource> files, String username) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

		for (Map.Entry<String, ByteArrayResource> entry : files.entrySet()) {
			File file = new File("/tmp/" + entry.getKey());
			zipOutputStream.putNextEntry(new ZipEntry(entry.getKey()));
			OutputStream out = new FileOutputStream(file);
			out.write(entry.getValue().getByteArray());
			FileInputStream fileInputStream = new FileInputStream(file);
			IOUtils.copy(fileInputStream, zipOutputStream);
			out.close();
			fileInputStream.close();
			zipOutputStream.closeEntry();
		}

		try {
			File file = new File("/tmp/" + "docments.csv");
			zipOutputStream.putNextEntry(new ZipEntry("docments.csv"));
			OutputStream out = new FileOutputStream(file);
			out.write(fileService.getCsv(username).getBytes());
			FileInputStream fileInputStream = new FileInputStream(file);
			IOUtils.copy(fileInputStream, zipOutputStream);
			out.close();
			fileInputStream.close();
			zipOutputStream.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (zipOutputStream != null) {
			zipOutputStream.finish();
			zipOutputStream.flush();
			IOUtils.closeQuietly(zipOutputStream);
		}

		IOUtils.closeQuietly(bufferedOutputStream);
		IOUtils.closeQuietly(byteArrayOutputStream);

		return byteArrayOutputStream.toByteArray();
	}
}
