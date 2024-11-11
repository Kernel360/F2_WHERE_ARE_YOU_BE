package org.badminton.api.interfaces.clubmember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;

public record RejectApplyResponse(
        @Schema(description = "동호회 가입 신청 아이디", requiredMode = RequiredMode.REQUIRED)
        Long clubApplyId,
        @Schema(description = "동호회 가입 신청 상태", requiredMode = RequiredMode.REQUIRED)
        ClubApply.ApplyStatus status
) {
    public static RejectApplyResponse fromRejectApplyInfo(RejectApplyInfo rejectApplyInfo) {
        return new RejectApplyResponse(
                rejectApplyInfo.clubApplyId(),
                rejectApplyInfo.status()
        );
    }
}