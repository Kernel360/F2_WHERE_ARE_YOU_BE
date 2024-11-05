package org.badminton.domain.domain.league.command;

import java.time.LocalDateTime;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.vo.Address;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueCreateCommand(
        String leagueName,

        String description,

        String fullAddress,

        Member.MemberTier tierLimit,

        LeagueStatus leagueStatus,

        MatchType matchType,

        LocalDateTime leagueAt,

        LocalDateTime recruitingClosedAt,

        int playerLimitCount,

        MatchGenerationType matchGenerationType,

        Club club,
        String memberToken
) {
    public static LeagueCreateCommand build(LeagueCreateNoIncludeClubCommand originCommand, String memberToken,
                                            Club club) {
        return new LeagueCreateCommand(
                originCommand.leagueName(),
                originCommand.description(),
                originCommand.fullAddress(),
                originCommand.tierLimit(),
                originCommand.leagueStatus(),
                originCommand.matchType(),
                originCommand.leagueAt(),
                originCommand.recruitingClosedAt(),
                originCommand.playerLimitCount(),
                originCommand.matchGenerationType(),
                club,
                memberToken
        );
    }

    public League toEntity() {
        Address address = new Address(this.fullAddress);
        return new League(
                this.memberToken,
                this.leagueName,
                this.description,
                address,
                this.leagueAt,
                this.tierLimit,
                this.recruitingClosedAt,
                this.playerLimitCount,
                this.matchType,
                this.matchGenerationType,
                this.club
        );
    }

}
