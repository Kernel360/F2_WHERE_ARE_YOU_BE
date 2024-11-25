package org.badminton.api.interfaces.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.badminton.domain.domain.member.entity.Member;

@Schema(description = "로그인 세션 확인 Dto")
public record SimpleMemberResponse(

        @Schema(description = "회원 이름", example = "이선우")
        String name,

        @Schema(description = "회원 토큰", example = "member_token_1")
        String memberToken,

        @Schema(description = "oAuth 로그인 이메일", example = "qosle@naver.com")
        String email,

        @Schema(description = "oAuth 제공 ID", example = "1070449979547641023123")
        Member.MemberTier memberTier,

        @Schema(description = "회원 역할", example = "AUTHORIZATION_USER")
        String authorization,

        @Schema(description = "oAuth 제공 이미지", example = "1070449979547641023123")
        String profileImage
) {

}
