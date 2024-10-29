package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.api.interfaces.clubmember.validation.ReasonValidator;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberBanRequest(

        ClubMember.BannedType type,

        @ReasonValidator
        String bannedReason
) {
        public ClubMemberBanCommand of() {
                return new ClubMemberBanCommand(this.type, this.bannedReason);
        }
}
