package org.badminton.api.aws.s3.event.memeber;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberImageEvent {
	private final byte[] byteFile;
	private final String originalFilename;
	private final String uuid;
}
