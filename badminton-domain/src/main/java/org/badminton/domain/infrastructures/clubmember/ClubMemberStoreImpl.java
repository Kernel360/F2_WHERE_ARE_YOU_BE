package org.badminton.domain.infrastructures.clubmember;

import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubMemberStoreImpl implements ClubMemberStore {
	private final ClubMemberRepository clubMemberRepository;

	@Override
	public void store(ClubMember member) {
		clubMemberRepository.save(member);
	}

	@Override
	public ClubMember createClubMember(ClubCreateInfo clubCreateInfo, Member member, ClubMember.ClubMemberRole role) {
		var club = new Club(clubCreateInfo);
		var clubMember = new ClubMember(club, member, role);
		return clubMemberRepository.save(clubMember);
	}
}
