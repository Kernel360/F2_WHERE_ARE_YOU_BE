package org.badminton.infrastructure.club;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.badminton.domain.common.exception.club.ClubNotExistException;
import org.badminton.domain.domain.club.ClubReader;
import org.badminton.domain.domain.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubReaderImpl implements ClubReader {
    private final ClubRepository clubRepository;

    @Override
    public Page<Club> readAllClubs(Pageable pageable) {
        return clubRepository.findAllByIsClubDeletedIsFalse(pageable);
    }

    @Override
    public Page<Club> keywordSearch(String keyword, Pageable pageable) {
        return clubRepository.findAllByClubNameContainingIgnoreCaseAndIsClubDeletedIsFalse(keyword, pageable);
    }

    @Override
    public List<Club> readRecentlyClubs() {
        return clubRepository.findTop10ByIsClubDeletedIsFalseOrderByCreatedAtDesc();
    }

    @Override
    public List<Club> readAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    public Club readClub(String clubToken) {
        return clubRepository.findByClubToken(clubToken).orElseThrow(() -> new ClubNotExistException(clubToken));
    }

    @Override
    public Club readClubByClubId(Long clubId) {
        return clubRepository.findByClubId(clubId);
    }

    @Override
    public Club readExistingClub(String clubToken) {
        return clubRepository.findByClubTokenAndIsClubDeletedFalse(clubToken)
                .orElseThrow(() -> new ClubNotExistException(clubToken));
    }

    @Override
    public boolean UniqueClubName(String clubName) {
        return clubRepository.existsByClubNameAndIsClubDeletedFalse(clubName);
    }
}
