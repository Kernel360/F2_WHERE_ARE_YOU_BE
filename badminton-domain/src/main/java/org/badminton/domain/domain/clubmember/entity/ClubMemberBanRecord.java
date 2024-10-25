package org.badminton.domain.domain.clubmember.entity;

import java.time.LocalDateTime;

import org.badminton.domain.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "banned_club_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ClubMemberBanRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubMemberBanRecordId;

    @Enumerated(EnumType.STRING)
    private ClubMember.BannedType bannedType;

    private String bannedReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubMemberId")
    private ClubMember clubMember;

    private boolean isActive;

    private LocalDateTime endDate;


    public ClubMemberBanRecord(ClubMember clubMember, ClubMember.BannedType bannedType, String bannedReason) {
        this.clubMember = clubMember;
        this.bannedType = bannedType;
        this.bannedReason = bannedReason;
        this.endDate = bannedType.isPermanent() ? null : LocalDateTime.now().plusDays(bannedType.getDays());
        this.isActive = true;
    }
}
