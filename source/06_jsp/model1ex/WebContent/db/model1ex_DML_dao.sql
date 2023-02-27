-- CustomerDao(아이디중복체크, 회원가입, 로그인, 상세보기(pk로 dto가져오기), 정보수정, 리스트(top-n), 총회원수)

  -- 1. 아이디 중복체크 public int duplicateCheck(String cid)
SELECT * FROM CUSTOMER WHERE cID = 'aaa';
  -- 2. 회원가입 public int joinCustomer(CustomerDto dto)
INSERT INTO CUSTOMER(cID, cPW, cNAME, cTEL, cEMAIL, cADDRESS, cGENDER, cBIRTH)
  VALUES('ZZZ','111','홍길동','010-1111-1111','hong@hong.com','서울시 서대문구','m','1999-11-11');
  -- 3. 로그인 public int login(String cid, String cpw)
SELECT * FROM CUSTOMER WHERE cID = 'aaa' AND cPW = '111';
  -- 4. 상세보기 public CustomerDto detailCustomer(String cid)
SELECT * FROM CUSTOMER WHERE cID = 'aaa';
  -- 5. 정보수정 public int updateCustomer(CustomerDto dto)
UPDATE CUSTOMER SET cPW = '222',
                    cNAME = '수정이름',
                    cTEL = '011-0111-0111',
                    cEMAIL = 'sujong@sujong.com',
                    cADDRESS = '수정시 수정동',
                    cGENDER = 'f',
                    cBIRTH = '2023-02-27'
                WHERE cID = 'ZZZ';
  -- 6. 리스트(탑앤) public ArrayList<CustomerDto> listCustomer(int startRow,int endRow)
SELECT * FROM
  (SELECT ROWNUM RN, A.* 
    FROM (SELECT * FROM CUSTOMER ORDER BY CRDATE DESC) A)
  WHERE RN BETWEEN 3 AND 5;
  -- 7. 총 회원수 public int totalCountCustomer()
SELECT COUNT(*) FROM CUSTOMER;



-- FileboardDao(리스트(top-n), 글갯수, 글쓰기(원글,답변글 전단계,답변글), 조회수올리기, 상세보기, 글수정, 글삭제)
  -- 1. 글목록 public ArrayList<FileboardDto> listFileboard(int startRow,int endRow)
SELECT *
  FROM(SELECT ROWNUM RN, A.*
    FROM(SELECT F.*, CNAME, CEMAIL
            FROM FILEBOARD F, CUSTOMER C
            WHERE F.CID = C.CID
            ORDER BY FREF DESC, FRE_STEP) A )
  WHERE RN BETWEEN 2 AND 3;
  -- 2. 등록된 글 수  public int totalCountFileboard()
SELECT COUNT(*) FROM FILEBOARD;
  -- 3. 글쓰기(원글) public int writeFileboard(FileboardDto dto)
INSERT INTO FILEBOARD(fNUM, cID, fSUBJECT, fCONTENT, fFILENAME, fPW, fREF, fRE_STEP, fRE_LEVEL, fIP)
 VALUES(FILEBOARD_SEQ.NEXTVAL, 'aaa', '글3', '본문', NULL, '111', FILEBOARD_SEQ.CURRVAL, 0, 0, '192.168.0.1'); 
  -- 4. 답글쓰기전 전처리 private void preReply(int fref, int fre_step)
UPDATE FILEBOARD SET FRE_STEP = FRE_STEP+1
                WHERE FREF = 2 AND FRE_STEP > 0;  -- 원글의 FREF, FRE_STEP 정보가 필요
  -- 5. 답글쓰기 public int reply(FileboardDto dto)
INSERT INTO FILEBOARD(fNUM, cID, fSUBJECT, fCONTENT, fFILENAME, fPW, fREF, fRE_STEP, fRE_LEVEL, fIP)
 VALUES(FILEBOARD_SEQ.NEXTVAL, 'aaa', '글2-2', '본문', NULL, '111', 2, 1, 1, '192.168.0.1');
                                            -- 원글의 FREF와 같고, FRE_STEP과 FRE_LEVEL은 원글보다 +1

  -- 6. 조회수올리기 public void hitUp(int fnum)
UPDATE FILEBOARD SET FHIT = FHIT + 1 WHERE FNUM = 1;

  -- 7. 상세보기 public FileboardDto detailFileboard(int fnum)
SELECT F.*, CNAME, CEMAIL FROM FILEBOARD F, CUSTOMER C WHERE F.CID = C.CID AND FNUM = 2;
            
  -- 8. 글 수정하기 public int updateFileboard(FileboardDto dto)
UPDATE FILEBOARD SET fSUBJECT = '수정된 제목',
                    fCONTENT = '수정된 본문',
                    fFILENAME = NULL,
                    fPW = '111',
                    fIP = '192.168.10.30'
                WHERE fNUM=4;

  -- 9. 글 삭제하기 public deleteFileboard(int fnum, String fpw)
DELETE FROM FILEBOARD WHERE fNUM=1 AND fPW='1';

--------
COMMIT;
select * from customer;
select * from fileboard;
select * from book;
ROLLBACK;
-------

-- Book (top-N 리스트, 책갯수, 책등록, 상세보기)
  -- 1. 책목록(top-N구문) public ArrayList<BookDto> listBook(int startRow,int endRow)
SELECT * 
    FROM (SELECT ROWNUM RN, A.* FROM (SELECT * FROM BOOK ORDER BY BRDATE DESC) A)
    WHERE RN BETWEEN 3 AND 5;

-- 2. 등록된 책 갯수 public int totalCountBook()
SELECT COUNT(*) CNT FROM BOOK;

-- 3. 책등록 public int registerBook(BookDto dto)
INSERT INTO BOOK (BID, BTITLE, BPRICE, BIMAGE1, BIMAGE2, BCONTENT, BDISCOUNT)
    VALUES (BOOK_SEQ.NEXTVAL, '이것은 자바다', 30000, 'noImg.png','NOTHING.JPG','책설명',20);
    
-- 4. 책 상세보기(bID로 dto가져오기) public BookDto detailBook(int bid)
SELECT * FROM BOOK WHERE BID=1;

COMMIT;



















