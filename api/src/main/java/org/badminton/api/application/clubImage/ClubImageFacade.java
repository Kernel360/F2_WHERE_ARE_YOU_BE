package org.badminton.api.application.clubImage;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.badminton.api.aws.s3.event.clubImage.ClubImageEvent;
import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.common.exception.EmptyFileException;
import org.badminton.api.common.exception.FileSizeOverException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubImageFacade {
	private final ApplicationEventPublisher eventPublisher;
	private static final long MAX_FILE_SIZE = 2548576;
	private static final String CLOUDFRONT_URL_PREFIX = "https://d36om9pjoifd2y.cloudfront.net/club-banner/";
	private static final String WEBP = "webp";
	private static final String AVIF = "avif";

	@Transactional
	public String saveImage(ImageUploadRequest request) {
		try {

			byte[] byteFile = request.multipartFile().getBytes();

			validateFileSize(byteFile);

			String originalNam = request.multipartFile().getOriginalFilename();
			String uuid = UUID.randomUUID().toString();
			String extension = getFileExtension(Objects.requireNonNull(originalNam));

			eventPublisher.publishEvent(new ClubImageEvent(byteFile, originalNam, uuid));

			return preReturnUrl(uuid, extension);
		} catch (IOException e) {
			throw new EmptyFileException(e);
		}
	}

	private void validateFileSize(byte[] byteFile) {
		if (byteFile.length > MAX_FILE_SIZE) {
			throw new FileSizeOverException(byteFile.length);
		}
	}

	private String preReturnUrl(String uuid, String extension) {
		String origin = CLOUDFRONT_URL_PREFIX + uuid + ".";
		if (extension.equals(WEBP) || extension.equals(AVIF)) {
			return origin + extension;
		}
		return origin + WEBP;
	}

	private String getFileExtension(String filename) {
		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex == -1) {
			return "";
		}
		return filename.substring(dotIndex + 1);
	}
}
