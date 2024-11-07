package org.badminton.api.interfaces.clubmember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;

public record ClubMemberResponse(
        @Schema(requiredMode = RequiredMode.REQUIRED)
        Long clubMemberId,
        @Schema(requiredMode = RequiredMode.REQUIRED)
        String image,
        @Schema(requiredMode = RequiredMode.REQUIRED)
        String name,
        @Schema(requiredMode = RequiredMode.REQUIRED)
        ClubMember.ClubMemberRole role
) {

    public static ClubMemberResponse entityToClubMemberResponse(ClubMemberInfo clubMemberInfo) {
        return new ClubMemberResponse(
                clubMemberInfo.clubMemberId(),
                clubMemberInfo.image(),
                clubMemberInfo.name(),
                clubMemberInfo.role()
        );
    }
}
