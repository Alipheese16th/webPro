---------------------------------------------------------------
-------------------- MemberDao에 들어갈 query -------------------
---------------------------------------------------------------
-- 1. ID 중복체크 
SELECT * FROM MVC_MEMBER WHERE MID = 'aaa';
-- 2. 회원 EMAIL 중복체크
SELECT * FROM MVC_MEMBER WHERE MEMAIL = 'hong@naver.com';
-- 3. 회원가입
INSERT INTO MVC_MEMBER (mID, mPw, mName, mEmail, mPhoto, mBirth, mAddress)
    VALUES ('bbb','1','asdf','asdf@naver.com','nothing.png','1972/09/09','광주광역시');
-- 4. 로그인
SELECT * FROM MVC_MEMBER WHERE mID='aaa' and mPW='1';
-- 5. mID로 MemberDto 가져오기
SELECT * FROM MVC_MEMBER WHERE MID = 'aaa';
-- 6. 회원 정보 수정
UPDATE MVC_MEMBER 
  SET MPW = '1',
      MNAME = '송중기',
      MEMAIL = 'SONG@S.COM',
      MPHOTO = 'NOIMG.JPG',
      MBIRTH = '1995-12-12',
      MADDRESS = '서대문'
  WHERE MID = 'aaa';
-- 7. 회원 리스트 보기(top-n)  -- 신입회원순
SELECT *
  FROM (SELECT ROWNUM RN, A.* FROM (SELECT * FROM MVC_MEMBER ORDER BY MRDATE DESC) A)
  WHERE RN BETWEEN 6 AND 7;
-- 8. 전체 등록된 회원수
SELECT COUNT(*) CNT FROM MVC_MEMBER;
-- 9. 회원 탈퇴
DELETE FROM MVC_MEMBER WHERE MID = 'eee';

---------------------------------------------------------------
-------------------- BoardDao에 들어갈 query --------------------
---------------------------------------------------------------
-- 1. 글목록 (startRow ~ endRow)
SELECT * 
  FROM (SELECT ROW_NUMBER() OVER(ORDER BY FGROUP DESC, FSTEP) AS RN, MNAME, F.* 
              FROM FILEBOARD F, MVC_MEMBER M WHERE F.MID = M.MID)
  WHERE RN BETWEEN 2 AND 3;
-- 2. 글갯수
SELECT COUNT(*) FROM FILEBOARD;
-- 3. 글쓰기(원글쓰기)
INSERT INTO FILEBOARD (fID, mID, fTITLE, fCONTENT, fFILENAME, fGROUP, fSTEP, fINDENT, fIP) 
  VALUES (FILEBOARD_SEQ.NEXTVAL,'aaa','제목1','본문1','file.jpg', FILEBOARD_SEQ.CURRVAL, 0, 0, '192.168.0.1');
-- 4. 조회수(hit) 1up
UPDATE FILEBOARD SET FHIT = FHIT + 1 WHERE FID = 1;
-- 5. 글번호(fid)로 글전체 내용(BoardDto) 가져오기
SELECT * FROM FILEBOARD WHERE FID = 1;
-- 6. 글 수정하기(fid, ftitle, fcontent, ffilename, frdate(SYSDATE), fip 수정)
UPDATE FILEBOARD 
  SET FTITLE = '수정제목',
      FCONTENT = '수정본문',
      FFILENAME = 'sujong.jpg',
      FRDATE = SYSDATE,
      FIP = '121.121.121.121'
  WHERE FID = 1;
-- 7. 글 삭제하기(fid로)
DELETE FROM FILEBOARD WHERE FID = 1;
ROLLBACK;
-- 8. 답변글 쓰기 전 단계(원글의 fgroup과 같고, 원글의 fstep보다 크면 fstep을 1 증가)
UPDATE FILEBOARD SET FSTEP = FSTEP + 1
    WHERE FGROUP = 2 AND FSTEP > 0;
-- 9. 답변글 쓰기
INSERT INTO FILEBOARD (fID, mID, fTITLE, fCONTENT, fFILENAME, fGROUP, fSTEP, fINDENT, fIP) 
  VALUES (FILEBOARD_SEQ.NEXTVAL,'you','답제목','답본문', 'file.jpg', 2, 1, 1, '192.168.0.1');
-- 10. 회원탈퇴시 해당회원(mid)이 쓴 글 모두 삭제하기
DELETE FROM FILEBOARD WHERE MID = 'aaa';


---------------------------------------------------------------
-------------------- AdminDao에 들어갈 query --------------------
---------------------------------------------------------------
-- 1. admin 로그인
SELECT * FROM ADMIN WHERE AID = 'admin' AND APW = '1';

-- 2. 로그인 후 세션에 넣을 용도 admin aid로 AdminDto 가져오기
SELECT * FROM ADMIN WHERE AID = 'admin';


