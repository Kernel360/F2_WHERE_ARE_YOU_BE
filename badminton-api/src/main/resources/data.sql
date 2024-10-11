-- Member 데이터 삽입 (이미지 URL 교체)
INSERT INTO member (providerId, email, name, authorization, profileImage, isDeleted, lastConnectionAt, createdAt,
                    modifiedAt)
VALUES ('provider_1', 'honggildong@example.com', '홍길동', 'AUTHORIZATION_USER',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/cat.jpg', false, '2024-01-01 10:00:00',
        '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       ('provider_2', 'satang@example.com', '사탕', 'AUTHORIZATION_USER',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/cow.jpg', false, '2024-01-02 10:00:00',
        '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       ('provider_3', 'candyman@example.com', '캔디맨', 'AUTHORIZATION_USER',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/lion.jpg', false, '2024-01-03 10:00:00',
        '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       ('provider_4', 'admin1@example.com', '관리자1', 'AUTHORIZATION_ADMIN',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/pelican.jpg', false, '2024-01-04 10:00:00',
        '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       ('provider_5', 'yulsooa@example.com', '율수아', 'AUTHORIZATION_USER',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/zebra.jpg', false, '2024-01-05 10:00:00',
        '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       ('provider_6', 'cheesecake@example.com', '치즈케이크', 'AUTHORIZATION_USER',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/cat.jpg', true, '2024-01-06 10:00:00',
        '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       ('provider_7', 'jellybean@example.com', '젤리빈', 'AUTHORIZATION_USER',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/cow.jpg', false, '2024-01-07 10:00:00',
        '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       ('provider_8', 'admin2@example.com', '관리자2', 'AUTHORIZATION_ADMIN',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/lion.jpg', false, '2024-01-08 10:00:00',
        '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       ('provider_9', 'honeydew@example.com', '허니듀', 'AUTHORIZATION_USER',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/pelican.jpg', false, '2024-01-09 10:00:00',
        '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       ('provider_10', 'chocobar@example.com', '초코바', 'AUTHORIZATION_USER',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/member/zebra.jpg', false, '2024-01-10 10:00:00',
        '2024-01-10 09:00:00', '2024-01-10 09:00:00');


-- Club 데이터 삽입
INSERT INTO club (clubName, clubDescription, clubImage, isClubDeleted, createdAt, modifiedAt)
VALUES ('Badminton Club 1', 'This is the first badminton club.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false, '2024-01-01 09:00:00',
        '2024-01-01 09:00:00'),
       ('Badminton Club 2', 'This is the second badminton club.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false, '2024-01-02 09:00:00',
        '2024-01-02 09:00:00'),
       ('Badminton Club 3', 'This is the third badminton club.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false, '2024-01-03 09:00:00',
        '2024-01-03 09:00:00'),
       ('Super Smashers', 'A club for power players.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false, '2024-01-04 09:00:00',
        '2024-01-04 09:00:00'),
       ('Shuttle Masters', 'Top-tier badminton players gather here.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false,
        '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       ('Net Warriors', 'A club focused on defense and strategy.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        true, '2024-01-06 09:00:00',
        '2024-01-06 09:00:00'),
       ('Birdie', 'Badminton lovers who play competitively.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false, '2024-01-07 09:00:00',
        '2024-01-07 09:00:00'),
       ('Feather', 'Focusing on technique and skill.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false, '2024-01-08 09:00:00',
        '2024-01-08 09:00:00'),
       ('Smash Kings', 'Club for those who love aggressive play.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false, '2024-01-09 09:00:00',
        '2024-01-09 09:00:00'),
       ('Rally Rulers', 'Endurance and long rallies define this club.',
        'https://badminton-team.s3.ap-northeast-2.amazonaws.com/club-banner/85e45bf0-2f68-4566-b17d-0f08c8b2c333/banner.avif',
        false,
        '2024-01-10 09:00:00', '2024-01-10 09:00:00');

-- ClubMember 데이터 삽입
INSERT INTO club_member (clubId, memberId, role, deleted, banned, createdAt, modifiedAt)
VALUES (1, 1, 'ROLE_OWNER', false, false, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (1, 2, 'ROLE_MANAGER', false, false, '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       (1, 3, 'ROLE_USER', false, false, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       (1, 4, 'ROLE_USER', false, false, '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       (1, 5, 'ROLE_USER', false, false, '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       (1, 6, 'ROLE_USER', false, false, '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       (2, 7, 'ROLE_OWNER', false, false, '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       (2, 8, 'ROLE_USER', false, false, '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       (2, 9, 'ROLE_OWNER', false, false, '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       (2, 10, 'ROLE_MANAGER', false, false, '2024-01-10 09:00:00', '2024-01-10 09:00:00');

-- League 데이터 삽입 (leagueLocation 필드 추가)
INSERT INTO league (leagueName, description, requiredTier, leagueStatus, matchType, leagueAt, closedAt,
                    playerLimitCount,
                    matchGenerationType, clubId, createdAt, modifiedAt, leagueLocation)
VALUES ('League 1', 'First league description', 'GOLD', 'OPEN', 'SINGLES', '2024-01-01 10:00:00', '2024-02-01 10:00:00',
        10, 'RANDOM', 1, '2024-01-01 09:00:00', '2024-01-01 09:00:00', '성동구 서울숲 체육관'),
       ('League 2', 'Second league description', 'SILVER', 'OPEN', 'DOUBLES', '2024-01-02 11:00:00',
        '2024-02-02 11:00:00', 4, 'RANDOM', 1, '2024-01-02 09:00:00', '2024-01-02 09:00:00', '동대문구민 체육센터'),
       ('League 3', 'Third league description', 'BRONZE', 'CLOSED', 'SINGLES', '2024-01-03 12:00:00',
        '2024-02-03 12:00:00', 15, 'RANDOM', 1, '2024-01-03 09:00:00', '2024-01-03 09:00:00', '장충동 체육관'),
       ('League 4', 'Fourth league description', 'GOLD', 'OPEN', 'DOUBLES', '2024-01-04 13:00:00',
        '2024-02-04 13:00:00', 12, 'RANDOM', 1, '2024-01-04 09:00:00', '2024-01-04 09:00:00', '성동구 서울숲 체육관'),
       ('League 5', 'Fifth league description', 'SILVER', 'CLOSED', 'SINGLES', '2024-01-05 14:00:00',
        '2024-02-05 14:00:00', 18, 'RANDOM', 1, '2024-01-05 09:00:00', '2024-01-05 09:00:00', '동대문구민 체육센터'),
       ('League 6', 'Sixth league description', 'BRONZE', 'OPEN', 'DOUBLES', '2024-01-06 15:00:00',
        '2024-02-06 15:00:00', 24, 'RANDOM', 1, '2024-01-06 09:00:00', '2024-01-06 09:00:00', '장충동 체육관'),
       ('League 7', 'Seventh league description', 'GOLD', 'OPEN', 'SINGLES', '2024-01-07 16:00:00',
        '2024-02-07 16:00:00', 8, 'RANDOM', 1, '2024-01-07 09:00:00', '2024-01-07 09:00:00', '성동구 서울숲 체육관'),
       ('League 8', 'Eighth league description', 'SILVER', 'CLOSED', 'DOUBLES', '2024-01-08 17:00:00',
        '2024-02-08 17:00:00', 16, 'RANDOM', 1, '2024-01-08 09:00:00', '2024-01-08 09:00:00', '동대문구민 체육센터'),
       ('League 9', 'Ninth league description', 'BRONZE', 'OPEN', 'SINGLES', '2024-01-09 18:00:00',
        '2024-02-09 18:00:00', 20, 'RANDOM', 1, '2024-01-09 09:00:00', '2024-01-09 09:00:00', '장충동 체육관'),
       ('League 10', 'Tenth league description', 'GOLD', 'CLOSED', 'DOUBLES', '2024-01-10 19:00:00',
        '2024-02-10 19:00:00', 14, 'RANDOM', 2, '2024-01-10 09:00:00', '2024-01-10 09:00:00', '성동구 서울숲 체육관');


-- LeagueParticipant 데이터 삽입
INSERT INTO league_participant (clubMemberId, leagueId, isCanceled, createdAt, modifiedAt, memberId)
VALUES (1, 1, false, '2024-01-01 09:00:00', '2024-01-01 09:00:00', 1),
       (2, 1, false, '2024-01-02 09:00:00', '2024-01-02 09:00:00', 2),
       (3, 1, false, '2024-01-03 09:00:00', '2024-01-03 09:00:00', 3),
       (4, 1, false, '2024-01-04 09:00:00', '2024-01-04 09:00:00', 4),
       (5, 1, false, '2024-01-05 09:00:00', '2024-01-05 09:00:00', 5),
       (6, 1, false, '2024-01-06 09:00:00', '2024-01-06 09:00:00', 6),
       (7, 1, false, '2024-01-07 09:00:00', '2024-01-07 09:00:00', 7),
       (8, 1, false, '2024-01-08 09:00:00', '2024-01-08 09:00:00', 8),
       (9, 1, false, '2024-01-09 09:00:00', '2024-01-09 09:00:00', 9),
       (10, 1, false, '2024-01-10 09:00:00', '2024-01-10 09:00:00', 10),
       (7, 2, false, '2024-01-11 09:00:00', '2024-01-11 09:00:00', 7),
       (8, 2, false, '2024-01-12 09:00:00', '2024-01-12 09:00:00', 8),
       (9, 2, false, '2024-01-13 09:00:00', '2024-01-13 09:00:00', 9),
       (10, 2, false, '2024-01-14 09:00:00', '2024-01-14 09:00:00', 10),
       (5, 3, false, '2024-01-15 09:00:00', '2024-01-15 09:00:00', 5),
       (6, 4, false, '2024-01-16 09:00:00', '2024-01-16 09:00:00', 6),
       (7, 4, false, '2024-01-17 09:00:00', '2024-01-17 09:00:00', 7),
       (8, 5, false, '2024-01-18 09:00:00', '2024-01-18 09:00:00', 8),
       (9, 5, false, '2024-01-19 09:00:00', '2024-01-19 09:00:00', 9),
       (10, 5, false, '2024-01-20 09:00:00', '2024-01-20 09:00:00', 10);

-- LeagueRecord 데이터 삽입 (티어 기준표 반영)
INSERT INTO league_record (winCount, loseCount, drawCount, matchCount, tier, clubMemberId, createdAt, modifiedAt)
VALUES (16, 3, 1, 20, 'GOLD', 1, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (7, 2, 1, 10, 'SILVER', 2, '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       (19, 1, 0, 20, 'GOLD', 3, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       (7, 3, 0, 10, 'SILVER', 4, '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       (4, 5, 1, 10, 'BRONZE', 5, '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       (17, 2, 1, 20, 'GOLD', 6, '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       (3, 6, 1, 10, 'BRONZE', 7, '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       (6, 3, 1, 10, 'SILVER', 8, '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       (7, 2, 1, 10, 'SILVER', 9, '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       (18, 1, 1, 20, 'GOLD', 10, '2024-01-10 09:00:00', '2024-01-10 09:00:00');