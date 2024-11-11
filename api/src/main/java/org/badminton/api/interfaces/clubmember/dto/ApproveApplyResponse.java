package org.badminton.api.interfaces.clubmember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;

public record ApproveApplyResponse(
        @Schema(description = "동호회 가입 신청 아이디", requiredMode = RequiredMode.REQUIRED)
        Long clubApplyId,
        @Schema(requiredMode = RequiredMode.REQUIRED)
        ClubApply.ApplyStatus status
) {
    public static ApproveApplyResponse fromApproveApplyInfo(ApproveApplyInfo approveApplyInfo) {
        return new ApproveApplyResponse(
                approveApplyInfo.clubApplyId(),
                approveApplyInfo.status()
        );
    }
}