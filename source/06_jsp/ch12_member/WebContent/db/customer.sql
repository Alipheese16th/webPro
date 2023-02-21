-- TABLE DROP & CREATE
DROP TABLE CUSTOMER;

CREATE TABLE CUSTOMER (
  cID VARCHAR2(50) PRIMARY KEY,
  cPW VARCHAR2(50) NOT NULL,
  cNAME VARCHAR2(50) NOT NULL,
  cTEL VARCHAR2(50),
  cEMAIL VARCHAR2(50),
  cADDRESS VARCHAR2(250),
  cBIRTH DATE,
  cGENDER VARCHAR2(1) CHECK(cGENDER='m' OR cGENDER='f'),
  cRDATE DATE DEFAULT SYSDATE NOT NULL
);
SELECT * FROM CUSTOMER;

-- 1. ȸ�����Խ� id�ߺ�üũ : public int confirmId(String cid)
SELECT COUNT(*) cnt FROM CUSTOMER WHERE CID='aab';
--SELECT * FROM CUSTOMER WHERE CID='aaa';

-- 2. ȸ������ : public int joinCustomer(CustomerDto dto)
INSERT INTO CUSTOMER (CID, CPW, CNAME, CTEL, CEMAIL, CADDRESS, CBIRTH, CGENDER)
  VALUES ('aaa', '111', 'ȫ', '010-9999-9999', 'h@h.com',
    '����', '1995-12-12', 'm');

-- 3. �α��� : public int loginCheck(String cid, String cpw)
SELECT CID, CPW FROM CUSTOMER WHERE CID = 'aaa' AND CPW = '111';

-- 4. cid�� DTO �������� : public CustomerDto getCustomer(String cid)
SELECT * FROM CUSTOMER WHERE CID = 'aaa';

-- 5. �������� : public int modifyCustomer(CustomerDto dto)
UPDATE CUSTOMER SET CPW = '111',
                    CNAME = 'ȫ�浿',
                    CTEL = '02-111-1111',
                    CEMAIL = 'GIL@H.COM',
                    CADDRESS = '���ֵ�',
                    CBIRTH = '1990-12-12',
                    CGENDER = 'f'
                WHERE CID = 'aaa';


SELECT * FROM CUSTOMER;
COMMIT;



