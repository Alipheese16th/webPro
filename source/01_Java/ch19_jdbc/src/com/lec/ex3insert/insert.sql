SELECT * FROM DEPT;
desc dept;
-- ex.1 ����ڿ��� �μ���ȣ, �μ���, �ٹ����� �Է¹޾� insert
INSERT INTO DEPT VALUES (50, 'IT','SEOUL');
COMMIT;

-- ex.2 �μ���ȣ �Է¹޾� �ߺ�üũ ���Ŀ� �μ���, �ٹ��� �Է¹޾� insert
SELECT COUNT(*) CNT FROM DEPT WHERE DEPTNO = 99;
INSERT INTO DEPT VALUES (60, 'IT','SEOUL');

-- update
SELECT COUNT(*) FROM DEPT WHERE DEPTNO = 99;
UPDATE DEPT SET DNAME = 'MARKETING', LOC = 'INCHEON' WHERE DEPTNO = 52;

-- DELETE
DELETE FROM DEPT WHERE DEPTNO = 52;
