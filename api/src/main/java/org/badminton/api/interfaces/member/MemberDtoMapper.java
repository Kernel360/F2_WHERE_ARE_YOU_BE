package org.badminton.api.interfaces.member;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {

	// @Mapping(source = "leagueRecordInfo", target = "leagueRecordResponse")
	// @Mapping(source = "clubMemberMyPageInfos", target = "clubMemberMyPageResponses")
	// MemberMyPageResponse of(MemberMyPageInfo memberMyPageInfo);

}
