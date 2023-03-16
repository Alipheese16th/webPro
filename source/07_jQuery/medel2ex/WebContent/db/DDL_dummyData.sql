---------------------------------------------------------------
------------------------- MVC_MEMBER --------------------------
---------------------------------------------------------------
-- DROP & CREATE
DROP TABLE MVC_MEMBER;   -- 추후에 파일게시판에서 참조하면 삭제가 안될수도.
-- 그럴 경우 DROP TABLE MVC_MEMBER CASCADE CONSTRAINTS; 를 사용

CREATE TABLE MVC_MEMBER (
  mID VARCHAR2(30) PRIMARY KEY,
  mPW VARCHAR2(30) NOT NULL,
  mNAME VARCHAR2(30) NOT NULL,
  mEMAIL VARCHAR2(30),
  mPHOTO VARCHAR2(30) DEFAULT 'nothing.png' NOT NULL,
  mBIRTH DATE,
  mADDRESS VARCHAR2(300),
  mRDATE DATE DEFAULT SYSDATE NOT NULL
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


---------------------------------------------------------------
------------------------- FILEBOARD ---------------------------
---------------------------------------------------------------
DROP TABLE FILEBOARD;
DROP SEQUENCE FILEBOARD_SEQ;
-- CREATE TABLE
CREATE TABLE FILEBOARD(
  fID NUMBER(6) PRIMARY KEY,
  mID VARCHAR2(30) NOT NULL,
  fTITLE VARCHAR2(100) NOT NULL,
  fCONTENT VARCHAR2(4000),
  fFILENAME VARCHAR2(100),
  fRDATE DATE DEFAULT SYSDATE,
  fHIT NUMBER(6) DEFAULT 0  NOT NULL,
  fGROUP NUMBER(6) NOT NULL,
  fSTEP NUMBER(6) NOT NULL,
  fINDENT NUMBER(6) NOT NULL,
  fIP VARCHAR2(30)
);
CREATE SEQUENCE FILEBOARD_SEQ MAXVALUE 999999 NOCACHE NOCYCLE;
-- DUMMY DATA (원글과 답변)
SELECT * FROM FILEBOARD;
-- 원글쓰기
INSERT INTO FILEBOARD (fID, mID, fTITLE, fCONTENT, fFILENAME, fGROUP, fSTEP, fINDENT, fIP) 
  VALUES (FILEBOARD_SEQ.NEXTVAL,'aaa','제목1','본문1','file.jpg', FILEBOARD_SEQ.CURRVAL, 0, 0, '192.168.0.1');
  INSERT INTO FILEBOARD (fID, mID, fTITLE, fCONTENT, fGROUP, fSTEP, fINDENT, fIP) 
  VALUES (FILEBOARD_SEQ.NEXTVAL,'son','제목2','본문2',FILEBOARD_SEQ.CURRVAL, 0, 0, '192.168.0.1');
-- 답글쓰기
INSERT INTO FILEBOARD (fID, mID, fTITLE, fCONTENT, fGROUP, fSTEP, fINDENT, fIP) 
  VALUES (FILEBOARD_SEQ.NEXTVAL,'you','답제목','답본문', 2, 1, 1, '192.168.0.1');

COMMIT;

---------------------------------------------------------------
--------------------------- ADMIN -----------------------------
---------------------------------------------------------------
DROP TABLE ADMIN;
CREATE TABLE ADMIN(
  aID VARCHAR2(30) PRIMARY KEY,
  aPW VARCHAR2(30) NOT NULL,
  aNAME VARCHAR2(30) NOT NULL
);

SELECT * FROM ADMIN;

INSERT INTO ADMIN VALUES ('admin', '1', '김관리');

COMMIT;

