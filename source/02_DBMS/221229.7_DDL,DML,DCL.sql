-- [ VII ] DDL, DCL, CML
-- SQL = DDL (���̺� ����, ���̺� ����, ���̺����� ����, ���̺� ��� ������ ����)
--       DML (SELECT, INSERT, UPDATE, DELETE) 
--       DCL (����� ���� ����, ����ڿ��� ���Ѻο�, ���� ��Ż

--  �� �� �� DDL �� �� ��
-- 1. ���̺� ���� (CREATE TABLE ���̺���...) : ���̺� ������ �����ϱ�
CREATE TABLE BOOK (
    BOOKID    NUMBER(4),    -- BOOKID �ʵ��� Ÿ���� ���� 4�ڸ� 
    BOOKNAME  VARCHAR2(20), -- BOOKNAME �ʵ��� Ÿ���� ���� 20BYTE
    PUBLISHER VARCHAR2(20), -- PUBLISHER �ʵ�Ÿ�� ���� 20BYTE
    RDATE     DATE,         -- RDATE �ʵ��� Ÿ���� DATE��
    PRICE     NUMBER(8,2),   -- PRICE �ʵ�Ÿ���� ������ü 8�ڸ�, �Ҽ���2�ڸ�, �Ҽ����� 6�ڸ�
    PRIMARY KEY(BOOKID)  -- �������� : BOOKID�ʵ尡 ��Ű(PRIMARY KEY : NOT NULL, UNIQUE)
);
SELECT * FROM BOOK;
DESC BOOK;
DROP TABLE BOOK; -- ���̺� ����
CREATE TABLE BOOK (
    BID     NUMBER(4) PRIMARY KEY,
    BNAME   VARCHAR2(20),
    PUBLISHER VARCHAR2(20),
    RDATE   DATE,
    PRICE   NUMBER(8)
);
    -- ex. EMP�� ������ EMP01 ���̺� : EMPNO(����4), ENAME(����10), SAL(����7,2)
CREATE TABLE EMP01(
    EMPNO NUMBER(4),
    ENAME VARCHAR2(10),
    SAL   NUMBER(7,2)
);
    -- ex. DEPT01 ���̺� : DEPTNO(����2:PK), DNAME(����14), LOC(����13)
CREATE TABLE DEPT01(
    DEPTNO NUMBER(2) PRIMARY KEY,
    DNAME  VARCHAR2(14),
    LOC    VARCHAR2(13)
);
DESC DEPT01;
-- ���������� �̿��� ���̺� ����
CREATE TABLE EMP02
    AS
    SELECT * FROM EMP; -- �������� ����� EMP02 ���̺� ������ ������ �Է� (�ٸ� ���� ���� ������)
SELECT * FROM EMP02;
    -- EMP03 : EMP ���̺����� EMPNO, ENAME, DEPTNO�� ������ ������
CREATE TABLE EMP03 AS SELECT EMPNO, ENAME, DEPTNO FROM EMP;
SELECT * FROM EMP03;
    -- EMP04 : EMP���̺����� 10�� �μ��� ������ ������
CREATE TABLE EMP04 AS SELECT * FROM EMP WHERE DEPTNO = 10;
SELECT * FROM EMP04;
    -- EMP05 : EMP ���̺� ������ ����(�����͸� �������� ����)
CREATE TABLE EMP05 AS SELECT * FROM EMP WHERE 1=0;
SELECT * FROM EMP05;

SELECT ROWNUM, EMPNO, ENAME, JOB FROM EMP; -- ���̺����� ���� ������ ����(�о���� ����)

-- 2. ���̺� ���� ���� (ALTER TABLE ���̺��� ADD || MODIFY || DROP ~)
-- (1) �ʵ� �߰�(ADD)
SELECT * FROM EMP03; -- EMPNO(��4), ENAME(��10), DEPTNO(��2)
ALTER TABLE EMP03 ADD (JOB VARCHAR2(20), SAL NUMBER(7,2) );
SELECT * FROM EMP03; -- �ʵ� �߰��� ������ NULL��
ALTER TABLE EMP03 ADD (COMM NUMBER(7,2));
-- (2) �ʵ� ����(MODIFY) -- EMPNO(��4), ENAME(��10), DEPTNO(��2), JOB, SAL, COMM �� NULL
ALTER TABLE EMP03 MODIFY (EMPNO VARCHAR2(5));   -- ���ڵ����Ͱ� ����־ ���ڷθ� ���� ����.
ALTER TABLE EMP03 MODIFY (EMPNO NUMBER(5));     -- ���� �뷮�� �ø��°� ���������� ���̴°� �Ұ���
ALTER TABLE EMP03 MODIFY (SAL VARCHAR2(10));    -- NULL �ʵ�� ������� ���� ����
ALTER TABLE EMP03 MODIFY (ENAME VARCHAR2(20));  -- ���ڵ����� �ø��ų� ���̰ų� ����
SELECT MAX(LENGTH(ENAME)) FROM EMP; -- ���� ENAME ������ �� �ִ���� 6
ALTER TABLE EMP03 MODIFY (ENAME VARCHAR2(6));   -- ����
ALTER TABLE EMP03 MODIFY (ENAME VARCHAR2(5));   -- �Ұ���
-- (3) �ʵ� ����(DROP)
ALTER TABLE EMP03 DROP COLUMN JOB; -- NULL�� �ʵ� ����(NULL�� �ƴ� �ʵ�� �����ͱ��� ����)
SELECT * FROM EMP03;
ALTER TABLE EMP03 DROP COLUMN DEPTNO;
-- ���������� Ư�� �ʵ带 ���� ���ϵ���(��)
ALTER TABLE EMP03 SET UNUSED (COMM);
-- ���������� ���� �Ұ��ߴ� �ʵ� ���������� ����(����)
ALTER TABLE EMP03 DROP UNUSED COLUMNS;

-- 3. ���̺� ����(DROP TABLE ���̺���)
DROP TABLE EMP01;
SELECT * FROM EMP01;
DROP TABLE DEPT; -- EMP���̺����� DEPT���̺��� �����ϴ� ��� EMP ���̺��� ������ �� DEPT ���̺� ����

-- 4. ���̺��� ��� ���� ���� (TRUNCATE TABLE ���̺���)
SELECT * FROM EMP03;
TRUNCATE TABLE EMP03; -- ROLLBACK �� ��

-- 5. ���̺��� ����(RENAME ���̺��� TO �ٲ� ���̺���)
SELECT * FROM EMP02;
RENAME EMP02 TO EMP2;
SELECT * FROM EMP2;

-- 6. ������ ��ųʸ�(���ٺҰ�) VS. ������ ��ųʸ� ��(���ٿ�)
-- ����
    -- USER_XXX : �� ������ �����ϰ� �ִ� ��ü(���̺�, ��������, ��, �ε���)
        -- USER_TABLES, USER_CONSTRAINTS, USER_INDEXES, USER_VIEWS
SELECT * FROM USER_TABLES;
SELECT * FROM USER_CONSTRAINTS;
SELECT * FROM USER_INDEXES;
SELECT * FROM USER_VIEWS;
    -- ALL_XXX : �� �������� ���� ������ ��ü
        -- ALL_TABLES, ALL_CONSTRAINTS, ALL_INDEXES, ALL_VIEWS
SELECT * FROM ALL_TABLES;
SELECT * FROM ALL_TABLES WHERE TABLE_NAME='EMP';
SELECT * FROM ALL_VIEWS;
    -- (3) DBA_XXX : DBA���ѿ����� ���ٰ����� DBMS�� ��� ��ü
        -- DBA_TABLES, DBA_CONSTRAINTS, DBA_INDEXES, DBA_VIEWS
SELECT * FROM DBA_TABLES;

-- �� �� �� DML �� �� ��
-- 1. INSERT INTO ���̺��� VALUES (��1, ��2, ...);
   -- INSERT INTO ���̺��� (�ʵ��1, �ʵ��2, ..) VALUES (��1, ��2, ..);
SELECT * FROM DEPT01;
ROLLBACK;
INSERT INTO DEPT01 VALUES (50,'ACCOUNTING','NEW YORK');
INSERT INTO DEPT01 VALUES (60,'SALES',NULL);  -- ���������� NULL �߰�
INSERT INTO DEPT01 (DEPTNO,DNAME,LOC) VALUES (70,'RESEARCH','���빮');
INSERT INTO DEPT01 (LOC,DEPTNO,DNAME) VALUES ('����',80,'IT');
INSERT INTO DEPT01 (DEPTNO,DNAME) VALUES (90,'OPERATION'); -- ���������� NULL �߰�
-- ���������� �̿��� INSERT
    -- ex. DEPT ���̺����� 10~30�� �μ��� DEPT01 ���̺��� 
INSERT INTO DEPT01 SELECT * FROM DEPT WHERE DEPTNO < 40;
SELECT * FROM DEPT01;
    -- ex. BOOK (BID�� 11, BNAME�� '����������', ���ǻ�� '�Ѽ�����', �������� ����, ������ 90000)
DESC BOOK;
INSERT INTO BOOK VALUES (11,'����������','�Ѽ�����',SYSDATE,90000);
INSERT INTO BOOK VALUES (12,'����������','�Ѽ�����',SYSDATE,90000);
-- Ʈ������ ���ɾ� (DML���ɾ���� ����)
    -- Ʈ�������� ������ ó���� �� ����. DML ���ɾ���� ����ʰ� ���ÿ� Ʈ�������� ����.
COMMIT; -- �� Ʈ�������� �۾��� DB�� �ݿ�
ROLLBACK; -- �� Ʈ�������� �۾��� ���

-- �������� (1������)
DROP TABLE SAM01;
CREATE TABLE SAM01 (
    EMPNO NUMBER(4) PRIMARY KEY,
    ENAME VARCHAR2(10),
    JOB VARCHAR2(9),
    SAL NUMBER(7,2)
);
SELECT * FROM SAM01;
INSERT INTO SAM01 VALUES(1000,'APPLE','POLICE',10000);
INSERT INTO SAM01 VALUES(1010,'BANANA','NURSE',15000);
INSERT INTO SAM01 VALUES(1020,'ORANGE','DOCTOR',25000);
INSERT INTO SAM01 (EMPNO,ENAME,SAL) VALUES(1030,'VERY',25000);
INSERT INTO SAM01 (EMPNO,ENAME,SAL) VALUES(1040,'CAT',2000);
INSERT INTO SAM01 SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE DEPTNO = 10;
COMMIT;

-- 2. UPDATE ���̺��� SET �ʵ��1=��1 [, �ʵ��2=��2, �ʵ��N=��N...] [WHERE ����];
SELECT * FROM EMP01;
CREATE TABLE EMP01 AS SELECT * FROM EMP;
    -- ex. �μ���ȣ�� 30���� ����
UPDATE EMP01 SET DEPTNO = 30;
    -- ex. ��� ����(EMP01)�� �޿�(SAL)�� 10% �λ�
UPDATE EMP01 SET SAL = SAL*1.1;
    -- ex. EMP01���̺� : 10�� �μ� ������ �Ի����� ���÷�, �μ���ȣ�� 30������ ����
UPDATE EMP01 SET HIREDATE = SYSDATE, DEPTNO = 30 WHERE DEPTNO =10;
    -- ex. SAL�� 3000�̻��� ����� �޿��� 10% �λ��Ű�ÿ�
UPDATE EMP01 SET SAL = SAL*1.1 WHERE SAL >= 3000;
    -- ex. DALLAS�� �ٹ��ϴ� ������ �޿��� 1000$ �λ�
UPDATE EMP01 SET SAL = SAL + 1000 
    WHERE DEPTNO = (SELECT DEPTNO FROM DEPT WHERE LOC = 'DALLAS');
    -- ex. SCOTT�� �μ���ȣ 20����, JOB�� MANAGER��, SAL�� COMM�� 500$�� �λ�, ���� KING���� ����
UPDATE EMP01 SET DEPTNO = 20, 
                JOB = 'MANAGER', 
                SAL = SAL+500, 
                COMM = NVL(COMM,0)+500,
                MGR = (SELECT EMPNO FROM EMP WHERE ENAME = 'KING')
        WHERE ENAME = 'SCOTT';
    -- ex. DEPT01���� 60�� �μ��� �������� 20�� �μ��� �������� ����
SELECT * FROM DEPT01;
UPDATE DEPT01 SET LOC = (SELECT LOC FROM DEPT01 WHERE DEPTNO=20) WHERE DEPTNO = 60;
    -- ex. EMP01���� ��� ����� �޿��� �Ի����� 'KING'�� �޿��� �Ի��Ϸ� ����
SELECT * FROM EMP01;
UPDATE EMP01 SET SAL = (SELECT SAL FROM EMP WHERE ENAME = 'KING'),
            HIREDATE = (SELECT HIREDATE FROM EMP WHERE ENAME = 'KING');
UPDATE EMP01 SET (SAL, HIREDATE) = (SELECT SAL, HIREDATE FROM EMP WHERE ENAME = 'KING');

-- 3. DELETE FROM ���̺��� WHERE ����;
SELECT * FROM EMP01;
DELETE FROM EMP01;
ROLLBACK; -- DML ��� ����
    -- ex. EMP01���� 30�� �μ� ������ ����
DELETE FROM EMP01 WHERE DEPTNO = 30;
    -- ex. 'FORD'��� ���
DELETE FROM EMP01 WHERE ENAME = 'FORD';
    -- ex. SAM01 ���̺����� JOB�� �������� ���� ����� ����
SELECT * FROM SAM01;
DELETE FROM SAM01 WHERE JOB IS NULL;
    -- EMP01 : �μ����� SALES�� ����� ���� (�������� �̿�)
DELETE FROM EMP01 WHERE DEPTNO = (SELECT DEPTNO FROM DEPT WHERE DNAME = 'SALES');
    -- EMP01 : �μ����� RESEARCH �μ��� �ٹ��ϴ� ����� ����
DELETE FROM EMP01 WHERE DEPTNO = (SELECT DEPTNO FROM DEPT WHERE DNAME = 'RESEARCH');
SELECT * FROM EMP01;
COMMIT ;

-- �� �������� (PDF 2 ~ 3 ������)
CREATE TABLE MY_DATA (
    ID NUMBER(4) PRIMARY KEY,
    NAME VARCHAR2(10),
    USERID VARCHAR2(30),
    SALARY NUMBER(10,2)
);
SELECT * FROM MY_DATA;
INSERT INTO MY_DATA VALUES (1,'Scott','sscott',10000);
INSERT INTO MY_DATA VALUES (2,'Ford','fford',13000);
INSERT INTO MY_DATA VALUES (3,'Patel','ppatel',33000);
INSERT INTO MY_DATA VALUES (4,'Report','rreport',23500);
INSERT INTO MY_DATA VALUES (5,'Good','ggood',44450);
SELECT ID,NAME,USERID,TO_CHAR(SALARY,'99,999.99') FROM MY_DATA;
COMMIT;
UPDATE MY_DATA SET SALARY = 65000 WHERE ID = 3;
COMMIT;
DELETE FROM MY_DATA WHERE NAME = 'Ford';
COMMIT;
UPDATE MY_DATA SET SALARY = 15000 WHERE SALARY < 15000;
DROP TABLE MY_DATA;

-- EMP ���̺��� ���� ������ ���� ������ ���̺� EMP01�� ����(���̺��� ������ ������ ��)�ϰ�, 
--��� ����� �μ���ȣ�� 30������ �����մϴ�.
DROP TABLE EMP01;
CREATE TABLE EMP01 AS SELECT * FROM EMP;
UPDATE EMP01 SET DEPTNO = 30;
-- EMP01���̺��� ��� ����� �޿��� 10% �λ��Ű�� UPDATE���� �ۼ�
UPDATE EMP01 SET SAL = SAL*1.1;
-- �޿��� 3000�̻��� ����� �޿��� 10%�λ�
UPDATE EMP01 SET SAL = SAL*1.1 WHERE SAL >= 3000;
-- EMP01���̺����� ��DALLAS������ �ٹ��ϴ� �������� ������ 1000�λ�
UPDATE EMP01 SET SAL = SAL+1000 WHERE DEPTNO = (SELECT DEPTNO FROM DEPT WHERE LOC = 'DALLAS');
-- SCOTT����� �μ���ȣ�� 20������, ������ MANAGER�� �Ѳ����� ����
UPDATE EMP01 SET JOB = 'MANAGER', DEPTNO =20 WHERE ENAME = 'SCOTT';
-- �μ����� SALES�� ����� ��� �����ϴ� SQL�ۼ�
DELETE FROM EMP01 WHERE DEPTNO = (SELECT DEPTNO FROM DEPT WHERE DNAME = 'SALES');
-- ������� ��FORD���� ����� �����ϴ� SQL �ۼ�
DELETE FROM EMP01 WHERE ENAME = 'FORD';
-- SAM01 ���̺����� JOB�� NULL�� ����� �����Ͻÿ�
DELETE FROM SAM01 WHERE JOB IS NULL;
-- SAM01���̺����� ENAME�� ORANGE�� ����� �����Ͻÿ�
DELETE FROM SAM01 WHERE ENAME = 'ORANGE';


-- �� �� ��  ��������
-- (1) PRIMARY KEY : ���̺��� �� ���� ������ ������ �ĺ��ϱ� ���� �ʵ�
-- (2) FOREIGN KEY : ���̺��� ���� �ٸ� ���̺��� ���� ����
-- (3) NOT NULL    : NULL �� �������� �ʴ´�
-- (4) UNIQUE      : ��� ���� ���� �����ؾ� �Ѵ�. NULL ���� ���(�������� ����)
-- (5) CHECK (����) : �ش� ������ �����ؾ��Ѵ�. (���������ʾƵ� NULL���� ���)
-- DEFAULT : �⺻�� ���� (�ش� ���� ������ �Է� ���� ��� ������ NULL�� �� ���� DEFAULT �� �Է�)
    -- DEPT1 & EMP1 ���̺� ����
DROP TABLE EMP1;
DROP TABLE DEPT1;
CREATE TABLE DEPT1 (
    DEPTNO NUMBER(2) PRIMARY KEY,
    DNAME VARCHAR2(14) NOT NULL UNIQUE,
    LOC VARCHAR2(13) NOT NULL
);
SELECT * FROM USER_CONSTRAINTS; -- USER�� ������ �������ǵ� 
SELECT * FROM USER_CONSTRAINTS WHERE TABLE_NAME = 'DEPT1';
CREATE TABLE DEPT1 (
    DEPTNO NUMBER(2) CONSTRAINT DEPT_PK PRIMARY KEY, -- ���������� �̸� ����
    DNAME VARCHAR2(14) CONSTRAINT DEPT_U NOT NULL UNIQUE,
    LOC VARCHAR2(13) CONSTRAINT DEPT_LOC NOT NULL
);
SELECT * FROM USER_CONSTRAINTS WHERE TABLE_NAME = 'DEPT1';
CREATE TABLE DEPT1 (
    DEPTNO NUMBER(2),
    DNAME VARCHAR2(14) NOT NULL, -- NOT NULL �� �ݵ�� �����ʿ� ����Ѵ�
    LOC VARCHAR2(13) NOT NULL,
    CONSTRAINT DEPTNO_P PRIMARY KEY(DEPTNO),
    CONSTRAINT DNAME_U UNIQUE(DNAME)
);
SELECT * FROM USER_CONSTRAINTS WHERE TABLE_NAME = 'DEPT1';
SELECT * FROM DEPT1;
SELECT * FROM USER_INDEXES WHERE TABLE_NAME = 'DEPT1';
DROP TABLE EMP1;
CREATE TABLE EMP1 (
    EMPNO NUMBER(4) PRIMARY KEY,
    ENAME VARCHAR2(10) NOT NULL,
    JOB VARCHAR2(9) NOT NULL,
    MGR NUMBER(4),
    HIREDATE DATE DEFAULT SYSDATE,
    SAL NUMBER(7,2) CHECK(SAL>0),
    COMM NUMBER(7,2),
    DEPTNO NUMBER(2) REFERENCES DEPT1(DEPTNO)
);
CREATE TABLE EMP1 (
    EMPNO NUMBER(4),
    ENAME VARCHAR2(10) NOT NULL,
    JOB VARCHAR2(9) NOT NULL,
    MGR NUMBER(4),
    HIREDATE DATE DEFAULT SYSDATE,
    SAL NUMBER(7,2),
    COMM NUMBER(7,2),
    DEPTNO NUMBER(2),
    PRIMARY KEY(EMPNO),
    CHECK(SAL>0),
    FOREIGN KEY(DEPTNO) REFERENCES DEPT1(DEPTNO) -- FOREIGN KEY �� �ؿ� ���°�찡 ����
);
INSERT INTO DEPT1 SELECT * FROM DEPT;
INSERT INTO DEPT1 VALUES (50, 'IT', 'SEOUL');
INSERT INTO DEPT1 (DEPTNO,DNAME,LOC) VALUES (60,'IT','PUSAN'); -- ���� (DNAME : ����ũ ��������)
INSERT INTO DEPT1 (DEPTNO,DNAME,LOC) VALUES (60,'PLANNING','PUSAN');
INSERT INTO DEPT1 (DEPTNO,DNAME) VALUES (70, 'CS'); -- ���� (LOC : NOT NULL ��������)
INSERT INTO DEPT1 (DEPTNO,DNAME,LOC) VALUES (70, 'CS','GANGNAM');
SELECT * FROM DEPT1;

INSERT INTO EMP1 (EMPNO, ENAME, JOB)
    VALUES (1001,'HONG','MANAGER');
SELECT * FROM EMP1;
INSERT INTO EMP1 (EMPNO, ENAME, JOB, SAL)
    VALUES (1002, 'HONG', 'MANAGER', 0); -- ���� (CHECK SAL�� 0���� Ŀ����) (NULL�� ����)
INSERT INTO EMP1 (EMPNO, ENAME, JOB, SAL)
    VALUES (1002, 'HONG', 'MANAGER', 10000);
INSERT INTO EMP1 VALUES (1003,'KIM','MANAGER',NULL,NULL,NULL,NULL,90); -- ���� (�θ� Ű�� ã���� ����)
INSERT INTO EMP1 VALUES (1003,'KIM','MANAGER',NULL,NULL,NULL,NULL,70);

-- ������ ������
SELECT * FROM BOOKCATEGORY;  -- 100, 200, 300, 400
SELECT * FROM BOOK;  -- 100, 400
DELETE FROM BOOKCATEGORY WHERE CATEGORY_CODE = 100;  -- CHILD���̺����� �����ϰ� �ִ� ������
DELETE FROM BOOK WHERE CATEGORY_CODE = 100;
DELETE FROM BOOKCATEGORY WHERE CATEGORY_CODE = 100;  -- �����ϴ� �����Ͱ� ������ ���� ����
DELETE FROM BOOKCATEGORY WHERE CATEGORY_CODE IN (200,300);
DELETE FROM BOOK;
SELECT * FROM BOOK;
COMMIT;
DELETE FROM BOOKCATEGORY;
DROP TABLE BOOK;
DROP TABLE BOOKCATEGORY;
DROP TABLE BOOKCATEGORY CASCADE CONSTRAINTS; -- ����

-- �� DCL (����� ���� ����, ����� ���� �ο�, ���� ��Ż, ����� ���� ����)
CREATE USER scott2 IDENTIFIED BY tiger; -- scott2 ���� ���� (����� ��ҹ��� ��������)
-- ���� �ο�(���� �ο�)
GRANT CREATE SESSION TO SCOTT2;
-- ���� �ο�(TABLE, VIEW ����)
CREATE ROLE ROLEex;  -- ���ϴ� ���� ����
GRANT CREATE TABLE, CREATE VIEW TO ROLEex; -- ���� ����
GRANT ROLEex TO SCOTT2;  -- ���� �ο�
-- ���� �ο�(EMP���̺��� ������)
GRANT ALL ON EMP TO SCOTT2;
GRANT SELECT ON DEPT TO SCOTT2;
-- ���� ��Ż(DEPT���̺�, EMP���̺� ����) : ���� ���� �� �� ���� ��õ
REVOKE ALL ON EMP FROM SCOTT2;
REVOKE SELECT ON DEPT FROM SCOTT2;
-- ����� ���� ����(���� ���� ���� �Ұ�)
DROP USER SCOTT2 CASCADE;

