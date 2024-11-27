package org.badminton.domain.domain.clubmember;

import java.util.List;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public interface ClubMemberReader {

	ClubMember getClubMember(Long clubMemberId);

	List<ClubMember> getClubMembersByMemberToken(String memberToken);

	List<ClubMember> getActiveClubMembersByMemberToken(String memberToken);

	boolean existsMemberInClub(String memberToken, String clubToken);

	List<ClubMember> getAllClubMemberByClubId(String clubToken);

	List<ClubMember> getAllMember(String clubToken);

	boolean checkIsClubMember(String memberToken, String clubToken);

	boolean checkIsExpelClubMember(String memberToken, String clubToken);

	ClubMember getClubMember(String clubToken, String memberToken);

	Integer getClubMemberApproveCount(Long clubId);

	Integer getClubMemberCounts(Long clubId);

	Integer getClubMemberCountByClubToken(String clubToken);

	ClubMember getClubOwner(String clubToken);
}
