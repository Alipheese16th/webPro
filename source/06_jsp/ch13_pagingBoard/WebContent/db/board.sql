-- BOARD TABLE DROP & CREATE
DROP TABLE BOARD;

CREATE TABLE BOARD(
  NUM     NUMBER(5) PRIMARY KEY,  -- �۹�ȣ
  WRITER  VARCHAR2(30) NOT NULL,  -- ���ۼ���
  SUBJECT VARCHAR2(100) NOT NULL, -- ������
  CONTENT VARCHAR2(100) NOT NULL, -- ����
  EMAIL   VARCHAR2(30),           -- �ۼ��� �̸���
  READCOUNT NUMBER(5) DEFAULT 0,  -- ��ȸ�� (HIT)
  PW      VARCHAR2(30) NOT NULL,  -- �� ������ �� ��й�ȣ
  REF     NUMBER(5) NOT NULL,     -- �۱׷�(������ ��� �۹�ȣ��/ �亯���ϰ�� ������ REF��)
  RE_STEP NUMBER(3) NOT NULL,     -- �۱׷쳻 ��� ����(���� 0)
  RE_INDENT NUMBER(3) NOT NULL,   -- �� LIST ��½� �� ���� �鿩���� ����(���� 0)
  IP      VARCHAR2(30) NOT NULL,  -- �� �ۼ��� ��ǻ�� IP �ּ�
  RDATE   DATE DEFAULT SYSDATE    -- �۾� ����(��¥+�ð�)
);

SELECT * FROM BOARD;

-- 1. �۰���
SELECT COUNT(*) FROM BOARD;

-- 2. �۸��(�۱׷��� �ֽ��� ���� ����)
SELECT * FROM BOARD ORDER BY REF DESC;

-- 2. �۸�� (TOP-N����) -- ����¡
SELECT * FROM BOARD ORDER BY REF DESC; -- 1�ܰ� - ���� ����
SELECT ROWNUM RN, A.* FROM (SELECT * FROM BOARD ORDER BY REF DESC) A; -- 2�ܰ� -- RN�ο�
SELECT * 
  FROM (SELECT ROWNUM RN, A.* FROM (SELECT * FROM BOARD ORDER BY REF DESC) A)
  WHERE RN BETWEEN 21 AND 30; -- 3�ܰ� �ϼ�

-- 3. �۾���(���۾���) - �۾���, ����, ����, �̸���, PW
SELECT NVL(MAX(NUM),0)+1 FROM BOARD; -- ���������� �Ϸ��� �̷��� ���������� �����

INSERT INTO BOARD(NUM, WRITER, SUBJECT, CONTENT, EMAIL,
                  PW, REF, RE_STEP, RE_INDENT, IP)
  VALUES((SELECT NVL(MAX(NUM),0)+1 FROM BOARD),'ȫ�浿','����1','����\n�氡',null,
                '111',(SELECT NVL(MAX(NUM),0)+1 FROM BOARD),0,0,'192.168.0.1');

INSERT INTO BOARD(NUM, WRITER, SUBJECT, CONTENT, EMAIL,
                  PW, REF, RE_STEP, RE_INDENT, IP)
  VALUES((SELECT NVL(MAX(NUM),0)+1 FROM BOARD),'���浿','����2','����\n�氡',null,
                '111',(SELECT NVL(MAX(NUM),0)+1 FROM BOARD),0,0,'192.168.0.1');


-- 4. �۹�ȣ�� �ۻ󼼺��� ����(DTO) ��������
SELECT * FROM BOARD WHERE NUM = 2;

-- 5. ��ȸ�� �ø���
UPDATE BOARD SET READCOUNT = READCOUNT + 1 WHERE NUM = 2;

-- 6. �� ����
UPDATE BOARD SET SUBJECT = '����������1',
                CONTENT = '������ ����\n�氡',
                EMAIL = 'hong@hong.com',
                PW = '111',
                IP = '127.0.0.1'
                WHERE NUM = 1;

-- 7. �� ����(��й�ȣ�� �°� �Է��� ��츸 ����)
COMMIT;
DELETE FROM BOARD WHERE NUM = 1 AND PW='111';
ROLLBACK;

-- ��ȸ�� ����
UPDATE BOARD SET READCOUNT = 11 WHERE NUM = 1;
SELECT * FROM BOARD ORDER BY REF DESC;

-- ������ ����
DELETE FROM BOARD;
COMMIT;

INSERT INTO BOARD(NUM, WRITER, SUBJECT, CONTENT, EMAIL,
                  PW, REF, RE_STEP, RE_INDENT, IP)
  VALUES((SELECT NVL(MAX(NUM),0)+1 FROM BOARD),'ȫ�浿','����1','����\n�氡',null,
                '111',(SELECT NVL(MAX(NUM),0)+1 FROM BOARD),0,0,'192.168.0.1');

INSERT INTO BOARD(NUM, WRITER, SUBJECT, CONTENT, EMAIL,
                  PW, REF, RE_STEP, RE_INDENT, IP)
  VALUES((SELECT NVL(MAX(NUM),0)+1 FROM BOARD),'���浿','����2','����\n�氡',null,
                '111',(SELECT NVL(MAX(NUM),0)+1 FROM BOARD),0,0,'192.168.0.1');







