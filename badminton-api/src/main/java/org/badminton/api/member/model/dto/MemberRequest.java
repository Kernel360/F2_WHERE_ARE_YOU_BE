package org.badminton.api.member.model.dto;

import org.badminton.domain.member.entity.MemberAuthorization;
import org.badminton.domain.member.entity.MemberEntity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 요청 DTO")
public record MemberRequest(

	@Schema(description = "회원 역할", example = "AUTHORIZATION_USER")
	MemberAuthorization authorization,

	@Schema(description = "회원 이름", example = "이선우")
	String name,

	@Schema(description = "oAuth 로그인 이메일", example = "qosle@naver.com")
	String email,

	@Schema(description = "oAuth 제공 ID", example = "1070449979547641023123")
	String providerId
) {

	public static MemberEntity memberRequestToEntity(MemberRequest memberRequest) {
		return new MemberEntity(memberRequest.email(), memberRequest.name(), memberRequest.providerId(),
			memberRequest.authorization());
	}
}

