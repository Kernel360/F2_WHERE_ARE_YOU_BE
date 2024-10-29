package org.badminton.api.aws.s3.service;

import java.util.UUID;

import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class ClubImageService extends AbstractFileUploadService {

	public ClubImageService(AmazonS3 s3Client) {
		super(s3Client);
	}

	@Override
	public String uploadFile(ImageUploadRequest file) {
		return super.uploadFile(file);
	}

	@Override
	public String makeFileName(String originalFilename) {
		String[] originFile = originalFilename.split("\\.");
		String extension = originFile[originFile.length - 1];
		return "club-banner/" + UUID.randomUUID() + "." + extension;
	}
}

