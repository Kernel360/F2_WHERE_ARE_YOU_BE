package org.badminton.infrastructure.clubmember;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.badminton.domain.common.exception.clubmember.ClubMemberAlreadyOwnerException;
import org.badminton.domain.common.exception.clubmember.ClubMemberNotExistException;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubMemberReaderImpl implements ClubMemberReader {

    public static final int NOT_OWNER_CLUB = 0;
    private final ClubMemberRepository clubMemberRepository;

    @Override
    public List<ClubMember> getClubMembersByMemberToken(String memberToken) {
        return clubMemberRepository.findAllByDeletedFalseAndMemberMemberToken(memberToken);
    }

    @Override
    public ClubMember getClubMember(Long clubMemberId) {
        return clubMemberRepository.findByClubMemberId(clubMemberId)
                .orElseThrow(() -> new ClubMemberNotExistException(clubMemberId));
    }

    @Override
    public boolean checkIsClubMember(String memberToken, String clubToken) {
        return clubMemberRepository.existsByClubClubTokenAndMemberMemberToken(clubToken, memberToken);
    }

    @Override
    public void checkIsClubOwner(String memberToken) {
        if (NOT_OWNER_CLUB != clubMemberRepository.countByMemberIdAndRoleOwner(memberToken)) {
            throw new ClubMemberAlreadyOwnerException(memberToken);
        }
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
    public Integer getClubMemberApproveCount(Long clubId) {
        return clubMemberRepository.countByClubClubIdAndDeletedFalse(clubId);
    }

    @Override
    public Integer getClubMemberCounts(Long clubId) {
        return clubMemberRepository.countByClubClubIdAndDeletedFalse(clubId);
    }

    @Override
    public ClubMember getClubOwner(String clubToken) {
        return clubMemberRepository.findByClubClubTokenAndRole(clubToken, ClubMember.ClubMemberRole.ROLE_OWNER);
    }

    @Override
    public List<ClubMember> getAllClubMemberByClubId(String clubToken) {
        return clubMemberRepository.findAllByClubClubTokenAndBannedFalseAndDeletedFalse(clubToken);
    }

}

