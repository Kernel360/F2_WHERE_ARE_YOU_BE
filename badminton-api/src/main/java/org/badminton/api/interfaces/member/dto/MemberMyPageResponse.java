package org.badminton.api.interfaces.member.dto;

import org.badminton.api.interfaces.clubmember.dto.ClubMemberMyPageResponse;
import org.badminton.api.interfaces.league.dto.LeagueRecordInfoResponse;
import org.badminton.domain.domain.member.entity.MemberEntity;
import org.badminton.domain.domain.clubmember.entity.ClubMemberEntity;
import org.badminton.domain.domain.league.entity.LeagueRecordEntity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Unified member response")
public record MemberMyPageResponse(
        @Schema(description = "Member ID", example = "1")
        Long memberId,

        @Schema(description = "Member name", example = "김철수")
        String name,

        @Schema(description = "Email", example = "example@email.com")
        String email,

        @Schema(description = "Profile image URL", example = "https://example.com/profile.jpg")
        String profileImage,

        @Schema(description = "ClubMember information")
        ClubMemberMyPageResponse clubMemberMyPageResponse,

        @Schema(description = "League record information")
        LeagueRecordInfoResponse leagueRecordInfo
) {
    public static MemberMyPageResponse fromMemberEntityAndClubMemberEntity(MemberEntity memberEntity,
                                                                           ClubMemberEntity clubMemberEntity,
                                                                           LeagueRecordEntity leagueRecordEntity) {
        return new MemberMyPageResponse(
                memberEntity.getMemberId(),
                memberEntity.getName(),
                memberEntity.getEmail(),
                memberEntity.getProfileImage(),
                ClubMemberMyPageResponse.fromClubMemberEntity(clubMemberEntity),
                LeagueRecordInfoResponse.entityToLeagueRecordInfoResponse(leagueRecordEntity)
        );
    }

    public static MemberMyPageResponse fromMemberEntity(MemberEntity memberEntity) {
        return new MemberMyPageResponse(
                memberEntity.getMemberId(),
                memberEntity.getName(),
                memberEntity.getEmail(),
                memberEntity.getProfileImage(),
                null,
                null
        );
    }
}