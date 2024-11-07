package org.badminton.api.interfaces.clubmember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;

public record ClubMemberWithdrawResponse(
        @Schema(requiredMode = RequiredMode.REQUIRED)
        Long clubId,
        @Schema(requiredMode = RequiredMode.REQUIRED)
        Long clubMemberId,
        @Schema(requiredMode = RequiredMode.REQUIRED)
        boolean isDeleted
) {
    public static ClubMemberWithdrawResponse valueOf(ClubMemberWithdrawInfo info) {
        return new ClubMemberWithdrawResponse(
                info.clubId(),
                info.clubMemberId(),
                info.isDeleted()
        );
    }
}
