package org.badminton.api.aws.s3.event.clubImage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClubImageEvent {
	private final byte[] byteFile;
	private final String originalFilename;
	private final String uuid;
}
