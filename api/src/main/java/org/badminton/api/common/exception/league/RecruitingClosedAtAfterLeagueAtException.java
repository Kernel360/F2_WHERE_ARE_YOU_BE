package org.badminton.api.common.exception.league;

import java.time.LocalDateTime;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class RecruitingClosedAtAfterLeagueAtException extends BadmintonException {
	public RecruitingClosedAtAfterLeagueAtException(LocalDateTime recruitingClosedAt, LocalDateTime leagueAt) {
		super(ErrorCode.RECRUITMENT_END_DATE_AFTER_LEAGUE_START,
			"[모집 마감 날짜 : " + recruitingClosedAt + " 경기 시작 날짜 : " + leagueAt + "]");
	}

	public RecruitingClosedAtAfterLeagueAtException(LocalDateTime recruitingClosedAt, LocalDateTime leagueAt,
		Exception e) {
		super(ErrorCode.RECRUITMENT_END_DATE_AFTER_LEAGUE_START,
			"[모집 마감 날짜 : " + recruitingClosedAt + " 경기 시작 날짜 : " + leagueAt + "]", e);

	}
}
