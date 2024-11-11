package org.badminton.api.interfaces.clubmember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;

public record ClubApplyResponse(
        @Schema(description = "동호회 가입 신청 아이디", requiredMode = RequiredMode.REQUIRED)
        Long clubApplyId,
        @Schema(description = "동호회 가입 신청 이유", requiredMode = RequiredMode.REQUIRED)
        String applyReason,
        @Schema(description = "동호회 가입 신청 상태", requiredMode = RequiredMode.REQUIRED)
        ClubApply.ApplyStatus status
) {
    public static ClubApplyResponse fromClubApplyInfo(ApplyClubInfo applyClubInfo) {
        return new ClubApplyResponse(
                applyClubInfo.clubApplyId(),
                applyClubInfo.applyReason(),
                applyClubInfo.status()
        );
    }
}