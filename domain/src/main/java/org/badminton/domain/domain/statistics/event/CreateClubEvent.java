package org.badminton.domain.domain.statistics.event;

import org.badminton.domain.domain.club.info.ClubCreateInfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateClubEvent {
	private final ClubCreateInfo clubCreateInfo;
}
