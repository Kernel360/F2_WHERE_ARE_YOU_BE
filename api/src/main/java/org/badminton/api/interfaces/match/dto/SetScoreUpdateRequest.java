package org.badminton.api.interfaces.match.dto;

public record SetScoreUpdateRequest(
        // TODO: 점수 숫자 검증해야 함. 승부가 난 숫자여야 한다.
        int score1,
        int score2
) {
}
