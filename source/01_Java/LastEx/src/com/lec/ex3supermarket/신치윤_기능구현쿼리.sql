SELECT * FROM GRADE;
SELECT * FROM CUSTOMER;

SELECT CID,CTEL,CNAME,CPOINT,CAMOUNT,C.GNO,GRADE,LAMOUNT,HIAMOUNT FROM CUSTOMER C, GRADE G   
    WHERE C.GNO = G.GNO;
    
--1.회원가입시	전화번호, 이름 입력 	★DTO(전화번호,이름) return int 1 , 0
INSERT INTO CUSTOMER VALUES(CUSTOMER_CID_SEQ.NEXTVAL,'010-9999-9999','홍길동',1000,0,1);

--2.폰4자리입력받아 SELECT 고객정보 출력	★DTO(ID,전화,이름,포인트,구매누적액,등급명,레벨업금액)
SELECT CID,CTEL,CNAME,CPOINT,CAMOUNT,GRADE,HIAMOUNT - CAMOUNT "LEVELUP" 
    FROM CUSTOMER C, GRADE G
    WHERE C.GNO = G.GNO AND CTEL LIKE '%8888';
	
--3.물품구입 고객아이디와 물품가격을 입력받아 처리 //레벨업 조건확인해야함 
--update누적금액,포인트,WHERE고객아디
UPDATE CUSTOMER SET CAMOUNT = CAMOUNT + 10000, CPOINT = CPOINT + 10000*0.05 
    WHERE CID = 1;
    
-- 레벨업시 필요한 금액 ( 0이하일시 등급변경)
SELECT HIAMOUNT - CAMOUNT "LEVELUP" 
    FROM CUSTOMER C, GRADE G 
    WHERE C.GNO = G.GNO AND CID = 1;

UPDATE CUSTOMER SET GNO = GNO +1 WHERE CID =1; -- 등급상승

--4.등급별 출력	/모든 등급명 출력보여주고/ 등급 입력받으면 정보출력★DTO(ID,전화,이름,포인트,구매누적액,등급명,레벨업을 위한 추가금액)

--5.모든멤버 출력	★DTO(ID,전화,이름,포인트,구매누적액,등급명,레벨업을 위한 추가금액)

--6.회원탈퇴	전화번호 입력받아 	DELETE WHERE 전화번호


