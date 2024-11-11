package org.badminton.api.interfaces.member;

import java.util.List;

import org.badminton.api.interfaces.club.dto.ClubCardResponse;
import org.badminton.api.interfaces.member.dto.MemberUpdateResponse;
import org.badminton.api.interfaces.member.dto.SimpleMemberResponse;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;
import org.badminton.domain.domain.member.info.SimpleMemberInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {

	SimpleMemberResponse of(SimpleMemberInfo simpleMemberInfo);

	MemberUpdateResponse of(MemberUpdateInfo memberUpdateInfo);

	List<ClubCardResponse> of(List<ClubCardInfo> clubCardInfos);

}
