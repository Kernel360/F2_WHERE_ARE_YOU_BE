package org.badminton.api.aws.s3.event.clubImage;

import org.badminton.api.aws.s3.service.ClubImageService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClubImageEventHandler {
	private final ClubImageService clubImageService;

	@Async
	@EventListener
	public void convertAndSaveImage(ClubImageEvent clubImageEvent) {
		clubImageService.uploadFile(clubImageEvent.getMultipartFile(), clubImageEvent.getUuid());
	}
}
