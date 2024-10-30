package org.badminton.domain.domain.clubmember;

import java.util.List;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public interface ClubMemberReader {

	ClubMember getClubMemberByMemberToken(String memberToken);

	ClubMember getClubMember(Long clubMemberId);

	List<ClubMember> getClubMembersByMemberToken(String memberToken);

	boolean isExist(String memberToken);

	boolean existsMemberInClub(String memberToken, String clubToken);

	List<ClubMember> getAllClubMemberByClubId(String clubToken);

	boolean checkIsClubMember(String memberToken, String clubToken);

	void checkIsClubOwner(String memberToken);

	List<ClubMember> getAllMember(String clubToken);

	ClubMember getClubMemberByMemberTokenAndClubToken(String clubToken, String memberToken);

	Integer getClubMemberApproveCount(Long clubId);
}
