package org.badminton.api.aws.s3.service;

public interface ImageService {
	String uploadFile(byte[] byteFiles, String fileName, String uuid);

	String makeFileName(String newFileExtension, String uuid);
}

