package org.badminton.domain.domain.clubmember.service;

import java.util.List;

import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;
import org.badminton.domain.domain.clubmember.info.MemberIsClubMemberInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubMemberService {

	void clubMemberOwner(String memberToken, ClubCreateInfo clubInfo);

	ClubMemberInfo updateClubMemberRole(ClubMemberRoleUpdateCommand command, Long clubMemberId, String clubToken);

	List<ClubMemberMyPageInfo> getClubMembers(String memberToken);

	Page<ClubMemberInfo> findAllActiveClubMembers(String clubToken, Pageable pageable);

	Page<ClubMemberInfo> findAllBannedClubMembers(String clubToken, Pageable pageable);

	ClubMemberInfo getClubMember(String memberToken, String clubToken);

	ClubMemberBanRecordInfo expelClubMember(ClubMemberExpelCommand command, Long clubMemberId);

	ClubMemberBanRecordInfo banClubMember(ClubMemberBanCommand command, Long clubMemberId);

	ClubMemberWithdrawInfo withdrawClubMember(Long clubMemberId, String clubToken);

	boolean checkIfMemberBelongsToClub(String memberToken, String clubToken);

	void deleteAllClubMembers(String clubToken);

	Integer countExistingClub(String clubToken);

	Integer countExistingClub(Long clubId);

	List<ClubCardInfo> getClubsByMemberToken(String memberToken);

	MemberIsClubMemberInfo checkIsClubMember(String memberToken, String clubToken);

}


