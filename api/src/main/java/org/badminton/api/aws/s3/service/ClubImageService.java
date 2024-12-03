package org.badminton.api.aws.s3.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class ClubImageService extends AbstractFileUploadService {

	public ClubImageService(AmazonS3 s3Client, ImageConversionService imageConversionService) {
		super(s3Client, imageConversionService);
	}

	@Override
	public String uploadFile(MultipartFile file, String uuid) {
		return super.uploadFile(file, uuid);
	}

	@Override
	public String makeFileName(String newFileExtension, String uuid) {
		return "club-banner/" + uuid + "." + newFileExtension;
	}
}

