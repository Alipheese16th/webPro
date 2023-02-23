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
SELECT * FROM BOARD ORDER BY REF DESC, RE_STEP ASC;  -- �� �� �� �� ��

-- 2. �۸�� (TOP-N����) -- ����¡

SELECT * FROM BOARD ORDER BY REF DESC; -- 1�ܰ� - ���� ����

SELECT ROWNUM RN, A.* FROM (SELECT * FROM BOARD ORDER BY REF DESC, RE_STEP ASC) A; -- 2�ܰ� -- RN�ο�

SELECT * 
  FROM (SELECT ROWNUM RN, A.* FROM (SELECT * FROM BOARD ORDER BY REF DESC, RE_STEP ASC) A)
  WHERE RN BETWEEN 21 AND 30; -- 3�ܰ� �ϼ�  -- �� �� �� �� ��

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


-- ��۴ޱ�
SELECT * FROM BOARD ORDER BY REF DESC, RE_STEP ASC;

-- 220�� (����)
INSERT INTO BOARD(NUM, WRITER, SUBJECT, CONTENT, EMAIL, PW, REF, RE_STEP, RE_INDENT, IP)
  VALUES(220,'ȫ','��1','����',NULL,'111',220,0,0,'168.125.1.1');
  
-- 220���� 1��° ��۴ޱ�
  -- �亯�� �߰� ���ܰ�(������ �ʹܰ�) -- ������ �ִ� �亯���� �Ʒ��� ����
  UPDATE BOARD SET RE_STEP = RE_STEP+1
    WHERE REF = 220 AND RE_STEP > 0;
  -- �亯�� INSERT (REF = 220, RE_STEP = 1, RE_INDENT = 1)
INSERT INTO BOARD(NUM, WRITER, SUBJECT, CONTENT, EMAIL, PW, REF, RE_STEP, RE_INDENT, IP)
  VALUES(221,'���','��1-��1','����','R@R.COM','111',220,1,1,'123.12.12.12');

-- 220���� 2��° ��۴ޱ�
  -- �亯�� �߰� ���ܰ�(������ �ʹܰ�)
  UPDATE BOARD SET RE_STEP = RE_STEP+1
    WHERE REF = 220 AND RE_STEP > 0;  -- 8. �亯�� �߰� �� �ܰ�

  -- �亯�� INSERT (REF = 220, RE_STEP = 1, RE_INDENT = 1)
  SELECT MAX(NUM) FROM BOARD;  -- BOARD �ְ� NUM �� Ȯ��
INSERT INTO BOARD(NUM, WRITER, SUBJECT, CONTENT, EMAIL, PW, REF, RE_STEP, RE_INDENT, IP)
  VALUES(222,'�̴���','��1-��2','����','LEE@L.COM','111',220,1,1,'121.1.1.1');  -- 9. �亯�� �߰�


SELECT MAX(NUM) FROM BOARD; -- �ְ� �� ��ȣ
SELECT * FROM BOARD WHERE REF=(SELECT REF FROM BOARD WHERE NUM=222);
-- 222���� 1��° ��۴ޱ�
  -- �亯�� �߰� ���ܰ�(������ �ʹܰ� - ������ REF:220 ������ RE_STEP:1)
  UPDATE BOARD SET RE_STEP = RE_STEP+1
    WHERE REF = 220 AND RE_STEP > 1;  -- 8. �亯�� �߰� �� �ܰ� (DAO)

  -- �亯�� INSERT (������ REF=220, ������ RE_STEP=1, ������ RE_INDENT=1)
  SELECT MAX(NUM) FROM BOARD;  -- BOARD �ְ� NUM �� Ȯ��
INSERT INTO BOARD(NUM, WRITER, SUBJECT, CONTENT, EMAIL, PW, REF, RE_STEP, RE_INDENT, IP)
  VALUES((SELECT NVL(MAX(NUM),0)+1 FROM BOARD),'�̴���','222�۴�','����','LEE@L.COM','111',220,2,2,'121.1.1.1');  -- 9. �亯�� �߰� (DAO)



SELECT * FROM BOARD ORDER BY REF DESC, RE_STEP ASC;
COMMIT;




