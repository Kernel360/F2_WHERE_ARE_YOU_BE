package org.badminton.api.aws.s3.event.memeber;

import org.badminton.api.aws.s3.service.MemberProfileImageService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberImageEventHandler {
	private final MemberProfileImageService memberProfileImageService;

	@Async
	@EventListener
	public void convertAndSaveImage(MemberImageEvent memberImageEvent) {
		memberProfileImageService.uploadFile(
			memberImageEvent.getMultipartFile(),
			memberImageEvent.getUuid()
		);
	}
}
