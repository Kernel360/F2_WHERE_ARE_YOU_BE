package org.badminton.api.interfaces.club.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.badminton.domain.common.consts.Constants;
import org.badminton.domain.domain.club.command.ClubApplyCommand;

public record ClubApplyRequest(
        @NotNull
        @Size(miㄴn = Constants.APPLY_MIN, max = Constants.APPLY_MAX)
        String applyReason
) {
    public ClubApplyCommand of() {
        return new ClubApplyCommand(this.applyReason);
    }
}
