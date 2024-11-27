package org.badminton.api.aws.s3.service;

import java.util.UUID;

import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class ClubImageService extends AbstractFileUploadService {

	public ClubImageService(AmazonS3 s3Client, ImageConversionService imageConversionService) {
		super(s3Client, imageConversionService);
	}

	@Override
	public String uploadFile(ImageUploadRequest file) {
		return super.uploadFile(file);
	}

	@Override
	public String makeFileName(String newFileExtension) {
		return "club-banner/" + UUID.randomUUID() + "." + newFileExtension;
	}
}

