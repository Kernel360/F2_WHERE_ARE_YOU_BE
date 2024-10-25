package org.badminton.domain.domain.club.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.common.BaseTimeEntity;
import org.badminton.domain.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "club")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ClubEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    private String clubName;

    @Column(columnDefinition = "text")
    private String clubDescription;

    private String clubImage;

    private boolean isClubDeleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<ClubMember> clubMembers = new ArrayList<>();

    public ClubEntity(String clubName, String clubDescription, String clubImage) {
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.clubImage = clubImage;
        isClubDeleted = false;
    }

    public void updateClub(String clubName, String clubDescription, String clubImage) {
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.clubImage = clubImage;
    }

    public Map<Member.MemberTier, Long> getClubMemberCountByTier() {
        List<Member.MemberTier> tierListForInit = Arrays.asList(Member.MemberTier.BRONZE, Member.MemberTier.SILVER, Member.MemberTier.GOLD);
        Map<Member.MemberTier, Long> tierCounts = new LinkedHashMap<>();

        for (Member.MemberTier tier : tierListForInit) {
            tierCounts.put(tier, 0L);
        }

        Map<Member.MemberTier, Long> actualCounts = getActualMemberCounts(this.clubMembers);
        tierCounts.putAll(actualCounts);

        return tierCounts;
    }

    private Map<Member.MemberTier, Long> getActualMemberCounts(List<ClubMember> clubMembers) {
        return clubMembers.stream()
            .filter(this::isValidMember)
            .filter(clubMember -> !clubMember.isDeleted())
            .collect(Collectors.groupingBy(
                this::getMemberTier,
                Collectors.counting()
            ));
    }

    public void doWithdrawal() {
        this.isClubDeleted = true;
    }

    private boolean isValidMember(ClubMember clubMember) {
        Member member = clubMember.getMember();
        return member != null && member.getLeagueRecord() != null;
    }

    private Member.MemberTier getMemberTier(ClubMember clubMember) {
        return clubMember.getMember().getTier();
    }
}