package org.badminton.api.interfaces.clubmember.dto;

import java.time.LocalDateTime;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;

public record ClubMemberBanRecordResponse(
        ClubMember.BannedType bannedType,
        String bannedReason,
        Long clubMemberId,
        boolean isActive,
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
