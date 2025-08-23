INSERT INTO users (phone_number, name, is_use) VALUES
                                                ("010-1234-5678", "백두팔", true),
                                                ( "010-3333-6789", "쿠키먹는 쿠키", true),
                                                ( "010-1234-9876", "백무식", true);

-- "배달" 들어가는 회사들 (15개)
INSERT INTO company (ceo_name, name, region) VALUES
                                                 ('김철수', '배달의천국', '서울'),
                                                 ('이영희', '배달특급', '경기'),
                                                 ('박민수', '배달왕', '부산'),
                                                 ('최지현', '배달GO', '인천'),
                                                 ('정우성', '배달24', '대전'),
                                                 ('김하늘', '배달One', '광주'),
                                                 ('강호동', '배달친구', '울산'),
                                                 ('신동엽', '배달마스터', '대구'),
                                                 ('유재석', '배달코리아', '서울'),
                                                 ('하하', '배달타임', '경기'),
                                                 ('정형돈', '배달센터', '부산'),
                                                 ('노홍철', '배달365', '대전'),
                                                 ('이수근', '배달라이더', '서울'),
                                                 ('김종국', '배달프렌즈', '경기'),
                                                 ('송지효', '배달피플', '인천'),

                                                 -- "배달" 들어가지 않는 회사들 (5개)
                                                 ('임창정', '퀵서비스코리아', '서울'),
                                                 ('장나라', '빠른택배', '부산'),
                                                 ('이준호', '라이더스클럽', '대구'),
                                                 ('김태희', '오늘의배송', '경기'),
                                                 ('현빈', '스피드익스프레스', '인천');

-- user_id = 1 (5개)
INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('10', 1, 1, NOW(), NOW(), '2025-07-05 10:00:00', '20'); -- 개발·데이터

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('20', 3, 1, NOW(), NOW(), '2025-07-15 14:00:00', '21'); -- 디자인

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('30', 5, 1, NOW(), NOW(), '2025-07-28 09:30:00', '10'); -- 기획·전략

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('40', 7, 1, NOW(), NOW(), '2025-08-02 16:00:00', '11'); -- 마케팅·홍보

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('10', 9, 1, NOW(), NOW(), '2025-08-20 11:00:00', '30'); -- 영업·고객관리

-- user_id = 2 (5개)
INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('20', 2, 2, NOW(), NOW(), '2025-07-08 15:00:00', '32'); -- 운전·운송·배송

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('30', 4, 2, NOW(), NOW(), '2025-07-22 13:00:00', '40'); -- 서비스

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('40', 6, 2, NOW(), NOW(), '2025-08-01 10:30:00', '60'); -- 금융·보험

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('10', 8, 2, NOW(), NOW(), '2025-08-10 09:00:00', '70'); -- 공공·복지

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('20', 10, 2, NOW(), NOW(), '2025-08-18 17:00:00', '50'); -- 건설·건축

-- user_id = 3 (5개)
INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('30', 11, 3, NOW(), NOW(), '2025-07-03 14:00:00', '12'); -- 회계·세무·재무

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('40', 12, 3, NOW(), NOW(), '2025-07-18 10:00:00', '13'); -- 인사·노무·HRD

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('10', 13, 3, NOW(), NOW(), '2025-07-30 11:30:00', '14'); -- 총무·법무·사무

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('20', 14, 3, NOW(), NOW(), '2025-08-08 15:30:00', '31'); -- 구매·자재·물류

INSERT INTO schedule (interview_step, company_id, user_id, created_at, updated_at, interview_datetime, position)
VALUES ('30', 15, 3, NOW(), NOW(), '2025-08-21 09:30:00', '20'); -- 개발·데이터

-- 유저 검색 기록 추가
INSERT INTO user_search_hist (user_id, content) VALUES
                                                    (1, '배달'),
                                                    (1, '대기업'),
                                                    (1, '소기업'),
                                                    (1, '무식한'),
                                                    (1, '민족'),
                                                    (2, '토스'),
                                                    (2, '카카오'),
                                                    (3, '12121'),
                                                    (3, '배달의민'),
                                                    (3, '배');

-- Memoir (User 1, Quick 1)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (1, 1, '10', '배달의천국', '20', '2025-07-05 10:00:00',
     '10', '20', '10', '10', '10', '10',
     '첫 번째 퀵회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions (퀵 → answer 없음)
INSERT INTO question (memoir_id, question_type, title, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '10', '자기소개를 해보세요.', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '20', '지원 직무에 필요한 역량은?', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '30', '가장 기억에 남는 프로젝트는?', 3, true, NOW(), NOW());


-- Memoir (User 1, Quick 2)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (1, 2, '10', '배달특급', '21', '2025-07-15 14:00:00',
     '20', '30', '20', '20', '20', '20',
     '두 번째 퀵회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions (퀵 → answer 없음)
INSERT INTO question (memoir_id, question_type, title, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '10', '장단점을 말해보세요.', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '40', '우리 회사를 왜 지원했나요?', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '50', '이전에 말한 프로젝트에서 어려움은?', 3, true, NOW(), NOW());


-- Memoir (User 1, General 1)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (1, 3, '20', '배달왕', '10', '2025-07-28 09:30:00',
     '30', '40', '30', '10', '10', '30',
     '첫 번째 일반회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions (일반 → answer 필수)
INSERT INTO question (memoir_id, question_type, title, answer, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '20', '직무 관련 경험은?', '배달물류 시스템 개발 경험', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '30', '가장 어려웠던 문제는?', '서버 트래픽 폭증 해결 경험', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '10', '압박 질문 시 대응?', '차분히 문제 해결 접근', 3, true, NOW(), NOW());


-- Memoir (User 1, General 2)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (1, 4, '20', '배달GO', '11', '2025-08-02 16:00:00',
     '40', '50', '10', '20', '20', '40',
     '두 번째 일반회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions (일반 → answer 필수)
INSERT INTO question (memoir_id, question_type, title, answer, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '20', '마케팅 성과 경험?', 'SNS 캠페인 성과 향상', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '40', '우리 회사에 기여할 점?', '배달 서비스 홍보 전략 제안', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '30', '동료와 갈등 해결 경험?', '고객 대응 방식 협의', 3, true, NOW(), NOW());

-- Memoir (User 2, Quick 1)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (2, 6, '10', '배달One', '32', '2025-07-08 15:00:00',
     '10', '10', '20', '30', '10', '10',
     '두 번째 유저 첫 번째 퀵회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions (퀵 → answer 없음)
INSERT INTO question (memoir_id, question_type, title, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '10', '압박 질문이 있었나요?', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '20', '지원 직무에서 가장 중요한 역량은?', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '30', '프로젝트 협업 경험을 말해보세요.', 3, true, NOW(), NOW());


-- Memoir (User 2, Quick 2)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (2, 7, '10', '배달친구', '40', '2025-07-22 13:00:00',
     '20', '20', '10', '10', '20', '20',
     '두 번째 유저 두 번째 퀵회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions
INSERT INTO question (memoir_id, question_type, title, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '40', '이 회사를 지원한 이유는?', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '10', '자신의 성격 장단점은?', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '50', '앞 질문에 대한 추가 꼬리 질문은?', 3, true, NOW(), NOW());


-- Memoir (User 2, General 1)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (2, 8, '20', '배달마스터', '60', '2025-08-01 10:30:00',
     '30', '30', '20', '20', '10', '30',
     '두 번째 유저 첫 번째 일반회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions (일반 → answer 필수)
INSERT INTO question (memoir_id, question_type, title, answer, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '20', '재무 분석 경험은?', '프로젝트 재무 데이터 분석 경험 설명', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '30', '가장 어려웠던 분석 과제는?', '데이터 정제 과정에서 발생한 오류 해결', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '10', '압박 질문 대처법은?', '침착하게 데이터 근거를 제시', 3, true, NOW(), NOW());


-- Memoir (User 2, General 2)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (2, 9, '20', '배달코리아', '70', '2025-08-10 09:00:00',
     '40', '40', '30', '20', '20', '40',
     '두 번째 유저 두 번째 일반회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions
INSERT INTO question (memoir_id, question_type, title, answer, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '40', '공공·복지 관련 경험은?', '지역 사회 봉사 프로젝트 참여 경험', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '30', '팀 갈등 해결 경험은?', '업무 분담 재조정으로 갈등 해결', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '20', '지원 직무에 대한 열정은?', '복지 정책 개발 목표 설명', 3, true, NOW(), NOW());

-- Memoir (User 3, Quick 1)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (3, 11, '10', '배달타임', '12', '2025-07-03 14:00:00',
     '10', '20', '10', '10', '10', '10',
     '세 번째 유저 첫 번째 퀵회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions
INSERT INTO question (memoir_id, question_type, title, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '10', '회계 직무 지원 동기는?', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '20', '세무 관련 경험은?', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '30', '재무 보고 경험은?', 3, true, NOW(), NOW());


-- Memoir (User 3, Quick 2)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (3, 12, '10', '배달센터', '13', '2025-07-18 10:00:00',
     '20', '50', '20', '30', '20', '20',
     '세 번째 유저 두 번째 퀵회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions
INSERT INTO question (memoir_id, question_type, title, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '40', 'HR 관련 경험은?', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '10', '자신의 강점은 무엇인가요?', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '50', '앞 질문에서 언급한 강점의 사례는?', 3, true, NOW(), NOW());


-- Memoir (User 3, General 1)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (3, 13, '20', '배달365', '14', '2025-07-30 11:30:00',
     '30', '40', '30', '10', '10', '30',
     '세 번째 유저 첫 번째 일반회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions
INSERT INTO question (memoir_id, question_type, title, answer, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '20', '법무 관련 경험은?', '계약 검토 경험', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '30', '어려웠던 법적 이슈 해결은?', '계약 분쟁 조율 경험', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '10', '압박 질문 대응은?', '법적 근거 중심으로 대응', 3, true, NOW(), NOW());


-- Memoir (User 3, General 2)
INSERT INTO memoir (user_id, schedule_id, type, company_name, position, interview_datetime,
                    interview_format, interview_mood, satisfaction_note, interview_status,
                    interview_method, interview_step, free_note, like_count, view_count,
                    is_tmp, is_public, is_use, created_at, updated_at)
VALUES
    (3, 14, '20', '배달라이더', '31', '2025-08-08 15:30:00',
     '40', '30', '20', '10', '20', '40',
     '세 번째 유저 두 번째 일반회고', 0, 0, false, true, true, NOW(), NOW());

-- Questions
INSERT INTO question (memoir_id, question_type, title, answer, display_order, is_use, created_at, updated_at)
VALUES
    (LAST_INSERT_ID(), '30', '물류 최적화 경험은?', '배송 효율화 프로젝트 참여 경험', 1, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '40', '우리 회사 물류 개선 아이디어는?', 'AI 기반 배송 경로 추천', 2, true, NOW(), NOW()),
    (LAST_INSERT_ID(), '20', '물류 관리 직무 역량은?', '창고 재고 관리 자동화 경험', 3, true, NOW(), NOW());

-- ===== User 1의 첫 번째 Memoir (id=1) =====

-- 댓글 1 (user 2 작성)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (NULL, '좋은 회고네요! 많은 도움이 되었습니다.', 2, 1, true, NOW(), NOW());

-- 댓글 1에 대한 대댓글 (user 1 작성)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (LAST_INSERT_ID(), '감사합니다! 도움이 되셨다니 기쁘네요.', 1, 1, true, NOW(), NOW());

-- 댓글 2 (user 3 작성, 대댓글 없음)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (NULL, '저도 면접 준비하면서 참고해야겠어요.', 3, 1, true, NOW(), NOW());


-- ===== User 2의 첫 번째 Memoir (id=5) =====

-- 댓글 1 (user 1 작성)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (NULL, '경험을 솔직하게 적어주셔서 좋아요.', 1, 5, true, NOW(), NOW());

-- 댓글 1에 대한 대댓글 (user 2 작성)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (LAST_INSERT_ID(), '읽어주셔서 감사합니다!', 2, 5, true, NOW(), NOW());

-- 댓글 2 (user 3 작성, 대댓글 없음)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (NULL, '이 기업 면접 분위기를 알 수 있어 좋네요.', 3, 5, true, NOW(), NOW());


-- ===== User 3의 첫 번째 Memoir (id=9) =====

-- 댓글 1 (user 2 작성)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (NULL, '회고를 보니 자신감이 생깁니다.', 2, 9, true, NOW(), NOW());

-- 댓글 1에 대한 대댓글 (user 3 작성)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (LAST_INSERT_ID(), '도움이 되었다니 다행이에요!', 3, 9, true, NOW(), NOW());

-- 댓글 2 (user 1 작성, 대댓글 없음)
INSERT INTO comment (parent_id, content, user_id, memoir_id, is_use, created_at, updated_at)
VALUES (NULL, '저도 이 질문을 받았는데 많이 공감되네요.', 1, 9, true, NOW(), NOW());



