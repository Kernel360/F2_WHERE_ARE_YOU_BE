package org.badminton.api.application.clubImage;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.badminton.api.aws.s3.event.clubImage.ClubImageEvent;
import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.common.exception.EmptyFileException;
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
	private static final String CLOUDFRONT_URL_PREFIX = "https://d36om9pjoifd2y.cloudfront.net/club-banner/";
	private static final String WEBP = "webp";
	private static final String AVIF = "avif";

	@Transactional
	public String saveImage(ImageUploadRequest request) {
		try {
			byte[] byteFile = request.multipartFile().getBytes();

			String originalNam = request.multipartFile().getOriginalFilename();
			// uuid 객체 생성
			String uuid = UUID.randomUUID().toString();
			String extension = getFileExtension(Objects.requireNonNull(originalNam));

			// 비동기 처리
			eventPublisher.publishEvent(new ClubImageEvent(byteFile, originalNam, uuid));

			// url 만드는 곳으로 전달
			return preReturnUrl(uuid, extension);
		} catch (IOException e) {
			throw new EmptyFileException(e);
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
