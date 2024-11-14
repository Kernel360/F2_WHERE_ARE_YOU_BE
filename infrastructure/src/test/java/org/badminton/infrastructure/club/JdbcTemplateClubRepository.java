package org.badminton.infrastructure.club;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.badminton.domain.domain.club.entity.Club;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateClubRepository implements ClubRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateClubRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Club> findByClubToken(String clubToken) {
        return Optional.empty();
    }

    public Optional<Club> findByClubTokenAndIsClubDeletedFalse(String clubToken) {
        String query = "SELECT * FROM club WHERE club_token = ? AND is_deleted = FALSE";

        try {
            Club club = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Club.class), clubToken);
            return Optional.ofNullable(club);
        } catch (Exception e) {
            // 데이터가 없는 경우 예외가 발생하므로 Optional.empty() 반환
            return Optional.empty();
        }
    }

    @Override
    public Page<Club> findAllByIsClubDeletedIsFalse(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Club> findAllByClubNameContainingIgnoreCaseAndIsClubDeletedIsFalse(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public List<Club> findTop10ByIsClubDeletedIsFalseOrderByCreatedAtDesc() {
        return List.of();
    }

    @Override
    public Club findByClubId(Long clubId) {
        return null;
    }

    @Override
    public boolean existsByClubNameAndIsClubDeletedFalse(String clubName) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Club> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Club> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Club> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Club getOne(Long aLong) {
        return null;
    }

    @Override
    public Club getById(Long aLong) {
        return null;
    }

    @Override
    public Club getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Club> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Club> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Club> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Club> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Club> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Club> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Club, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Club> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Club> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Club> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Club> findAll() {
        return List.of();
    }

    @Override
    public List<Club> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Club entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Club> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Club> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Club> findAll(Pageable pageable) {
        return null;
    }
}
