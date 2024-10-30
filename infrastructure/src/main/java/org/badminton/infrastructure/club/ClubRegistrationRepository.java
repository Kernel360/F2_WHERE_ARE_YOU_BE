package org.badminton.infrastructure.club;

import java.util.List;

import org.badminton.domain.domain.club.entity.ClubRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRegistrationRepository extends JpaRepository<ClubRegistration, Long> {

	List<ClubRegistration> findAllByClubClubToken(String clubToken);

	ClubRegistration findByClubClubTokenAndMemberMemberToken(String clubToken, String memberToken);
}
