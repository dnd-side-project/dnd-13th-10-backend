INSERT INTO users (social_id, phone_number, name, is_use) VALUES (1, "010-1234-5678", "백무식", true);
INSERT INTO users (social_id, phone_number, name, is_use) VALUES (2, "010-3333-6789", "백쿠키", true);

INSERT INTO memoir (
    user_id, type, interview_format, interview_mood, satisfaction_note, interview_status, interview_method,
    free_note, url, company_name, position, interview_step,
    interview_datetime, like_count, view_count, is_public, is_use, created_at
) VALUES
      (1, '10', '10', '10', '10',  '10', '10', '첫 회고 내용입니다.', 'http://example.com/1', '네이버', '20', '10', '2025-08-15 10:00:00', 0, 0, 1, 1, '2025-08-12 12:12:12'),

      (1, '10', '30', '50', '10', '20', '10', '네 번째 회고입니다.', 'http://example.com/4', '배달의민족', '30', '10', '2025-08-12 16:45:00', 1, 5, 1, 1, '2025-08-15 00:05:05'),

      (2, '10', '10', '30', '20',  '30', '10', '다섯 번째 회고입니다.', 'http://example.com/5', '토스', '20', '40', '2025-08-11 11:00:00', 7, 100, 1, 1, '2025-07-20 20:20:20');

-- memoir_id = 1
INSERT INTO question (memoir_id, question_type, content, answer, display_order, is_use)
VALUES
    (1, '70', '최근에 해결한 성능 이슈와 튜닝 과정을 설명해주세요.', NULL, 1, 1),
    (1, '71', '대규모 트래픽을 고려한 시스템 설계를 어떻게 접근하시나요?', NULL, 2, 1),
    (1, '72', '어려웠던 알고리즘 문제와 해결 전략을 설명해주세요.', NULL, 3, 1);

-- memoir_id = 2
INSERT INTO question (memoir_id, question_type, content, answer, display_order, is_use)
VALUES
    (2, '61', '비개발자와 협업할 때 커뮤니케이션은 어떻게 하시나요?', NULL, 1, 1),
    (2, '20', '어려운 이해관계자와 협상했던 경험을 말씀해 주세요.', NULL, 2, 1),
    (2, '21', '최근 발표(프레젠테이션)에서 핵심 메시지를 어떻게 전달했나요?', NULL, 3, 1);

-- memoir_id = 3
INSERT INTO question (memoir_id, question_type, content, answer, display_order, is_use)
VALUES
    (3, '50', '문제 해결을 위해 어떤 접근 방법을 사용하시나요?', NULL, 1, 1),
    (3, '53', '중대한 의사결정을 내린 경험을 말씀해주세요.', NULL, 2, 1),
    (3, '31', '팀을 리드하며 겪었던 어려움과 극복 방법을 설명해주세요.', NULL, 3, 1);

INSERT INTO schedule (id) VALUES (1)