package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.member.entity.Member;

public class TierLimitException extends BadmintonException {

	public TierLimitException(Member.MemberTier limitTier) {
		super(ErrorCode.LEAGUE_LIMIT_TIER, "[기준 티어 : " + limitTier + "]");
	}

	public TierLimitException(Member.MemberTier limitTier, Exception e) {
		super(ErrorCode.LEAGUE_LIMIT_TIER, "[기준 티어 : " + limitTier + "]", e);
	}
}
