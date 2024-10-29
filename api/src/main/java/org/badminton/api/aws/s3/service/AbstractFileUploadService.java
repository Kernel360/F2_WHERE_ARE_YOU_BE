package org.badminton.api.aws.s3.service;

import java.io.IOException;
import java.util.Objects;

import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.common.exception.EmptyFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractFileUploadService implements ImageService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3 s3Client;
	private static final String S3_URL_PREFIX = "https://badminton-team.s3.ap-northeast-2.amazonaws.com";
	private static final String CLOUDFRONT_URL_PREFIX = "https://d36om9pjoifd2y.cloudfront.net";

	public String uploadFile(ImageUploadRequest file) {
		MultipartFile uploadFile = file.multipartFile();
		if (uploadFile.isEmpty() || Objects.isNull(uploadFile.getOriginalFilename())) {
			throw new EmptyFileException();
		}
		String fileName = makeFileName(uploadFile.getOriginalFilename());

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(uploadFile.getSize());
		objectMetadata.setContentType(uploadFile.getContentType());
		try {
			s3Client.putObject(new PutObjectRequest(bucket, fileName,
				uploadFile.getInputStream(), objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
			return toCloudFrontUrl(s3Client.getUrl(bucket, fileName).toString());
		} catch (IOException exception) {
			throw new EmptyFileException(exception);
		}
	}

	private String toCloudFrontUrl(String originUrl) {
		if (originUrl != null && originUrl.startsWith(S3_URL_PREFIX)) {
			return originUrl.replace(S3_URL_PREFIX, CLOUDFRONT_URL_PREFIX);
		} else {
			throw new EmptyFileException();
		}
	}

	@Override
	public abstract String makeFileName(String originalFilename);

}

