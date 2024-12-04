package org.badminton.domain.common.exception.league;

import java.time.LocalDateTime;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class InvalidLeagueParticipantCountException extends BadmintonException {
	public InvalidLeagueParticipantCountException(Long leagueId, int playerCount) {
		super(ErrorCode.INVALID_PLAYER_COUNT, "[경기 일정 아이디 : " + leagueId + " 현재 모집 인원 : " + playerCount + "]");
	}

	public InvalidLeagueParticipantCountException(Long leagueId, LocalDateTime closedAt) {
		super(ErrorCode.INVALID_PLAYER_COUNT, "[경기 일정 아이디 : " + leagueId + " 모집 마감 날짜 : " + closedAt + "]");
	}

	public InvalidLeagueParticipantCountException(Long leagueId, Long playerCount, Exception e) {
		super(ErrorCode.INVALID_PLAYER_COUNT, "[경기 일정 아이디 : " + leagueId + " 현재 모집 인원 : " + playerCount + "]", e);
	}

	public InvalidLeagueParticipantCountException(Long leagueId, LocalDateTime closedAt, Exception e) {
		super(ErrorCode.INVALID_PLAYER_COUNT, "[경기 일정 아이디 : " + leagueId + " 모집 마감 날짜 : " + closedAt + "]", e);
	}

}
