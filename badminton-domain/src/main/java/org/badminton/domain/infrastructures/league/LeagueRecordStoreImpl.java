package org.badminton.domain.infrastructures.league;

import org.badminton.domain.domain.league.LeagueRecordStore;
import org.badminton.domain.domain.league.entity.LeagueRecord;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueRecordStoreImpl implements LeagueRecordStore {
	private final LeagueRecordRepository leagueRecordRepository;

	@Override
	public void initScore(Member member) {
		LeagueRecord leagueRecord = new LeagueRecord(member);
		leagueRecordRepository.save(leagueRecord);
	}

	@Override
	public void store(LeagueRecord leagueRecord) {
		leagueRecordRepository.save(leagueRecord);
	}
}
