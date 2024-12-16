package org.badminton.domain.domain.match.info;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.member.entity.Member;

public record SinglesMatchInfo(
	Long matchId,
	int roundNumber,
	boolean isParticipant1Banned,
	String participant1MemberToken,
	String participant1Name,
	String participant1Image,
	int participant1WinSetCount,
	boolean isParticipant2Banned,
	String participant2MemberToken,
	String participant2Name,
	String participant2Image,
	int participant2WinSetCount,
	MatchStatus matchStatus,
	String winnerToken,
	Member.MemberTier participant1Tier,
	Member.MemberTier participant2Tier
) {

	public static SinglesMatchInfo fromSinglesMatch(SinglesMatch singlesMatch) {
		return new SinglesMatchInfo(
			singlesMatch.getId(),
			singlesMatch.getRoundNumber(),
			isParticipantBanned(singlesMatch.getLeagueParticipant1()),
			getParticipantMemberToken(singlesMatch.getLeagueParticipant1()),
			getParticipantName(singlesMatch.getLeagueParticipant1()),
			getParticipantImage(singlesMatch.getLeagueParticipant1()),
			singlesMatch.getPlayer1WinSetCount(),
			isParticipantBanned(singlesMatch.getLeagueParticipant2()),
			getParticipantMemberToken(singlesMatch.getLeagueParticipant2()),
			getParticipantName(singlesMatch.getLeagueParticipant2()),
			getParticipantImage(singlesMatch.getLeagueParticipant2()),
			singlesMatch.getPlayer2WinSetCount(),
			singlesMatch.getMatchStatus(),
			getWinnerToken(singlesMatch),
			getParticipantMemberTier(singlesMatch.getLeagueParticipant1()),
			getParticipantMemberTier(singlesMatch.getLeagueParticipant2())
		);
	}

	private static boolean isParticipantBanned(LeagueParticipant participant) {
		return (participant != null && participant.getMember() != null)
			? participant.getClubMember().isBanned()
			: false;
	}

	private static Member.MemberTier getParticipantMemberTier(LeagueParticipant participant) {
		return (participant != null && participant.getMember() != null)
			? participant.getMember().getTier()
			: null;
	}

	private static String getWinnerToken(SinglesMatch singlesMatch) {
		if (singlesMatch.getPlayer1MatchResult() == MatchResult.WIN) {
			return singlesMatch.getLeagueParticipant1().getMember().getMemberToken();
		} else if (singlesMatch.getPlayer2MatchResult() == MatchResult.WIN) {
			return singlesMatch.getLeagueParticipant2().getMember().getMemberToken();
		}
		return null;
	}

	private static String getParticipantName(LeagueParticipant participant) {
		return (participant != null && participant.getMember() != null)
			? participant.getMember().getName()
			: null;
	}

	private static String getParticipantMemberToken(LeagueParticipant participant) {
		return (participant != null && participant.getMember() != null)
			? participant.getMember().getMemberToken()
			: null;
	}

	private static String getParticipantImage(LeagueParticipant participant) {
		return (participant != null && participant.getMember() != null)
			? participant.getMember().getProfileImage()
			: null;
	}
}
