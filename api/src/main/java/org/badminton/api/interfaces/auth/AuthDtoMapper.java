package org.badminton.api.interfaces.auth;

import org.badminton.api.interfaces.auth.dto.MemberDeleteResponse;
import org.badminton.domain.domain.auth.info.MemberDeleteInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {
	MemberDeleteResponse of(MemberDeleteInfo deleteResponse);
}
