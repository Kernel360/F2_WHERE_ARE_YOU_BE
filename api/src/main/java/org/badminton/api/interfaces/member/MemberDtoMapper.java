package org.badminton.api.interfaces.member;

import org.badminton.api.interfaces.member.dto.MemberIsClubMemberResponse;
import org.badminton.api.interfaces.member.dto.MemberMyPageResponse;
import org.badminton.domain.domain.member.info.MemberIsClubMemberInfo;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {

	@Mapping(source = "leagueRecordInfo", target = "leagueRecordResponse")
	@Mapping(source = "clubMemberMyPageInfo", target = "clubMemberMyPageResponse")
	MemberMyPageResponse of(MemberMyPageInfo memberMyPageInfo);

	MemberIsClubMemberResponse of(MemberIsClubMemberInfo memberIsClubMemberInfo);

}
