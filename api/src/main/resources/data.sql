-- Member 데이터 삽입 (이미지 URL 교체)
use badminton;
INSERT INTO member (providerId, email, name, authorization, profileImage, isDeleted, lastConnectionAt, createdAt,
                    modifiedAt, tier, memberToken)
VALUES ('provider_1', 'honggildong@example.com', '홍길동', 'AUTHORIZATION_USER',
        'https://d36om9pjoifd2y.cloudfront.net/member/cat.jpg', false, '2024-01-01 10:00:00',
        '2024-01-01 09:00:00', '2024-01-01 09:00:00', 'BRONZE', 'me_token_01'),
       ('provider_2', 'satang@example.com', '사탕', 'AUTHORIZATION_USER',
        'https://d36om9pjoifd2y.cloudfront.net/member/cow.jpg', false, '2024-01-02 10:00:00',
        '2024-01-02 09:00:00', '2024-01-02 09:00:00', 'BRONZE', 'me_token_02'),
       ('provider_3', 'candyman@example.com', '캔디맨', 'AUTHORIZATION_USER',
        'https://d36om9pjoifd2y.cloudfront.net/member/lion.jpg', false, '2024-01-03 10:00:00',
        '2024-01-03 09:00:00', '2024-01-03 09:00:00', 'BRONZE', 'me_token_03'),
       ('provider_4', 'admin1@example.com', '관리자1', 'AUTHORIZATION_ADMIN',
        'https://d36om9pjoifd2y.cloudfront.net/member/pelican.jpg', false, '2024-01-04 10:00:00',
        '2024-01-04 09:00:00', '2024-01-04 09:00:00', 'BRONZE', 'me_token_04'),
       ('provider_5', 'yulsooa@example.com', '율수아', 'AUTHORIZATION_USER',
        'https://d36om9pjoifd2y.cloudfront.net/member/zebra.jpg', false, '2024-01-05 10:00:00',
        '2024-01-05 09:00:00', '2024-01-05 09:00:00', 'BRONZE', 'me_token_05'),
       ('provider_6', 'cheesecake@example.com', '치즈케이크', 'AUTHORIZATION_USER',
        'https://d36om9pjoifd2y.cloudfront.net/member/cat.jpg', true, '2024-01-06 10:00:00',
        '2024-01-06 09:00:00', '2024-01-06 09:00:00', 'BRONZE', 'me_token_06'),
       ('provider_7', 'jellybean@example.com', '젤리빈', 'AUTHORIZATION_USER',
        'https://d36om9pjoifd2y.cloudfront.net/member/cow.jpg', false, '2024-01-07 10:00:00',
        '2024-01-07 09:00:00', '2024-01-07 09:00:00', 'BRONZE', 'me_token_07'),
       ('provider_8', 'admin2@example.com', '관리자2', 'AUTHORIZATION_ADMIN',
        'https://d36om9pjoifd2y.cloudfront.net/member/lion.jpg', false, '2024-01-08 10:00:00',
        '2024-01-08 09:00:00', '2024-01-08 09:00:00', 'BRONZE', 'me_token_08'),
       ('provider_9', 'honeydew@example.com', '허니듀', 'AUTHORIZATION_USER',
        'https://d36om9pjoifd2y.cloudfront.net/member/pelican.jpg', false, '2024-01-09 10:00:00',
        '2024-01-09 09:00:00', '2024-01-09 09:00:00', 'BRONZE', 'me_token_09'),
       ('provider_10', 'chocobar@example.com', '초코바', 'AUTHORIZATION_USER',
        'https://d36om9pjoifd2y.cloudfront.net/member/zebra.jpg', false, '2024-01-10 10:00:00',
        '2024-01-10 09:00:00', '2024-01-10 09:00:00', 'BRONZE', 'me_token_10');

INSERT INTO club (clubName, clubDescription, clubImage, isClubDeleted, createdAt, modifiedAt, clubToken)
VALUES
    ('스매싱 파이터', '강력한 스매싱을 즐기는 분들을 위한 동호회입니다. 초보부터 고수까지 환영합니다!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/3257bbf2-52dc-41b2-9d1f-a6c66f3c7216.avif', false, '2024-01-11 09:00:00', '2024-01-11 09:00:00', 'club_asdq7UjhInO0BhBO'),

    ('클래식 배드민턴', '기본에 충실한 플레이를 중시하는 클럽입니다. 클래식한 스타일을 좋아하는 분들을 환영합니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/505adfb7-b355-4721-a622-3ce0f50eef4e.avif', false, '2024-01-12 09:00:00', '2024-01-12 09:00:00', 'club_asfw8UjhInO0BhBO'),

    ('스타 스매셔', '화려한 스매싱과 드라이브로 게임을 지배하는 모임입니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/66fd31cd-f112-4796-b823-682016c4f58b.avif', false, '2024-01-13 09:00:00', '2024-01-13 09:00:00', 'club_dgfehUjhInO0BhBO'),

    ('스피드 앤 파워', '빠른 스피드와 강한 파워로 게임을 즐기는 동호회입니다. 열정 있는 분들을 기다립니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/c87e6e09-71ce-43a1-bd5e-b8ce73fcd646.webp', false, '2024-01-14 09:00:00', '2024-01-14 09:00:00', 'club_ghjrkUjhInO0BhBO'),

    ('배드민턴 마스터', '기본부터 고급 기술까지 다양한 스킬을 함께 연습합니다. 친절한 멤버들이 기다립니다!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/9cd6bb46-53f8-4480-9c91-4eaeed0686a9.webp', false, '2024-01-15 09:00:00', '2024-01-15 09:00:00', 'club_hgkftUjhInO0BhBO'),

    ('왕초보 배드민턴', '완전 초보도 부담 없이 즐길 수 있는 클럽입니다. 기초부터 차근차근 배워봐요!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-16 09:00:00', '2024-01-16 09:00:00', 'club_iuytaUjhInO0BhBO'),

    ('즐거운 배드민턴', '친목과 운동을 동시에! 모두가 편안하게 즐길 수 있는 배드민턴 클럽입니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-17 09:00:00', '2024-01-17 09:00:00', 'club_plmqnsUjhInO0BhBO'),

    ('슈퍼 드라이버', '빠른 드라이브와 강한 리턴을 연습하는 클럽입니다. 공격적인 플레이를 좋아하시는 분 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-18 09:00:00', '2024-01-18 09:00:00', 'club_qweqdrUjhInO0BhBO'),

    ('피닉스', '회복과 성장! 배드민턴을 통해 체력을 기르고 건강한 생활을 함께 추구해요.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-19 09:00:00', '2024-01-19 09:00:00', 'club_zxrfqtUjhInO0BhBO'),

    ('프로 스매시', '각종 대회 출전을 목표로 하는 분들을 위한 실전 중심의 배드민턴 클럽입니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-20 09:00:00', '2024-01-20 09:00:00', 'club_qwfvqgUjhInO0BhBO'),

    ('정복자', '힘찬 스매시와 지치지 않는 체력을 자랑하는 클럽입니다. 함께 성장해봐요!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-21 09:00:00', '2024-01-21 09:00:00', 'club_iutqqhUjhInO0BhBO'),

    ('에이스', '배드민턴의 기본부터 실전 플레이까지, 에이스를 목표로 함께 연습해요!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-22 09:00:00', '2024-01-22 09:00:00', 'club_uiqowpUjhInO0BhBO'),

    ('바운스', '탄력 있는 플레이와 민첩성을 기르는 클럽입니다. 체력 단련에 관심 있는 분 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-23 09:00:00', '2024-01-23 09:00:00', 'club_rtqyeuUjhInO0BhBO'),

    ('스피드 러너', '순발력과 스피드를 연습하는 클럽입니다. 다이나믹한 게임을 좋아하는 분들 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-24 09:00:00', '2024-01-24 09:00:00', 'club_vbqnryUjhInO0BhBO'),

    ('패스트 플레이', '빠른 리턴과 드라이브를 중심으로 경기를 풀어나가는 클럽입니다. 열정 넘치는 분들 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-25 09:00:00', '2024-01-25 09:00:00', 'club_njatujUjhInO0BhBO'),

    ('미라클 배드민턴', '기적 같은 플레이를 목표로 하는 열정 넘치는 클럽입니다!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-26 09:00:00', '2024-01-26 09:00:00', 'club_jkiylUjhaInO0BhBO'),

    ('파워 샷', '강력한 파워와 에너지를 자랑하는 클럽입니다. 파워 있는 게임을 즐기시는 분들 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-27 09:00:00', '2024-01-27 09:00:00', 'club_zacavnUjhInO0BhBO'),

    ('레전드 플레이어', '전설이 되고 싶은 분들을 위한 모임입니다. 함께 전설적인 경기를 만들어봐요!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-28 09:00:00', '2024-01-28 09:00:00', 'club_mlsnkaUjhInO0BhBO'),
    ('스매싱 파이터', '강력한 스매싱을 즐기는 분들을 위한 동호회입니다. 초보부터 고수까지 환영합니다!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/3257bbf2-52dc-41b2-9d1f-a6c66f3c7216.avif', false, '2024-01-11 09:00:00', '2024-01-11 09:00:00', 'club_asd7aUdjhInO0BhBO'),

    ('클래식 배드민턴', '기본에 충실한 플레이를 중시하는 클럽입니다. 클래식한 스타일을 좋아하는 분들을 환영합니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/505adfb7-b355-4721-a622-3ce0f50eef4e.avif', false, '2024-01-12 09:00:00', '2024-01-12 09:00:00', 'club_asffa8UjhInO0BhBO'),

    ('스타 스매셔', '화려한 스매싱과 드라이브로 게임을 지배하는 모임입니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/66fd31cd-f112-4796-b823-682016c4f58b.avif', false, '2024-01-13 09:00:00', '2024-01-13 09:00:00', 'club_dggfahUjhInO0BhBO'),

    ('스피드 앤 파워1', '빠른 스피드와 강한 파워로 게임을 즐기는 동호회입니다. 열정 있는 분들을 기다립니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/c87e6e09-71ce-43a1-bd5e-b8ce73fcd646.webp', false, '2024-01-14 09:00:00', '2024-01-14 09:00:00', 'club_ghjakasUjhInO0BhBO'),

    ('배드민턴 마스터1', '기본부터 고급 기술까지 다양한 스킬을 함께 연습합니다. 친절한 멤버들이 기다립니다!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/9cd6bb46-53f8-4480-9c91-4eaeed0686a9.webp', false, '2024-01-15 09:00:00', '2024-01-15 09:00:00', 'club_hgkqfsUjhInO0BhBO'),

    ('왕초보 배드민턴1', '완전 초보도 부담 없이 즐길 수 있는 클럽입니다. 기초부터 차근차근 배워봐요!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-16 09:00:00', '2024-01-16 09:00:00', 'club_iuytsUjhInO0BhBO'),

    ('즐거운 배드민턴1', '친목과 운동을 동시에! 모두가 편안하게 즐길 수 있는 배드민턴 클럽입니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-17 09:00:00', '2024-01-17 09:00:00', 'club_plmwsnUjhInO0BhBO'),

    ('슈퍼 드라이버1', '빠른 드라이브와 강한 리턴을 연습하는 클럽입니다. 공격적인 플레이를 좋아하시는 분 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-18 09:00:00', '2024-01-18 09:00:00', 'club_qweersUjhInO0BhBO'),

    ('피닉스1', '회복과 성장! 배드민턴을 통해 체력을 기르고 건강한 생활을 함께 추구해요.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-19 09:00:00', '2024-01-19 09:00:00', 'club_zxrrtUsjhInO0BhBO'),

    ('프로1 스매시', '각종 대회 출전을 목표로 하는 분들을 위한 실전 중심의 배드민턴 클럽입니다.',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-20 09:00:00', '2024-01-20 09:00:00', 'club_qwafvUsjhInO0BhBO'),

    ('정복자1', '힘찬 스매시와 지치지 않는 체력을 자랑하는 클럽입니다. 함께 성장해봐요!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-21 09:00:00', '2024-01-21 09:00:00', 'club_iutshsUjhInO0BhBO'),

    ('에이스1', '배드민턴의 기본부터 실전 플레이까지, 에이스를 목표로 함께 연습해요!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-22 09:00:00', '2024-01-22 09:00:00', 'club_uioapsUjhInO0BhBO'),

    ('바운스1', '탄력 있는 플레이와 민첩성을 기르는 클럽입니다. 체력 단련에 관심 있는 분 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-23 09:00:00', '2024-01-23 09:00:00', 'club_rtydusUjhInO0BhBO'),

    ('스피드1 러너', '순발력과 스피드를 연습하는 클럽입니다. 다이나믹한 게임을 좋아하는 분들 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-24 09:00:00', '2024-01-24 09:00:00', 'club_vbfnsyUjhInO0BhBO'),

    ('패스트1 플레이', '빠른 리턴과 드라이브를 중심으로 경기를 풀어나가는 클럽입니다. 열정 넘치는 분들 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-25 09:00:00', '2024-01-25 09:00:00', 'club_njuesjUjhInO0BhBO'),

    ('미라클1 배드민턴', '기적 같은 플레이를 목표로 하는 열정 넘치는 클럽입니다!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-26 09:00:00', '2024-01-26 09:00:00', 'club_jkslqUjhInO0BhBO'),

    ('파워1 샷', '강력한 파워와 에너지를 자랑하는 클럽입니다. 파워 있는 게임을 즐기시는 분들 환영!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-27 09:00:00', '2024-01-27 09:00:00', 'club_zcswnUjhInO0BhBO'),

    ('레전드1 플레이어', '전설이 되고 싶은 분들을 위한 모임입니다. 함께 전설적인 경기를 만들어봐요!',
     'https://d36om9pjoifd2y.cloudfront.net/club-banner/45c13288-9d26-46bd-93ec-37e7e83ac700.webp', false, '2024-01-28 09:00:00', '2024-01-28 09:00:00', 'club_mlensUjhInO0BhBO');

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

INSERT INTO league (leagueOwnerMemberToken, leagueId, leagueName, description, fullAddress, region, requiredTier, leagueStatus, matchType,
                    leagueAt,
                    recruitingClosedAt,
                    playerLimitCount,
                    matchGenerationType, clubId, createdAt, modifiedAt)
VALUES ('me_token_04', 1, 'League 1', 'First league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'GOLD', 'RECRUITING_COMPLETED',
        'SINGLES',
        '2024-01-01 10:00:00',
        '2024-02-01 10:00:00',
        10, 'FREE', 1, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       ('me_token_04', 2, 'League 2', 'Second league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'SILVER', 'RECRUITING_COMPLETED',
        'DOUBLES',
        '2024-01-02 11:00:00',
        '2024-02-02 11:00:00', 4, 'FREE', 1, '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       ('me_token_04', 3, 'League 3', 'Third league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'BRONZE', 'RECRUITING_COMPLETED',
        'SINGLES',
        '2024-01-03 12:00:00',
        '2024-02-03 12:00:00', 15, 'FREE', 1, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       ('me_token_04', 4, 'League 4', 'Fourth league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'GOLD', 'RECRUITING_COMPLETED',
        'DOUBLES',
        '2024-01-04 13:00:00',
        '2024-02-04 13:00:00', 12, 'FREE', 1, '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       ('me_token_04', 5, 'League 5', 'Fifth league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'SILVER', 'RECRUITING_COMPLETED',
        'SINGLES',
        '2024-01-05 14:00:00',
        '2024-02-05 14:00:00', 18, 'FREE', 1, '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       ('me_token_04', 6, 'League 6', 'Sixth league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'BRONZE', 'RECRUITING_COMPLETED',
        'DOUBLES',
        '2024-12-25 15:00:00',
        '2024-02-06 15:00:00', 24, 'FREE', 1, '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       ('me_token_04', 7, 'League 7', 'Seventh league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'GOLD', 'PLAYING', 'SINGLES',
        '2024-12-25 16:00:00',
        '2024-02-07 16:00:00', 8, 'FREE', 1, '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       ('me_token_04', 8, 'League 8', 'Eighth league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'SILVER', 'PLAYING', 'DOUBLES',
        '2024-12-25 17:00:00',
        '2024-02-08 17:00:00', 16, 'FREE', 1, '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       ('me_token_04', 9, 'League 9', 'Ninth league description', '서울시 동대문구 장충동 체육관''', '서울시', 'BRONZE', 'FINISHED', 'SINGLES',
        '2024-12-25 18:00:00',
        '2024-02-09 18:00:00', 20, 'FREE', 1, '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       ('me_token_04', 10, 'League 10', 'Tenth league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'GOLD', 'CANCELED', 'DOUBLES',
        '2024-12-25 19:00:00',
        '2024-02-10 19:00:00', 14, 'FREE', 2, '2024-01-10 09:00:00', '2024-01-10 09:00:00'),
       ('me_token_04', 11, 'League 11', '11 league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'GOLD', 'PLAYING', 'DOUBLES',
        '2024-12-25 19:00:00',
        '2024-02-10 19:00:00', 14, 'FREE', 2, '2024-01-10 09:00:00', '2024-01-01 09:00:00'),
       ('me_token_04', 12, 'League 12', '12 league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'GOLD', 'CANCELED', 'DOUBLES',
        '2024-12-25 19:00:00',
        '2024-02-10 19:00:00', 14, 'FREE', 2, '2024-01-10 09:00:00', '2024-01-01 09:00:00'),
       ('me_token_04', 13, 'League 13', '13 league description', '서울시 동대문구 동대문구민 체육센터', '서울시', 'GOLD', 'FINISHED', 'DOUBLES',
        '2024-12-25 19:00:00',
        '2024-02-10 19:00:00', 14, 'FREE', 2, '2024-01-10 09:00:00', '2024-01-01 09:00:00'),
       ('me_token_04', 14, 'League 14', '14 league description', '서울시 성동구 서울숲 체육관', '서울시', 'GOLD', 'RECRUITING_COMPLETED', 'DOUBLES',
        '2024-01-10 19:00:00',
        '2024-02-10 19:00:00', 14, 'FREE', 2, '2024-01-10 09:00:00', '2024-01-01 09:00:00'),
       ('me_token_04', 15, 'League 15', '15 league description', '서울시 성동구 서울숲 체육관', '서울시', 'BRONZE', 'RECRUITING', 'DOUBLES',
        '2024-12-25 19:00:00',
        '2024-02-10 19:00:00', 14, 'FREE', 2, '2024-01-10 09:00:00', '2024-01-01 09:00:00');


-- LeagueParticipant 데이터 삽입
INSERT INTO league_participant (clubMemberId, leagueId, canceled, createdAt, modifiedAt, memberId)
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
INSERT INTO league_record (winCount, loseCount, drawCount, matchCount, memberId, createdAt, modifiedAt)
VALUES (16, 3, 1, 20, 1, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (7, 2, 1, 10, 2, '2024-01-02 09:00:00', '2024-01-02 09:00:00'),
       (19, 1, 0, 20, 3, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
       (7, 3, 0, 10, 4, '2024-01-04 09:00:00', '2024-01-04 09:00:00'),
       (4, 5, 1, 10, 5, '2024-01-05 09:00:00', '2024-01-05 09:00:00'),
       (17, 2, 1, 20, 6, '2024-01-06 09:00:00', '2024-01-06 09:00:00'),
       (3, 6, 1, 10, 7, '2024-01-07 09:00:00', '2024-01-07 09:00:00'),
       (6, 3, 1, 10, 8, '2024-01-08 09:00:00', '2024-01-08 09:00:00'),
       (7, 2, 1, 10, 9, '2024-01-09 09:00:00', '2024-01-09 09:00:00'),
       (18, 1, 1, 20, 10, '2024-01-10 09:00:00', '2024-01-10 09:00:00');


insert into club_statistics (leagueCount, registrationCount, visitedCount, clubId, clubStatisticsId, createdAt,
                             modifiedAt)
values (0, 0, 0, 1, 1, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 2, 2, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 3, 3, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 4, 4, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 5, 5, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 6, 6, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 7, 7, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 8, 8, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 9, 9, '2024-01-01 09:00:00', '2024-01-01 09:00:00'),
       (0, 0, 0, 10, 10, '2024-01-01 09:00:00', '2024-01-01 09:00:00');