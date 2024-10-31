package org.badminton.domain.domain.clubmember.service;

import java.util.List;
import java.util.Map;

import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberDetailInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;

public interface ClubMemberService {

	// ClubMemberJoinInfo applyClub(String memberToken, String clubToken);

	void clubMemberOwner(String memberToken, ClubCreateInfo clubInfo);

	ClubMemberInfo updateClubMemberRole(ClubMemberRoleUpdateCommand command, Long clubMemberId);

	Map<ClubMember.ClubMemberRole, List<ClubMemberInfo>> findAllClubMembers(String clubToken);

	List<ClubMemberMyPageInfo> getClubMembers(String memberToken);

	ClubMemberInfo getClubMember(String memberToken, String clubToken);

	ClubMemberDetailInfo getClubMemberDetail(String memberToken, String clubToken);

	ClubMemberBanRecordInfo expelClubMember(ClubMemberExpelCommand command, Long clubMemberId);

	ClubMemberBanRecordInfo banClubMember(ClubMemberBanCommand command, Long clubMemberId);

	ClubMemberWithdrawInfo withDrawClubMember(Long clubMemberId);

	boolean checkIfMemberBelongsToClub(String memberToken, String clubToken);

	void checkMyOwnClub(String memberToken);

	void deleteAllClubMembers(String clubToken);

	ClubMemberDetailInfo getClubMemberDetailByClubToken(String clubToken, String memberToken);

	Integer getClubMemberApproveCount(Long clubId);
}


