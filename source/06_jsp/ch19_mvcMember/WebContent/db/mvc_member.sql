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
INSERT INTO MVC_MEMBER (mID, mPw, mName, mEmail, mPhoto, mBirth, mAddress)
    VALUES ('gayun','1','김가연','gayun@naver.com','gayun.jpg','1972/09/09','광주광역시');
INSERT INTO MVC_MEMBER (mID, mPw, mName, mEmail, mPhoto, mBirth, mAddress)
    VALUES ('gico','1','지코','gico@naver.com','gico.jpg','1992/09/14','서울시');
INSERT INTO MVC_MEMBER (mID, mPw, mName, mEmail, mPhoto, mBirth, mAddress)
    VALUES ('go','1','고소영','go@naver.com','go.jpg','1972/10/06','서울시');
INSERT INTO MVC_MEMBER (mID, mPw, mName, mEmail, mPhoto, mBirth, mAddress)
    VALUES ('ham','1','함소원','ham@naver.com','ham.jpg','1976/06/16','서울시');
INSERT INTO MVC_MEMBER (mID, mPw, mName, mEmail, mPhoto, mBirth, mAddress)
    VALUES ('han','1','한지민','han@naver.com','han.jpg','1982/11/05','서울시');

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

