--1. EMP ���̺��� sal�� 3000�̻��� ����� empno, ename, job, sal�� ���
SELECT EMPNO, ENAME, JOB, SAL FROM EMP WHERE SAL >= 3000;
--2. EMP ���̺��� empno�� 7788�� ����� ename�� deptno�� ���
SELECT ENAME, DEPTNO FROM EMP WHERE EMPNO = 7788;
--3. ����(SAL*12+COMM)�� 24000 �̻��� ���, �̸�, �޿� ��� (�޿�������)
SELECT EMPNO, ENAME, SAL FROM EMP 
    WHERE (SAL*12+NVL(COMM,0)) >= 2000 ORDER BY SAL;
--4. �Ի����� 1981�� 2�� 20�� 1981�� 5�� 1�� ���̿� �Ի��� ����� �̸�, ��å, �Ի����� ��� (�� hiredate ������ ���)
SELECT ENAME, JOB, HIREDATE FROM EMP 
    WHERE HIREDATE BETWEEN TO_DATE('1981-02-20','YYYY-MM-DD') AND 
    TO_DATE('1981-05-01','YYYY-MM-DD') ORDER BY HIREDATE;
--5. deptno�� 10,20�� ����� ��� ������ ��� (�� ename������ ����)
SELECT * FROM EMP WHERE DEPTNO IN (10,20) ORDER BY ENAME;
--6. sal�� 1500�̻��̰� deptno�� 10,30�� ����� ename�� sal�� ���(�� TITLE�� employee�� Monthly Salary�� ���)
SELECT ENAME "employee", SAL "Monthly Salary" FROM EMP 
    WHERE SAL >= 1500 AND DEPTNO IN (10,30);
--7. hiredate�� 1982���� ����� ��� ������ ���
SELECT * FROM EMP WHERE TO_CHAR(HIREDATE,'YYYY') = '1982';
--8. �̸��� ù�ڰ� C���� P�� �����ϴ� ����� �̸�, �޿� ���(�̸��� ����)
SELECT ENAME, SAL FROM EMP WHERE ENAME 
    BETWEEN 'C' AND 'Q' AND ENAME != 'Q' ORDER BY ENAME;
--9. comm�� sal���� 10%�̻� ���� ��� ����� ���Ͽ� �̸�, �޿�, �󿩱��� ���
SELECT ENAME, SAL, COMM FROM EMP WHERE COMM > SAL*1.1;
--10. job�� CLERK�̰ų� ANALYST�̰� sal�� 1000,3000,5000�� �ƴ� ��� ����� ������ ���
SELECT * FROM EMP WHERE JOB IN ('CLERK','ANALYST') AND SAL NOT IN (1000,3000,5000);
-- 11. ename�� L�� �� �ڰ� �ְ� deptno�� 30�̰ų� �Ǵ� mgr�� 7782�� ����� ��� ������ ���
SELECT * FROM EMP WHERE ENAME LIKE '%L%L%' AND DEPTNO = 30 OR MGR = 7782;
-- 12. �Ի�����81���̰� ������ 'SALESMAN'�� �ƴ� ������ ���, �����, �Ի���, ����, �޿��� ���.

-- 13. ���, �����, �Ի���, ����, �޿��� �޿��� ���� ������ �����ϰ�, �޿��� ������ �Ի����� ���� ������� �����Ͽ� ���
-- 14. ������� �� ��° ���ĺ��� ��N���� ����� ���, ������� ���
-- 15. ����� ��A���� �� ����� ���, ������� ���
-- 16. ����(SAL*12)�� 35000 �̻��� ���, �����, ������ �˻�