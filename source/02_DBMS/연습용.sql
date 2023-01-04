-- SELECT문 연습문제
--1. EMP 테이블에서 sal이 3000이상인 사원의 empno, ename, job, sal을 출력
SELECT EMPNO, ENAME, JOB, SAL FROM EMP WHERE SAL >= 3000;
--2. EMP 테이블에서 empno가 7788인 사원의 ename과 deptno를 출력
SELECT ENAME, DEPTNO FROM EMP WHERE EMPNO = 7788;
--3. 연봉(SAL*12+COMM)이 24000 이상인 사번, 이름, 급여 출력 (급여순정렬)
SELECT EMPNO, ENAME, SAL FROM EMP 
    WHERE (SAL*12+NVL(COMM,0)) >= 2000 ORDER BY SAL;
--4. 입사일이 1981년 2월 20과 1981년 5월 1일 사이에 입사한 사원의 이름, 직책, 입사일을 출력 (단 hiredate 순으로 출력)
SELECT ENAME, JOB, HIREDATE FROM EMP 
    WHERE HIREDATE BETWEEN TO_DATE('1981-02-20','YYYY-MM-DD') AND 
    TO_DATE('1981-05-01','YYYY-MM-DD') ORDER BY HIREDATE;
--5. deptno가 10,20인 사원의 모든 정보를 출력 (단 ename순으로 정렬)
SELECT * FROM EMP WHERE DEPTNO IN (10,20) ORDER BY ENAME;
--6. sal이 1500이상이고 deptno가 10,30인 사원의 ename과 sal를 출력(단 TITLE을 employee과 Monthly Salary로 출력)
SELECT ENAME "employee", SAL "Monthly Salary" FROM EMP 
    WHERE SAL >= 1500 AND DEPTNO IN (10,30);
--7. hiredate가 1982년인 사원의 모든 정보를 출력
SELECT * FROM EMP WHERE TO_CHAR(HIREDATE,'YYYY') = '1982';
--8. 이름의 첫자가 C부터 P로 시작하는 사람의 이름, 급여 출력(이름순 정렬)
SELECT ENAME, SAL FROM EMP WHERE ENAME 
    BETWEEN 'C' AND 'Q' AND ENAME != 'Q' ORDER BY ENAME;
--9. comm이 sal보다 10%이상 많은 모든 사원에 대하여 이름, 급여, 상여금을 출력
SELECT ENAME, SAL, COMM FROM EMP WHERE COMM > SAL*1.1;
--10. job이 CLERK이거나 ANALYST이고 sal이 1000,3000,5000이 아닌 모든 사원의 정보를 출력
SELECT * FROM EMP WHERE JOB IN ('CLERK','ANALYST') AND SAL NOT IN (1000,3000,5000);
-- 11. ename에 L이 두 자가 있고 deptno가 30이거나 또는 mgr이 7782인 사원의 모든 정보를 출력
SELECT * FROM EMP WHERE ENAME LIKE '%L%L%' AND DEPTNO = 30 OR MGR = 7782;
-- 12. 입사일이81년이고 업무가 'SALESMAN'이 아닌 직원의 사번, 사원명, 입사일, 업무, 급여를 출력.
SELECT EMPNO, ENAME, HIREDATE, JOB, SAL FROM EMP WHERE TO_CHAR(HIREDATE,'RR')=81 AND JOB != 'SALESMAN';
-- 13. 사번, 사원명, 입사일, 업무, 급여를 급여가 높은 순으로 정렬하고, 급여가 같으면 입사일이 빠른 사원으로 정렬하여 출력
SELECT EMPNO, ENAME, HIREDATE, JOB, SAL FROM EMP ORDER BY SAL DESC, HIREDATE;
-- 14. 사원명의 세 번째 알파벳이 ＇N＇인 사원의 사번, 사원명을 출력
SELECT EMPNO, ENAME FROM EMP WHERE ENAME LIKE '__N%';
-- 15. 사원명에 ‘A’가 들어간 사원의 사번, 사원명을 출력
SELECT EMPNO, ENAME FROM EMP WHERE ENAME LIKE '%A%';
-- 16. 연봉(SAL*12)이 35000 이상인 사번, 사원명, 연봉을 검색
SELECT EMPNO, ENAME, SAL*12 FROM EMP WHERE SAL*12 > 35000;

-- JOIN 연습문제
-- 예제 PART1
--1. EMP테이블의 모든 사원에 대해 사원명과 직속상사명을 출력하시오.
SELECT W.ENAME, M.ENAME FROM EMP W, EMP M WHERE W.MGR = M.EMPNO;
--2. 상사가 있는 직원에 대해 사원명, 급여, 직책, 직속상사명을 출력하시오.
SELECT W.ENAME, W.SAL, W.JOB, M.ENAME FROM EMP W, EMP M WHERE W.MGR = M.EMPNO;
--3. 상사가 없는 직원과 상사가 있는 직원들 모두에 대해 사원명, 급여, 직책, 직속상사명을 출력하시오.
SELECT W.ENAME, W.SAL, W.JOB, M.ENAME FROM EMP W, EMP M WHERE W.MGR = M.EMPNO(+);
--4. 상사가 있는 직원에 대해 사원명, 사원명, 급여, 부서명, 직속상사명을 출력하시오.
SELECT W.ENAME, W.SAL, DNAME, M.ENAME FROM EMP W, EMP M, DEPT D WHERE W.MGR = M.EMPNO AND W.DEPTNO = D.DEPTNO;
--5. 상사가 없는 직원과 상사가 있는 직원 모두에 대해 사원명, 급여, 부서코드, 부서명, 근무지, 직속상사명을 출력하시오.
--단, 직속상사가 없을 경우 직속상사명에는 ‘없음’으로 대신 출력하시오.
SELECT W.ENAME, W.SAL, W.DEPTNO, DNAME, LOC, NVL(M.ENAME,'없음') FROM EMP W, EMP M, DEPT D WHERE W.MGR = M.EMPNO(+) AND W.DEPTNO = D.DEPTNO;
--6. 급여가 2000이상인 사원에 대해 사원명, 급여, 급여등급, 부서명, 직속상사명을 출력하시오.
SELECT W.ENAME, W.SAL, GRADE, DNAME, M.ENAME FROM EMP W, EMP M, SALGRADE, DEPT D
    WHERE W.DEPTNO = D.DEPTNO AND W.MGR = M.EMPNO AND W.SAL BETWEEN LOSAL AND HISAL AND W.SAL >= 2000;
--7. 상사가 있는 직원과 상사가 없는 직원 모두에 대해 사원명, 급여, 급여등급, 부서명, 직속상사명을 부서명 알파벳 순으
--로 정렬하여 출력하시오.
SELECT W.ENAME, W.SAL, GRADE, DNAME, M.ENAME FROM EMP W, EMP M, SALGRADE, DEPT D
    WHERE W.DEPTNO = D.DEPTNO AND W.MGR = M.EMPNO AND W.SAL BETWEEN LOSAL AND HISAL ORDER BY DNAME;
--8. 사원명, 급여, 급여등급, 부서명, 연봉, 직속상사명을 출력하시오. 단 연봉은 (SAL+COMM)*12로 계산한다.
SELECT W.ENAME, W.SAL, GRADE, DNAME, (W.SAL+NVL(W.COMM,0))*12, M.ENAME FROM EMP W, EMP M, SALGRADE, DEPT D
    WHERE W.DEPTNO = D.DEPTNO AND W.MGR = M.EMPNO AND W.SAL BETWEEN LOSAL AND HISAL;
--9. 위 8번을 부서명 알파벳 순으로 정렬하고 부서명이 같으면 급여가 큰 사원에서 작은 사원 순으로 정렬하여 출력하시오.
SELECT W.ENAME, W.SAL, GRADE, DNAME, (W.SAL+NVL(W.COMM,0))*12, M.ENAME FROM EMP W, EMP M, SALGRADE, DEPT D
    WHERE W.DEPTNO = D.DEPTNO AND W.MGR = M.EMPNO AND W.SAL BETWEEN LOSAL AND HISAL
    ORDER BY DNAME, SAL DESC;
--예제PART2
--1. EMP테이블의 모든 사원에 대해 사원명, 부서번호 부서명을 출력하시오.
SELECT ENAME,E.DEPTNO,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO;
--2. EMP테이블에서 NEW YORK에서 근무하고 있는 사원에 대해 사원명, 직책, 급여, 부서명을 출력하시오.
SELECT ENAME,JOB,SAL,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO AND LOC = 'NEW YORK';
--3. EMP테이블에서 상여(COMM)를 받는 사원에 대해 사원명, 부서명, 위치를 출력하시오.
SELECT ENAME,DNAME,LOC FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO AND COMM IS NOT NULL AND COMM > 0;
--4. EMP테이블에서 사원명에 알파벳 L이 있는 사원에 대해 사원명, 직책, 부서명, 근무지를 출력하시오.
SELECT ENAME,JOB,DNAME,LOC FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO AND ENAME LIKE '%L%';
--5. EMP테이블에서 사번, 사원명, 부서번호, 부서명을 출력하되 사원명 알파벳 순으로 출력하시오.
SELECT EMPNO,ENAME,E.DEPTNO,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO ORDER BY ENAME;
--6. 급여가 2000이상인 사원에 대해 사번, 사원명, 급여, 부서명을 급여기준으로 내림차순 정렬하여 출력하시오.
SELECT EMPNO,ENAME,SAL,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO ORDER BY SAL DESC;
--7. 직책이 MANAGER이며 급여가 2500이상인 사원에 대해 사번, 사원명, 직책, 급여, 부서명을 사번 기준으로
--오름차순 정렬하여 출력하시오.
SELECT EMPNO,ENAME,JOB,SAL,DNAME FROM EMP E,DEPT D WHERE E.DEPTNO = D.DEPTNO AND SAL >= 2500 ORDER BY EMPNO;
--8. 사번, 사원명, 직책, 급여, 급여등급을 급여기준으로 내림차순 정렬하여 출력하시오
SELECT EMPNO,ENAME,JOB,SAL,GRADE FROM EMP,SALGRADE WHERE SAL BETWEEN LOSAL AND HISAL ORDER BY SAL DESC;
--9. 상사가 없는 직원과 상사가 있는 직원 모두에 대해, 사원명, 직속상사명을 출력하시오.
SELECT W.ENAME, M.ENAME FROM EMP W, EMP M
    WHERE W.MGR = M.EMPNO(+);
--10.상사가 없는 직원과 상사가 있는 직원 모두에 대해, 사원명, 상사명, 상사의 상사명을 출력하시오
SELECT W.ENAME, M.ENAME, MM.ENAME FROM EMP W, EMP M, EMP MM
    WHERE W.MGR = M.EMPNO(+) AND M.MGR = MM.EMPNO(+);

-- 단일행함수 연습문제
--1. 현재 날짜를 TITLE에 Current Date로 별칭으로 출력하는 SELECT 문장을 기술하시오.
SELECT SYSDATE "Current Date" FROM DUAL;
--2. EMP 테이블에서 현재 급여에 15%가 증가된 급여를 사원번호,이름,업무,급여,증가된 급여(New Salary),증가액(Increase)를 출력하는 SELECT 문장을 기술하시오.
SELECT EMPNO,ENAME,JOB,SAL,SAL*1.15 "New Salary",SAL*0.15 "Increase" FROM EMP;
--3. EMP 테이블에서 이름,입사일,입사일로부터 6개월 후 돌아오는 월요일 구하여 출력하는 SELECT 문장을 기술하시오.
SELECT ENAME,HIREDATE, NEXT_DAY(ADD_MONTHS(HIREDATE,6),'월') FROM EMP;
--4. EMP 테이블에서 이름,입사일, 입사일로부터 현재까지의 월수,급여, 입사일부터 현재까지의 급여의 총계를 출력하는 SELECT 문장을 기술하시오.
SELECT ENAME, HIREDATE, TRUNC(MONTHS_BETWEEN(SYSDATE,HIREDATE)),SAL,TRUNC(MONTHS_BETWEEN(SYSDATE,HIREDATE))*SAL FROM EMP;
--5. EMP 테이블에서 모든 사원의 이름과 급여(15자리로 출력 좌측의 빈 곳은 “*”로 대치)를 출력하는 SELECT 문장을 기술하시오.
SELECT ENAME,LPAD(SAL,15,'*') FROM EMP;
--6. EMP 테이블에서 모든 사원의 정보를 이름,업무,입사일,입사한 요일을 출력하는 SELECT 문장을 기술하시오.
SELECT ENAME,JOB,HIREDATE,TO_CHAR(HIREDATE,'DY') FROM EMP;
--7. EMP 테이블에서 이름의 길이가 6자 이상인 사원의 정보를 이름,이름의 글자수,업무를 출력하는 SELECT 문장을 기술하시오.
SELECT ENAME,LENGTH(ENAME),JOB FROM EMP WHERE LENGTH(ENAME) >= 6;
--8. EMP 테이블에서 모든 사원의 정보를 이름, 업무, 급여, 보너스, 급여+보너스를 출력하는 SELECT 문장을 기술하시오
SELECT ENAME,JOB,SAL,COMM,SAL+NVL(COMM,0) FROM EMP;
--9. 사원 테이블의 사원명에서 2번째 문자부터 3개의 문자를 추출하시오. 
SELECT SUBSTR(ENAME,2,3) FROM EMP;
--10. 사원 테이블에서 입사일이 12월인 사원의 사번, 사원명, 입사일을 검색하시오 (EXTRACT함수, TO_CHAR함수)
SELECT EMPNO, ENAME, HIREDATE FROM EMP WHERE TO_CHAR(HIREDATE,'MM')=12;
--11. 다음과 같은 결과를 검색할 수 있는 SQL 문장을 작성하시오
--EMPNO ENAME 급여
--7369 SMITH *******800
--7499 ALLEN ******1600
--7521 WARD ******1250
--…….
SELECT EMPNO, ENAME, LPAD(SAL,10,'*') FROM EMP;
--12. 다음과 같은 결과를 검색할 수 있는 SQL 문장을 작성하시오
--EMPNO ENAME 입사일
--7369 SMITH 1980-12-17
--….
SELECT EMPNO, ENAME, TO_CHAR(HIREDATE,'YYYY-MM-DD') FROM EMP;
--13. 사원 테이블에서 부서번호가 20인 사원의 사번, 이름, 직무, 급여를 출력하시오(급여는 앞에 $를 삽입하고 3자리마다 , 를 출력한다
SELECT EMPNO, ENAME, JOB, TO_CHAR(SAL,'$99,999') FROM EMP WHERE DEPTNO = 20;


-- 그룹함수 <연습문제>
-- 1. 인원수,최대 급여,최소 급여,급여의 합을 출력
SELECT COUNT(*), MAX(SAL), MIN(SAL), SUM(SAL) FROM EMP;
-- 2. 업무별 인원수를 구하여 출력
SELECT JOB, COUNT(*) FROM EMP GROUP BY JOB;
--- 3. 최고 급여와 최소 급여의 차이는 얼마인가 출력
SELECT MAX(SAL) - MIN(SAL) FROM EMP;
-- 4. 각 부서별로 인원수, 급여 평균, 최저 급여, 최고 급여, 급여의 합을 출력(급여의 합이 많은 순으로)
SELECT DEPTNO, COUNT(*), ROUND(AVG(SAL),1), MIN(SAL), MAX(SAL), SUM(SAL) 
    FROM EMP GROUP BY DEPTNO
    ORDER BY SUM(SAL) DESC;
-- 5. 부서별, 업무별 그룹하여 결과를 부서번호, 업무, 인원수, 급여의 평균, 급여의 합을 출력(부서번호, 업무순으로 오름차순 정렬)
SELECT DEPTNO,JOB,COUNT(*),AVG(SAL),SUM(SAL) FROM EMP
    GROUP BY DEPTNO, JOB
    ORDER BY DEPTNO, JOB;
-- 6. 업무별, 부서별 그룹하여 결과를  업무, 부서번호, 인원수, 급여의 평균, 급여의 합을 출력(출력결과는 업무순, 부서번호 순 오름차순 정렬)
SELECT JOB, DEPTNO, COUNT(*), AVG(SAL), SUM(SAL) FROM EMP
    GROUP BY JOB, DEPTNO ORDER BY JOB, DEPTNO;
--7. 사원수가 5명이상 넘는 부서번호와 사원수를 출력
SELECT DEPTNO, COUNT(*) FROM EMP GROUP BY DEPTNO HAVING COUNT(*) >= 5;
-- 8. 사원수가 5명이상 넘는 부서명과 사원수를 출력
SELECT DNAME, COUNT(*) FROM EMP, DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO 
    GROUP BY DNAME HAVING COUNT(*) >= 5;
--9. 업무별 급여의 평균이 3000이상인 업무에 대해서 업무명, 평균 급여, 급여의 합을 출력
SELECT JOB, AVG(SAL), SUM(SAL) FROM EMP
    GROUP BY JOB HAVING AVG(SAL) >= 3000;
--10. 급여 합이 5000을 초과하는 각 업무에 대해서 업무와 급여합을 출력(급여 합계순 내림차순 정렬)
SELECT JOB, SUM(SAL) FROM EMP
    GROUP BY JOB HAVING SUM(SAL) > 5000 
    ORDER BY SUM(SAL) DESC;
--11. 부서별 급여평균, 부서별 급여합계, 부서별 최소급여를 출력
SELECT DEPTNO, AVG(SAL), SUM(SAL), MIN(SAL) FROM EMP
    GROUP BY DEPTNO;
--12. 위의 11번을 수정하여, 부서별 급여평균 최대치, 부서별 급여합의 최대치, 부서별 최소급여의 최대치를 출력
SELECT MAX(AVG(SAL)), MAX(SUM(SAL)), MAX(MIN(SAL)) FROM EMP
    GROUP BY DEPTNO;
--13. 사원 테이블에서 아래의 결과를 출력
--H_YEAR COUNT(*)	MIN(SAL)	MAX(SAL)	AVG(SAL)	SUM(SAL)
--  80	  1		    800		    800		    800		    800
--	81	 10		    950		    5000	    2282.5	  22825
--	82	  2		    1300	    3000	   2150		   4300
--	83	  1		    1100	    1100	    1100	   1100
SELECT TO_CHAR(HIREDATE,'RR') H_YEAR, COUNT(*), MIN(SAL), MAX(SAL), AVG(SAL), SUM(SAL)
    FROM EMP GROUP BY TO_CHAR(HIREDATE,'RR')
    ORDER BY H_YEAR;
-- 14.  아래의 결과를 출력(입사년도별 사원수)
--  1980     1	
--  1981     10
--  1982     2
--  1983     1
--  total    14	
SELECT NVL(TO_CHAR(HIREDATE,'YYYY'),'total') YEAR, COUNT(*) 
    FROM EMP GROUP BY ROLLUP(TO_CHAR(HIREDATE,'YYYY'))
    ORDER BY YEAR;
--15. 최대급여, 최소급여, 전체급여합, 평균을 출력
SELECT MAX(SAL), MIN(SAL), SUM(SAL), AVG(SAL) FROM EMP;
--16. 부서별 인원수 출력
SELECT DEPTNO, COUNT(*) FROM EMP GROUP BY DEPTNO;
--17. 부서별 인원수가 6명이상인 부서번호 출력
SELECT DEPTNO, COUNT(*) FROM EMP GROUP BY DEPTNO HAVING COUNT(*) >=6 ;
--18. 급여가 높은 순서대로 등수를 부여하여 다음과 같은 결과가 나오게 하시오. 
-- (힌트 self join, group by, count사용)
--ENAME	    등수
--________________________
--KING		1
--SCOTT		2
--……
SELECT E1.ENAME, COUNT(E2.ENAME)+1 "등수"
    FROM EMP E1, EMP E2
    WHERE E1.SAL < E2.SAL(+)
    GROUP BY E1.ENAME
    ORDER BY 등수;

-- 서브쿼리문제 
--1. 사원테이블에서 가장 먼저 입사한 사람의 이름, 급여, 입사일
SELECT ENAME, SAL, HIREDATE FROM EMP WHERE HIREDATE = (SELECT MIN(HIREDATE) FROM EMP);

-- 2. 회사에서 가장 급여가 적은 사람의 이름, 급여
SELECT ENAME, SAL FROM EMP WHERE SAL = (SELECT MIN(SAL) FROM EMP);

-- 3. 회사 평균보다 급여를 많이 받는 사람의 이름, 급여, 부서코드
SELECT ENAME, SAL, DEPTNO FROM EMP WHERE SAL > (SELECT AVG(SAL) FROM EMP);

--4. 회사 평균 이하의 급여를 받는 사람의 이름, 급여, 부서명
SELECT ENAME, SAL, DNAME FROM EMP, DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO AND SAL <= (SELECT AVG(SAL) FROM EMP);

--5. SCOTT보다 먼저 입사한 사람의 이름, 급여, 입사일, 급여 등급
SELECT ENAME, SAL, HIREDATE, GRADE 
    FROM EMP, SALGRADE WHERE SAL BETWEEN LOSAL AND HISAL 
    AND HIREDATE < (SELECT HIREDATE FROM EMP WHERE ENAME = 'SCOTT');
    
--6. 5번(SCOTT보다 먼저 입사한 사람의 이름, 급여, 입사일, 급여 등급)에 부서명 추가하고 급여가 큰 순 정렬
SELECT ENAME, SAL, HIREDATE, GRADE, DNAME
    FROM EMP, SALGRADE, DEPT WHERE SAL BETWEEN LOSAL AND HISAL AND EMP.DEPTNO = DEPT.DEPTNO
    AND HIREDATE < (SELECT HIREDATE FROM EMP WHERE ENAME = 'SCOTT')
    ORDER BY SAL DESC;
    
--7. BLAKE 보다 급여가 많은 사원들의 사번, 이름, 급여
SELECT EMPNO, ENAME, SAL FROM EMP
    WHERE SAL < (SELECT SAL FROM EMP WHERE ENAME = 'BLAKE');
    
--8. MILLER보다 늦게 입사한 사원의 사번, 이름, 입사일
SELECT EMPNO, ENAME, HIREDATE FROM EMP
    WHERE HIREDATE > (SELECT HIREDATE FROM EMP WHERE ENAME = 'MILLER');
    
--9. 사원전체 평균 급여보다 급여가 많은 사원들의 사번, 이름, 급여
SELECT EMPNO, ENAME, SAL FROM EMP
    WHERE SAL > (SELECT AVG(SAL) FROM EMP);
    
--10. CLARK와 같은 부서번호이며, 사번이 7698인 직원의 급여보다 많은 급여를 받는 사원들의 사번, 이름, 급여
SELECT EMPNO, ENAME, SAL FROM EMP
    WHERE DEPTNO = (SELECT DEPTNO FROM EMP WHERE ENAME = 'CLARK')
    AND SAL > (SELECT SAL FROM EMP WHERE EMPNO = 7698);
    
--11. CLARK와 같은 부서명이며, 사번이 7698인 직원의 급여보다 많은 급여를 받는 사원들의 사번, 이름, 급여
SELECT EMPNO, ENAME, SAL FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO
    AND DNAME = (SELECT DNAME FROM EMP E,DEPT D WHERE E.DEPTNO=D.DEPTNO AND ENAME = 'CLARK')
    AND SAL > (SELECT SAL FROM EMP WHERE EMPNO = 7698);
    
--12. BLAKE와 같은 부서에 있는 모든 사원의 이름과 입사일자
SELECT ENAME, HIREDATE FROM EMP
    WHERE DEPTNO = (SELECT DEPTNO FROM EMP WHERE ENAME = 'BLAKE')
    AND ENAME != 'BLAKE';
    
--13. 평균 급여 이상을 받는 모든 종업원에 대해서 사원번호와 이름 단 급여가 많은 순으로 출력)
SELECT EMPNO, ENAME FROM EMP
    WHERE SAL > (SELECT AVG(SAL) FROM EMP)
    ORDER BY SAL DESC;
    
-- 14. 이름에 “T”가 있는 사원이 근무하는 부서에서 근무하는 모든 직원의 사원 번호,이름,급여(단 사번 순 출력)
SELECT EMPNO, ENAME, SAL FROM EMP
    WHERE DEPTNO IN (SELECT DEPTNO FROM EMP WHERE ENAME LIKE '%T%')
    ORDER BY EMPNO;
    
-- 15. 부서 위치가 Dallas인 모든 종업원에 대해 이름,업무,급여
SELECT ENAME, JOB, SAL FROM EMP
    WHERE DEPTNO IN (SELECT E.DEPTNO FROM EMP E, DEPT D 
                        WHERE E.DEPTNO=D.DEPTNO
                        AND INITCAP(LOC) = 'Dallas'); -- 서브쿼리 이용
                        
SELECT ENAME, JOB, SAL FROM EMP E, DEPT D -- EQUI JOIN 이용
    WHERE E.DEPTNO = D.DEPTNO
    AND LOC = 'DALLAS';
    
-- 16. EMP 테이블에서 King에게 보고하는 모든 사원의 이름과 급여
SELECT ENAME, SAL FROM EMP
    WHERE MGR IN (SELECT EMPNO FROM EMP WHERE ENAME = 'KING');
    
-- 17. SALES부서 사원의 이름, 업무
SELECT ENAME, JOB FROM EMP
    WHERE DEPTNO IN (SELECT DEPTNO FROM DEPT WHERE DNAME = 'SALES');
    
-- 18. 월급이 부서 30의 최저 월급보다 높은 사원의 모든 필드
SELECT * FROM EMP WHERE SAL > (SELECT MIN(SAL) FROM EMP WHERE DEPTNO = 30);

-- 19. 부서 10에서 부서 30의 사원과 같은 업무를 맡고 있는 사원의 이름과 업무
SELECT ENAME, JOB FROM EMP WHERE DEPTNO = 10
    AND JOB IN (SELECT JOB FROM EMP WHERE DEPTNO = 30);
    
-- 20. FORD와 업무도 월급도 같은 사원의 모든 필드
SELECT * FROM EMP WHERE (JOB,SAL) =
    (SELECT JOB, SAL FROM EMP WHERE ENAME = 'FORD')
    AND ENAME <> 'FORD';
    
-- 21. 이름이 JONES인 직원의 JOB과 같거나 FORD의 SAL 이상을 받는 사원의 정보를 이름, 업무, 부서번호, 급여
-- 단, 업무별 알파벳 순, 월급이 많은 순으로 출력
SELECT ENAME,JOB,DEPTNO,SAL FROM EMP
    WHERE JOB = (SELECT JOB FROM EMP WHERE ENAME = 'JONES')
    OR SAL >= (SELECT SAL FROM EMP WHERE ENAME = 'FORD')
    ORDER BY JOB, SAL DESC;
    
-- 22. SCOTT 또는 WARD와 월급이 같은 사원의 정보를 이름,업무,급여
SELECT ENAME, JOB, SAL FROM EMP
    WHERE SAL IN (SELECT SAL FROM EMP WHERE ENAME IN ('SCOTT','WARD'))
    AND ENAME NOT IN ('SCOTT','WARD');
    
-- 23. CHICAGO에서 근무하는 사원과 같은 업무를 하는 사원들의 이름,업무
SELECT ENAME, JOB FROM EMP
    WHERE JOB IN (SELECT JOB 
                    FROM EMP, DEPT 
                    WHERE EMP.DEPTNO = DEPT.DEPTNO 
                    AND LOC ='CHICAGO');
                    
-- 24. 부서별 평균 월급보다 높은 사원을 사번, 이름, 급여
SELECT EMPNO, ENAME, SAL FROM EMP E
    WHERE SAL > (SELECT AVG(SAL) FROM EMP WHERE DEPTNO = E.DEPTNO);
    
-- 25. 업무별 평균 월급보다 적은 월급을 받는 사원을 부서번호, 이름, 급여
SELECT DEPTNO, ENAME, SAL FROM EMP E
    WHERE SAL < (SELECT AVG(SAL) FROM EMP WHERE JOB = E.JOB);
    
-- 26. 적어도 한 명 이상으로부터 보고를 받을 수 있는 사원을 업무, 이름, 사번, 부서번호를 출력
--(단, 부서번호 순으로 오름차순 정렬)
SELECT JOB, ENAME, EMPNO, DEPTNO FROM EMP E
    WHERE EXISTS (SELECT * FROM EMP WHERE E.EMPNO = MGR)
    ORDER BY DEPTNO;    -- EXISTS 이용
    
SELECT DISTINCT M.JOB, M.ENAME, M.EMPNO, M.DEPTNO
    FROM EMP W, EMP M
    WHERE W.MGR = M.EMPNO
    ORDER BY DEPTNO;    -- SELF JOIN 이용

-- 27. 말단 사원의 사번, 이름, 업무, 부서번
SELECT EMPNO, ENAME, JOB, DEPTNO FROM EMP E
    WHERE NOT EXISTS (SELECT * FROM EMP WHERE E.EMPNO = MGR); -- EXISTS 이용
    
SELECT M.EMPNO, M.ENAME, M.JOB, M.DEPTNO
    FROM EMP W, EMP M
    WHERE W.MGR(+) = M.EMPNO
    AND W.ENAME IS NULL; -- SELF JOIN 이용

ROLLBACK;
-- DML 연습문제 굵은글씨
-- 급여가 3000이상인 사원만 급여를 10%인상
UPDATE EMP01 SET SAL = SAL*1.1 WHERE SAL >= 3000;
-- EMP01테이블에서 ‘DALLAS’에서 근무하는 직원들의 연봉을 1000인상
UPDATE EMP01 SET SAL = SAL+1000 WHERE DEPTNO = (SELECT DEPTNO FROM DEPT WHERE LOC = 'DALLAS');
-- SCOTT사원의 부서번호는 20번으로, 직급은 MANAGER로 한꺼번에 수정
UPDATE EMP01 SET DEPTNO = 20, JOB = 'MANAGER' WHERE ENAME = 'SCOTT';
-- 사원명이 ‘FORD’인 사원을 삭제하는 SQL 작성
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















