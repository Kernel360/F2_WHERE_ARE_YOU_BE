package org.badminton.infrastructure.clubmember;

import java.util.List;

import org.badminton.domain.common.exception.clubmember.ClubMemberNotExistException;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubMemberReaderImpl implements ClubMemberReader {

	private final ClubMemberRepository clubMemberRepository;

	@Override
	public List<ClubMember> getClubMembersByMemberToken(String memberToken) {
		return clubMemberRepository.findAllByDeletedFalseAndMemberMemberToken(memberToken);
	}

	@Override
	public List<ClubMember> getActiveClubMembersByMemberToken(String memberToken) {
		return clubMemberRepository.findAllByDeletedFalseAndBannedFalseAndMemberMemberToken(memberToken);

	}

	@Override
	public ClubMember getClubMember(Long clubMemberId) {
		return clubMemberRepository.findByClubMemberId(clubMemberId)
			.orElseThrow(() -> new ClubMemberNotExistException(clubMemberId));
	}

	@Override
	public boolean checkIsClubMember(String memberToken, String clubToken) {
		return clubMemberRepository.existsByClubClubTokenAndMemberMemberTokenAndDeletedFalse(clubToken, memberToken);
	}

	@Override
	public boolean existsMemberInClub(String memberToken, String clubToken) {
		return clubMemberRepository.existsByMemberMemberTokenAndClubClubToken(memberToken, clubToken);
	}

	public List<ClubMember> getAllMember(String clubToken) {
		return clubMemberRepository.findAllByClubClubToken(clubToken);
	}

	@Override
	public ClubMember getClubMemberByMemberTokenAndClubToken(String clubToken, String memberToken) {
		return clubMemberRepository.findByClubClubTokenAndMemberMemberToken(clubToken, memberToken)
			.orElseThrow(() -> new ClubMemberNotExistException(clubToken, memberToken));
	}

	@Override
	public Integer getClubMemberCounts(Long clubId) {
		return clubMemberRepository.countByClubClubIdAndDeletedFalse(clubId);
	}

	@Override
	public Integer getClubMemberCountByClubToken(String clubToken) {
		return clubMemberRepository.countByClubClubTokenAndDeletedFalse(clubToken);
	}

	@Override
	public ClubMember getClubOwner(String clubToken) {
		return clubMemberRepository.findByClubClubTokenAndRole(clubToken, ClubMember.ClubMemberRole.ROLE_OWNER);
	}

	@Override
	public List<ClubMember> getAllClubMemberByClubId(String clubToken) {
		return clubMemberRepository.findAllByClubClubTokenAndDeletedFalse(clubToken);
	}

	@Override
	public Integer getClubMemberApproveCount(Long clubId) {
		return clubMemberRepository.countByClubClubIdAndDeletedFalse(clubId);
	}

}

