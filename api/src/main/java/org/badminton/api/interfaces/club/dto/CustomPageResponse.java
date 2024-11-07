package org.badminton.api.interfaces.club.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomPageResponse<T>(
	@Schema(description = "실제 내용", requiredMode = Schema.RequiredMode.REQUIRED)
	List<T> content,
	@Schema(description = "페이징 설정에 따라 나누어진 총 페이지 수", requiredMode = Schema.RequiredMode.REQUIRED)
	int totalPages,
	@Schema(description = "페이징된 전체 데이터의 개수", requiredMode = Schema.RequiredMode.REQUIRED)
	long totalElements,
	@Schema(description = "한 페이지에 포함되는 데이터의 개수", requiredMode = Schema.RequiredMode.REQUIRED)
	int size,
	@Schema(description = "현재 페이지의 번호", requiredMode = Schema.RequiredMode.REQUIRED)
	int number,
	@Schema(description = "현재 페이지가 첫 번째 페이지인지 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	boolean first,
	@Schema(description = "현재 페이지가 마지막 페이지인지 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	boolean last,
	@Schema(description = "현재 페이지에 포함된 데이터의 개수", requiredMode = Schema.RequiredMode.REQUIRED)
	int numberOfElements,
	@Schema(description = "현재 페이지가 비어 있는지 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	boolean empty
) {
	public CustomPageResponse(Page<T> page) {
		this(
			page.getContent(),
			page.getTotalPages(),
			page.getTotalElements(),
			page.getSize(),
			page.getNumber(),
			page.isFirst(),
			page.isLast(),
			page.getNumberOfElements(),
			page.isEmpty()
		);
	}
}
