-- [ V ] �׷��Լ� : SUM, AVG, MIN, MAX, COUNT, STDDEV(ǥ������), VARIANCE(�л�)
SELECT MAX(SAL), MIN(SAL), COUNT(SAL) FROM EMP;
SELECT DEPTNO, MAX(SAL) FROM EMP GROUP BY DEPTNO;  -- �μ���
SELECT MAX(SAL) FROM EMP;  -- �ִ� �޿��� �޴� ����� �̸��� �ִ�޿� -> VI. ��������
SELECT ENAME, SAL FROM EMP WHERE SAL = (SELECT MAX(SAL) FROM EMP);

-- �� 1. �׷��Լ��� �ǽ�
SELECT ROUND(AVG(SAL),2) FROM EMP;
SELECT AVG(SAL), SUM(SAL)/COUNT(SAL) FROM EMP;
SELECT SUM(COMM) FROM EMP;  -- NULL�� �־ �����ϰ� ���
SELECT SUM(COMM), AVG(COMM) FROM EMP;  -- ��� �׷��Լ��� NULL�� �����ϰ� ���. ����� 14���� ������ �ƴ� 4�� ������ �ǹ���
SELECT COUNT(*) FROM EMP;  -- ���̺� ���



SELECT SUM(COMM), AVG(NVL(COMM,0)) FROM EMP; 