-- 2025 calendar. Session start times are UTC, from the Jolpica (Ergast) F1 API.
-- Weekend dates are the local calendar dates of first practice and the race.

INSERT INTO championship_season (championship_season_uid, championship_year, championship_name)
VALUES ('79c4bd1a-3cdb-46f4-a3cc-11ab2587af6e', '2025', '2025 Formula One World Championship');

INSERT INTO championship_leaderboard (championship_leaderboard_uid, championship_season_id, championship_leaderboard_name, created_at, leaderboard_type, owner_account_id)
VALUES ('d1b4f8e2-2f3a-4c4e-9f7a-2e8b6c5d9f1a', (SELECT id FROM championship_season WHERE championship_year = '2025'), '2025 Global Championship', NOW(), 'GLOBAL_DEFAULT', NULL);

-- Round 1: Australian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('73567e11-c707-5b90-ad63-2cf3215b8295', (SELECT id FROM championship_season WHERE championship_year = '2025'), 1, 'AUSTRALIA', 'AU', '2025-03-14', '2025-03-16');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('28cfccab-1e08-58c3-bbfd-c37b5b408b8e', (SELECT id FROM race_weekend WHERE race_weekend_uid = '73567e11-c707-5b90-ad63-2cf3215b8295'), 'PRACTICE_1', '2025-03-14T01:30:00Z'),
    ('7f5bc88d-ced5-5fbe-a306-6bd8637b1c95', (SELECT id FROM race_weekend WHERE race_weekend_uid = '73567e11-c707-5b90-ad63-2cf3215b8295'), 'PRACTICE_2', '2025-03-14T05:00:00Z'),
    ('a49f8db1-c8a3-5be3-8d7f-dfcd09c4e736', (SELECT id FROM race_weekend WHERE race_weekend_uid = '73567e11-c707-5b90-ad63-2cf3215b8295'), 'PRACTICE_3', '2025-03-15T01:30:00Z'),
    ('41660098-de2c-52cb-bc61-dbc7824b6633', (SELECT id FROM race_weekend WHERE race_weekend_uid = '73567e11-c707-5b90-ad63-2cf3215b8295'), 'QUALIFYING', '2025-03-15T05:00:00Z'),
    ('e43bdc76-a54f-50af-b155-32ed97cd4d50', (SELECT id FROM race_weekend WHERE race_weekend_uid = '73567e11-c707-5b90-ad63-2cf3215b8295'), 'RACE', '2025-03-16T04:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '73567e11-c707-5b90-ad63-2cf3215b8295'), NOW(), 'UPCOMING');

-- Round 2: Chinese Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('7f996ee5-1ba2-5056-807e-8dcdd76219a1', (SELECT id FROM championship_season WHERE championship_year = '2025'), 2, 'CHINA', 'CN', '2025-03-21', '2025-03-23');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('8467ed3f-67f9-5323-84e7-ed6214ac2f8b', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7f996ee5-1ba2-5056-807e-8dcdd76219a1'), 'PRACTICE_1', '2025-03-21T03:30:00Z'),
    ('03c6b4dd-fccd-5af3-835d-edfcdaa7e1bf', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7f996ee5-1ba2-5056-807e-8dcdd76219a1'), 'SPRINT_QUALIFYING', '2025-03-21T07:30:00Z'),
    ('7a695a41-226f-5bca-a964-9a8b40c11889', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7f996ee5-1ba2-5056-807e-8dcdd76219a1'), 'SPRINT', '2025-03-22T03:00:00Z'),
    ('77595e88-8a12-545e-826a-4d0ad54f92e7', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7f996ee5-1ba2-5056-807e-8dcdd76219a1'), 'QUALIFYING', '2025-03-22T07:00:00Z'),
    ('14d4553e-fe41-5d5d-a447-6e202b00c33d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7f996ee5-1ba2-5056-807e-8dcdd76219a1'), 'RACE', '2025-03-23T07:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '7f996ee5-1ba2-5056-807e-8dcdd76219a1'), NOW(), 'UPCOMING');

-- Round 3: Japanese Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('69522899-66c9-546a-acfc-5e1ae0454d67', (SELECT id FROM championship_season WHERE championship_year = '2025'), 3, 'JAPAN', 'JP', '2025-04-04', '2025-04-06');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('60a028a7-6f73-5f27-ad7c-d6eb7a80eede', (SELECT id FROM race_weekend WHERE race_weekend_uid = '69522899-66c9-546a-acfc-5e1ae0454d67'), 'PRACTICE_1', '2025-04-04T02:30:00Z'),
    ('a0164db3-e7b2-5533-a32e-dccd5ac88ff9', (SELECT id FROM race_weekend WHERE race_weekend_uid = '69522899-66c9-546a-acfc-5e1ae0454d67'), 'PRACTICE_2', '2025-04-04T06:00:00Z'),
    ('0cca2304-09c7-5b1b-a644-a7913be5255a', (SELECT id FROM race_weekend WHERE race_weekend_uid = '69522899-66c9-546a-acfc-5e1ae0454d67'), 'PRACTICE_3', '2025-04-05T02:30:00Z'),
    ('e4a869be-9ce9-5900-84a2-0bc8a770da4c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '69522899-66c9-546a-acfc-5e1ae0454d67'), 'QUALIFYING', '2025-04-05T06:00:00Z'),
    ('3f9bb273-3347-5ad3-9904-89edc267053b', (SELECT id FROM race_weekend WHERE race_weekend_uid = '69522899-66c9-546a-acfc-5e1ae0454d67'), 'RACE', '2025-04-06T05:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '69522899-66c9-546a-acfc-5e1ae0454d67'), NOW(), 'UPCOMING');

-- Round 4: Bahrain Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('3d4e33d1-abec-513b-b25e-940300978b5e', (SELECT id FROM championship_season WHERE championship_year = '2025'), 4, 'BAHRAIN', 'BH', '2025-04-11', '2025-04-13');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('e9dcb90a-3069-5af9-8f0b-3133db68b9d6', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3d4e33d1-abec-513b-b25e-940300978b5e'), 'PRACTICE_1', '2025-04-11T11:30:00Z'),
    ('b4c4ccd6-7efd-5fbc-a8f3-e9e32f4a74b7', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3d4e33d1-abec-513b-b25e-940300978b5e'), 'PRACTICE_2', '2025-04-11T15:00:00Z'),
    ('362e50f4-5826-5e0b-9c74-a67b34c3c8bc', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3d4e33d1-abec-513b-b25e-940300978b5e'), 'PRACTICE_3', '2025-04-12T12:30:00Z'),
    ('dcd23f61-43d1-5840-ad0f-1448a5898a4d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3d4e33d1-abec-513b-b25e-940300978b5e'), 'QUALIFYING', '2025-04-12T16:00:00Z'),
    ('12956b86-d1d8-5bd2-afd1-64d2b6926f73', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3d4e33d1-abec-513b-b25e-940300978b5e'), 'RACE', '2025-04-13T15:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '3d4e33d1-abec-513b-b25e-940300978b5e'), NOW(), 'UPCOMING');

-- Round 5: Saudi Arabian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('5ae69d83-c72b-5dc2-9441-5c195d0c2d71', (SELECT id FROM championship_season WHERE championship_year = '2025'), 5, 'SAUDI_ARABIA', 'SA', '2025-04-18', '2025-04-20');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('f732e2ba-47c7-5581-a6dc-0c4fa4fea0da', (SELECT id FROM race_weekend WHERE race_weekend_uid = '5ae69d83-c72b-5dc2-9441-5c195d0c2d71'), 'PRACTICE_1', '2025-04-18T13:30:00Z'),
    ('e07cfd64-b457-53dc-9d67-614f1d5f87f3', (SELECT id FROM race_weekend WHERE race_weekend_uid = '5ae69d83-c72b-5dc2-9441-5c195d0c2d71'), 'PRACTICE_2', '2025-04-18T17:00:00Z'),
    ('a128df88-26fe-5cde-bd98-a01d3ebc7093', (SELECT id FROM race_weekend WHERE race_weekend_uid = '5ae69d83-c72b-5dc2-9441-5c195d0c2d71'), 'PRACTICE_3', '2025-04-19T13:30:00Z'),
    ('8c81bcc2-0427-559f-a30b-79b1f4f5254c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '5ae69d83-c72b-5dc2-9441-5c195d0c2d71'), 'QUALIFYING', '2025-04-19T17:00:00Z'),
    ('6372d055-0e91-5ef4-93e8-377598de5ba2', (SELECT id FROM race_weekend WHERE race_weekend_uid = '5ae69d83-c72b-5dc2-9441-5c195d0c2d71'), 'RACE', '2025-04-20T17:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '5ae69d83-c72b-5dc2-9441-5c195d0c2d71'), NOW(), 'UPCOMING');

-- Round 6: Miami Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('07bac144-3259-5e7c-9ec5-e06a9b665f09', (SELECT id FROM championship_season WHERE championship_year = '2025'), 6, 'MIAMI', 'US', '2025-05-02', '2025-05-04');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('4a988dbf-ec73-5003-aa8e-e2ac167ff025', (SELECT id FROM race_weekend WHERE race_weekend_uid = '07bac144-3259-5e7c-9ec5-e06a9b665f09'), 'PRACTICE_1', '2025-05-02T16:30:00Z'),
    ('96cbe924-4f72-514b-a665-f968c933aa26', (SELECT id FROM race_weekend WHERE race_weekend_uid = '07bac144-3259-5e7c-9ec5-e06a9b665f09'), 'SPRINT_QUALIFYING', '2025-05-02T20:30:00Z'),
    ('3a03feb9-26c2-5e00-966e-5bb1285cf71f', (SELECT id FROM race_weekend WHERE race_weekend_uid = '07bac144-3259-5e7c-9ec5-e06a9b665f09'), 'SPRINT', '2025-05-03T16:00:00Z'),
    ('caf7ad1d-ba52-5dd4-b83f-b6c081331723', (SELECT id FROM race_weekend WHERE race_weekend_uid = '07bac144-3259-5e7c-9ec5-e06a9b665f09'), 'QUALIFYING', '2025-05-03T20:00:00Z'),
    ('dd06b7c5-01b4-5844-9700-9fe5b3df6cbc', (SELECT id FROM race_weekend WHERE race_weekend_uid = '07bac144-3259-5e7c-9ec5-e06a9b665f09'), 'RACE', '2025-05-04T20:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '07bac144-3259-5e7c-9ec5-e06a9b665f09'), NOW(), 'UPCOMING');

-- Round 7: Emilia Romagna Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('b91cc046-8265-5be9-a1ed-aa5fe739aa69', (SELECT id FROM championship_season WHERE championship_year = '2025'), 7, 'EMILIA_ROMAGNA', 'IT', '2025-05-16', '2025-05-18');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('633de601-708f-597f-813e-cd8bcccad6cc', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b91cc046-8265-5be9-a1ed-aa5fe739aa69'), 'PRACTICE_1', '2025-05-16T11:30:00Z'),
    ('ee55c6d9-c979-5036-952e-d892fe70ed64', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b91cc046-8265-5be9-a1ed-aa5fe739aa69'), 'PRACTICE_2', '2025-05-16T15:00:00Z'),
    ('c5e9ee2d-a49f-57e3-952b-55136f145495', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b91cc046-8265-5be9-a1ed-aa5fe739aa69'), 'PRACTICE_3', '2025-05-17T10:30:00Z'),
    ('b6aeb671-b580-53b2-8b3b-94343bd01ab4', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b91cc046-8265-5be9-a1ed-aa5fe739aa69'), 'QUALIFYING', '2025-05-17T14:00:00Z'),
    ('be883c59-40a5-53f5-8241-ddd00de7c619', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b91cc046-8265-5be9-a1ed-aa5fe739aa69'), 'RACE', '2025-05-18T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'b91cc046-8265-5be9-a1ed-aa5fe739aa69'), NOW(), 'UPCOMING');

-- Round 8: Monaco Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('d5dbf9dd-08ae-5b10-b021-335ef33c06a1', (SELECT id FROM championship_season WHERE championship_year = '2025'), 8, 'MONACO', 'MC', '2025-05-23', '2025-05-25');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('d4c0b0c7-3698-5c5a-9003-07ce06deb116', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd5dbf9dd-08ae-5b10-b021-335ef33c06a1'), 'PRACTICE_1', '2025-05-23T11:30:00Z'),
    ('df573def-5073-5240-b0ec-29e3d21b992e', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd5dbf9dd-08ae-5b10-b021-335ef33c06a1'), 'PRACTICE_2', '2025-05-23T15:00:00Z'),
    ('1a72b2e5-f51a-57f2-839b-d7a62e76a903', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd5dbf9dd-08ae-5b10-b021-335ef33c06a1'), 'PRACTICE_3', '2025-05-24T10:30:00Z'),
    ('9cb47dfd-14f5-5296-bdc5-39722d37d1d9', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd5dbf9dd-08ae-5b10-b021-335ef33c06a1'), 'QUALIFYING', '2025-05-24T14:00:00Z'),
    ('ccf61759-3bb5-5a9d-8b53-1253a181de51', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd5dbf9dd-08ae-5b10-b021-335ef33c06a1'), 'RACE', '2025-05-25T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'd5dbf9dd-08ae-5b10-b021-335ef33c06a1'), NOW(), 'UPCOMING');

-- Round 9: Spanish Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('c0e233c2-38f8-55b0-86cc-6cca197b410f', (SELECT id FROM championship_season WHERE championship_year = '2025'), 9, 'SPAIN', 'ES', '2025-05-30', '2025-06-01');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('f225cd0a-226b-5055-b494-c7d1cfe46ea1', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'c0e233c2-38f8-55b0-86cc-6cca197b410f'), 'PRACTICE_1', '2025-05-30T11:30:00Z'),
    ('62ac1f49-8cca-5c7b-9f51-d827f070b369', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'c0e233c2-38f8-55b0-86cc-6cca197b410f'), 'PRACTICE_2', '2025-05-30T15:00:00Z'),
    ('c11a1252-77fb-50b6-adab-f6f67e2ddf01', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'c0e233c2-38f8-55b0-86cc-6cca197b410f'), 'PRACTICE_3', '2025-05-31T10:30:00Z'),
    ('f6dd2068-d6c5-5850-965f-f07d1791eb3c', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'c0e233c2-38f8-55b0-86cc-6cca197b410f'), 'QUALIFYING', '2025-05-31T14:00:00Z'),
    ('9fc5d3a7-de1f-5476-9a7f-4c61a39b8382', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'c0e233c2-38f8-55b0-86cc-6cca197b410f'), 'RACE', '2025-06-01T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'c0e233c2-38f8-55b0-86cc-6cca197b410f'), NOW(), 'UPCOMING');

-- Round 10: Canadian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('eb79001b-81cb-5c6e-bb18-32defa6248a9', (SELECT id FROM championship_season WHERE championship_year = '2025'), 10, 'CANADA', 'CA', '2025-06-13', '2025-06-15');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('4aac430b-3d56-56b3-9dfb-2865f5298e37', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'eb79001b-81cb-5c6e-bb18-32defa6248a9'), 'PRACTICE_1', '2025-06-13T17:30:00Z'),
    ('147e9bb4-bc6d-503d-8bf5-53d75d529df1', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'eb79001b-81cb-5c6e-bb18-32defa6248a9'), 'PRACTICE_2', '2025-06-13T21:00:00Z'),
    ('48232b7f-5a61-55e6-80a8-021631a7184a', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'eb79001b-81cb-5c6e-bb18-32defa6248a9'), 'PRACTICE_3', '2025-06-14T16:30:00Z'),
    ('599e5e85-7f87-5297-9602-f7d79e8bf722', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'eb79001b-81cb-5c6e-bb18-32defa6248a9'), 'QUALIFYING', '2025-06-14T20:00:00Z'),
    ('e9589771-61bf-5eb3-84a0-38e74208af0a', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'eb79001b-81cb-5c6e-bb18-32defa6248a9'), 'RACE', '2025-06-15T18:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'eb79001b-81cb-5c6e-bb18-32defa6248a9'), NOW(), 'UPCOMING');

-- Round 11: Austrian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('e594fd1a-eecf-5fbc-998c-0c04e4def5c7', (SELECT id FROM championship_season WHERE championship_year = '2025'), 11, 'AUSTRIA', 'AT', '2025-06-27', '2025-06-29');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('32ab9c92-2654-59af-8bd1-1d0214fc285b', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'e594fd1a-eecf-5fbc-998c-0c04e4def5c7'), 'PRACTICE_1', '2025-06-27T11:30:00Z'),
    ('b82799bd-212e-58c8-a111-1485f46a4a24', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'e594fd1a-eecf-5fbc-998c-0c04e4def5c7'), 'PRACTICE_2', '2025-06-27T15:00:00Z'),
    ('35b241d4-358a-5869-969e-51074cd448a3', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'e594fd1a-eecf-5fbc-998c-0c04e4def5c7'), 'PRACTICE_3', '2025-06-28T10:30:00Z'),
    ('4f323306-e08f-52b3-aa2d-c100931baad4', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'e594fd1a-eecf-5fbc-998c-0c04e4def5c7'), 'QUALIFYING', '2025-06-28T14:00:00Z'),
    ('f2066a63-79c5-5ddc-a50d-47725efd46ae', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'e594fd1a-eecf-5fbc-998c-0c04e4def5c7'), 'RACE', '2025-06-29T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'e594fd1a-eecf-5fbc-998c-0c04e4def5c7'), NOW(), 'UPCOMING');

-- Round 12: British Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('821febfc-0037-5595-9350-76ed2c25ff72', (SELECT id FROM championship_season WHERE championship_year = '2025'), 12, 'GREAT_BRITAIN', 'GB', '2025-07-04', '2025-07-06');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('5193e82d-bf5a-5c0e-b7a9-fd5b5165e7be', (SELECT id FROM race_weekend WHERE race_weekend_uid = '821febfc-0037-5595-9350-76ed2c25ff72'), 'PRACTICE_1', '2025-07-04T11:30:00Z'),
    ('aa43eed1-f8df-5b76-8aa0-29bcd085a3db', (SELECT id FROM race_weekend WHERE race_weekend_uid = '821febfc-0037-5595-9350-76ed2c25ff72'), 'PRACTICE_2', '2025-07-04T15:00:00Z'),
    ('ba280a0b-c99e-51bd-9204-10e4bc32785c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '821febfc-0037-5595-9350-76ed2c25ff72'), 'PRACTICE_3', '2025-07-05T10:30:00Z'),
    ('46edae88-e6e7-59d1-be08-513e0b0589b5', (SELECT id FROM race_weekend WHERE race_weekend_uid = '821febfc-0037-5595-9350-76ed2c25ff72'), 'QUALIFYING', '2025-07-05T14:00:00Z'),
    ('963b4e6f-ac94-5536-b8f5-10d09ba7fce3', (SELECT id FROM race_weekend WHERE race_weekend_uid = '821febfc-0037-5595-9350-76ed2c25ff72'), 'RACE', '2025-07-06T14:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '821febfc-0037-5595-9350-76ed2c25ff72'), NOW(), 'UPCOMING');

-- Round 13: Belgian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('f2a5040f-91af-501d-86dc-7d2df916e575', (SELECT id FROM championship_season WHERE championship_year = '2025'), 13, 'BELGIUM', 'BE', '2025-07-25', '2025-07-27');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('d81d8ac4-454b-5b79-8676-de5602cc774e', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'f2a5040f-91af-501d-86dc-7d2df916e575'), 'PRACTICE_1', '2025-07-25T10:30:00Z'),
    ('804a00cd-5948-5227-ab90-6918a54dd33b', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'f2a5040f-91af-501d-86dc-7d2df916e575'), 'SPRINT_QUALIFYING', '2025-07-25T14:30:00Z'),
    ('aef66d1b-caf7-5c1b-b4e8-002d10c586e8', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'f2a5040f-91af-501d-86dc-7d2df916e575'), 'SPRINT', '2025-07-26T10:00:00Z'),
    ('fd808287-5bb2-57f4-8c8f-32f818b32c2b', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'f2a5040f-91af-501d-86dc-7d2df916e575'), 'QUALIFYING', '2025-07-26T14:00:00Z'),
    ('11ad32ea-9458-5e9a-b08f-37a32b784c39', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'f2a5040f-91af-501d-86dc-7d2df916e575'), 'RACE', '2025-07-27T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'f2a5040f-91af-501d-86dc-7d2df916e575'), NOW(), 'UPCOMING');

-- Round 14: Hungarian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('de006d10-1bfb-5788-85be-f71b37128487', (SELECT id FROM championship_season WHERE championship_year = '2025'), 14, 'HUNGARY', 'HU', '2025-08-01', '2025-08-03');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('c41b7c1e-e9bc-5a99-ba00-af035ab94205', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'de006d10-1bfb-5788-85be-f71b37128487'), 'PRACTICE_1', '2025-08-01T11:30:00Z'),
    ('ba39f0b7-04c1-54a3-8227-109221334e09', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'de006d10-1bfb-5788-85be-f71b37128487'), 'PRACTICE_2', '2025-08-01T15:00:00Z'),
    ('49dad6cb-2f36-5919-947d-4870a1b6a223', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'de006d10-1bfb-5788-85be-f71b37128487'), 'PRACTICE_3', '2025-08-02T10:30:00Z'),
    ('c4e72d04-322b-53b5-9d83-ba34eb59497d', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'de006d10-1bfb-5788-85be-f71b37128487'), 'QUALIFYING', '2025-08-02T14:00:00Z'),
    ('8450543f-81e4-5e7f-bcf7-742f89b78d2e', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'de006d10-1bfb-5788-85be-f71b37128487'), 'RACE', '2025-08-03T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'de006d10-1bfb-5788-85be-f71b37128487'), NOW(), 'UPCOMING');

-- Round 15: Dutch Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('7ea097f3-ec76-5bae-92ac-9278eb169edb', (SELECT id FROM championship_season WHERE championship_year = '2025'), 15, 'NETHERLANDS', 'NL', '2025-08-29', '2025-08-31');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('adde84e9-deb6-5c49-a1cb-4efb1e863c3c', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7ea097f3-ec76-5bae-92ac-9278eb169edb'), 'PRACTICE_1', '2025-08-29T10:30:00Z'),
    ('32ff188a-a741-5e4c-b542-a7d64e9abff9', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7ea097f3-ec76-5bae-92ac-9278eb169edb'), 'PRACTICE_2', '2025-08-29T14:00:00Z'),
    ('b3708743-ece5-5ad8-af4e-bb31abf25ddc', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7ea097f3-ec76-5bae-92ac-9278eb169edb'), 'PRACTICE_3', '2025-08-30T09:30:00Z'),
    ('badb630b-5fc3-5b4e-a470-bc71836ddc54', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7ea097f3-ec76-5bae-92ac-9278eb169edb'), 'QUALIFYING', '2025-08-30T13:00:00Z'),
    ('e5c48cd4-3cfd-5737-99e4-d2aae7b17056', (SELECT id FROM race_weekend WHERE race_weekend_uid = '7ea097f3-ec76-5bae-92ac-9278eb169edb'), 'RACE', '2025-08-31T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '7ea097f3-ec76-5bae-92ac-9278eb169edb'), NOW(), 'UPCOMING');

-- Round 16: Italian Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('d2f37a0b-74b9-5e9a-9283-11c976d89402', (SELECT id FROM championship_season WHERE championship_year = '2025'), 16, 'ITALY', 'IT', '2025-09-05', '2025-09-07');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('a5a71edc-60b8-52f0-bf43-51485608ee33', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd2f37a0b-74b9-5e9a-9283-11c976d89402'), 'PRACTICE_1', '2025-09-05T11:30:00Z'),
    ('a1df4ebf-ea1c-56be-b547-7d17bf59afa4', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd2f37a0b-74b9-5e9a-9283-11c976d89402'), 'PRACTICE_2', '2025-09-05T15:00:00Z'),
    ('0c4b75c8-99e1-5574-b0a4-75be3146658c', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd2f37a0b-74b9-5e9a-9283-11c976d89402'), 'PRACTICE_3', '2025-09-06T10:30:00Z'),
    ('1968d849-e0c4-5eab-92e6-acd7f35e142d', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd2f37a0b-74b9-5e9a-9283-11c976d89402'), 'QUALIFYING', '2025-09-06T14:00:00Z'),
    ('77544484-5807-5e0e-b4b5-3c51a7afb65c', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'd2f37a0b-74b9-5e9a-9283-11c976d89402'), 'RACE', '2025-09-07T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'd2f37a0b-74b9-5e9a-9283-11c976d89402'), NOW(), 'UPCOMING');

-- Round 17: Azerbaijan Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('fd3dc3fe-469c-5ba6-93d3-6945c8e35593', (SELECT id FROM championship_season WHERE championship_year = '2025'), 17, 'AZERBAIJAN', 'AZ', '2025-09-19', '2025-09-21');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('9d020535-4f38-5d60-935e-8f4e714de7dc', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'fd3dc3fe-469c-5ba6-93d3-6945c8e35593'), 'PRACTICE_1', '2025-09-19T08:30:00Z'),
    ('5bf64640-bb0b-56cf-981d-632627641e50', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'fd3dc3fe-469c-5ba6-93d3-6945c8e35593'), 'PRACTICE_2', '2025-09-19T12:00:00Z'),
    ('91676d6b-e98d-51e1-b0b6-fb26312191a6', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'fd3dc3fe-469c-5ba6-93d3-6945c8e35593'), 'PRACTICE_3', '2025-09-20T08:30:00Z'),
    ('4583621b-fb23-5ede-9612-64b58f9ad5c0', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'fd3dc3fe-469c-5ba6-93d3-6945c8e35593'), 'QUALIFYING', '2025-09-20T12:00:00Z'),
    ('28d7504a-df9d-5b3c-bae5-1132508e0cab', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'fd3dc3fe-469c-5ba6-93d3-6945c8e35593'), 'RACE', '2025-09-21T11:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'fd3dc3fe-469c-5ba6-93d3-6945c8e35593'), NOW(), 'UPCOMING');

-- Round 18: Singapore Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('3a3c97e4-7080-59b7-b2b5-145281b80dea', (SELECT id FROM championship_season WHERE championship_year = '2025'), 18, 'SINGAPORE', 'SG', '2025-10-03', '2025-10-05');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('8ba2a567-39f3-5b99-9296-426768d35848', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3a3c97e4-7080-59b7-b2b5-145281b80dea'), 'PRACTICE_1', '2025-10-03T09:30:00Z'),
    ('b1653949-8391-559e-8d18-8a003b20f6a9', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3a3c97e4-7080-59b7-b2b5-145281b80dea'), 'PRACTICE_2', '2025-10-03T13:00:00Z'),
    ('5efc842f-d39d-5ecd-b0a7-10f0ce32b6ae', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3a3c97e4-7080-59b7-b2b5-145281b80dea'), 'PRACTICE_3', '2025-10-04T09:30:00Z'),
    ('445fce06-36cb-54e2-9e30-b323071efb83', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3a3c97e4-7080-59b7-b2b5-145281b80dea'), 'QUALIFYING', '2025-10-04T13:00:00Z'),
    ('1ae0ae50-4c7b-5aad-9d59-9d65945bc435', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3a3c97e4-7080-59b7-b2b5-145281b80dea'), 'RACE', '2025-10-05T12:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '3a3c97e4-7080-59b7-b2b5-145281b80dea'), NOW(), 'UPCOMING');

-- Round 19: United States Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('0edc3b7e-2928-50d0-aac1-c07e9227ed6d', (SELECT id FROM championship_season WHERE championship_year = '2025'), 19, 'UNITED_STATES', 'US', '2025-10-17', '2025-10-19');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('316fe524-3071-5ab2-9865-085e63fbe50d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0edc3b7e-2928-50d0-aac1-c07e9227ed6d'), 'PRACTICE_1', '2025-10-17T17:30:00Z'),
    ('519beb2a-3a1f-5ff6-83ec-1c92c20698c3', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0edc3b7e-2928-50d0-aac1-c07e9227ed6d'), 'SPRINT_QUALIFYING', '2025-10-17T21:30:00Z'),
    ('3fae54a2-afa2-5696-8d13-6d19f6cda94d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0edc3b7e-2928-50d0-aac1-c07e9227ed6d'), 'SPRINT', '2025-10-18T17:00:00Z'),
    ('e3fce20d-5dfd-5d91-bd29-d0152a3a1532', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0edc3b7e-2928-50d0-aac1-c07e9227ed6d'), 'QUALIFYING', '2025-10-18T21:00:00Z'),
    ('d6d4687f-a51a-5264-8d2f-a34c73f5ba48', (SELECT id FROM race_weekend WHERE race_weekend_uid = '0edc3b7e-2928-50d0-aac1-c07e9227ed6d'), 'RACE', '2025-10-19T19:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '0edc3b7e-2928-50d0-aac1-c07e9227ed6d'), NOW(), 'UPCOMING');

-- Round 20: Mexico City Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('dc11ed14-af62-5586-8fa7-0a427920bfd6', (SELECT id FROM championship_season WHERE championship_year = '2025'), 20, 'MEXICO', 'MX', '2025-10-24', '2025-10-26');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('4fa77db2-0d5a-5644-91e6-97a3368861fa', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'dc11ed14-af62-5586-8fa7-0a427920bfd6'), 'PRACTICE_1', '2025-10-24T18:30:00Z'),
    ('ca7c49dd-ba36-51c2-a6ab-54480cac50f7', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'dc11ed14-af62-5586-8fa7-0a427920bfd6'), 'PRACTICE_2', '2025-10-24T22:00:00Z'),
    ('181d0825-b580-5085-b41c-a5b81b8edc8f', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'dc11ed14-af62-5586-8fa7-0a427920bfd6'), 'PRACTICE_3', '2025-10-25T17:30:00Z'),
    ('fdbd1674-d2d4-5362-a9aa-7db735629b49', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'dc11ed14-af62-5586-8fa7-0a427920bfd6'), 'QUALIFYING', '2025-10-25T21:00:00Z'),
    ('2eb5b9e9-51e1-5cfd-a9f5-a110efa316e3', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'dc11ed14-af62-5586-8fa7-0a427920bfd6'), 'RACE', '2025-10-26T20:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'dc11ed14-af62-5586-8fa7-0a427920bfd6'), NOW(), 'UPCOMING');

-- Round 21: São Paulo Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('b1c94e8e-5a0b-5198-98b3-274a5ad43a08', (SELECT id FROM championship_season WHERE championship_year = '2025'), 21, 'BRAZIL', 'BR', '2025-11-07', '2025-11-09');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('be9b10c6-b5bd-5637-bfe3-97559ba90fb0', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b1c94e8e-5a0b-5198-98b3-274a5ad43a08'), 'PRACTICE_1', '2025-11-07T14:30:00Z'),
    ('806c8a96-f012-5c5a-9f59-530d2069e8be', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b1c94e8e-5a0b-5198-98b3-274a5ad43a08'), 'SPRINT_QUALIFYING', '2025-11-07T18:30:00Z'),
    ('9699c658-031a-5000-91f5-f6449087a541', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b1c94e8e-5a0b-5198-98b3-274a5ad43a08'), 'SPRINT', '2025-11-08T14:00:00Z'),
    ('81d49c31-d2ea-5303-ad45-8d202b3a1f08', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b1c94e8e-5a0b-5198-98b3-274a5ad43a08'), 'QUALIFYING', '2025-11-08T18:00:00Z'),
    ('018d6200-9995-5901-9e21-099d793651fc', (SELECT id FROM race_weekend WHERE race_weekend_uid = 'b1c94e8e-5a0b-5198-98b3-274a5ad43a08'), 'RACE', '2025-11-09T17:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = 'b1c94e8e-5a0b-5198-98b3-274a5ad43a08'), NOW(), 'UPCOMING');

-- Round 22: Las Vegas Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('2522aa78-6972-560a-84d6-dc5d8888ab96', (SELECT id FROM championship_season WHERE championship_year = '2025'), 22, 'LAS_VEGAS', 'US', '2025-11-20', '2025-11-22');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('32d11f6e-8673-5327-a539-dc6beaf5f98d', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2522aa78-6972-560a-84d6-dc5d8888ab96'), 'PRACTICE_1', '2025-11-21T00:30:00Z'),
    ('4d1dc893-9e38-50cb-be5a-9e448297a420', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2522aa78-6972-560a-84d6-dc5d8888ab96'), 'PRACTICE_2', '2025-11-21T04:00:00Z'),
    ('d650b306-165b-5d50-8c8c-918c03b7c695', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2522aa78-6972-560a-84d6-dc5d8888ab96'), 'PRACTICE_3', '2025-11-22T00:30:00Z'),
    ('8ce71f38-afc1-524e-b7fe-f84553247776', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2522aa78-6972-560a-84d6-dc5d8888ab96'), 'QUALIFYING', '2025-11-22T04:00:00Z'),
    ('a8dd1474-c973-519d-86ec-35075e8bb5a4', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2522aa78-6972-560a-84d6-dc5d8888ab96'), 'RACE', '2025-11-23T04:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '2522aa78-6972-560a-84d6-dc5d8888ab96'), NOW(), 'UPCOMING');

-- Round 23: Qatar Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('3cd1bdd5-049f-57ff-9750-cad60ce23a3c', (SELECT id FROM championship_season WHERE championship_year = '2025'), 23, 'QATAR', 'QA', '2025-11-28', '2025-11-30');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('b8d1de0a-adfd-52a1-8d0f-fdef9ca0a3e0', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3cd1bdd5-049f-57ff-9750-cad60ce23a3c'), 'PRACTICE_1', '2025-11-28T13:30:00Z'),
    ('4f07fc04-9e8d-5d87-9c9b-1b9480921dfc', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3cd1bdd5-049f-57ff-9750-cad60ce23a3c'), 'SPRINT_QUALIFYING', '2025-11-28T17:30:00Z'),
    ('54ba3b42-dc11-5f77-a074-4f523cf489f4', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3cd1bdd5-049f-57ff-9750-cad60ce23a3c'), 'SPRINT', '2025-11-29T14:00:00Z'),
    ('5b88b6d9-ab0d-545e-83dd-1fce47df9db1', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3cd1bdd5-049f-57ff-9750-cad60ce23a3c'), 'QUALIFYING', '2025-11-29T18:00:00Z'),
    ('746b03a8-839e-5956-860e-e5ae5373498b', (SELECT id FROM race_weekend WHERE race_weekend_uid = '3cd1bdd5-049f-57ff-9750-cad60ce23a3c'), 'RACE', '2025-11-30T16:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '3cd1bdd5-049f-57ff-9750-cad60ce23a3c'), NOW(), 'UPCOMING');

-- Round 24: Abu Dhabi Grand Prix
INSERT INTO race_weekend (race_weekend_uid, championship_season_id, round_number, race_name, race_location, race_weekend_start_date, race_weekend_end_date)
VALUES ('2e14cd30-bbbf-5b19-a809-06214b794e28', (SELECT id FROM championship_season WHERE championship_year = '2025'), 24, 'ABU_DHABI', 'AE', '2025-12-05', '2025-12-07');
INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at) VALUES
    ('220412f3-080b-579d-98fb-b63ea33797c1', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2e14cd30-bbbf-5b19-a809-06214b794e28'), 'PRACTICE_1', '2025-12-05T09:30:00Z'),
    ('3f6e8e95-0bfa-5119-ac1a-65995bb19dc2', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2e14cd30-bbbf-5b19-a809-06214b794e28'), 'PRACTICE_2', '2025-12-05T13:00:00Z'),
    ('fda6504c-2663-5a31-be42-3f64fd7269d5', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2e14cd30-bbbf-5b19-a809-06214b794e28'), 'PRACTICE_3', '2025-12-06T10:30:00Z'),
    ('7facbec2-fe5e-55ba-81a6-bce9f24cf0bc', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2e14cd30-bbbf-5b19-a809-06214b794e28'), 'QUALIFYING', '2025-12-06T14:00:00Z'),
    ('86f0b226-acb9-5911-95c4-2eb3c9bfaeee', (SELECT id FROM race_weekend WHERE race_weekend_uid = '2e14cd30-bbbf-5b19-a809-06214b794e28'), 'RACE', '2025-12-07T13:00:00Z');
INSERT INTO race_weekend_current_status (race_weekend_id, event_time, status)
VALUES ((SELECT id FROM race_weekend WHERE race_weekend_uid = '2e14cd30-bbbf-5b19-a809-06214b794e28'), NOW(), 'UPCOMING');
