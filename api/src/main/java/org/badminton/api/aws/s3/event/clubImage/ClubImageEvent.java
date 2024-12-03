package org.badminton.api.aws.s3.event.clubImage;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClubImageEvent {
	private final MultipartFile multipartFile;
	private final String uuid;
}
