
-- 검색 조건 1. 사원명 2. 직책 3. 부서
SELECT * FROM EMP WHERE ENAME LIKE '%' || 'A' || '%';
SELECT * FROM EMP WHERE JOB LIKE '%' || 'A' || '%';
SELECT * FROM EMP WHERE DEPTNO = 20;

-- Dept.xml 의 id = deptList
SELECT * FROM DEPT;

-- Emp.xml 의 id = empList
SELECT * FROM EMP;
SELECT * FROM EMP WHERE ENAME LIKE '%' || 'A' || '%';
SELECT * FROM EMP WHERE ENAME LIKE '%' || 'A' || '%' AND JOB LIKE '%' || 'A' || '%';
SELECT * FROM EMP WHERE ENAME LIKE '%' || 'A' || '%' AND JOB LIKE '%' || 'A' || '%' AND DEPTNO = 20;

