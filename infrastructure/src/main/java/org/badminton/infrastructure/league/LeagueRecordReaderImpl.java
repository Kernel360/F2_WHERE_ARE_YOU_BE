package org.badminton.infrastructure.league;

import org.badminton.domain.domain.league.LeagueRecordReader;
import org.badminton.domain.domain.league.entity.LeagueRecord;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueRecordReaderImpl implements LeagueRecordReader {
	private final LeagueRecordRepository leagueRecordRepository;

	@Override
	public LeagueRecord getLeagueRecord(String memberToken) {
		return leagueRecordRepository.findByMemberMemberToken(memberToken).orElse(null);
	}
}
