
-- ★ ★ ★ ★ ★ book.xml ★ ★ ★ ★ ★ 

-- id = mainList (신간도서순 bookList)
SELECT * FROM BOOK ORDER BY BRDATE;

-- id = bookList (startRow~endRow까지 bookList) 출셕순서 : btitle 기준
  -- (1) schItem이 null이거나 ''일 때
SELECT * FROM 
    (SELECT ROWNUM RN, A.* FROM (SELECT * FROM BOOK ORDER BY BTITLE) A)
  WHERE RN BETWEEN 1 AND 10;
  -- (2) schItem이 'all'일 때
SELECT * FROM 
    (SELECT ROWNUM RN, A.* FROM 
        (SELECT * FROM BOOK WHERE BTITLE LIKE '%' || 'J' || '%' OR BWRITER LIKE '%' || 'J' || '%' ORDER BY BTITLE) A)
  WHERE RN BETWEEN 1 AND 10;
  -- (3) schItem이 'btitle'일 때
SELECT * FROM 
    (SELECT ROWNUM RN, A.* FROM 
        (SELECT * FROM BOOK WHERE BTITLE LIKE '%' || 'J' || '%' ORDER BY BTITLE) A)
  WHERE RN BETWEEN 1 AND 10;
  -- (4) schItem이 'bwriter'일 때
SELECT * FROM 
    (SELECT ROWNUM RN, A.* FROM 
        (SELECT * FROM BOOK WHERE BWRITER LIKE '%' || 'J' || '%' ORDER BY BTITLE) A)
  WHERE RN BETWEEN 1 AND 10;

-- id = totCntBook (등록된 책 갯수)
  -- (1) schItem이 null 이거나 ''일 때
SELECT COUNT(*) FROM BOOK;
  -- (2) schItem이 'all'일 때
SELECT COUNT(*) FROM BOOK WHERE BTITLE LIKE '%' || 'J' || '%' OR BWRITER LIKE '%' || 'J' || '%';
  -- (3) schItem이 'btitle'일 때
SELECT COUNT(*) FROM BOOK WHERE BTITLE LIKE '%' || 'J' || '%';
  -- (4) schItem이 'bwriter'일 때
SELECT COUNT(*) FROM BOOK WHERE BWRITER LIKE '%' || 'J' || '%';

-- id = getDetailBook (책번호로 dto가져오기)
SELECT * FROM BOOK WHERE BNUM = 1;

-- id = registerBook (책등록) -- 파일첨부 갯수에 따라 if문으로 sql이 달라질수있음
INSERT INTO BOOK (bNUM, bTITLE, bWRITER, bRDATE, bIMG1, bIMG2, bINFO)
    VALUES (BOOK_SQ.NEXTVAL, 'JYP', '박진영', SYSDATE, 'noImg.png', 'noImg.png', 'jyp의모든것');

-- id = modifyBook (책수정)
UPDATE BOOK 
  SET BTITLE = '수정',
      BWRITER = '수정자',
      BRDATE = SYSDATE,
      BIMG1 = 'noImg.png',
      BIMG2 = 'noImg.png',
      BINFO = '설명설명'
  WHERE BNUM = 5;

select * from book;
-- ★ ★ ★ ★ ★ member.xml ★ ★ ★ ★ ★ 

-- id = idConfirm (mid가 id인 데이터 갯수)
SELECT COUNT(*) FROM MEMBER WHERE MID = 'aaa';

-- id = joinMember
INSERT INTO MEMBER(MID, MPW, MNAME, MMAIL, MPOST, MADDR) 
  VALUES ('bbb','1','홍길순','hong@gmail.com','12345','서울');

-- id = getDetailMember (mid로 MEMBER dto가져오기)
SELECT * FROM MEMBER WHERE MID = 'bbb';

-- id = modifyMember
UPDATE MEMBER
  SET MPW = '2',
      MNAME = '수정',
      MMAIL = 'sujong@naver.com',
      MPOST = '4321',
      MADDR = '인천'
  WHERE MID = 'bbb';


select * from member;

commit;