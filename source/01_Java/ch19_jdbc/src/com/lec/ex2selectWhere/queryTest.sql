-- Ex1. �μ���ȣ �ް� �μ����� ����ϱ�
SELECT * FROM DEPT WHERE DEPTNO = 40;
-- 40�� ����

-- Ex2. �μ���ȣ �ް� �μ������� ������� ���(���, �̸�, �޿�, ����)
SELECT * FROM DEPT WHERE DEPTNO = 30;

SELECT W.EMPNO, W.ENAME, W.SAL, M.ENAME MANAGER 
    FROM EMP W, EMP M 
    WHERE W.MGR = M.EMPNO
    AND W.DEPTNO = 30;
    
SELECT * FROM EMP;
SELECT * FROM DEPT;
COMMIT;

-- Ex3. �μ��̸��� �Է¹޾� �μ������� ���
SELECT * FROM DEPT WHERE DNAME = UPPER('sales');
