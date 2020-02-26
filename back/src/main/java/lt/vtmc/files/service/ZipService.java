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

	/**
	 * This method will zip files up using ZipOutputStream.
	 * 
	 * @param files to be zipped
	 * @return
	 * @throws IOException
	 */
//	public byte[] zipFiles(List<File> files) throws IOException {
//
//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
//		ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
//
//		for (File fileTmp : files) {
//			System.out.println(
//					"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//			System.out.println(fileTmp.getName() + ": " + fileTmp.length());
//			System.out.println(
//					"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//			zipOutputStream.putNextEntry(new ZipEntry(fileTmp.getName()));
//			FileInputStream fileInputStream = new FileInputStream(fileTmp);
//			IOUtils.copy(fileInputStream, zipOutputStream);
//			fileInputStream.close();
//			zipOutputStream.closeEntry();
//		}
//
//		if (zipOutputStream != null) {
//			zipOutputStream.finish();
//			zipOutputStream.flush();
//			IOUtils.closeQuietly(zipOutputStream);
//		}
//
//		IOUtils.closeQuietly(bufferedOutputStream);
//		IOUtils.closeQuietly(byteArrayOutputStream);
//
//		return byteArrayOutputStream.toByteArray();
//	}

	public byte[] zipFiles(Map<String, ByteArrayResource> files) throws IOException {

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
