package org.badminton.infrastructure.clubmember;

import java.util.List;
import java.util.Objects;

import org.badminton.domain.common.exception.clubmember.ClubMemberAlreadyOwnerException;
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

	public static final int NOT_OWNER_CLUB = 0;
	private final ClubMemberRepository clubMemberRepository;

	@Override
	public ClubMember getClubMemberByMemberToken(String memberToken) {
		return clubMemberRepository.findByDeletedFalseAndMemberMemberToken(memberToken).orElse(null);
	}

	@Override
	public ClubMember getClubMember(Long clubMemberId) {
		return clubMemberRepository.findByClubMemberId(clubMemberId)
			.orElseThrow(() -> new ClubMemberNotExistException(clubMemberId));
	}

	@Override
	public boolean checkIsClubMember(String memberToken, String clubToken) {
		return clubMemberRepository.findByClubClubTokenAndMemberMemberToken(clubToken, memberToken)
			.map(clubMember -> Objects.equals(clubMember.getClub().getClubId(), clubToken))
			.orElse(false);
	}

	@Override
	public void checkIsClubOwner(String memberToken) {
		if (NOT_OWNER_CLUB != clubMemberRepository.countByMemberIdAndRoleOwner(memberToken)) {
			throw new ClubMemberAlreadyOwnerException(memberToken);
		}
	}

	@Override
	public List<ClubMember> getClubMembersByMemberToken(String memberToken) {
		return clubMemberRepository.findAllByMemberMemberToken(memberToken);

	}

	@Override
	public boolean isExist(String memberToken) {
		return clubMemberRepository.existsByMember_MemberTokenAndDeletedFalse(memberToken);
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
	public List<ClubMember> getAllClubMemberByClubId(String clubToken) {
		return clubMemberRepository.findAllByClubClubTokenAndBannedFalseAndDeletedFalse(clubToken);
	}

}

