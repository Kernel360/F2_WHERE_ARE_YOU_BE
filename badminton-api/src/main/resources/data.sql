-- Member 데이터 삽입
INSERT INTO member (providerId, email, name, authorization, profileImage, isDeleted, lastConnectionAt, createdAt,
                    modifiedAt)
VALUES ('provider_1', 'honggildong@example.com', '홍길동', 'AUTHORIZATION_USER', 'profile1.png', false,
        '2024-01-01 10:00:00', '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       ('provider_2', 'satang@example.com', '사탕', 'AUTHORIZATION_USER', 'profile2.png', false, '2024-01-02 10:00:00',
        '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       ('provider_3', 'candyman@example.com', '캔디맨', 'AUTHORIZATION_USER', 'profile3.png', false, '2024-01-03 10:00:00',
        '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       ('provider_4', 'admin1@example.com', '관리자1', 'AUTHORIZATION_ADMIN', 'profile4.png', false, '2024-01-04 10:00:00',
        '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       ('provider_5', 'yulsooa@example.com', '율수아', 'AUTHORIZATION_USER', 'profile5.png', false, '2024-01-05 10:00:00',
        '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       ('provider_6', 'cheesecake@example.com', '치즈케이크', 'AUTHORIZATION_USER', 'profile6.png', true,
        '2024-01-06 10:00:00', '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       ('provider_7', 'jellybean@example.com', '젤리빈', 'AUTHORIZATION_USER', 'profile7.png', false,
        '2024-01-07 10:00:00', '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       ('provider_8', 'admin2@example.com', '관리자2', 'AUTHORIZATION_ADMIN', 'profile8.png', false, '2024-01-08 10:00:00',
        '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       ('provider_9', 'honeydew@example.com', '허니듀', 'AUTHORIZATION_USER', 'profile9.png', false, '2024-01-09 10:00:00',
        '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       ('provider_10', 'chocobar@example.com', '초코바', 'AUTHORIZATION_USER', 'profile10.png', false,
        '2024-01-10 10:00:00', '2024-01-10 09:00:00', '2024-01-10 09:00:00');

-- Club 데이터 삽입
INSERT INTO club (clubName, clubDescription, clubImage, isClubDeleted, createdAt, modifiedAt)
VALUES ('Badminton Club 1', 'This is the first badminton club.', 'club_image1.png', false, '2024-01-01 09:00:00',
        '2024-01-01 09:00:00'),
       ('Badminton Club 2', 'This is the second badminton club.', 'club_image2.png', false, '2024-01-02 09:00:00',
        '2024-01-02 09:00:00'),
       ('Badminton Club 3', 'This is the third badminton club.', 'club_image3.png', false, '2024-01-03 09:00:00',
        '2024-01-03 09:00:00'),
       ('Super Smashers', 'A club for power players.', 'super_smashers.png', false, '2024-01-04 09:00:00',
        '2024-01-04 09:00:00'),
       ('Shuttle Masters', 'Top-tier badminton players gather here.', 'shuttle_masters.png', false,
        '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       ('Net Warriors', 'A club focused on defense and strategy.', 'net_warriors.png', true, '2024-01-06 09:00:00',
        '2024-01-06 09:00:00'),
       ('Birdie', 'Badminton lovers who play competitively.', 'birdie_bashers.png', false, '2024-01-07 09:00:00',
        '2024-01-07 09:00:00'),
       ('Feather', 'Focusing on technique and skill.', 'feather_finessers.png', false, '2024-01-08 09:00:00',
        '2024-01-08 09:00:00'),
       ('Smash Kings', 'Club for those who love aggressive play.', 'smash_kings.png', false, '2024-01-09 09:00:00',
        '2024-01-09 09:00:00'),
       ('Rally Rulers', 'Endurance and long rallies define this club.', 'rally_rulers.png', false,
        '2024-01-10 09:00:00', '2024-01-10 09:00:00');

-- ClubMember 데이터 삽입
INSERT INTO club_member (clubId, memberId, role, isDeleted, createdAt, modifiedAt)
VALUES (1, 1, 'ROLE_OWNER', false, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (1, 2, 'ROLE_MANAGER', false, '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       (1, 3, 'ROLE_USER', false, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       (1, 4, 'ROLE_USER', false, '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       (1, 5, 'ROLE_USER', false, '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       (1, 6, 'ROLE_USER', false, '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       (2, 7, 'ROLE_OWNER', false, '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       (2, 8, 'ROLE_USER', false, '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       (2, 9, 'ROLE_OWNER', false, '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       (2, 10, 'ROLE_MANAGER', false, '2024-01-10 09:00:00', '2024-01-10 09:00:00');

-- League 데이터 삽입
INSERT INTO league (leagueName, description, tierLimit, leagueStatus, matchType, leagueAt, closedAt, playerCount,
                    matchingRequirement, clubId, createdAt, modifiedAt)
VALUES ('League 1', 'First league description', 'GOLD', 'OPEN', 'SINGLES', '2024-01-01 10:00:00', '2024-02-01 10:00:00',
        10, 'random', 1, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       ('League 2', 'Second league description', 'SILVER', 'OPEN', 'DOUBLES', '2024-01-02 11:00:00',
        '2024-02-02 11:00:00', 4, 'random', 1, '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       ('League 3', 'Third league description', 'BRONZE', 'CLOSED', 'SINGLES', '2024-01-03 12:00:00',
        '2024-02-03 12:00:00', 15, 'random', 1, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       ('League 4', 'Fourth league description', 'GOLD', 'OPEN', 'DOUBLES', '2024-01-04 13:00:00',
        '2024-02-04 13:00:00', 12, 'random', 1, '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       ('League 5', 'Fifth league description', 'SILVER', 'CLOSED', 'SINGLES', '2024-01-05 14:00:00',
        '2024-02-05 14:00:00', 18, 'random', 1, '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       ('League 6', 'Sixth league description', 'BRONZE', 'OPEN', 'DOUBLES', '2024-01-06 15:00:00',
        '2024-02-06 15:00:00', 24, 'random', 1, '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       ('League 7', 'Seventh league description', 'GOLD', 'OPEN', 'SINGLES', '2024-01-07 16:00:00',
        '2024-02-07 16:00:00', 8, 'random', 1, '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       ('League 8', 'Eighth league description', 'SILVER', 'CLOSED', 'DOUBLES', '2024-01-08 17:00:00',
        '2024-02-08 17:00:00', 16, 'random', 1, '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       ('League 9', 'Ninth league description', 'BRONZE', 'OPEN', 'SINGLES', '2024-01-09 18:00:00',
        '2024-02-09 18:00:00', 20, 'random', 1, '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       ('League 10', 'Tenth league description', 'GOLD', 'CLOSED', 'DOUBLES', '2024-01-10 19:00:00',
        '2024-02-10 19:00:00', 14, 'random', 2, '2024-01-10 09:00:00', '2024-01-10 09:00:00');

-- LeagueParticipant 데이터 삽입
INSERT INTO league_participant (clubMemberId, leagueId, isCanceled, createdAt, modifiedAt)
VALUES (1, 1, false, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (2, 1, false, '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       (3, 1, false, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       (4, 1, false, '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       (5, 1, false, '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       (6, 1, false, '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       (7, 1, false, '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       (8, 1, false, '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       (9, 1, false, '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       (10, 1, false, '2024-01-10 09:00:00', '2024-01-10 09:00:00'),
       (7, 2, false, '2024-01-11 09:00:00', '2024-01-11 09:00:00'),
       (8, 2, false, '2024-01-12 09:00:00', '2024-01-12 09:00:00'),
       (9, 2, false, '2024-01-13 09:00:00', '2024-01-13 09:00:00'),
       (10, 2, false, '2024-01-14 09:00:00', '2024-01-14 09:00:00'),
       (5, 3, false, '2024-01-15 09:00:00', '2024-01-15 09:00:00'),
       (6, 4, false, '2024-01-16 09:00:00', '2024-01-16 09:00:00'),
       (7, 4, false, '2024-01-17 09:00:00', '2024-01-17 09:00:00'),
       (8, 5, false, '2024-01-18 09:00:00', '2024-01-18 09:00:00'),
       (9, 5, false, '2024-01-19 09:00:00', '2024-01-19 09:00:00'),
       (10, 5, false, '2024-01-20 09:00:00', '2024-01-20 09:00:00');
