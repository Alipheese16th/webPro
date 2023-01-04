-- SELECT�� ��������
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
SELECT EMPNO, ENAME, HIREDATE, JOB, SAL FROM EMP WHERE TO_CHAR(HIREDATE,'RR')=81 AND JOB != 'SALESMAN';
-- 13. ���, �����, �Ի���, ����, �޿��� �޿��� ���� ������ �����ϰ�, �޿��� ������ �Ի����� ���� ������� �����Ͽ� ���
SELECT EMPNO, ENAME, HIREDATE, JOB, SAL FROM EMP ORDER BY SAL DESC, HIREDATE;
-- 14. ������� �� ��° ���ĺ��� ��N���� ����� ���, ������� ���
SELECT EMPNO, ENAME FROM EMP WHERE ENAME LIKE '__N%';
-- 15. ����� ��A���� �� ����� ���, ������� ���
SELECT EMPNO, ENAME FROM EMP WHERE ENAME LIKE '%A%';
-- 16. ����(SAL*12)�� 35000 �̻��� ���, �����, ������ �˻�
SELECT EMPNO, ENAME, SAL*12 FROM EMP WHERE SAL*12 > 35000;

-- JOIN ��������
-- ���� PART1
--1. EMP���̺��� ��� ����� ���� ������ ���ӻ����� ����Ͻÿ�.
SELECT W.ENAME, M.ENAME FROM EMP W, EMP M WHERE W.MGR = M.EMPNO;
--2. ��簡 �ִ� ������ ���� �����, �޿�, ��å, ���ӻ����� ����Ͻÿ�.
SELECT W.ENAME, W.SAL, W.JOB, M.ENAME FROM EMP W, EMP M WHERE W.MGR = M.EMPNO;
--3. ��簡 ���� ������ ��簡 �ִ� ������ ��ο� ���� �����, �޿�, ��å, ���ӻ����� ����Ͻÿ�.
SELECT W.ENAME, W.SAL, W.JOB, M.ENAME FROM EMP W, EMP M WHERE W.MGR = M.EMPNO(+);
--4. ��簡 �ִ� ������ ���� �����, �����, �޿�, �μ���, ���ӻ����� ����Ͻÿ�.
SELECT W.ENAME, W.SAL, DNAME, M.ENAME FROM EMP W, EMP M, DEPT D WHERE W.MGR = M.EMPNO AND W.DEPTNO = D.DEPTNO;
--5. ��簡 ���� ������ ��簡 �ִ� ���� ��ο� ���� �����, �޿�, �μ��ڵ�, �μ���, �ٹ���, ���ӻ����� ����Ͻÿ�.
--��, ���ӻ�簡 ���� ��� ���ӻ����� ������������ ��� ����Ͻÿ�.
SELECT W.ENAME, W.SAL, W.DEPTNO, DNAME, LOC, NVL(M.ENAME,'����') FROM EMP W, EMP M, DEPT D WHERE W.MGR = M.EMPNO(+) AND W.DEPTNO = D.DEPTNO;
--6. �޿��� 2000�̻��� ����� ���� �����, �޿�, �޿����, �μ���, ���ӻ����� ����Ͻÿ�.
SELECT W.ENAME, W.SAL, GRADE, DNAME, M.ENAME FROM EMP W, EMP M, SALGRADE, DEPT D
    WHERE W.DEPTNO = D.DEPTNO AND W.MGR = M.EMPNO AND W.SAL BETWEEN LOSAL AND HISAL AND W.SAL >= 2000;
--7. ��簡 �ִ� ������ ��簡 ���� ���� ��ο� ���� �����, �޿�, �޿����, �μ���, ���ӻ����� �μ��� ���ĺ� ����
--�� �����Ͽ� ����Ͻÿ�.
SELECT W.ENAME, W.SAL, GRADE, DNAME, M.ENAME FROM EMP W, EMP M, SALGRADE, DEPT D
    WHERE W.DEPTNO = D.DEPTNO AND W.MGR = M.EMPNO AND W.SAL BETWEEN LOSAL AND HISAL ORDER BY DNAME;
--8. �����, �޿�, �޿����, �μ���, ����, ���ӻ����� ����Ͻÿ�. �� ������ (SAL+COMM)*12�� ����Ѵ�.
SELECT W.ENAME, W.SAL, GRADE, DNAME, (W.SAL+NVL(W.COMM,0))*12, M.ENAME FROM EMP W, EMP M, SALGRADE, DEPT D
    WHERE W.DEPTNO = D.DEPTNO AND W.MGR = M.EMPNO AND W.SAL BETWEEN LOSAL AND HISAL;
--9. �� 8���� �μ��� ���ĺ� ������ �����ϰ� �μ����� ������ �޿��� ū ������� ���� ��� ������ �����Ͽ� ����Ͻÿ�.
SELECT W.ENAME, W.SAL, GRADE, DNAME, (W.SAL+NVL(W.COMM,0))*12, M.ENAME FROM EMP W, EMP M, SALGRADE, DEPT D
    WHERE W.DEPTNO = D.DEPTNO AND W.MGR = M.EMPNO AND W.SAL BETWEEN LOSAL AND HISAL
    ORDER BY DNAME, SAL DESC;
--����PART2
--1. EMP���̺��� ��� ����� ���� �����, �μ���ȣ �μ����� ����Ͻÿ�.
SELECT ENAME,E.DEPTNO,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO;
--2. EMP���̺��� NEW YORK���� �ٹ��ϰ� �ִ� ����� ���� �����, ��å, �޿�, �μ����� ����Ͻÿ�.
SELECT ENAME,JOB,SAL,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO AND LOC = 'NEW YORK';
--3. EMP���̺��� ��(COMM)�� �޴� ����� ���� �����, �μ���, ��ġ�� ����Ͻÿ�.
SELECT ENAME,DNAME,LOC FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO AND COMM IS NOT NULL AND COMM > 0;
--4. EMP���̺��� ����� ���ĺ� L�� �ִ� ����� ���� �����, ��å, �μ���, �ٹ����� ����Ͻÿ�.
SELECT ENAME,JOB,DNAME,LOC FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO AND ENAME LIKE '%L%';
--5. EMP���̺��� ���, �����, �μ���ȣ, �μ����� ����ϵ� ����� ���ĺ� ������ ����Ͻÿ�.
SELECT EMPNO,ENAME,E.DEPTNO,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO ORDER BY ENAME;
--6. �޿��� 2000�̻��� ����� ���� ���, �����, �޿�, �μ����� �޿��������� �������� �����Ͽ� ����Ͻÿ�.
SELECT EMPNO,ENAME,SAL,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO ORDER BY SAL DESC;
--7. ��å�� MANAGER�̸� �޿��� 2500�̻��� ����� ���� ���, �����, ��å, �޿�, �μ����� ��� ��������
--�������� �����Ͽ� ����Ͻÿ�.
SELECT EMPNO,ENAME,JOB,SAL,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO AND SAL >= 2500 ORDER BY EMPNO;
--8. ���, �����, ��å, �޿�, �޿������ �޿��������� �������� �����Ͽ� ����Ͻÿ�
SELECT EMPNO,ENAME,JOB,SAL,GRADE FROM EMP,SALGRADE WHERE SAL BETWEEN LOSAL AND HISAL ORDER BY SAL DESC;
--9. ��簡 ���� ������ ��簡 �ִ� ���� ��ο� ����, �����, ���ӻ����� ����Ͻÿ�.
SELECT W.ENAME, M.ENAME FROM EMP W, EMP M
    WHERE W.MGR = M.EMPNO(+);
--10.��簡 ���� ������ ��簡 �ִ� ���� ��ο� ����, �����, ����, ����� ������ ����Ͻÿ�
SELECT W.ENAME, M.ENAME, MM.ENAME FROM EMP W, EMP M, EMP MM
    WHERE W.MGR = M.EMPNO(+) AND M.MGR = MM.EMPNO(+);

-- �������Լ� ��������
--1. ���� ��¥�� TITLE�� Current Date�� ��Ī���� ����ϴ� SELECT ������ ����Ͻÿ�.
SELECT SYSDATE "Current Date" FROM DUAL;
--2. EMP ���̺��� ���� �޿��� 15%�� ������ �޿��� �����ȣ,�̸�,����,�޿�,������ �޿�(New Salary),������(Increase)�� ����ϴ� SELECT ������ ����Ͻÿ�.
SELECT EMPNO,ENAME,JOB,SAL,SAL*1.15 "New Salary",SAL*0.15 "Increase" FROM EMP;
--3. EMP ���̺��� �̸�,�Ի���,�Ի��Ϸκ��� 6���� �� ���ƿ��� ������ ���Ͽ� ����ϴ� SELECT ������ ����Ͻÿ�.
SELECT ENAME,HIREDATE, NEXT_DAY(ADD_MONTHS(HIREDATE,6),'��') FROM EMP;
--4. EMP ���̺��� �̸�,�Ի���, �Ի��Ϸκ��� ��������� ����,�޿�, �Ի��Ϻ��� ��������� �޿��� �Ѱ踦 ����ϴ� SELECT ������ ����Ͻÿ�.
SELECT ENAME, HIREDATE, TRUNC(MONTHS_BETWEEN(SYSDATE,HIREDATE)),SAL,TRUNC(MONTHS_BETWEEN(SYSDATE,HIREDATE))*SAL FROM EMP;
--5. EMP ���̺��� ��� ����� �̸��� �޿�(15�ڸ��� ��� ������ �� ���� ��*���� ��ġ)�� ����ϴ� SELECT ������ ����Ͻÿ�.
SELECT ENAME,LPAD(SAL,15,'*') FROM EMP;
--6. EMP ���̺��� ��� ����� ������ �̸�,����,�Ի���,�Ի��� ������ ����ϴ� SELECT ������ ����Ͻÿ�.
SELECT ENAME,JOB,HIREDATE,TO_CHAR(HIREDATE,'DY') FROM EMP;
--7. EMP ���̺��� �̸��� ���̰� 6�� �̻��� ����� ������ �̸�,�̸��� ���ڼ�,������ ����ϴ� SELECT ������ ����Ͻÿ�.
SELECT ENAME,LENGTH(ENAME),JOB FROM EMP WHERE LENGTH(ENAME) >= 6;
--8. EMP ���̺��� ��� ����� ������ �̸�, ����, �޿�, ���ʽ�, �޿�+���ʽ��� ����ϴ� SELECT ������ ����Ͻÿ�
SELECT ENAME,JOB,SAL,COMM,SAL+NVL(COMM,0) FROM EMP;
--9. ��� ���̺��� ������� 2��° ���ں��� 3���� ���ڸ� �����Ͻÿ�. 
SELECT SUBSTR(ENAME,2,3) FROM EMP;
--10. ��� ���̺��� �Ի����� 12���� ����� ���, �����, �Ի����� �˻��Ͻÿ� (EXTRACT�Լ�, TO_CHAR�Լ�)
SELECT EMPNO, ENAME, HIREDATE FROM EMP WHERE TO_CHAR(HIREDATE,'MM')=12;
--11. ������ ���� ����� �˻��� �� �ִ� SQL ������ �ۼ��Ͻÿ�
--EMPNO ENAME �޿�
--7369 SMITH *******800
--7499 ALLEN ******1600
--7521 WARD ******1250
--����.
SELECT EMPNO, ENAME, LPAD(SAL,10,'*') FROM EMP;
--12. ������ ���� ����� �˻��� �� �ִ� SQL ������ �ۼ��Ͻÿ�
--EMPNO ENAME �Ի���
--7369 SMITH 1980-12-17
--��.
SELECT EMPNO, ENAME, TO_CHAR(HIREDATE,'YYYY-MM-DD') FROM EMP;
--13. ��� ���̺��� �μ���ȣ�� 20�� ����� ���, �̸�, ����, �޿��� ����Ͻÿ�(�޿��� �տ� $�� �����ϰ� 3�ڸ����� , �� ����Ѵ�
SELECT EMPNO, ENAME, JOB, TO_CHAR(SAL,'$99,999') FROM EMP WHERE DEPTNO = 20;


-- �׷��Լ� <��������>
-- 1. �ο���,�ִ� �޿�,�ּ� �޿�,�޿��� ���� ���
SELECT COUNT(*), MAX(SAL), MIN(SAL), SUM(SAL) FROM EMP;
-- 2. ������ �ο����� ���Ͽ� ���
SELECT JOB, COUNT(*) FROM EMP GROUP BY JOB;
--- 3. �ְ� �޿��� �ּ� �޿��� ���̴� ���ΰ� ���
SELECT MAX(SAL) - MIN(SAL) FROM EMP;
-- 4. �� �μ����� �ο���, �޿� ���, ���� �޿�, �ְ� �޿�, �޿��� ���� ���(�޿��� ���� ���� ������)
SELECT DEPTNO, COUNT(*), ROUND(AVG(SAL),1), MIN(SAL), MAX(SAL), SUM(SAL) 
    FROM EMP GROUP BY DEPTNO
    ORDER BY SUM(SAL) DESC;
-- 5. �μ���, ������ �׷��Ͽ� ����� �μ���ȣ, ����, �ο���, �޿��� ���, �޿��� ���� ���(�μ���ȣ, ���������� �������� ����)
SELECT DEPTNO,JOB,COUNT(*),AVG(SAL),SUM(SAL) FROM EMP
    GROUP BY DEPTNO, JOB
    ORDER BY DEPTNO, JOB;
-- 6. ������, �μ��� �׷��Ͽ� �����  ����, �μ���ȣ, �ο���, �޿��� ���, �޿��� ���� ���(��°���� ������, �μ���ȣ �� �������� ����)
SELECT JOB, DEPTNO, COUNT(*), AVG(SAL), SUM(SAL) FROM EMP
    GROUP BY JOB, DEPTNO ORDER BY JOB, DEPTNO;
--7. ������� 5���̻� �Ѵ� �μ���ȣ�� ������� ���
SELECT DEPTNO, COUNT(*) FROM EMP GROUP BY DEPTNO HAVING COUNT(*) >= 5;
-- 8. ������� 5���̻� �Ѵ� �μ���� ������� ���
SELECT DNAME, COUNT(*) FROM EMP, DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO 
    GROUP BY DNAME HAVING COUNT(*) >= 5;
--9. ������ �޿��� ����� 3000�̻��� ������ ���ؼ� ������, ��� �޿�, �޿��� ���� ���
SELECT JOB, AVG(SAL), SUM(SAL) FROM EMP
    GROUP BY JOB HAVING AVG(SAL) >= 3000;
--10. �޿� ���� 5000�� �ʰ��ϴ� �� ������ ���ؼ� ������ �޿����� ���(�޿� �հ�� �������� ����)
SELECT JOB, SUM(SAL) FROM EMP
    GROUP BY JOB HAVING SUM(SAL) > 5000 
    ORDER BY SUM(SAL) DESC;
--11. �μ��� �޿����, �μ��� �޿��հ�, �μ��� �ּұ޿��� ���
SELECT DEPTNO, AVG(SAL), SUM(SAL), MIN(SAL) FROM EMP
    GROUP BY DEPTNO;
--12. ���� 11���� �����Ͽ�, �μ��� �޿���� �ִ�ġ, �μ��� �޿����� �ִ�ġ, �μ��� �ּұ޿��� �ִ�ġ�� ���
SELECT MAX(AVG(SAL)), MAX(SUM(SAL)), MAX(MIN(SAL)) FROM EMP
    GROUP BY DEPTNO;
--13. ��� ���̺��� �Ʒ��� ����� ���
--H_YEAR COUNT(*)	MIN(SAL)	MAX(SAL)	AVG(SAL)	SUM(SAL)
--  80	  1		    800		    800		    800		    800
--	81	 10		    950		    5000	    2282.5	  22825
--	82	  2		    1300	    3000	   2150		   4300
--	83	  1		    1100	    1100	    1100	   1100
SELECT TO_CHAR(HIREDATE,'RR') H_YEAR, COUNT(*), MIN(SAL), MAX(SAL), AVG(SAL), SUM(SAL)
    FROM EMP GROUP BY TO_CHAR(HIREDATE,'RR')
    ORDER BY H_YEAR;
-- 14.  �Ʒ��� ����� ���(�Ի�⵵�� �����)
--  1980     1	
--  1981     10
--  1982     2
--  1983     1
--  total    14	
SELECT NVL(TO_CHAR(HIREDATE,'YYYY'),'total') YEAR, COUNT(*) 
    FROM EMP GROUP BY ROLLUP(TO_CHAR(HIREDATE,'YYYY'))
    ORDER BY YEAR;
--15. �ִ�޿�, �ּұ޿�, ��ü�޿���, ����� ���
SELECT MAX(SAL), MIN(SAL), SUM(SAL), AVG(SAL) FROM EMP;
--16. �μ��� �ο��� ���
SELECT DEPTNO, COUNT(*) FROM EMP GROUP BY DEPTNO;
--17. �μ��� �ο����� 6���̻��� �μ���ȣ ���
SELECT DEPTNO, COUNT(*) FROM EMP GROUP BY DEPTNO HAVING COUNT(*) >=6 ;
--18. �޿��� ���� ������� ����� �ο��Ͽ� ������ ���� ����� ������ �Ͻÿ�. 
-- (��Ʈ self join, group by, count���)
--ENAME	    ���
--________________________
--KING		1
--SCOTT		2
--����
SELECT E1.ENAME, COUNT(E2.ENAME)+1 "���"
    FROM EMP E1, EMP E2
    WHERE E1.SAL < E2.SAL(+)
    GROUP BY E1.ENAME
    ORDER BY ���;

-- ������������ 
--1. ������̺��� ���� ���� �Ի��� ����� �̸�, �޿�, �Ի���
SELECT ENAME, SAL, HIREDATE FROM EMP WHERE HIREDATE = (SELECT MIN(HIREDATE) FROM EMP);

-- 2. ȸ�翡�� ���� �޿��� ���� ����� �̸�, �޿�
SELECT ENAME, SAL FROM EMP WHERE SAL = (SELECT MIN(SAL) FROM EMP);

-- 3. ȸ�� ��պ��� �޿��� ���� �޴� ����� �̸�, �޿�, �μ��ڵ�
SELECT ENAME, SAL, DEPTNO FROM EMP WHERE SAL > (SELECT AVG(SAL) FROM EMP);

--4. ȸ�� ��� ������ �޿��� �޴� ����� �̸�, �޿�, �μ���
SELECT ENAME, SAL, DNAME FROM EMP, DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO AND SAL <= (SELECT AVG(SAL) FROM EMP);

--5. SCOTT���� ���� �Ի��� ����� �̸�, �޿�, �Ի���, �޿� ���
SELECT ENAME, SAL, HIREDATE, GRADE 
    FROM EMP, SALGRADE WHERE SAL BETWEEN LOSAL AND HISAL 
    AND HIREDATE < (SELECT HIREDATE FROM EMP WHERE ENAME = 'SCOTT');
    
--6. 5��(SCOTT���� ���� �Ի��� ����� �̸�, �޿�, �Ի���, �޿� ���)�� �μ��� �߰��ϰ� �޿��� ū �� ����
SELECT ENAME, SAL, HIREDATE, GRADE, DNAME
    FROM EMP, SALGRADE, DEPT WHERE SAL BETWEEN LOSAL AND HISAL AND EMP.DEPTNO = DEPT.DEPTNO
    AND HIREDATE < (SELECT HIREDATE FROM EMP WHERE ENAME = 'SCOTT')
    ORDER BY SAL DESC;
    
--7. BLAKE ���� �޿��� ���� ������� ���, �̸�, �޿�
SELECT EMPNO, ENAME, SAL FROM EMP
    WHERE SAL < (SELECT SAL FROM EMP WHERE ENAME = 'BLAKE');
    
--8. MILLER���� �ʰ� �Ի��� ����� ���, �̸�, �Ի���
SELECT EMPNO, ENAME, HIREDATE FROM EMP
    WHERE HIREDATE > (SELECT HIREDATE FROM EMP WHERE ENAME = 'MILLER');
    
--9. �����ü ��� �޿����� �޿��� ���� ������� ���, �̸�, �޿�
SELECT EMPNO, ENAME, SAL FROM EMP
    WHERE SAL > (SELECT AVG(SAL) FROM EMP);
    
--10. CLARK�� ���� �μ���ȣ�̸�, ����� 7698�� ������ �޿����� ���� �޿��� �޴� ������� ���, �̸�, �޿�
SELECT EMPNO, ENAME, SAL FROM EMP
    WHERE DEPTNO = (SELECT DEPTNO FROM EMP WHERE ENAME = 'CLARK')
    AND SAL > (SELECT SAL FROM EMP WHERE EMPNO = 7698);
    
--11. CLARK�� ���� �μ����̸�, ����� 7698�� ������ �޿����� ���� �޿��� �޴� ������� ���, �̸�, �޿�
SELECT EMPNO, ENAME, SAL FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO
    AND DNAME = (SELECT DNAME FROM EMP E,DEPT D WHERE E.DEPTNO=D.DEPTNO AND ENAME = 'CLARK')
    AND SAL > (SELECT SAL FROM EMP WHERE EMPNO = 7698);
    
--12. BLAKE�� ���� �μ��� �ִ� ��� ����� �̸��� �Ի�����
SELECT ENAME, HIREDATE FROM EMP
    WHERE DEPTNO = (SELECT DEPTNO FROM EMP WHERE ENAME = 'BLAKE')
    AND ENAME != 'BLAKE';
    
--13. ��� �޿� �̻��� �޴� ��� �������� ���ؼ� �����ȣ�� �̸� �� �޿��� ���� ������ ���)
SELECT EMPNO, ENAME FROM EMP
    WHERE SAL > (SELECT AVG(SAL) FROM EMP)
    ORDER BY SAL DESC;
    
-- 14. �̸��� ��T���� �ִ� ����� �ٹ��ϴ� �μ����� �ٹ��ϴ� ��� ������ ��� ��ȣ,�̸�,�޿�(�� ��� �� ���)
SELECT EMPNO, ENAME, SAL FROM EMP
    WHERE DEPTNO IN (SELECT DEPTNO FROM EMP WHERE ENAME LIKE '%T%')
    ORDER BY EMPNO;
    
-- 15. �μ� ��ġ�� Dallas�� ��� �������� ���� �̸�,����,�޿�
SELECT ENAME, JOB, SAL FROM EMP
    WHERE DEPTNO IN (SELECT E.DEPTNO FROM EMP E, DEPT D 
                        WHERE E.DEPTNO=D.DEPTNO
                        AND INITCAP(LOC) = 'Dallas'); -- �������� �̿�
                        
SELECT ENAME, JOB, SAL FROM EMP E, DEPT D -- EQUI JOIN �̿�
    WHERE E.DEPTNO = D.DEPTNO
    AND LOC = 'DALLAS';
    
-- 16. EMP ���̺��� King���� �����ϴ� ��� ����� �̸��� �޿�
SELECT ENAME, SAL FROM EMP
    WHERE MGR IN (SELECT EMPNO FROM EMP WHERE ENAME = 'KING');
    
-- 17. SALES�μ� ����� �̸�, ����
SELECT ENAME, JOB FROM EMP
    WHERE DEPTNO IN (SELECT DEPTNO FROM DEPT WHERE DNAME = 'SALES');
    
-- 18. ������ �μ� 30�� ���� ���޺��� ���� ����� ��� �ʵ�
SELECT * FROM EMP WHERE SAL > (SELECT MIN(SAL) FROM EMP WHERE DEPTNO = 30);

-- 19. �μ� 10���� �μ� 30�� ����� ���� ������ �ð� �ִ� ����� �̸��� ����
SELECT ENAME, JOB FROM EMP WHERE DEPTNO = 10
    AND JOB IN (SELECT JOB FROM EMP WHERE DEPTNO = 30);
    
-- 20. FORD�� ������ ���޵� ���� ����� ��� �ʵ�
SELECT * FROM EMP WHERE (JOB,SAL) =
    (SELECT JOB, SAL FROM EMP WHERE ENAME = 'FORD')
    AND ENAME <> 'FORD';
    
-- 21. �̸��� JONES�� ������ JOB�� ���ų� FORD�� SAL �̻��� �޴� ����� ������ �̸�, ����, �μ���ȣ, �޿�
-- ��, ������ ���ĺ� ��, ������ ���� ������ ���
SELECT ENAME,JOB,DEPTNO,SAL FROM EMP
    WHERE JOB = (SELECT JOB FROM EMP WHERE ENAME = 'JONES')
    OR SAL >= (SELECT SAL FROM EMP WHERE ENAME = 'FORD')
    ORDER BY JOB, SAL DESC;
    
-- 22. SCOTT �Ǵ� WARD�� ������ ���� ����� ������ �̸�,����,�޿�
SELECT ENAME, JOB, SAL FROM EMP
    WHERE SAL IN (SELECT SAL FROM EMP WHERE ENAME IN ('SCOTT','WARD'))
    AND ENAME NOT IN ('SCOTT','WARD');
    
-- 23. CHICAGO���� �ٹ��ϴ� ����� ���� ������ �ϴ� ������� �̸�,����
SELECT ENAME, JOB FROM EMP
    WHERE JOB IN (SELECT JOB 
                    FROM EMP, DEPT 
                    WHERE EMP.DEPTNO = DEPT.DEPTNO 
                    AND LOC ='CHICAGO');
                    
-- 24. �μ��� ��� ���޺��� ���� ����� ���, �̸�, �޿�
SELECT EMPNO, ENAME, SAL FROM EMP E
    WHERE SAL > (SELECT AVG(SAL) FROM EMP WHERE DEPTNO = E.DEPTNO);
    
-- 25. ������ ��� ���޺��� ���� ������ �޴� ����� �μ���ȣ, �̸�, �޿�
SELECT DEPTNO, ENAME, SAL FROM EMP E
    WHERE SAL < (SELECT AVG(SAL) FROM EMP WHERE JOB = E.JOB);
    
-- 26. ��� �� �� �̻����κ��� ���� ���� �� �ִ� ����� ����, �̸�, ���, �μ���ȣ�� ���
--(��, �μ���ȣ ������ �������� ����)
SELECT JOB, ENAME, EMPNO, DEPTNO FROM EMP E
    WHERE EXISTS (SELECT * FROM EMP WHERE E.EMPNO = MGR)
    ORDER BY DEPTNO;    -- EXISTS �̿�
    
SELECT DISTINCT M.JOB, M.ENAME, M.EMPNO, M.DEPTNO
    FROM EMP W, EMP M
    WHERE W.MGR = M.EMPNO
    ORDER BY DEPTNO;    -- SELF JOIN �̿�

-- 27. ���� ����� ���, �̸�, ����, �μ���
SELECT EMPNO, ENAME, JOB, DEPTNO FROM EMP E
    WHERE NOT EXISTS (SELECT * FROM EMP WHERE E.EMPNO = MGR); -- EXISTS �̿�
    
SELECT M.EMPNO, M.ENAME, M.JOB, M.DEPTNO
    FROM EMP W, EMP M
    WHERE W.MGR(+) = M.EMPNO
    AND W.ENAME IS NULL; -- SELF JOIN �̿�

ROLLBACK;
-- DML �������� �����۾�
-- �޿��� 3000�̻��� ����� �޿��� 10%�λ�
UPDATE EMP01 SET SAL = SAL*1.1 WHERE SAL >= 3000;
-- EMP01���̺��� ��DALLAS������ �ٹ��ϴ� �������� ������ 1000�λ�
UPDATE EMP01 SET SAL = SAL+1000 WHERE DEPTNO = (SELECT DEPTNO FROM DEPT WHERE LOC = 'DALLAS');
-- SCOTT����� �μ���ȣ�� 20������, ������ MANAGER�� �Ѳ����� ����
UPDATE EMP01 SET DEPTNO = 20, JOB = 'MANAGER' WHERE ENAME = 'SCOTT';
-- ������� ��FORD���� ����� �����ϴ� SQL �ۼ�
DELETE FROM EMP01 WHERE ENAME = 'FORD';

SELECT ENAME, JOB, HIREDATE 
    FROM EMP 
    WHERE HIREDATE BETWEEN TO_DATE('1981-02-20','YYYY-MM-DD') 
    AND TO_DATE('1981-05-01','YYYY-MM-DD') 
    ORDER BY HIREDATE;

SELECT ENAME, JOB, HIREDATE 
    FROM EMP 
    WHERE HIREDATE BETWEEN TO_CHAR('81/02/20','RR/MM/DD') AND TO_CHAR('81/05/01','RR/MM/DD') ORDER BY HIREDATE;

------------------------------------------















