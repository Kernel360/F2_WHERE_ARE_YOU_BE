package org.badminton.api.common.exception.member;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ImageFileNotFoundException extends BadmintonException {

	public ImageFileNotFoundException() {
		super(ErrorCode.IMAGE_FILE_NOT_FOUND);
	}

}
