-- ex2_oracle.jsp
SELECT * FROM EMP;
SELECT * FROM DEPT;
-- ex3
SELECT * FROM EMP WHERE DEPTNO LIKE '%' || '10';


-- ex4
select UPPER(TRIM('  s  ')) from dual; -- upper와 trim 중복적용 실험 - 가능하네

SELECT EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, D.DEPTNO, DNAME 
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO
    AND ENAME LIKE '%' || UPPER(TRIM('   s')) || '%';

SELECT E.*, DNAME FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO
    AND ENAME LIKE '%' || UPPER(TRIM('   s')) || '%';
