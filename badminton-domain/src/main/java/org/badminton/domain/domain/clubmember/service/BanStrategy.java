package org.badminton.domain.domain.clubmember.service;

import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.badminton.domain.infrastructures.clubmember.ClubMemberRepository;
import org.springframework.stereotype.Component;

@Component
public class BanStrategy extends AbstractClubMemberBanStrategy {

    public BanStrategy(ClubMemberStore ClubMemberStore) {
        super(ClubMemberStore);
    }

    @Override
    protected ClubMember.BannedType getBannedType(Object request) {
        return ((ClubMemberBanCommand) request).type();
    }

    @Override
    protected String getReason(Object request) {
        return ((ClubMemberBanCommand) request).bannedReason();
    }

    @Override
    protected ClubMemberBanRecord createBanRecord(ClubMember clubMember, ClubMember.BannedType bannedType,
                                                        String reason) {
        return new ClubMemberBanRecord(clubMember, bannedType, reason);
    }

    @Override
    protected void applyExpel(ClubMember clubMember, ClubMember.BannedType bannedType) {
    }
}