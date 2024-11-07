package org.badminton.api.interfaces.clubmember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.badminton.domain.domain.clubmember.info.ClubMemberJoinInfo;

public record ClubMemberJoinResponse(
        @Schema(requiredMode = RequiredMode.REQUIRED)
        Long clubMemberId,
        @Schema(requiredMode = RequiredMode.REQUIRED)
        String role
) {
    public static ClubMemberJoinResponse fromClubMemberJoinInfo(ClubMemberJoinInfo info) {
        return new ClubMemberJoinResponse(
                info.clubMemberId(), info.role()
        );

    }
}
