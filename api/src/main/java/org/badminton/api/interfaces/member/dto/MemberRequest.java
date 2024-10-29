package org.badminton.api.interfaces.member.dto;

import org.badminton.domain.domain.member.command.MemberCommand;
import org.badminton.domain.domain.member.entity.MemberAuthorization;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 요청 DTO")
public record MemberRequest(

        @Schema(description = "회원 역할", example = "AUTHORIZATION_USER")
        MemberAuthorization authorization,

        @Schema(description = "회원 이름", example = "이영희")
        String name,

        @Schema(description = "oAuth 로그인 이메일", example = "qosle@naver.com")
        String email,

        @Schema(description = "oAuth 제공 ID", example = "1070449979547641023123")
        String providerId,

        @Schema(description = "프로필 사진", example = "1070449979547641023123")
        String profileImage
) {

    public MemberCommand of() {
        return new MemberCommand(this.authorization, this.name, this.email, this.providerId, this.profileImage);
    }
}


