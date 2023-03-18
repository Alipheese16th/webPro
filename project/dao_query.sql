---------------------------------------------------------------
--------------------- UserDao에 들어갈 query --------------------
---------------------------------------------------------------
-- 아이디 중복체크
SELECT * FROM USERS WHERE USER_ID = 'aaa';
-- 이메일 중복체크
SELECT * FROM USERS WHERE USER_EMAIL = 'hong@naver.com';
-- 회원가입
INSERT INTO USERS(USER_ID, USER_PW, USER_NAME, USER_BIRTH, USER_GENDER, USER_EMAIL, USER_TELL)
  VALUES('ZXZ','ZXZ','ZXZ','93/11/03','m','ZXZ@naver.com','010-0101-0101');
-- 로그인
SELECT * FROM USERS WHERE USER_ID = 'aaa' AND USER_PW = '1';
-- user_id로 dto 가져오기 (로그인시 세션에 유저 정보 저장)
SELECT * FROM USERS WHERE USER_ID = 'aaa';
-- 회원 정보 수정
UPDATE USERS 
  SET USER_PW = '2',
      USER_NAME = '수정이름',
      USER_BIRTH = '00/11/03',
      USER_GENDER = 'f',
      USER_EMAIL = 'sujong@su.com',
      USER_TELL = '999-9999-9999'
  WHERE USER_ID = 'ZXZ';
-- 회원 리스트 보기(top-n)  -- 신입회원순
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY USER_RDATE DESC) RN, U.* FROM USERS U)
  WHERE RN BETWEEN 2 AND 3;
-- 전체 등록된 회원수 (회원리스트 페이지에서 페이징때 필요한)
SELECT COUNT(*) FROM USERS;
-- 회원 탈퇴
DELETE FROM USERS WHERE USER_ID = 'aaa';
ROLLBACK;

---------------------------------------------------------------
-------------------- AdminDao에 들어갈 query --------------------
---------------------------------------------------------------
-- admin 로그인
SELECT * FROM ADMIN WHERE ADMIN_ID = 'zzz' AND ADMIN_PW = '1';
-- 로그인 후 세션에 넣을 용도 admin aid로 AdminDto 가져오기
SELECT * FROM ADMIN WHERE ADMIN_ID = 'zzz';

---------------------------------------------------------------
-------------------- MovieDao에 들어갈 query --------------------
---------------------------------------------------------------
-- 영화등록
INSERT INTO MOVIE(MOVIE_ID, MOVIE_NAME, MOVIE_SUMMARY, MOVIE_RUNNING, MOVIE_IMAGE,
            MOVIE_DATE, MOVIE_GRADE, MOVIE_AUDIENCE, STATE)
  VALUES('m'||LPAD(MOVIE_SEQ.NEXTVAL,3,'0'),'아이언맨','아이언맨은 히어로 이야기이다. 머시기 저시기',
  126,'ironman.png','08/04/30','12세 관람가',4300365,3);
-- 영화 수정
UPDATE MOVIE
  SET MOVIE_NAME = '아이언맨2',
      MOVIE_SUMMARY = '아이언맨2로 수정',
      MOVIE_RUNNING = 100,
      MOVIE_IMAGE = 'ironSujong.jpg',
      MOVIE_DATE = '08/05/30',
      MOVIE_GRADE = '15세 등급',
      MOVIE_AUDIENCE = 4400365,
      STATE = 0
  WHERE MOVIE_ID = 'm006';
-- 영화리스트(탑앤구문없이 전부출력, 개봉일 최근순)
-- 상영중
SELECT * FROM MOVIE WHERE STATE = 2 ORDER BY MOVIE_DATE DESC;
-- 상영예고
SELECT * FROM MOVIE WHERE STATE = 1 ORDER BY MOVIE_DATE;
-- 상영예고중인 작품중에서 상영일이 지났으면 상영중으로 업데이트
UPDATE MOVIE
  SET STATE = 2
  WHERE STATE = 1
  AND MOVIE_DATE < SYSDATE;
-- 영화 검색
-- 영화를 이름으로 검색
SELECT * FROM MOVIE WHERE MOVIE_NAME LIKE '%' || '포터' || '%';
-- 영화를 태그로 검색
SELECT * FROM MOVIE WHERE MOVIE_ID IN (SELECT MOVIE_ID FROM TAG WHERE TAG = '판타지');
-- 영화 상세보기
SELECT * FROM MOVIE WHERE MOVIE_ID = 'm001';
-- 영화태그출력(여러개일수있음)
SELECT TAG FROM TAG WHERE MOVIE_ID = 'm002';
-- 영화제작진출력(여러행)
SELECT P.*
  FROM MOVIE_PERSON MP, PERSON P
  WHERE MP.PERSON_ID = P.PERSON_ID
  AND MP.MOVIE_ID = 'm001';

---------------------------------------------------------------
-------------------- BoardDao에 들어갈 query --------------------
---------------------------------------------------------------
-- 글 목록 (탑앤구문)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_GROUP DESC, BOARD_STEP) RN, B.* FROM BOARD B)
  WHERE RN BETWEEN 3 AND 4;
-- 글 갯수 (페이징)
SELECT COUNT(*) FROM BOARD;
-- 글쓰기(원글)
INSERT INTO BOARD(BOARD_NO, USER_ID, BOARD_TITLE, BOARD_CONTENT, BOARD_GROUP, BOARD_STEP, BOARD_INDENT, BOARD_IP)
  VALUES(BOARD_SEQ.NEXTVAL, 'aaa', '글제목', '글본문', BOARD_SEQ.CURRVAL, 0, 0, '192.168.0.1');
-- 조회수 UP
UPDATE BOARD SET BOARD_HIT = BOARD_HIT + 1 WHERE BOARD_NO = 1;
-- 글번호로 DTO가져오기 (글상세보기)
SELECT * FROM BOARD WHERE BOARD_NO = 1;
-- 글 수정하기
UPDATE BOARD
  SET BOARD_TITLE = '수정스',
      BOARD_CONTENT = '수정스',
      BOARD_UPDATE = SYSDATE,
      BOARD_IP = '수정IP'
  WHERE BOARD_NO = 1;
-- 글 삭제하기
DELETE FROM BOARD WHERE BOARD_NO = 1;
ROLLBACK;
-- 답변글 전처리(원글의 그룹과 같고, 원글의 STEP보다 크면 STEP을 1씩 증가)
UPDATE BOARD SET BOARD_STEP = BOARD_STEP + 1
    WHERE BOARD_GROUP = 2 AND BOARD_STEP > 0;
-- 답변글 쓰기(원글의 그룹과 같고, 원글의 STEP과 INDENT보다 1크게 입력)
INSERT INTO BOARD(BOARD_NO, USER_ID, BOARD_TITLE, BOARD_CONTENT, BOARD_GROUP, BOARD_STEP, BOARD_INDENT, BOARD_IP)
  VALUES(BOARD_SEQ.NEXTVAL, 'bbb', '답글제목', '답글본문', 2, 1, 1, '192.168.0.1');

---------------------------------------------------------------
-------------------- CommentDao에 들어갈 query ------------------
---------------------------------------------------------------
-- 댓글 리스트 (탑앤구문 작성일 순서)(1번 자유게시판글의 댓글)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY COMMENT_DATE DESC) RN, C.* FROM COMMENTS C WHERE BOARD_NO = 1)
  WHERE RN BETWEEN 1 AND 10;
-- 댓글 갯수(페이징)(1번글의 댓글수)
SELECT COUNT(*) FROM COMMENTS WHERE BOARD_NO = 1;
-- 댓글 작성
INSERT INTO COMMENTS(COMMENT_NO, BOARD_NO, USER_ID, COMMENT_CONTENT)
  VALUES(COMMENTS_SEQ.NEXTVAL, 1, 'aaa', '테스트');
-- 댓글 수정
UPDATE COMMENTS SET COMMENT_CONTENT = '1번수정' WHERE COMMENT_NO = 1;
-- 댓글 삭제
DELETE FROM COMMENTS WHERE COMMENT_NO = 1;
ROLLBACK;

---------------------------------------------------------------
-------------------- RatingDao에 들어갈 query -------------------
---------------------------------------------------------------
-- 평점 리스트(탑앤구문 작성순서)(영화아이디에 해당하는 평점들만 출력)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY RATING_DATE DESC) RN, R.* FROM RATING R WHERE MOVIE_ID = 'm001')
  WHERE RN BETWEEN 1 AND 10;
-- 평점 갯수(페이징)(영화아이디에 해당하는 평점만 출력)
SELECT COUNT(*) FROM RATING WHERE MOVIE_ID = 'm001';
-- 평점 작성
INSERT INTO RATING (USER_ID, MOVIE_ID, RATING_CONTENT, RATING_SCORE)
  VALUES('aaa','m003','으이게뭐냐',0);
-- 평점 수정(영화아이디,회원아이디 필요)
UPDATE RATING
  SET RATING_CONTENT = '해리포터재밌다니까요',
      RATING_SCORE = 10
  WHERE USER_ID = 'aaa' AND MOVIE_ID = 'm001';
-- 평점 삭제
DELETE FROM RATING WHERE USER_ID = 'aaa' AND MOVIE_ID = 'm001';
ROLLBACK;


