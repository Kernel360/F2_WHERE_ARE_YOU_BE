package org.badminton.api.application.clubImage;

import java.util.Objects;
import java.util.UUID;

import org.badminton.api.aws.s3.event.clubImage.ClubImageEvent;
import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubImageFacade {
	private final ApplicationEventPublisher eventPublisher;
	private static final String CLOUDFRONT_URL_PREFIX = "https://d36om9pjoifd2y.cloudfront.net/club-banner/";
	private static final String WEBP = "webp";
	private static final String AVIF = "avif";

	@Transactional
	public String saveImage(ImageUploadRequest request) {
		// uuid 객체 생성
		String uuid = UUID.randomUUID().toString();
		String extension = getFileExtension(Objects.requireNonNull(request.multipartFile().getOriginalFilename()));

		// 비동기 처리
		eventPublisher.publishEvent(new ClubImageEvent(request.multipartFile(), uuid));

		// url 만드는 곳으로 전달
		return preReturnUrl(uuid, extension);
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
