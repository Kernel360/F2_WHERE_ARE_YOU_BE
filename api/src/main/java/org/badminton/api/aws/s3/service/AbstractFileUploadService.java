package org.badminton.api.aws.s3.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

import org.badminton.api.common.exception.EmptyFileException;
import org.badminton.api.common.exception.FileSizeOverException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFileUploadService implements ImageService {
	private static final long MAX_FILE_SIZE = 2548576; // 1.5MB
	private static final String WEBP = "webp";
	private static final String AVIF = "avif";
	private static final String CONTENT_TYPE = "image/webp";

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3 s3Client;
	private final ImageConversionService imageConversionService;
	private static final String S3_URL_PREFIX = "https://badminton-team.s3.ap-northeast-2.amazonaws.com";
	private static final String CLOUDFRONT_URL_PREFIX = "https://d36om9pjoifd2y.cloudfront.net";

	public String uploadFile(MultipartFile uploadFile, String uuid) {
		if (uploadFile.getSize() > MAX_FILE_SIZE) {
			throw new FileSizeOverException(uploadFile.getSize());
		}
		log.info("42 체크 전 : {}", uploadFile.getName());

		if (uploadFile.isEmpty() || Objects.isNull(uploadFile.getOriginalFilename())) {
			throw new EmptyFileException();
		}
		log.info("uploadFile try 전 : {}", uploadFile.getName());
		try {
			String fileExtension = getFileExtension(uploadFile.getOriginalFilename());
			byte[] processedImage = processImage(uploadFile, fileExtension);
			log.info(" try processedImage : {}", processedImage);
			String newFileExtension = determineNewFileExtension(fileExtension);
			String fileName = makeFileName(newFileExtension, uuid);

			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(processedImage.length);
			objectMetadata.setContentType(CONTENT_TYPE);

			// S3에 파일 업로드
			s3Client.putObject(new PutObjectRequest(bucket, fileName,
				new ByteArrayInputStream(processedImage), objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
			log.info(" s3Client 실행 : {}", fileName);

			// 업로드 후 CloudFront URL 반환
			return toCloudFrontUrl(s3Client.getUrl(bucket, fileName).toString());

		} catch (IOException e) {
			throw new EmptyFileException();
		}
	}

	private String getFileExtension(String filename) {
		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex == -1) {
			return "";
		}
		return filename.substring(dotIndex + 1);
	}

	private String toCloudFrontUrl(String originUrl) {
		if (originUrl != null && originUrl.startsWith(S3_URL_PREFIX)) {
			return originUrl.replace(S3_URL_PREFIX, CLOUDFRONT_URL_PREFIX);
		} else {
			throw new EmptyFileException();
		}
	}

	private byte[] processImage(MultipartFile file, String extension) throws IOException {
		if (WEBP.equalsIgnoreCase(extension) || AVIF.equalsIgnoreCase(extension)) {
			return file.getBytes();
		}
		return imageConversionService.convertToWebP(file);
	}

	private String determineNewFileExtension(String extension) {
		if (WEBP.equalsIgnoreCase(extension) || AVIF.equalsIgnoreCase(extension)) {
			return extension;
		} else {
			return WEBP;
		}
	}

	@Override
	public abstract String makeFileName(String newFileExtension, String uuid);
}
