package org.badminton.api.aws.s3.service;

import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;

public interface ImageService {
	String uploadFile(ImageUploadRequest file);

	String makeFileName(String newFileExtension);
}

