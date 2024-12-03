package org.badminton.api.aws.s3.event.memeber;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberImageEvent {
	private final MultipartFile multipartFile;
	private final String uuid;
}
