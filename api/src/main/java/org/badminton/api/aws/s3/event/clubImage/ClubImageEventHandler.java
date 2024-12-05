package org.badminton.api.aws.s3.event.clubImage;

import org.badminton.api.aws.s3.service.ClubImageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubImageEventHandler {
	private final ClubImageService clubImageService;

	@Async
	@TransactionalEventListener
	public void convertAndSaveImage(ClubImageEvent clubImageEvent) {
		log.info("ClubImageEventHandler : {} ", clubImageEvent);
		clubImageService.uploadFile(
			clubImageEvent.getByteFile(),
			clubImageEvent.getOriginalFilename(),
			clubImageEvent.getUuid()
		);
		log.info("ClubImageEventHandler 후 : {} ", clubImageEvent);
	}
}
