DROP TABLE MVC_MEMBER;
CREATE TABLE MVC_MEMBER (
  MID VARCHAR2(30) PRIMARY KEY,
  MPW VARCHAR2(30) NOT NULL,
  MNAME VARCHAR2(30) NOT NULL,
  MEMAIL VARCHAR2(30),
  MPHOTO VARCHAR2(30) DEFAULT 'nothing.png' NOT NULL,
  MBIRTH DATE,
  MADDRESS VARCHAR2(300),
  MRDATE DATE DEFAULT SYSDATE NOT NULL
);

-- DUMMY DATA
INSERT INTO MVC_MEMBER (mID, mPw, mName)
    VALUES ('aaa','1','홍길동');
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('gayun','1','김가연','gayun@naver.com','gayun.jpg','1972/09/09','광주광역시');
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('gico','1','지코','gico@naver.com','gico.jpg','1992/09/14','서울시');
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('go','1','고소영','go@naver.com','go.jpg','1972/10/06','서울시');
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('ham','1','함소원','ham@naver.com','ham.jpg','1976/06/16','서울시');
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('han','1','한지민','han@naver.com','han.jpg','1982/11/05','서울시');
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('jang','1','장동건',null,'jang.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('jo','1','조현우',null,'jo.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('kang','1','강동원',null,'kang.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('kim','1','김태희',null,'kim.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('lee','1','이선빈',null,'lee.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('lim','1','임요한',null,'lim.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('park','1','박보검',null,'park.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('rain','1','비',null,'rain.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('sgy','1','송가연',null,'sgy.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('son','1','손흥민',null,'son.jpg',null,null);
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('song','1','송중기',null,'song.jpg',null,null);


SELECT * FROM MVC_MEMBER;
COMMIT;
-- DAO에 들어갈 쿼리

  -- 1. ID 중복체크 
SELECT * FROM MVC_MEMBER WHERE MID = 'aaa';

  -- 2. JOIN
INSERT INTO MVC_MEMBER (mID, mPw, mName, mEmail, mPhoto, mBirth, mAddress)
    VALUES ('bbb','1','asdf','asdf@naver.com','nothing.png','1972/09/09','광주광역시');

  -- 3. LOGIN CHECK
SELECT * FROM MVC_MEMBER WHERE mID='aaa' and mPW='1';

  -- 4. mID로 MemberDto 가져오기
SELECT * FROM MVC_MEMBER WHERE MID = 'aaa';

  -- 5. 회원 정보 수정
UPDATE MVC_MEMBER 
  SET MPW = '1',
      MNAME = '송중기',
      MEMAIL = 'SONG@S.COM',
      MPHOTO = 'NOIMG.JPG',
      MBIRTH = '1995-12-12',
      MADDRESS = '서대문'
  WHERE MID = 'aaa';

  -- 6. 회원 리스트 보기(top-n)  -- 신입회원순
SELECT *
  FROM (SELECT ROWNUM RN, A.* FROM (SELECT * FROM MVC_MEMBER ORDER BY MRDATE DESC) A)
  WHERE RN BETWEEN 6 AND 7;

  -- 7. 전체 등록된 회원수
SELECT COUNT(*) CNT FROM MVC_MEMBER;

  -- 8. 회원 탈퇴
DELETE FROM MVC_MEMBER WHERE MID = 'aaa';

