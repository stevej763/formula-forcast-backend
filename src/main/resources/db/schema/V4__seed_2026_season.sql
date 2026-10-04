-- 2026 calendar. Session start times are UTC, from the Jolpica (Ergast) F1 API.
-- Weekend dates are the local calendar dates of first practice and the race.

INSERT INTO championship_season (championship_season_uid, championship_year, championship_name)
VALUES ('18f1c12c-2536-4e5a-a3d9-40da06fb56f8', '2026', '2026 Formula One World Championship');

INSERT INTO championship_leaderboard (championship_leaderboard_uid, championship_season_id, championship_leaderboard_name, created_at, leaderboard_type, owner_account_id)
VALUES ('8c8b754d-060f-545f-add2-3f5b3a9aaa51', (SELECT id FROM championship_season WHERE championship_year = '2026'), '2026 Global Championship', NOW(), 'GLOBAL_DEFAULT', NULL);

-- Round 1: Australian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('7a4bae93-db70-5dc3-b35b-5d7db30beb90', (SELECT id FROM championship_season WHERE championship_year = '2026'), 1, 'AUSTRALIA', 'AU', '2026-03-06', '2026-03-08');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('dd34d3d1-269b-5c91-a99b-70b34a7204af', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a4bae93-db70-5dc3-b35b-5d7db30beb90'), 'PRACTICE_1', '2026-03-06T01:30:00Z'),
    ('923ba27b-0e92-553a-9300-5ac72786d19c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a4bae93-db70-5dc3-b35b-5d7db30beb90'), 'PRACTICE_2', '2026-03-06T05:00:00Z'),
    ('2398cf66-9820-525a-bb88-6378fbb9abab', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a4bae93-db70-5dc3-b35b-5d7db30beb90'), 'PRACTICE_3', '2026-03-07T01:30:00Z'),
    ('e12ffebd-26c6-5161-a7ce-91857861c844', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a4bae93-db70-5dc3-b35b-5d7db30beb90'), 'QUALIFYING', '2026-03-07T05:00:00Z'),
    ('f590464b-7a31-5b33-83aa-0c61bf26d513', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a4bae93-db70-5dc3-b35b-5d7db30beb90'), 'RACE', '2026-03-08T04:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '7a4bae93-db70-5dc3-b35b-5d7db30beb90'), NOW(), 'UPCOMING');

-- Round 2: Chinese Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('3c07e681-710a-5faf-8109-226fe5834336', (SELECT id FROM championship_season WHERE championship_year = '2026'), 2, 'CHINA', 'CN', '2026-03-13', '2026-03-15');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('f34f6942-af61-5d5f-91d1-f7bc39eab2b9', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3c07e681-710a-5faf-8109-226fe5834336'), 'PRACTICE_1', '2026-03-13T03:30:00Z'),
    ('f398a3bc-0d13-50d3-b521-342419051d0c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3c07e681-710a-5faf-8109-226fe5834336'), 'SPRINT_QUALIFYING', '2026-03-13T07:30:00Z'),
    ('b749c742-d23f-5891-a52e-7b1df3e1d47d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3c07e681-710a-5faf-8109-226fe5834336'), 'SPRINT', '2026-03-14T03:00:00Z'),
    ('661b19ae-f148-5518-89bf-3ff5e525ec5a', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3c07e681-710a-5faf-8109-226fe5834336'), 'QUALIFYING', '2026-03-14T07:00:00Z'),
    ('9c139a77-85bc-514e-aa96-a8f4bb626a4c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3c07e681-710a-5faf-8109-226fe5834336'), 'RACE', '2026-03-15T07:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '3c07e681-710a-5faf-8109-226fe5834336'), NOW(), 'UPCOMING');

-- Round 3: Japanese Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('a5da360f-7845-5c89-9f74-c6df13d81e41', (SELECT id FROM championship_season WHERE championship_year = '2026'), 3, 'JAPAN', 'JP', '2026-03-27', '2026-03-29');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('b0114f75-ec99-5d06-a8f2-7f6215bbfce4', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'a5da360f-7845-5c89-9f74-c6df13d81e41'), 'PRACTICE_1', '2026-03-27T02:30:00Z'),
    ('a1421183-aeb8-5ff9-ab5b-5e21939598f1', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'a5da360f-7845-5c89-9f74-c6df13d81e41'), 'PRACTICE_2', '2026-03-27T06:00:00Z'),
    ('92c0632d-408c-5c71-a1ab-bd7c5c86d8ff', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'a5da360f-7845-5c89-9f74-c6df13d81e41'), 'PRACTICE_3', '2026-03-28T02:30:00Z'),
    ('cfd6832f-b6fa-5293-a82c-4bdeccc54872', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'a5da360f-7845-5c89-9f74-c6df13d81e41'), 'QUALIFYING', '2026-03-28T06:00:00Z'),
    ('5a80c202-f70d-5564-b6b1-ad347eb12b2d', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'a5da360f-7845-5c89-9f74-c6df13d81e41'), 'RACE', '2026-03-29T05:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'a5da360f-7845-5c89-9f74-c6df13d81e41'), NOW(), 'UPCOMING');

-- Round 4: Miami Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('6605948c-3b69-5a9a-baa8-e37d49e5d2f4', (SELECT id FROM championship_season WHERE championship_year = '2026'), 4, 'MIAMI', 'US', '2026-05-01', '2026-05-03');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('6622e8fe-94bd-5f3d-9d2c-cd41fc31a1d0', (SELECT id FROM race_weekend WHERE race_weekend_uid = '6605948c-3b69-5a9a-baa8-e37d49e5d2f4'), 'PRACTICE_1', '2026-05-01T16:00:00Z'),
    ('f0aaf08f-766e-5589-a0f5-cb43bbd33a1c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '6605948c-3b69-5a9a-baa8-e37d49e5d2f4'), 'SPRINT_QUALIFYING', '2026-05-01T20:30:00Z'),
    ('76150011-1bfb-52b2-865c-330b8cc78e2d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '6605948c-3b69-5a9a-baa8-e37d49e5d2f4'), 'SPRINT', '2026-05-02T16:00:00Z'),
    ('da3b5483-7cfd-52eb-a96e-ca0877e61a7d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '6605948c-3b69-5a9a-baa8-e37d49e5d2f4'), 'QUALIFYING', '2026-05-02T20:00:00Z'),
    ('1ef9e3a8-f29b-5f06-8efd-ea13bb285c06', (SELECT id FROM race_weekend WHERE race_weekend_uid = '6605948c-3b69-5a9a-baa8-e37d49e5d2f4'), 'RACE', '2026-05-03T20:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '6605948c-3b69-5a9a-baa8-e37d49e5d2f4'), NOW(), 'UPCOMING');

-- Round 5: Canadian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('ae191026-5e85-523e-b748-65f19514a266', (SELECT id FROM championship_season WHERE championship_year = '2026'), 5, 'CANADA', 'CA', '2026-05-22', '2026-05-24');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('60c00ae4-1f37-5633-ba67-4dcf50e2f325', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ae191026-5e85-523e-b748-65f19514a266'), 'PRACTICE_1', '2026-05-22T16:30:00Z'),
    ('c2e700f8-c986-5f19-8635-3451ed00bb42', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ae191026-5e85-523e-b748-65f19514a266'), 'SPRINT_QUALIFYING', '2026-05-22T20:30:00Z'),
    ('a4a304fe-8981-50e1-aaba-038e299f18eb', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ae191026-5e85-523e-b748-65f19514a266'), 'SPRINT', '2026-05-23T16:00:00Z'),
    ('f9bdb492-4a5a-585f-be7b-d6227f9cdf2a', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ae191026-5e85-523e-b748-65f19514a266'), 'QUALIFYING', '2026-05-23T20:00:00Z'),
    ('1bf8227c-05c0-56b8-a2fd-646f3ec4f4bd', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ae191026-5e85-523e-b748-65f19514a266'), 'RACE', '2026-05-24T20:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'ae191026-5e85-523e-b748-65f19514a266'), NOW(), 'UPCOMING');

-- Round 6: Monaco Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('23851c4b-b0bd-5eb0-ac6c-ef7d59f8f22d', (SELECT id FROM championship_season WHERE championship_year = '2026'), 6, 'MONACO', 'MC', '2026-06-05', '2026-06-07');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('e102b0d1-2a51-5340-886a-ebe586459a4f', (SELECT id FROM race_weekend WHERE race_weekend_uid = '23851c4b-b0bd-5eb0-ac6c-ef7d59f8f22d'), 'PRACTICE_1', '2026-06-05T11:30:00Z'),
    ('7534dca3-6130-5a02-9d15-a00938c6870f', (SELECT id FROM race_weekend WHERE race_weekend_uid = '23851c4b-b0bd-5eb0-ac6c-ef7d59f8f22d'), 'PRACTICE_2', '2026-06-05T15:00:00Z'),
    ('b2fe0a78-175d-5e52-9912-826b08ec99e2', (SELECT id FROM race_weekend WHERE race_weekend_uid = '23851c4b-b0bd-5eb0-ac6c-ef7d59f8f22d'), 'PRACTICE_3', '2026-06-06T10:30:00Z'),
    ('30fcae4d-b845-56e7-bdf9-6bcfebc4eaee', (SELECT id FROM race_weekend WHERE race_weekend_uid = '23851c4b-b0bd-5eb0-ac6c-ef7d59f8f22d'), 'QUALIFYING', '2026-06-06T14:00:00Z'),
    ('63e06470-82d4-558a-86e1-4451be13ef91', (SELECT id FROM race_weekend WHERE race_weekend_uid = '23851c4b-b0bd-5eb0-ac6c-ef7d59f8f22d'), 'RACE', '2026-06-07T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '23851c4b-b0bd-5eb0-ac6c-ef7d59f8f22d'), NOW(), 'UPCOMING');

-- Round 7: Barcelona Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('2c9e8981-2a2e-58fc-a75b-79e5a4db9ffe', (SELECT id FROM championship_season WHERE championship_year = '2026'), 7, 'SPAIN', 'ES', '2026-06-12', '2026-06-14');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('655126c6-3442-50a4-b0fc-1fc94f99fd9e', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2c9e8981-2a2e-58fc-a75b-79e5a4db9ffe'), 'PRACTICE_1', '2026-06-12T11:30:00Z'),
    ('206bede1-9b3b-5fb7-aa5d-7cd6b04a889e', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2c9e8981-2a2e-58fc-a75b-79e5a4db9ffe'), 'PRACTICE_2', '2026-06-12T15:00:00Z'),
    ('e7a8c548-193f-5342-957a-eaf7348d9f55', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2c9e8981-2a2e-58fc-a75b-79e5a4db9ffe'), 'PRACTICE_3', '2026-06-13T10:30:00Z'),
    ('821ae2ed-1468-5996-a708-3742772e8927', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2c9e8981-2a2e-58fc-a75b-79e5a4db9ffe'), 'QUALIFYING', '2026-06-13T14:00:00Z'),
    ('19129001-3a0d-5c69-9db2-5e11f8d6a920', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2c9e8981-2a2e-58fc-a75b-79e5a4db9ffe'), 'RACE', '2026-06-14T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '2c9e8981-2a2e-58fc-a75b-79e5a4db9ffe'), NOW(), 'UPCOMING');

-- Round 8: Austrian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('2ce9e011-4b20-57ac-8be9-9db433c3bec9', (SELECT id FROM championship_season WHERE championship_year = '2026'), 8, 'AUSTRIA', 'AT', '2026-06-26', '2026-06-28');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('4db65f88-1911-5667-a9f4-fe5fe06d97cb', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2ce9e011-4b20-57ac-8be9-9db433c3bec9'), 'PRACTICE_1', '2026-06-26T11:30:00Z'),
    ('b1a7e6e8-0988-5af5-9e4f-5ba936f72f67', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2ce9e011-4b20-57ac-8be9-9db433c3bec9'), 'PRACTICE_2', '2026-06-26T15:00:00Z'),
    ('94f85c1e-7530-5f55-bd3c-12a6be7b41e6', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2ce9e011-4b20-57ac-8be9-9db433c3bec9'), 'PRACTICE_3', '2026-06-27T10:30:00Z'),
    ('37ef8360-bc1e-5ca0-812f-e049fc82d112', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2ce9e011-4b20-57ac-8be9-9db433c3bec9'), 'QUALIFYING', '2026-06-27T14:00:00Z'),
    ('a33784ab-8df3-5381-a00b-35fd1e7733cb', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2ce9e011-4b20-57ac-8be9-9db433c3bec9'), 'RACE', '2026-06-28T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '2ce9e011-4b20-57ac-8be9-9db433c3bec9'), NOW(), 'UPCOMING');

-- Round 9: British Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('8853bb47-d196-5d9b-8b35-fa90b8c93ffa', (SELECT id FROM championship_season WHERE championship_year = '2026'), 9, 'GREAT_BRITAIN', 'GB', '2026-07-03', '2026-07-05');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('b235cfbd-2d92-5498-96c3-20e91151dd7b', (SELECT id FROM race_weekend WHERE race_weekend_uid = '8853bb47-d196-5d9b-8b35-fa90b8c93ffa'), 'PRACTICE_1', '2026-07-03T11:30:00Z'),
    ('98917bcf-0caf-540e-8ab4-55def48c3e22', (SELECT id FROM race_weekend WHERE race_weekend_uid = '8853bb47-d196-5d9b-8b35-fa90b8c93ffa'), 'SPRINT_QUALIFYING', '2026-07-03T15:30:00Z'),
    ('0871077a-cbaa-5310-8c32-a7f5d081f769', (SELECT id FROM race_weekend WHERE race_weekend_uid = '8853bb47-d196-5d9b-8b35-fa90b8c93ffa'), 'SPRINT', '2026-07-04T11:00:00Z'),
    ('fbb82f6c-3d7f-5f82-8e2e-ccbcc3640c5f', (SELECT id FROM race_weekend WHERE race_weekend_uid = '8853bb47-d196-5d9b-8b35-fa90b8c93ffa'), 'QUALIFYING', '2026-07-04T15:00:00Z'),
    ('542e7b77-b8e4-5d56-8e23-0b5acf57d770', (SELECT id FROM race_weekend WHERE race_weekend_uid = '8853bb47-d196-5d9b-8b35-fa90b8c93ffa'), 'RACE', '2026-07-05T14:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '8853bb47-d196-5d9b-8b35-fa90b8c93ffa'), NOW(), 'UPCOMING');

-- Round 10: Belgian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('2acf76e6-8b28-5485-9c65-c93246740780', (SELECT id FROM championship_season WHERE championship_year = '2026'), 10, 'BELGIUM', 'BE', '2026-07-17', '2026-07-19');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('504aa600-feb8-50b8-b0eb-ce0683b1564b', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2acf76e6-8b28-5485-9c65-c93246740780'), 'PRACTICE_1', '2026-07-17T11:30:00Z'),
    ('54774e4d-0cb6-5fde-8d6e-07a6f8827f0e', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2acf76e6-8b28-5485-9c65-c93246740780'), 'PRACTICE_2', '2026-07-17T15:00:00Z'),
    ('8340a6de-0247-5e6e-bb67-e42027e023bb', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2acf76e6-8b28-5485-9c65-c93246740780'), 'PRACTICE_3', '2026-07-18T10:30:00Z'),
    ('3696a109-7a74-59f1-a540-2ae6496893cd', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2acf76e6-8b28-5485-9c65-c93246740780'), 'QUALIFYING', '2026-07-18T14:00:00Z'),
    ('943beaeb-2575-577b-981b-d40e27d2721f', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2acf76e6-8b28-5485-9c65-c93246740780'), 'RACE', '2026-07-19T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '2acf76e6-8b28-5485-9c65-c93246740780'), NOW(), 'UPCOMING');

-- Round 11: Hungarian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('1eeb6424-2629-540f-94d6-4c888cc8faa9', (SELECT id FROM championship_season WHERE championship_year = '2026'), 11, 'HUNGARY', 'HU', '2026-07-24', '2026-07-26');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('35e9ade3-7b9d-5d40-9511-d4da3e99f815', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1eeb6424-2629-540f-94d6-4c888cc8faa9'), 'PRACTICE_1', '2026-07-24T11:30:00Z'),
    ('889a5a33-863f-570d-9e3d-df44d7201554', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1eeb6424-2629-540f-94d6-4c888cc8faa9'), 'PRACTICE_2', '2026-07-24T15:00:00Z'),
    ('6714f6b7-a979-57ac-9a18-83519b1ad3ce', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1eeb6424-2629-540f-94d6-4c888cc8faa9'), 'PRACTICE_3', '2026-07-25T10:30:00Z'),
    ('53873303-c9e1-5624-9140-af11caed12ab', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1eeb6424-2629-540f-94d6-4c888cc8faa9'), 'QUALIFYING', '2026-07-25T14:00:00Z'),
    ('5c9b5010-766d-5366-b8d0-d0b5d6eabd98', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1eeb6424-2629-540f-94d6-4c888cc8faa9'), 'RACE', '2026-07-26T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '1eeb6424-2629-540f-94d6-4c888cc8faa9'), NOW(), 'UPCOMING');

-- Round 12: Dutch Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('be04bdcd-992b-5078-9a60-70d2018212b2', (SELECT id FROM championship_season WHERE championship_year = '2026'), 12, 'NETHERLANDS', 'NL', '2026-08-21', '2026-08-23');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('c75a9ee4-adf0-5234-8780-1cd0d7e8c695', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'be04bdcd-992b-5078-9a60-70d2018212b2'), 'PRACTICE_1', '2026-08-21T10:30:00Z'),
    ('4cc96f15-d519-5f0d-a526-8b24678ba9ed', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'be04bdcd-992b-5078-9a60-70d2018212b2'), 'SPRINT_QUALIFYING', '2026-08-21T14:30:00Z'),
    ('16e8a894-71ec-56f8-8080-97b09987e17e', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'be04bdcd-992b-5078-9a60-70d2018212b2'), 'SPRINT', '2026-08-22T10:00:00Z'),
    ('fa754cd8-5c7a-5837-8963-81b638ebdf96', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'be04bdcd-992b-5078-9a60-70d2018212b2'), 'QUALIFYING', '2026-08-22T14:00:00Z'),
    ('860da98c-de73-5285-b586-3997c4d815fb', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'be04bdcd-992b-5078-9a60-70d2018212b2'), 'RACE', '2026-08-23T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'be04bdcd-992b-5078-9a60-70d2018212b2'), NOW(), 'UPCOMING');

-- Round 13: Italian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('7a35c5f0-4526-5df9-a350-494b01a7e466', (SELECT id FROM championship_season WHERE championship_year = '2026'), 13, 'ITALY', 'IT', '2026-09-04', '2026-09-06');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('d5e12765-22d4-56c1-8188-14a7b4e47816', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a35c5f0-4526-5df9-a350-494b01a7e466'), 'PRACTICE_1', '2026-09-04T10:30:00Z'),
    ('244487c9-b16b-572f-b19d-01a45e2f10b2', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a35c5f0-4526-5df9-a350-494b01a7e466'), 'PRACTICE_2', '2026-09-04T14:00:00Z'),
    ('4305584a-d8a7-55b0-ad85-9f1b95d4896b', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a35c5f0-4526-5df9-a350-494b01a7e466'), 'PRACTICE_3', '2026-09-05T10:30:00Z'),
    ('a898c92a-30b2-5cdd-9564-71c6f54181b0', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a35c5f0-4526-5df9-a350-494b01a7e466'), 'QUALIFYING', '2026-09-05T14:00:00Z'),
    ('bccced79-7516-5bed-9c13-6915d4eac216', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7a35c5f0-4526-5df9-a350-494b01a7e466'), 'RACE', '2026-09-06T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '7a35c5f0-4526-5df9-a350-494b01a7e466'), NOW(), 'UPCOMING');

-- Round 14: Spanish Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('175411ce-276d-5bbb-9906-6f59dfa40acd', (SELECT id FROM championship_season WHERE championship_year = '2026'), 14, 'MADRID', 'ES', '2026-09-11', '2026-09-13');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('990856ba-2da8-5f87-aab6-641190e8fd98', (SELECT id FROM race_weekend WHERE race_weekend_uid = '175411ce-276d-5bbb-9906-6f59dfa40acd'), 'PRACTICE_1', '2026-09-11T11:30:00Z'),
    ('fe4ee663-493b-5e3f-a77f-076c7c66b82e', (SELECT id FROM race_weekend WHERE race_weekend_uid = '175411ce-276d-5bbb-9906-6f59dfa40acd'), 'PRACTICE_2', '2026-09-11T15:00:00Z'),
    ('10c1dd92-217b-5ea3-9a14-a2fdd830931d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '175411ce-276d-5bbb-9906-6f59dfa40acd'), 'PRACTICE_3', '2026-09-12T10:30:00Z'),
    ('b123c1c5-27c4-5708-8d6e-ca9064e844eb', (SELECT id FROM race_weekend WHERE race_weekend_uid = '175411ce-276d-5bbb-9906-6f59dfa40acd'), 'QUALIFYING', '2026-09-12T14:00:00Z'),
    ('87d05766-2a7a-5e9c-8796-6df44824ddd3', (SELECT id FROM race_weekend WHERE race_weekend_uid = '175411ce-276d-5bbb-9906-6f59dfa40acd'), 'RACE', '2026-09-13T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '175411ce-276d-5bbb-9906-6f59dfa40acd'), NOW(), 'UPCOMING');

-- Round 15: Azerbaijan Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('d4d64c3f-68c2-5fe4-955c-5e75dbf88627', (SELECT id FROM championship_season WHERE championship_year = '2026'), 15, 'AZERBAIJAN', 'AZ', '2026-09-24', '2026-09-26');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('9077db87-b08a-5152-96f3-496b8fcf2def', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd4d64c3f-68c2-5fe4-955c-5e75dbf88627'), 'PRACTICE_1', '2026-09-24T08:30:00Z'),
    ('bc684a3e-f3af-58b9-a0c6-59ea81eb7c89', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd4d64c3f-68c2-5fe4-955c-5e75dbf88627'), 'PRACTICE_2', '2026-09-24T12:00:00Z'),
    ('59b6d980-170d-5cf8-91e7-e22c2788d599', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd4d64c3f-68c2-5fe4-955c-5e75dbf88627'), 'PRACTICE_3', '2026-09-25T08:30:00Z'),
    ('48352845-e5ad-5ac4-a38f-b26949bc1a46', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd4d64c3f-68c2-5fe4-955c-5e75dbf88627'), 'QUALIFYING', '2026-09-25T12:00:00Z'),
    ('bb33d67e-247a-5a21-8f56-cfc91d712db6', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd4d64c3f-68c2-5fe4-955c-5e75dbf88627'), 'RACE', '2026-09-26T11:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'd4d64c3f-68c2-5fe4-955c-5e75dbf88627'), NOW(), 'UPCOMING');

-- Round 16: Bahrain Grand Prix in Malaysia
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('b8046d14-694f-568d-9eaf-480383591d09', (SELECT id FROM championship_season WHERE championship_year = '2026'), 16, 'BAHRAIN', 'MY', '2026-10-02', '2026-10-04');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('e04fdf0c-6ba3-5bdc-b5e1-ea3380ff805d', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b8046d14-694f-568d-9eaf-480383591d09'), 'PRACTICE_1', '2026-10-02T04:30:00Z'),
    ('6d831ab7-0f20-5ea4-b203-9f9ae1609674', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b8046d14-694f-568d-9eaf-480383591d09'), 'PRACTICE_2', '2026-10-02T08:00:00Z'),
    ('928f86ad-4857-5d74-b649-dcee9f3364cb', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b8046d14-694f-568d-9eaf-480383591d09'), 'PRACTICE_3', '2026-10-03T04:30:00Z'),
    ('f87fa226-4315-584a-b8a6-1f494e2f6a7f', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b8046d14-694f-568d-9eaf-480383591d09'), 'QUALIFYING', '2026-10-03T08:00:00Z'),
    ('280e444d-9ea4-50ef-ba8a-4f7f84f492fb', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b8046d14-694f-568d-9eaf-480383591d09'), 'RACE', '2026-10-04T07:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'b8046d14-694f-568d-9eaf-480383591d09'), NOW(), 'UPCOMING');

-- Round 17: Singapore Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('213e70c3-e69c-5664-93bf-06b493fbdd41', (SELECT id FROM championship_season WHERE championship_year = '2026'), 17, 'SINGAPORE', 'SG', '2026-10-09', '2026-10-11');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('3cdeb0a7-6a3f-5953-927e-2b7181b9524f', (SELECT id FROM race_weekend WHERE race_weekend_uid = '213e70c3-e69c-5664-93bf-06b493fbdd41'), 'PRACTICE_1', '2026-10-09T08:30:00Z'),
    ('b6a0bbf5-0b33-5198-ab25-1f855b28eac6', (SELECT id FROM race_weekend WHERE race_weekend_uid = '213e70c3-e69c-5664-93bf-06b493fbdd41'), 'SPRINT_QUALIFYING', '2026-10-09T12:30:00Z'),
    ('a364e25e-703b-5f9b-bec7-e4197eafb55a', (SELECT id FROM race_weekend WHERE race_weekend_uid = '213e70c3-e69c-5664-93bf-06b493fbdd41'), 'SPRINT', '2026-10-10T09:00:00Z'),
    ('db1cd6c7-744e-5c8b-ae64-1efb1ca0d295', (SELECT id FROM race_weekend WHERE race_weekend_uid = '213e70c3-e69c-5664-93bf-06b493fbdd41'), 'QUALIFYING', '2026-10-10T13:00:00Z'),
    ('29a53032-bcc5-5308-b828-ec0dee8bbcc1', (SELECT id FROM race_weekend WHERE race_weekend_uid = '213e70c3-e69c-5664-93bf-06b493fbdd41'), 'RACE', '2026-10-11T12:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '213e70c3-e69c-5664-93bf-06b493fbdd41'), NOW(), 'UPCOMING');

-- Round 18: United States Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('1a437c4c-8bc6-519e-ab45-b67f8df2e105', (SELECT id FROM championship_season WHERE championship_year = '2026'), 18, 'UNITED_STATES', 'US', '2026-10-23', '2026-10-25');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('f71f3d24-688c-5eff-8327-36b989df0395', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1a437c4c-8bc6-519e-ab45-b67f8df2e105'), 'PRACTICE_1', '2026-10-23T17:30:00Z'),
    ('26f44787-f3f0-51ff-9c87-0a9bc3b36767', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1a437c4c-8bc6-519e-ab45-b67f8df2e105'), 'PRACTICE_2', '2026-10-23T21:00:00Z'),
    ('a179885c-ab67-5131-bf2d-f08a8810cf47', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1a437c4c-8bc6-519e-ab45-b67f8df2e105'), 'PRACTICE_3', '2026-10-24T17:30:00Z'),
    ('13b48fc3-f3fb-547b-8273-323701069c1b', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1a437c4c-8bc6-519e-ab45-b67f8df2e105'), 'QUALIFYING', '2026-10-24T21:00:00Z'),
    ('db92ac82-86d4-560a-a40e-52591d382a64', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1a437c4c-8bc6-519e-ab45-b67f8df2e105'), 'RACE', '2026-10-25T20:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '1a437c4c-8bc6-519e-ab45-b67f8df2e105'), NOW(), 'UPCOMING');

-- Round 19: Mexico City Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('0c7d6b11-d39e-53e4-b627-4601cd477670', (SELECT id FROM championship_season WHERE championship_year = '2026'), 19, 'MEXICO', 'MX', '2026-10-30', '2026-11-01');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('8a38d9a9-8545-5a45-a213-141882f020ef', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0c7d6b11-d39e-53e4-b627-4601cd477670'), 'PRACTICE_1', '2026-10-30T18:30:00Z'),
    ('92164621-114f-5c87-981e-350d19dd6c9b', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0c7d6b11-d39e-53e4-b627-4601cd477670'), 'PRACTICE_2', '2026-10-30T22:00:00Z'),
    ('5ae2cdbc-ac34-5670-9f5f-e83199eb9a67', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0c7d6b11-d39e-53e4-b627-4601cd477670'), 'PRACTICE_3', '2026-10-31T17:30:00Z'),
    ('f039d739-7958-5ad2-a7f1-11714dfa32a2', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0c7d6b11-d39e-53e4-b627-4601cd477670'), 'QUALIFYING', '2026-10-31T21:00:00Z'),
    ('a4634c8d-e21f-5bad-a017-d594f69a8d24', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0c7d6b11-d39e-53e4-b627-4601cd477670'), 'RACE', '2026-11-01T20:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '0c7d6b11-d39e-53e4-b627-4601cd477670'), NOW(), 'UPCOMING');

-- Round 20: Brazilian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('0d84a84a-dc30-5861-8826-a8354e701e7a', (SELECT id FROM championship_season WHERE championship_year = '2026'), 20, 'BRAZIL', 'BR', '2026-11-06', '2026-11-08');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('b04a8fea-490b-5a2c-83ba-e83a27eb97cb', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0d84a84a-dc30-5861-8826-a8354e701e7a'), 'PRACTICE_1', '2026-11-06T15:30:00Z'),
    ('0a2ed240-453a-56e9-b20d-cdd1d98feb92', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0d84a84a-dc30-5861-8826-a8354e701e7a'), 'PRACTICE_2', '2026-11-06T19:00:00Z'),
    ('25bd55e5-2958-5ea7-9423-9a88991a804c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0d84a84a-dc30-5861-8826-a8354e701e7a'), 'PRACTICE_3', '2026-11-07T14:30:00Z'),
    ('a8fb2b7f-ebc5-5443-9e1e-d779fd7f4991', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0d84a84a-dc30-5861-8826-a8354e701e7a'), 'QUALIFYING', '2026-11-07T18:00:00Z'),
    ('ecabe4b0-becd-5b8a-95e2-a1eae4938e24', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0d84a84a-dc30-5861-8826-a8354e701e7a'), 'RACE', '2026-11-08T17:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '0d84a84a-dc30-5861-8826-a8354e701e7a'), NOW(), 'UPCOMING');

-- Round 21: Las Vegas Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('d1ceb3d5-52ef-5085-8dc6-e46090dbfa8d', (SELECT id FROM championship_season WHERE championship_year = '2026'), 21, 'LAS_VEGAS', 'US', '2026-11-19', '2026-11-21');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('ac435890-8726-50c7-9f11-6bd1f28d57a8', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd1ceb3d5-52ef-5085-8dc6-e46090dbfa8d'), 'PRACTICE_1', '2026-11-20T00:30:00Z'),
    ('fac83002-ca35-5bab-b099-c75e606512d1', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd1ceb3d5-52ef-5085-8dc6-e46090dbfa8d'), 'PRACTICE_2', '2026-11-20T04:00:00Z'),
    ('1d49c404-9f12-50bb-998a-bf23b4b3e337', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd1ceb3d5-52ef-5085-8dc6-e46090dbfa8d'), 'PRACTICE_3', '2026-11-21T00:30:00Z'),
    ('f2ab50c8-d3e0-53c6-ad7b-5030aafd71bb', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd1ceb3d5-52ef-5085-8dc6-e46090dbfa8d'), 'QUALIFYING', '2026-11-21T04:00:00Z'),
    ('f5ba645f-ed6c-5d3d-9d6f-ff140e307828', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd1ceb3d5-52ef-5085-8dc6-e46090dbfa8d'), 'RACE', '2026-11-22T04:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'd1ceb3d5-52ef-5085-8dc6-e46090dbfa8d'), NOW(), 'UPCOMING');

-- Round 22: Qatar Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('1cf1e660-f863-5305-a235-03b9ee1f3865', (SELECT id FROM championship_season WHERE championship_year = '2026'), 22, 'QATAR', 'QA', '2026-11-27', '2026-11-29');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('030d5fae-e927-5191-9187-c3fcbb0fe6b9', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1cf1e660-f863-5305-a235-03b9ee1f3865'), 'PRACTICE_1', '2026-11-27T13:30:00Z'),
    ('3bd9c342-a9c3-58d9-a1ae-5c2d329f0ed8', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1cf1e660-f863-5305-a235-03b9ee1f3865'), 'PRACTICE_2', '2026-11-27T17:00:00Z'),
    ('5530f586-dca3-5ef9-a918-69a401664f97', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1cf1e660-f863-5305-a235-03b9ee1f3865'), 'PRACTICE_3', '2026-11-28T14:30:00Z'),
    ('49274bef-808a-580d-bce5-e88a98324110', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1cf1e660-f863-5305-a235-03b9ee1f3865'), 'QUALIFYING', '2026-11-28T18:00:00Z'),
    ('f289dc03-5c71-5cac-b5f1-d839c9ab3ffc', (SELECT id FROM race_weekend WHERE race_weekend_uid = '1cf1e660-f863-5305-a235-03b9ee1f3865'), 'RACE', '2026-11-29T16:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '1cf1e660-f863-5305-a235-03b9ee1f3865'), NOW(), 'UPCOMING');

-- Round 23: Abu Dhabi Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('ca3b32d1-88fa-53a8-bbd9-76e65c20e01f', (SELECT id FROM championship_season WHERE championship_year = '2026'), 23, 'ABU_DHABI', 'AE', '2026-12-04', '2026-12-06');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('fcdd037e-d910-5a92-bcac-2cc76874dac1', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ca3b32d1-88fa-53a8-bbd9-76e65c20e01f'), 'PRACTICE_1', '2026-12-04T09:30:00Z'),
    ('a0c5111f-a296-5144-8bcf-6cc6ba4a1fb0', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ca3b32d1-88fa-53a8-bbd9-76e65c20e01f'), 'PRACTICE_2', '2026-12-04T13:00:00Z'),
    ('f6b35476-2360-52c4-bed2-b16965560723', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ca3b32d1-88fa-53a8-bbd9-76e65c20e01f'), 'PRACTICE_3', '2026-12-05T10:30:00Z'),
    ('b36be306-9aac-5b11-80c8-0f45a55fb6ba', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ca3b32d1-88fa-53a8-bbd9-76e65c20e01f'), 'QUALIFYING', '2026-12-05T14:00:00Z'),
    ('f1b1c57e-89fb-511d-9792-362977126d3e', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'ca3b32d1-88fa-53a8-bbd9-76e65c20e01f'), 'RACE', '2026-12-06T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'ca3b32d1-88fa-53a8-bbd9-76e65c20e01f'), NOW(), 'UPCOMING');

-- 2026 grid: the 22 drivers and 11 constructors from the Azerbaijan Grand Prix (round 15),
-- from the Jolpica (Ergast) F1 API.

INSERT INTO constructor (constructor_uid, team_name, base) VALUES
    ('fd7d3be4-f998-5404-9eaa-91d9f32788b0', 'Alpine', 'Enstone'),
    ('19fd3ee7-9a8c-59ce-950c-caad31f60c44', 'Aston Martin', 'Silverstone'),
    ('f141eb67-8f29-5780-97b4-36f1318e389b', 'Audi', 'Hinwil'),
    ('8825a094-38fc-5ee2-b93d-d95430562503', 'Cadillac', 'Fishers'),
    ('ce44de4e-9d20-5084-a561-cf50160bd01a', 'Ferrari', 'Maranello'),
    ('7bc99519-9029-5a14-99c9-a5e594849312', 'Haas', 'Kannapolis'),
    ('eca750d3-801a-5fd4-93aa-2cb70b6407f2', 'McLaren', 'Woking'),
    ('f6062063-e55d-5239-aae7-2eebc24ec831', 'Mercedes', 'Brackley'),
    ('6c3b910f-f0c1-5934-9152-a5a3e68d74ed', 'Racing Bulls', 'Faenza'),
    ('7b472065-97af-54f9-bec9-0ec99d1169a2', 'Red Bull Racing', 'Milton Keynes'),
    ('045e2f0f-0c3a-567f-a68b-16ba35119ca3', 'Williams', 'Grove');

INSERT INTO driver (driver_uid, first_name, last_name, nickname, date_of_birth, nationality) VALUES
    ('4342f724-f4dc-5b5a-9d44-5c3880d23b14', 'George', 'Russell', 'RUS', '1998-02-15', 'GB'),
    ('acd02550-1232-534f-83d8-866e3991fe9b', 'Max', 'Verstappen', 'VER', '1997-09-30', 'NL'),
    ('6810ef43-f49b-5d49-8e50-a665810063bc', 'Isack', 'Hadjar', 'HAD', '2004-09-28', 'FR'),
    ('b4dadae1-21f6-54d7-91ad-1996da358c9a', 'Charles', 'Leclerc', 'LEC', '1997-10-16', 'MC'),
    ('af3f21a6-9970-5485-b401-6339eac855c5', 'Andrea Kimi', 'Antonelli', 'ANT', '2006-08-25', 'IT'),
    ('19f7123f-e05d-576a-8a5f-69066b72692d', 'Lewis', 'Hamilton', 'HAM', '1985-01-07', 'GB'),
    ('90786e03-b42c-5c66-8933-c128c84be998', 'Arvid', 'Lindblad', 'LIN', '2007-08-08', 'GB'),
    ('e76d287a-3edc-52fa-8d22-a29f90c390ac', 'Esteban', 'Ocon', 'OCO', '1996-09-17', 'FR'),
    ('530d8563-f61d-5e3b-9a2f-1a9fa0712cad', 'Oliver', 'Bearman', 'BEA', '2005-05-08', 'GB'),
    ('2c835a6f-7dd5-54fb-94ee-58cf7ffa29a6', 'Carlos', 'Sainz', 'SAI', '1994-09-01', 'ES'),
    ('66f778a3-db77-5274-9855-e8fcb7d83460', 'Nico', 'Hülkenberg', 'HUL', '1987-08-19', 'DE'),
    ('2979d5b5-4200-52af-bc0b-7041c2e4ac0d', 'Liam', 'Lawson', 'LAW', '2002-02-11', 'NZ'),
    ('768c72f2-48dd-5270-afca-cafc96613332', 'Oscar', 'Piastri', 'PIA', '2001-04-06', 'AU'),
    ('9111344a-4820-5ab3-b315-2d8f65691e60', 'Sergio', 'Pérez', 'PER', '1990-01-26', 'MX'),
    ('7a5d9c35-c9c3-553a-b3e1-7a0a80f290f0', 'Gabriel', 'Bortoleto', 'BOR', '2004-10-14', 'BR'),
    ('50471862-9e8f-5274-bf4a-027a4174ad39', 'Valtteri', 'Bottas', 'BOT', '1989-08-28', 'FI'),
    ('5bfc0d5e-8b4a-5d2d-aa3a-eed13f6e06ba', 'Franco', 'Colapinto', 'COL', '2003-05-27', 'AR'),
    ('a8c68f20-2cd6-5daa-9773-56cde56827ed', 'Pierre', 'Gasly', 'GAS', '1996-02-07', 'FR'),
    ('0d7ad855-110e-5a5e-b801-9601dcd74eb2', 'Lando', 'Norris', 'NOR', '1999-11-13', 'GB'),
    ('19b1a6b1-10da-5a62-b815-1a81251fe7fe', 'Alexander', 'Albon', 'ALB', '1996-03-23', 'TH'),
    ('993ddc64-5a01-5c20-9d98-44d4dd1bbf87', 'Fernando', 'Alonso', 'ALO', '1981-07-29', 'ES'),
    ('0e68fc87-4b97-5ccc-84c2-5efbd635b798', 'Lance', 'Stroll', 'STR', '1998-10-29', 'CA');

INSERT INTO driver_constructor_mapping (championship_season_id, driver_id, constructor_id) VALUES
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '4342f724-f4dc-5b5a-9d44-5c3880d23b14'), (SELECT id FROM constructor WHERE constructor_uid = 'f6062063-e55d-5239-aae7-2eebc24ec831')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = 'acd02550-1232-534f-83d8-866e3991fe9b'), (SELECT id FROM constructor WHERE constructor_uid = '7b472065-97af-54f9-bec9-0ec99d1169a2')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '6810ef43-f49b-5d49-8e50-a665810063bc'), (SELECT id FROM constructor WHERE constructor_uid = '7b472065-97af-54f9-bec9-0ec99d1169a2')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = 'b4dadae1-21f6-54d7-91ad-1996da358c9a'), (SELECT id FROM constructor WHERE constructor_uid = 'ce44de4e-9d20-5084-a561-cf50160bd01a')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = 'af3f21a6-9970-5485-b401-6339eac855c5'), (SELECT id FROM constructor WHERE constructor_uid = 'f6062063-e55d-5239-aae7-2eebc24ec831')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '19f7123f-e05d-576a-8a5f-69066b72692d'), (SELECT id FROM constructor WHERE constructor_uid = 'ce44de4e-9d20-5084-a561-cf50160bd01a')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '90786e03-b42c-5c66-8933-c128c84be998'), (SELECT id FROM constructor WHERE constructor_uid = '6c3b910f-f0c1-5934-9152-a5a3e68d74ed')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = 'e76d287a-3edc-52fa-8d22-a29f90c390ac'), (SELECT id FROM constructor WHERE constructor_uid = '7bc99519-9029-5a14-99c9-a5e594849312')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '530d8563-f61d-5e3b-9a2f-1a9fa0712cad'), (SELECT id FROM constructor WHERE constructor_uid = '7bc99519-9029-5a14-99c9-a5e594849312')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '2c835a6f-7dd5-54fb-94ee-58cf7ffa29a6'), (SELECT id FROM constructor WHERE constructor_uid = '045e2f0f-0c3a-567f-a68b-16ba35119ca3')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '66f778a3-db77-5274-9855-e8fcb7d83460'), (SELECT id FROM constructor WHERE constructor_uid = 'f141eb67-8f29-5780-97b4-36f1318e389b')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '2979d5b5-4200-52af-bc0b-7041c2e4ac0d'), (SELECT id FROM constructor WHERE constructor_uid = '6c3b910f-f0c1-5934-9152-a5a3e68d74ed')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '768c72f2-48dd-5270-afca-cafc96613332'), (SELECT id FROM constructor WHERE constructor_uid = 'eca750d3-801a-5fd4-93aa-2cb70b6407f2')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '9111344a-4820-5ab3-b315-2d8f65691e60'), (SELECT id FROM constructor WHERE constructor_uid = '8825a094-38fc-5ee2-b93d-d95430562503')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '7a5d9c35-c9c3-553a-b3e1-7a0a80f290f0'), (SELECT id FROM constructor WHERE constructor_uid = 'f141eb67-8f29-5780-97b4-36f1318e389b')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '50471862-9e8f-5274-bf4a-027a4174ad39'), (SELECT id FROM constructor WHERE constructor_uid = '8825a094-38fc-5ee2-b93d-d95430562503')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '5bfc0d5e-8b4a-5d2d-aa3a-eed13f6e06ba'), (SELECT id FROM constructor WHERE constructor_uid = 'fd7d3be4-f998-5404-9eaa-91d9f32788b0')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = 'a8c68f20-2cd6-5daa-9773-56cde56827ed'), (SELECT id FROM constructor WHERE constructor_uid = 'fd7d3be4-f998-5404-9eaa-91d9f32788b0')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '0d7ad855-110e-5a5e-b801-9601dcd74eb2'), (SELECT id FROM constructor WHERE constructor_uid = 'eca750d3-801a-5fd4-93aa-2cb70b6407f2')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '19b1a6b1-10da-5a62-b815-1a81251fe7fe'), (SELECT id FROM constructor WHERE constructor_uid = '045e2f0f-0c3a-567f-a68b-16ba35119ca3')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '993ddc64-5a01-5c20-9d98-44d4dd1bbf87'), (SELECT id FROM constructor WHERE constructor_uid = '19fd3ee7-9a8c-59ce-950c-caad31f60c44')),
    ((SELECT id FROM championship_season WHERE championship_year = '2026'), (SELECT id FROM driver WHERE driver_uid = '0e68fc87-4b97-5ccc-84c2-5efbd635b798'), (SELECT id FROM constructor WHERE constructor_uid = '19fd3ee7-9a8c-59ce-950c-caad31f60c44'));
