package org.badminton.api.aws.s3.event.memeber;

import org.badminton.api.aws.s3.service.MemberProfileImageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberImageEventHandler {
	private final MemberProfileImageService memberProfileImageService;

	@Async
	@TransactionalEventListener
	public void convertAndSaveImage(MemberImageEvent memberImageEvent) {
		log.info("memberException : {} ", memberImageEvent);
		memberProfileImageService.uploadFile(
			memberImageEvent.getByteFile(),
			memberImageEvent.getOriginalFilename(),
			memberImageEvent.getUuid()
		);
		log.info("memberException 후 : {} ", memberImageEvent);

	}
}
