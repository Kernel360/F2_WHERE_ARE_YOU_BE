package org.badminton.api.interfaces.match.dto;

import java.util.List;

import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.domain.match.info.SinglesSetInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record SinglesSetResponse(
	@Schema(description = "세트 번호 (1 | 2 | 3)")
	int setNumber,
	@Schema(description = "스코어 1")
	int score1,
	@Schema(description = "스코어 2")
	int score2,
	@Schema(description = "세트 상태")
	SetStatus setStatus
) {

	public static List<SinglesSetResponse> fromSinglesSetInfoList(List<SinglesSetInfo> singlesSetInfoList) {
		if (singlesSetInfoList == null) {
			return null;
		}
		return singlesSetInfoList.stream()
			.map(singlesSetInfo -> new SinglesSetResponse(singlesSetInfo.setNumber(), singlesSetInfo.score1(),
				singlesSetInfo.score2(), singlesSetInfo.setStatus()))
			.toList();
	}
}
