SELECT * FROM GRADE;
SELECT * FROM CUSTOMER;

SELECT CID,CTEL,CNAME,CPOINT,CAMOUNT,C.GNO,GRADE,LAMOUNT,HIAMOUNT FROM CUSTOMER C, GRADE G   
    WHERE C.GNO = G.GNO;
    
--1.ȸ�����Խ�	��ȭ��ȣ, �̸� �Է� 	��DTO(��ȭ��ȣ,�̸�) return int 1 , 0
INSERT INTO CUSTOMER VALUES(CUSTOMER_CID_SEQ.NEXTVAL,'010-9999-9999','ȫ�浿',1000,0,1);

--2.��4�ڸ��Է¹޾� SELECT ������ ���	��DTO(ID,��ȭ,�̸�,����Ʈ,���Ŵ�����,��޸�,�������ݾ�)
SELECT CID,CTEL,CNAME,CPOINT,CAMOUNT,GRADE,HIAMOUNT - CAMOUNT "LEVELUP" 
    FROM CUSTOMER C, GRADE G
    WHERE C.GNO = G.GNO AND CTEL LIKE '%8888';
	
--3.��ǰ���� �����̵�� ��ǰ������ �Է¹޾� ó�� //������ ����Ȯ���ؾ��� 
--update�����ݾ�,����Ʈ,WHERE���Ƶ�
UPDATE CUSTOMER SET CAMOUNT = CAMOUNT + 10000, CPOINT = CPOINT + 10000*0.05 
    WHERE CID = 1;
    
-- �������� �ʿ��� �ݾ� ( 0�����Ͻ� ��޺���)
SELECT HIAMOUNT - CAMOUNT "LEVELUP" 
    FROM CUSTOMER C, GRADE G 
    WHERE C.GNO = G.GNO AND CID = 1;

UPDATE CUSTOMER SET GNO = GNO +1 WHERE CID =1; -- ��޻��

--4.��޺� ���	/��� ��޸� ��º����ְ�/ ��� �Է¹����� ������¡�DTO(ID,��ȭ,�̸�,����Ʈ,���Ŵ�����,��޸�,�������� ���� �߰��ݾ�)

--5.����� ���	��DTO(ID,��ȭ,�̸�,����Ʈ,���Ŵ�����,��޸�,�������� ���� �߰��ݾ�)

--6.ȸ��Ż��	��ȭ��ȣ �Է¹޾� 	DELETE WHERE ��ȭ��ȣ


