package com.academicsupportsystem.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class File {

	public static File convert(MultipartFile file) throws IOException {
		File convFile = new File();
		FileOutputStream fos = new FileOutputStream(convFile.toString());
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	
}
