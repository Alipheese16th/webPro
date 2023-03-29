---------------------------------------------------------------
--------------------- UserDao에 들어갈 query --------------------
---------------------------------------------------------------
-- 아이디 중복체크
SELECT * FROM USERS WHERE USERID = 'aaa';
-- 이메일 중복체크
SELECT * FROM USERS WHERE USEREMAIL = 'hong@naver.com';
-- 회원가입
INSERT INTO USERS(USERID, USERPW, USERNAME, USERBIRTH, USERGENDER, USEREMAIL, USERTEL)
  VALUES('ZXZ','ZXZ','ZXZ','93/11/03','m','ZXZ@naver.com','010-0101-0101');
-- 로그인
SELECT * FROM USERS WHERE USERID = 'aaa' AND USERPW = '1';
-- userid로 dto 가져오기 (로그인시 세션에 유저 정보 저장)
SELECT * FROM USERS WHERE USERID = 'aaa';
-- 회원 정보 수정
UPDATE USERS 
  SET USERPW = '2',
      USERNAME = '수정이름',
      USERBIRTH = '00/11/03',
      USERGENDER = 'f',
      USEREMAIL = 'sujong@su.com',
      USERTEL = '999-9999-9999'
  WHERE USERID = 'ZXZ';
-- 회원 탈퇴
DELETE FROM USERS WHERE USERID = 'aaa';
ROLLBACK;
---------
-- 특정유저의 최근 자유게시판 글 목록 (댓글갯수 서브쿼리 추가)
SELECT B.*,(SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = B.BOARDNO) COMMENTCNT  FROM BOARD B
      WHERE USERID = 'aaa' ORDER BY BOARDDATE DESC;
-- 특정유저의 최근 댓글목록
SELECT * FROM COMMENTS WHERE USERID = 'aaa' ORDER BY COMMENTDATE DESC;
-- 특정유저의 최근 평점목록
SELECT MOVIETITLE, R.* FROM RATING R, MOVIE M WHERE R.MOVIEID = M.MOVIEID AND USERID = 'aaa' ORDER BY RATINGDATE DESC;

---------------------------------------------------------------
-------------------- AdminDao에 들어갈 query --------------------
---------------------------------------------------------------
-- admin 로그인
SELECT * FROM ADMIN WHERE ADMINID = 'zzz' AND ADMINPW = '1';
-- 로그인 후 세션에 넣을 용도 admin aid로 AdminDto 가져오기
SELECT * FROM ADMIN WHERE ADMINID = 'zzz';

-- 회원 리스트 보기(top-n)  -- 신입회원순
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY USERDATE DESC) RN, U.* FROM USERS U)
  WHERE RN BETWEEN 1 AND 10;
-- 전체 등록된 회원수 (회원리스트 페이지에서 페이징때 필요한)
SELECT COUNT(*) FROM USERS;

-- 검색할때 탑앤구문도 넣어야함 페이지네비게이션이 있기때문
-- 회원 검색 (아이디로)
SELECT * FROM
  (SELECT ROWNUM RN ,U.* FROM USERS U WHERE USERID LIKE '%' || '' || '%')
  WHERE RN BETWEEN 1 AND 10;
-- 페이징 갯수
SELECT COUNT(*) FROM USERS WHERE USERID LIKE '%' || 'aa' || '%';

-- 회원 제제 (userId, 제재기간)
UPDATE USERS
  SET USERLIMIT = '23/04/04'
  WHERE USERID = 'aaa';
---------------------------------------------------------------
-------------------- MovieDao에 들어갈 query --------------------
---------------------------------------------------------------
select * from movie;

-- 영화 리스트 상영중(개봉일 최근순)   -- 평균평점 서브쿼리
SELECT M.*,(SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID) as avgSCORE
  FROM MOVIE M WHERE STATE = 2 ORDER BY MOVIEDATE DESC;
-- 영화리스트 상영예고(가장빨리개봉되는순)   -- 평균평점 서브쿼리
SELECT M.*,(SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID) as avgSCORE
  FROM MOVIE M WHERE STATE = 1 ORDER BY MOVIEDATE;

-- 영화 상세보기(평균 평점 서브쿼리)
SELECT M.*,(SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID) as avgScore
  FROM MOVIE M WHERE MOVIEID = 'm001';
  -- 해당 영화의 태그리스트 (moviedto 속성으로 추가할것 ArrayList<string>)
SELECT TAG FROM TAG WHERE MOVIEID = 'm002';
  -- 해당 영화의 인물리스트 역할, 배역 (moviedto 속성으로 추가할것 ArrayList<PersonDto>)
SELECT P.PERSONID,PERSONNAME,PERSONIMAGE,PERSONNATION,PERSONBIRTH,CASTING,ROLE
FROM MOVIE_PERSON MP,PERSON P WHERE MP.PERSONID = P.PERSONID AND MOVIEID = 'm001';
  -- 해당 영화의 예고편리스트 (moviedto 속성으로 추가 ArrayList<TrailerDto>)
SELECT * FROM TRAILER WHERE MOVIEID = 'm002';

-- 메인페이지 꾸미기
  -- 메인 페이지 관람객순 영화 (현재 상영작중) (캐러셀에 넣을거) -- 이거 이제 안쓰고 아래의 영화랭킹페이지 제작시 관람객순 탑앤구문으로 사용
SELECT M.*,(SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID) as avgScore
  FROM MOVIE M WHERE STATE = 2 ORDER BY MOVIEAUDIENCE DESC;
  -- 메인 페이지 예고편(트레일러) 리스트
SELECT * FROM TRAILER ORDER BY MOVIEID DESC;

-- 영화 검색(평균 평점 서브쿼리 추가)
  -- 영화를 이름으로 검색 
SELECT M.*,(SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID) as avgScore
  FROM MOVIE M WHERE MOVIETITLE LIKE '%' || '웅남' || '%' ORDER BY MOVIEDATE DESC;
  -- 영화를 태그로 검색
SELECT M.*,(SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID) as avgScore
  FROM MOVIE M WHERE MOVIEID IN (SELECT MOVIEID FROM TAG WHERE TAG = '코미디');
  -- 해당 영화이름의 트레일러들 검색
SELECT T.*,MOVIETITLE FROM TRAILER T, MOVIE M 
  WHERE T.MOVIEID = M.MOVIEID AND MOVIETITLE LIKE '%' || '웅남' || '%';

-- 예고편 리스트 , 영화정보 (태그리스트,인물리스트는 dao에서)
  SELECT TRAILERNAME,TRAILERURL,M.* FROM TRAILER T, MOVIE M WHERE T.MOVIEID = M.MOVIEID;
  -- 페이징 갯수
  SELECT COUNT(*) FROM TRAILER;

-- 영화 랭킹 페이지 
  -- 관람객순 (현재영화) 탑앤구문
SELECT * FROM (
    SELECT ROWNUM RN, A.* FROM
      (SELECT M.*,(SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID) as avgScore
        FROM MOVIE M WHERE STATE = 2 ORDER BY MOVIEAUDIENCE DESC) A)
  WHERE RN BETWEEN 1 AND 50;
  -- 현재영화 페이징
SELECT COUNT(*) FROM MOVIE WHERE STATE = 2;
  -- 평점순 (현재영화) 탑앤구문
SELECT * FROM (
    SELECT ROWNUM RN, A.* FROM
      (SELECT M.*, NVL((SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID),0) as avgScore
        FROM MOVIE M WHERE STATE = 2 ORDER BY avgScore DESC) A)
  WHERE RN BETWEEN 1 AND 50;
  -- 평점순 (모든영화) 탑앤구문
SELECT * FROM (
    SELECT ROWNUM RN, A.* FROM
      (SELECT M.*,NVL((SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID),0) as avgScore
        FROM MOVIE M ORDER BY avgScore DESC) A)
  WHERE RN BETWEEN 1 AND 50;
  -- 모든영화 페이징
SELECT COUNT(*) FROM MOVIE;



-- 상영예고중인 작품중에서 상영일이 지났으면 상영중으로 업데이트
UPDATE MOVIE
  SET STATE = 2
  WHERE STATE = 1
  AND MOVIEDATE <= SYSDATE;
-- 영화등록
INSERT INTO MOVIE(MOVIEID, ORIGINALTITLE, MOVIETITLE, MOVIESUMMARY, MOVIERUNNING, MOVIEIMAGE,
            MOVIEDATE, MOVIEGRADE, MOVIEAUDIENCE, STATE)
  VALUES('m'||LPAD(MOVIE_SEQ.NEXTVAL,3,'0'),'Iron Man','아이언맨','아이언맨은 히어로 이야기이다. 머시기 저시기',
  126,'ironman.png','08/04/30','12세 관람가',4300365,3);
-- 영화 수정
UPDATE MOVIE
  SET ORIGINALTITLE = 'Iron Man 2',
      MOVIETITLE = '아이언맨2',
      MOVIESUMMARY = '아이언맨2로 수정',
      MOVIERUNNING = 100,
      MOVIEIMAGE = 'ironSujong.jpg',
      MOVIEDATE = '08/05/30',
      MOVIEGRADE = '15세 등급',
      MOVIEAUDIENCE = 4400365,
      STATE = 0
  WHERE MOVIEID = 'm006';


---------------------------------------------------------------
-------------------- RatingDao에 들어갈 query -------------------
---------------------------------------------------------------

-- 평점 중복확인
SELECT * FROM RATING WHERE MOVIEID = 'm001' AND USERID = 'aaa';


-- 특정 영화의 평점 리스트(유저이름 조인 추가)(탑앤구문 최신순)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY RATINGDATE DESC) RN ,USERNAME, R.* 
    FROM RATING R, USERS U WHERE R.USERID = U.USERID AND MOVIEID = 'm005')
  WHERE RN BETWEEN 1 AND 10;
-- 특정영화의 평점 갯수(페이징)(영화아이디에 해당하는 평점만 출력)
SELECT COUNT(*) FROM RATING WHERE MOVIEID = 'm001';

-- 전체 영화의 평점 리스트(유저이름 조인 추가,영화이름 조인추가)(탑앤구문 최신순)
SELECT * FROM
  (SELECT ROWNUM RN, A.* FROM
    (SELECT USERNAME, MOVIETITLE ,R.* 
      FROM RATING R, USERS U, MOVIE M
      WHERE R.USERID = U.USERID AND R.MOVIEID = M.MOVIEID
      ORDER BY RATINGDATE DESC) A)
  WHERE RN BETWEEN 2 AND 4;
-- 전제 평점리스트 페이징
SELECT COUNT(*) FROM RATING;


-- 평점 작성
INSERT INTO RATING (USERID, MOVIEID, RATINGCONTENT, RATINGSCORE)
  VALUES('aaa','m003','으이게뭐냐',0);
-- 평점 수정
UPDATE RATING
  SET RATINGCONTENT = '해리포터재밌다니까요',
      RATINGSCORE = 10,
      RATINGDATE = SYSDATE
  WHERE USERID = 'aaa' AND MOVIEID = 'm001';
-- 평점 삭제
DELETE FROM RATING WHERE USERID = 'aaa' AND MOVIEID = 'm001';
ROLLBACK;


---------------------------------------------------------------
-------------------- PersonDao에 들어갈 query --------------------
---------------------------------------------------------------
-- 이름으로 인물 검색
SELECT * FROM PERSON WHERE PERSONNAME LIKE '%' || '엠마' || '%';

-- 인물 상세보기 (id로 dto가져오기)
SELECT * FROM PERSON WHERE PERSONID = 'p001';

-- 특정 인물의 영화 출연작 리스트 (평균 평점 조인)
SELECT M.*,(SELECT ROUND(AVG(RATINGSCORE),1) FROM RATING WHERE MOVIEID = M.MOVIEID) as avgSCORE
  FROM MOVIE M, MOVIE_PERSON MP
  WHERE MP.MOVIEID = M.MOVIEID AND MP.PERSONID = 'p002';

-- 인물 등록
INSERT INTO PERSON(PERSONID, PERSONNAME, PERSONIMAGE, PERSONNATION, PERSONBIRTH)
  VALUES('p'||LPAD(PERSON_SEQ.NEXTVAL,3,'0'),'신카이 마코토', 'sinkai.jpg','Japan','73/02/09');

-- 인물 정보 수정
UPDATE PERSON
  SET PERSONNAME = '데이빗 예이츠',
      PERSONIMAGE = 'Daivid.png',
      PERSONNATION = 'United Kingdon',
      PERSONBIRTH = '63/11/30'
  WHERE PERSONID = 'p001';



---------------------------------------------------------------
-------------------- BoardDao에 들어갈 query --------------------
---------------------------------------------------------------
-- 글 목록 (탑앤구문) (댓글 갯수추가 서브쿼리 COMMENTCNT)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY BOARDGROUP DESC, BOARDSTEP) RN,USERNAME ,B.*,
      (SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = B.BOARDNO) COMMENTCNT  FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID)
  WHERE RN BETWEEN 1 AND 10;

-- 글 갯수 (페이징)
SELECT COUNT(*) FROM BOARD;
-- 글쓰기(원글)
INSERT INTO BOARD(BOARDNO, USERID, BOARDTITLE, BOARDCONTENT, BOARDGROUP, BOARDSTEP, BOARDINDENT, BOARDIP)
  VALUES(BOARD_SEQ.NEXTVAL, 'aaa', '글제목', '글본문', BOARD_SEQ.CURRVAL, 0, 0, '192.168.0.1');
-- 조회수 UP
UPDATE BOARD SET BOARDHIT = BOARDHIT + 1 WHERE BOARDNO = 1;
-- 글번호로 DTO가져오기 (글상세보기) (댓글갯수 서브쿼리 추가 COMMENTCNT)
SELECT USERNAME, B.*,(SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = B.BOARDNO) COMMENTCNT 
  FROM BOARD B, USERS U WHERE B.USERID = U.USERID AND BOARDNO = 1;

-- 글 수정하기
UPDATE BOARD
  SET BOARDTITLE = '수정스',
      BOARDCONTENT = '수정스',
      BOARDUPDATE = SYSDATE,
      BOARDIP = '수정IP'
  WHERE BOARDNO = 1;
-- 글 삭제하기
DELETE FROM BOARD WHERE BOARDNO = 1;
ROLLBACK;
-- 답변글 전처리(원글의 그룹과 같고, 원글의 STEP보다 크면 STEP을 1씩 증가)
UPDATE BOARD SET BOARDSTEP = BOARDSTEP + 1
    WHERE BOARDGROUP = 2 AND BOARDSTEP > 0;
-- 답변글 쓰기(원글의 그룹과 같고, 원글의 STEP과 INDENT보다 1크게 입력)
INSERT INTO BOARD(BOARDNO, USERID, BOARDTITLE, BOARDCONTENT, BOARDGROUP, BOARDSTEP, BOARDINDENT, BOARDIP)
  VALUES(BOARD_SEQ.NEXTVAL, 'bbb', '답글제목', '답글본문', 2, 1, 1, '192.168.0.1');


------------------------
-- 1-1 글 전체 검색(탑앤구문,댓글갯수,작성자 이름 조인)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY BOARDGROUP DESC, BOARDSTEP) RN,USERNAME ,B.*,
      (SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = B.BOARDNO) COMMENTCNT  FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID AND (
          BOARDTITLE LIKE '%' || 'test' || '%' 
          OR BOARDCONTENT LIKE '%' || 'test' || '%' 
          OR USERNAME LIKE '%' || '점순' || '%'))
  WHERE RN BETWEEN 1 AND 100;
-- 1-2 전체검색 페이징
SELECT COUNT(*) FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID AND 
          (BOARDTITLE LIKE '%' || 'test' || '%' 
          OR BOARDCONTENT LIKE '%' || 'test' || '%' 
          OR USERNAME LIKE '%' || '점순' || '%');

-- 2-1 글 제목으로 검색(탑앤구문,댓글갯수,작성자 이름 조인)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY BOARDGROUP DESC, BOARDSTEP) RN,USERNAME ,B.*,
      (SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = B.BOARDNO) COMMENTCNT  FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID AND BOARDTITLE LIKE '%' || '쓰자' || '%')
  WHERE RN BETWEEN 1 AND 4;
-- 2-2 제목검색 페이징
SELECT COUNT(*) FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID AND BOARDTITLE LIKE '%' || '쓰자' || '%';

-- 3-1 글 내용으로 검색(탑앤구문,댓글갯수,작성자 이름 조인)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY BOARDGROUP DESC, BOARDSTEP) RN,USERNAME ,B.*,
      (SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = B.BOARDNO) COMMENTCNT  FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID AND BOARDCONTENT LIKE '%' || '본문' || '%')
  WHERE RN BETWEEN 1 AND 10;
-- 3-2 글 내용 검색 페이징
SELECT COUNT(*) FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID AND BOARDCONTENT LIKE '%' || '본문' || '%';

-- 4-1 작성자 닉네임으로 검색(탑앤구문,댓글갯수,작성자 이름 조인)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY BOARDGROUP DESC, BOARDSTEP) RN,USERNAME ,B.*,
      (SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = B.BOARDNO) COMMENTCNT  FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID AND USERNAME LIKE '%' || '점순' || '%')
  WHERE RN BETWEEN 1 AND 10;
-- 4-2 작성자닉네임 페이징
SELECT COUNT(*) FROM BOARD B, USERS U
      WHERE B.USERID = U.USERID AND USERNAME LIKE '%' || '점순' || '%';

---------------------------
  
-- 댓글 검색 (원글정보 조인,작성자 이름 조인, 댓글 갯수 조인, 탑앤구문)
SELECT * FROM
  (SELECT USERNAME, B.*, COMMENTNO,COMMENTCONTENT,COMMENTDATE, (SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = B.BOARDNO) COMMENTCNT,
          ROW_NUMBER() OVER(ORDER BY COMMENTDATE DESC) RN
    FROM COMMENTS C, BOARD B, USERS U
    WHERE C.BOARDNO = B.BOARDNO AND C.USERID = U.USERID AND COMMENTCONTENT LIKE '' || '댓글' || '')
  WHERE RN BETWEEN 1 AND 4;
---------------------------------------------------------------
-------------------- CommentDao에 들어갈 query ------------------
---------------------------------------------------------------
-- 댓글 리스트 (탑앤구문 작성일 순서)(1번 자유게시판글의 댓글)
SELECT * FROM
  (SELECT ROW_NUMBER() OVER(ORDER BY COMMENTDATE DESC) RN, USERNAME, C.* 
    FROM COMMENTS C, USERS U WHERE C.USERID = U.USERID AND BOARDNO = 1)
  WHERE RN BETWEEN 1 AND 10;
-- 댓글 갯수(페이징)(1번글의 댓글수)
SELECT COUNT(*) FROM COMMENTS WHERE BOARDNO = 1;
-- 댓글번호로 댓글정보 가져오기 (수정 위해서)
SELECT * FROM COMMENTS WHERE COMMENTNO = 1;
-- 댓글 작성
INSERT INTO COMMENTS(COMMENTNO, BOARDNO, USERID, COMMENTCONTENT)
  VALUES(COMMENTS_SEQ.NEXTVAL, 120, 'aaa', '댓글이에용');

-- 댓글 수정
UPDATE COMMENTS SET COMMENTCONTENT = '1번수정' WHERE COMMENTNO = 1;
-- 댓글 삭제
DELETE FROM COMMENTS WHERE COMMENTNO = 1;
ROLLBACK;

