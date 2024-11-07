package org.badminton.domain.domain.match.service;

import java.util.List;

import org.badminton.domain.domain.match.info.MatchResultInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MatchResultService {

	List<MatchResultInfo> getAllMatchResultsByClubMember(Long clubMemberId);
}
