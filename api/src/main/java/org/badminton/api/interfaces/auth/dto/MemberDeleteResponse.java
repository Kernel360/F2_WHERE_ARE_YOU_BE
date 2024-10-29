package org.badminton.api.interfaces.auth.dto;

import org.badminton.domain.domain.auth.info.MemberDeleteInfo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 삭제 responseDto")
public record MemberDeleteResponse(
	@Schema(description = "멤버 id", example = "1")
	String memberToken,
	@Schema(description = "삭제 여부", example = "true")
	boolean isDeleted
) {

	public static MemberDeleteResponse fromMemberDeleteInfo(MemberDeleteInfo info) {
		return new MemberDeleteResponse(info.memberToken(), info.isDeleted());
	}

}
