package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberJoinInfo;

public record ClubMemberJoinResponse(
        Long clubMemberId,
        String role
) {
    public static ClubMemberJoinResponse fromClubMemberJoinInfo(ClubMemberJoinInfo info){
        return new ClubMemberJoinResponse(
            info.clubMemberId(), info.role()
        );

    }
}
