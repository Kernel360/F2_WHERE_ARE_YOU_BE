package org.badminton.domain.domain.league.service;

import org.badminton.domain.domain.league.LeagueRecordReader;
import org.badminton.domain.domain.league.LeagueRecordStore;
import org.badminton.domain.domain.league.entity.LeagueRecord;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueRecordServiceImpl implements LeagueRecordService {

	private static final int GOLD_TIER_MIN_MATCHES = 20;
	private static final int GOLD_TIER_MIN_WIN_RATE = 80;
	private static final int SILVER_TIER_MIN_MATCHES = 10;
	private static final int SILVER_TIER_MIN_WIN_RATE = 60;

	private final LeagueRecordStore leagueRecordStore;
	private final LeagueRecordReader leagueRecordReader;
	private final MemberReader memberReader;

	@Override
	public void initScore(Member member) {
		leagueRecordStore.initScore(member);

	}

	@Override
	public void addWin(String memberToken) {
		LeagueRecord record = getLeagueRecord(memberToken);
		record.updateWinCount(record.getWinCount() + 1);
		record.updateMatchCount(record.getMatchCount() + 1);
		updateTier(memberToken);
		leagueRecordStore.store(record);
	}

	@Override
	public void addLoss(String memberToken) {
		LeagueRecord record = getLeagueRecord(memberToken);
		record.updateLoseCount(record.getLoseCount() + 1);
		record.updateMatchCount(record.getMatchCount() + 1);
		updateTier(memberToken);
		leagueRecordStore.store(record);
	}

	@Override
	public void addDraw(String memberToken) {
		LeagueRecord record = getLeagueRecord(memberToken);
		record.updateDrawCount(record.getDrawCount() + 1);
		record.updateMatchCount(record.getMatchCount() + 1);
		updateTier(memberToken);
		leagueRecordStore.store(record);
	}

	@Override
	@Transactional(readOnly = true)
	public LeagueRecord getLeagueRecord(String memberToken) {
		return leagueRecordReader.getLeagueRecord(memberToken);
	}

	private double getWinRate(String memberToken) {
		LeagueRecord record = getLeagueRecord(memberToken);
		if (record.getMatchCount() == 0) {
			return 0.0;
		}
		return (double)record.getWinCount() / record.getMatchCount() * 100;
	}

	private void updateTier(String memberToken) {
		Member member = memberReader.getMember(memberToken);
		double winRate = getWinRate(memberToken);
		LeagueRecord record = getLeagueRecord(memberToken);
		int matchCount = record.getMatchCount();

		if (matchCount >= GOLD_TIER_MIN_MATCHES && winRate >= GOLD_TIER_MIN_WIN_RATE) {
			member.updateTier(Member.MemberTier.GOLD);
		} else if (matchCount >= SILVER_TIER_MIN_MATCHES && winRate >= SILVER_TIER_MIN_WIN_RATE) {
			member.updateTier(Member.MemberTier.SILVER);
		} else {
			member.updateTier(Member.MemberTier.BRONZE);
		}
	}
}


