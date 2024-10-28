package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.TeamPlayerNameInfo;

public record TeamResultResponse(
        String participant1Name,
        String participant2Name
) {
    public static TeamResultResponse from(TeamPlayerNameInfo teamPlayerNameInfo) {
        return new TeamResultResponse(
                teamPlayerNameInfo.participant1Name(),
                teamPlayerNameInfo.participant2Name()
        );
    }

}