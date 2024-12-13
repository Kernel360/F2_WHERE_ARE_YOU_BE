package org.badminton.infrastructure.club;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public record RedisPage<T>(
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
	public RedisPage(Page<T> page) {
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

	public Page<T> toPage(Pageable pageable) {
		return new PageImpl<>(content, pageable, totalElements);
	}
}
