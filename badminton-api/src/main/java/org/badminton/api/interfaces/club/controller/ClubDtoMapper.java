package org.badminton.api.interfaces.club.controller;

import java.util.List;

import org.badminton.api.interfaces.club.dto.ClubCardResponse;
import org.badminton.api.interfaces.club.dto.ClubCreateRequest;
import org.badminton.api.interfaces.club.dto.ClubCreateResponse;
import org.badminton.api.interfaces.club.dto.ClubDeleteResponse;
import org.badminton.api.interfaces.club.dto.ClubDetailsResponse;
import org.badminton.api.interfaces.club.dto.ClubUpdateRequest;
import org.badminton.api.interfaces.club.dto.ClubUpdateResponse;
import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.info.*;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ClubDtoMapper {

	List<ClubCardResponse> of(List<ClubCardInfo> clubCardInfoList);

	default Page<ClubCardResponse> of(Page<ClubCardInfo> clubCardInfoPage) {
		List<ClubCardResponse> clubCardResponses = of(clubCardInfoPage.getContent());
		return new PageImpl<>(clubCardResponses, clubCardInfoPage.getPageable(), clubCardInfoPage.getTotalElements());
	}

	ClubDetailsResponse of(ClubDetailsInfo clubDetailsInfo);

	ClubCreateCommand of(ClubCreateRequest clubCreateRequest);

	ClubUpdateCommand of(ClubUpdateRequest clubUpdateRequest);

	ClubUpdateResponse of(ClubUpdateInfo clubUpdateInfo);

	ClubDeleteResponse of(ClubDeleteInfo club);

    ClubCreateResponse of(ClubCreateInfo created);
}
