package org.badminton.api.interfaces.clubmember;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.badminton.api.interfaces.clubmember.dto.ApproveApplyResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubApplyResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberBanRecordResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberJoinResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberWithdrawResponse;
import org.badminton.api.interfaces.clubmember.dto.RejectApplyResponse;
import org.badminton.domain.domain.clubmember.command.ClubMemberStatusCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberJoinInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ClubMemberDtoMapper {
	ClubMemberJoinResponse of(ClubMemberJoinInfo clubMemberJoinInfo);

	ClubMemberResponse of(ClubMemberInfo clubMemberInfo);

	ClubMemberBanRecordResponse of(ClubMemberBanRecordInfo clubMemberBanRecordInfo);

	ClubMemberWithdrawResponse of(ClubMemberWithdrawInfo clubMemberWithdrawInfo);

	default Map<ClubMember.ClubMemberRole, List<ClubMemberResponse>> of(
		Map<ClubMember.ClubMemberRole, List<ClubMemberInfo>> infoMap
	) {
		return infoMap.entrySet().stream()
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> entry.getValue().stream()
					.map(this::of)
					.collect(Collectors.toList())
			));
	}

	ClubMemberStatusCommand of(String clubToken, Long clubMemberId);

	ClubApplyResponse of(ApplyClubInfo applyClubInfo);

	ApproveApplyResponse of(ApproveApplyInfo approveApplyInfo);

	RejectApplyResponse of(RejectApplyInfo rejectApplyInfo);

}
