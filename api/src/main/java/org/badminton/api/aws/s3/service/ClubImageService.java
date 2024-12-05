package org.badminton.api.aws.s3.service;

import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class ClubImageService extends AbstractFileUploadService {

	public ClubImageService(AmazonS3 s3Client, ImageConversionService imageConversionService) {
		super(s3Client, imageConversionService);
	}

	@Override
	public String uploadFile(byte[] byteFiles, String fileName, String uuid) {
		return super.uploadFile(byteFiles, fileName, uuid);
	}

	@Override
	public String makeFileName(String newFileExtension, String uuid) {
		return "club-banner/" + uuid + "." + newFileExtension;
	}
}

