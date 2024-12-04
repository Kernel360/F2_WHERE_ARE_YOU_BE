package org.badminton.api.aws.s3.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	String uploadFile(MultipartFile file, String uuid);

	String makeFileName(String newFileExtension, String uuid);
}

