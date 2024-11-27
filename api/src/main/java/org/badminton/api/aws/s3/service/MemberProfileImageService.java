package org.badminton.api.aws.s3.service;

import java.util.UUID;

import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.common.exception.member.MemberNotExistException;
import org.badminton.domain.domain.member.service.MemberService;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class MemberProfileImageService extends AbstractFileUploadService {

	private String currentMemberToken;

	public MemberProfileImageService(AmazonS3 s3Client, MemberService memberService,
		ImageConversionService imageConversionService) {
		super(s3Client, imageConversionService);
	}

	public String uploadFile(ImageUploadRequest file, String memberToken) {
		this.currentMemberToken = memberToken;
		return super.uploadFile(file);

	}

	@Override
	public String makeFileName(String originalFilename) {
		if (this.currentMemberToken == null) {
			throw new MemberNotExistException("멤버 토큰이 없습니다. 로그인을 먼저 해주세요.");
		}
		return "member-profile/" + UUID.randomUUID() + ".webp";
	}
}
