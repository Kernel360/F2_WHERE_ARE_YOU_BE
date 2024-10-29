package org.badminton.domain.domain.clubmember.service;

import java.util.Comparator;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public class ClubMemberRoleComparator implements Comparator<ClubMember.ClubMemberRole> {
    @Override
    public int compare(ClubMember.ClubMemberRole o1, ClubMember.ClubMemberRole o2) {
        if (o1 == ClubMember.ClubMemberRole.ROLE_OWNER) {
            return -1;
        }
        if (o2 == ClubMember.ClubMemberRole.ROLE_OWNER) {
            return 1;
        }
        if (o1 == ClubMember.ClubMemberRole.ROLE_MANAGER) {
            return -1;
        }
        if (o2 == ClubMember.ClubMemberRole.ROLE_MANAGER) {
            return 1;
        }
        return 0;
    }

}
