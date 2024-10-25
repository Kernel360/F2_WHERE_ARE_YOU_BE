package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.api.interfaces.clubmember.validation.ReasonValidator;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;

public record ClubMemberExpelRequest(
        @ReasonValidator
        String expelReason
) {
        public ClubMemberExpelCommand of() {
                return new ClubMemberExpelCommand(this.expelReason);
        }
}
