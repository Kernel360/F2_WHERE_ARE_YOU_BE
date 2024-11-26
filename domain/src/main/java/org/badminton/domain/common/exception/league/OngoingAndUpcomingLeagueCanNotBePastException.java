package org.badminton.domain.common.exception.league;

import java.time.LocalDate;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class OngoingAndUpcomingLeagueCanNotBePastException extends BadmintonException {
	public OngoingAndUpcomingLeagueCanNotBePastException(LocalDate requiredDate, LocalDate todayDate) {
		super(ErrorCode.ONGOING_AND_UPCOMING_LEAGUE_CANNOT_BE_PAST,
			"[사용자가 입력한 날짜 : " + requiredDate + " 오늘 날짜 : " + todayDate + "]");
	}

	public OngoingAndUpcomingLeagueCanNotBePastException(LocalDate requiredDate, LocalDate todayDate, Exception e) {
		super(ErrorCode.ONGOING_AND_UPCOMING_LEAGUE_CANNOT_BE_PAST,
			"[사용자가 입력한 날짜 : " + requiredDate + " 오늘 날짜 : " + todayDate + "]", e);
	}
}
