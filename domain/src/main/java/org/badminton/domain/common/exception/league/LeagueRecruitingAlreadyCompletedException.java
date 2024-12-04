package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public class LeagueRecruitingAlreadyCompletedException extends BadmintonException {
	public LeagueRecruitingAlreadyCompletedException(Long leagueId, LeagueStatus leagueStatus, int participantCount,
		int playerLimitCount) {
		super(ErrorCode.LEAGUE_PARTICIPANT_IS_ALREADY_FULL,
			"[경기 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + " 현재까지 모집된 인원 : " + participantCount
				+
				" 모집 인원 : " + playerLimitCount + "]");
	}

	public LeagueRecruitingAlreadyCompletedException(Long leagueId, LeagueStatus leagueStatus, int participantCount,
		int playerLimitCount,
		Exception e) {
		super(ErrorCode.LEAGUE_PARTICIPANT_IS_ALREADY_FULL,
			"[경기 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + " 현재까지 모집된 인원 : " + participantCount
				+
				" 모집 인원 : " + playerLimitCount + "]", e);
	}
}
