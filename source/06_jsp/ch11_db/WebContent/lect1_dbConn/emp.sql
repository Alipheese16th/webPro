-- ex2_oracle.jsp
SELECT * FROM EMP;
SELECT * FROM DEPT;
-- ex3
SELECT * FROM EMP WHERE DEPTNO LIKE '%' || '10';


-- ex4
select UPPER(TRIM('  s  ')) from dual; -- upper�� trim �ߺ����� ���� - �����ϳ�

SELECT EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, D.DEPTNO, DNAME 
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO
    AND ENAME LIKE '%' || UPPER(TRIM('   s')) || '%';

SELECT E.*, DNAME FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO
    AND ENAME LIKE '%' || UPPER(TRIM('   s')) || '%';
