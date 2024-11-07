package org.badminton.api.interfaces.clubmember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;

public record ClubMemberBanRecordResponse(
        @Schema(requiredMode = RequiredMode.REQUIRED)
        ClubMember.BannedType bannedType,
        @Schema(description = "동호회 정지 이유", requiredMode = RequiredMode.REQUIRED)
        String bannedReason,
        @Schema(requiredMode = RequiredMode.REQUIRED)
        Long clubMemberId,
        @Schema(description = "동호회 활동 정지 여부", requiredMode = RequiredMode.REQUIRED)
        boolean isActive,
        @Schema(description = "동호회 활동 정지 해제 날짜", requiredMode = RequiredMode.REQUIRED)
        LocalDateTime endDate
) {
    public static ClubMemberBanRecordResponse fromClubMemberInfo(ClubMemberBanRecordInfo info) {
        return new ClubMemberBanRecordResponse(
                info.bannedType(),
                info.bannedReason(),
                info.clubMemberId(),
                info.isActive(),
                info.endDate()
        );
    }
}
