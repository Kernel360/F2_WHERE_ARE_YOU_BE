package org.badminton.api.aws.s3.event.memeber;

import org.badminton.api.aws.s3.service.MemberProfileImageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberImageEventHandler {
	private final MemberProfileImageService memberProfileImageService;

	@Async
	@TransactionalEventListener
	public void convertAndSaveImage(MemberImageEvent memberImageEvent) {
		memberProfileImageService.uploadFile(
			memberImageEvent.getByteFile(),
			memberImageEvent.getOriginalFilename(),
			memberImageEvent.getUuid()
		);
	}
}
