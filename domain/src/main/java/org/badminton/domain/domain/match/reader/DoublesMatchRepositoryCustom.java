package org.badminton.domain.domain.match.reader;

import java.util.List;
import org.badminton.domain.domain.match.entity.DoublesMatch;

public interface DoublesMatchRepositoryCustom {
    List<DoublesMatch> findAllCompletedByClubMemberId(Long clubMemberId);
}
