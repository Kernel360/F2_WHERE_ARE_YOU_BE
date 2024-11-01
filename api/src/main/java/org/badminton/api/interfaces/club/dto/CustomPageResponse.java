package org.badminton.api.interfaces.club.dto;

import java.util.List;

import org.springframework.data.domain.Page;

public record CustomPageResponse<T>(
	List<T> content,
	int totalPages,
	long totalElements,
	int size,
	int number,
	boolean first,
	boolean last,
	int numberOfElements,
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
