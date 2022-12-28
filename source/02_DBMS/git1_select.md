
 # Select문   
 가장 많이 쓰이는 DML(데이터 조작 언어) 질의어    
   
SELECT [DISTINCT] {*, column [Alias], ….}  
FROM 데이블명  
[WHERE condition]  
[ORDER BY {column, expression} [ASC|DESC]];   
   
SELECT : 원하는 컬럼 선택  
*	: 테이블의 모든 column 출력  
alias	: 해당 column에 대한 다른 이름 부여  
DISTINCT: 중복 행 제거 옵션  
FROM  : 원하는 데이터가 저장된 테이블 명을 기술  
WHERE : 조회되는 행을 제한하기 위한 조건 추가  
condition : column, 표현식, 상수 및 비교연산자  
ORDER BY:정렬을 위한 옵션 (ASC :오름차순이며 default, DECS 내림차순)  
   
1. SQL 문장 작성법  
 - SQL 문장은 대소문자를 구별하지 않습니다 
 - SQL 문장은 한 줄 또는 여러 줄에 입력될 수 있습니다. 
 - 절은 보통 읽고 편집하기 쉽게 줄을 나누도록 합니다.(권장)
 - 탭과 줄 넣기(들여쓰기)는 코드를 보다 읽기 쉽게 하기 위해 사용됩니다.(권장)
 - 일반적으로 키워드는 대문자로 입력합니다
 - 키워드를 제외한 다른 모든 단어, 즉 테이블 이름, 열 이름은 소문자로 입력하기도 합니다
 - 명령어의 끝은 ;
 - 주석처리는 --
   
2. SQL 문장 실행  
 - SELECT * FROM TAB;  -- 유저가 소유하고 있는 table을 전부 보여줌
 - SELECT * FROM dept;  -- dept테이블 데이터 전체 보기
 - DESC dept;          -- dept테이블의 구조보기
   ­ not null : null값이 있으면 안 된다.
   ­ number(4) : 부서코드는 정수 4자리
   ­ number(7,2) ; 총자리수 7자리 중 소수점 이하는 2자리
 - SELECT empno AS "사번", ename AS "직원 이름", job AS "직업" FROM emp; -- 별명 사용
   
3. WHERE 조건과 비교연산자 : 같다(=), 크거나같다(>=) 작거나같다(<=) 다르다(<> ^= !=)  
<>, !=, ^= 모두 다르다를 의미   
 - SELECT * FROM emp WHERE deptno=10;
 - SELECT EMPNO, ENAME, SAL FORM emp WHERE ename=’FORD’;
   - SQL에서 문자열이나 날짜는 반드시 작은따옴표안에 표시해야 합니다.
   - SQL문에 사용되는 키워드인 SELECT나 FORM이나 WHERE 등은 대소문자를 구 별하지 않지만 테이블 내에 저장된 데이터 값은 대소문자를 구분하기에 ‘FORD’ 와 ‘ford’는 다릅니다. 
 - SELECT * FROM emp WHERE hiredate <= ‘82/01/01’;
   - emp테이블에서 82년1월1일이전에 입사한 사원에 대한 모든 정보를 출력
   
4. 논리연산자  (AND OR NOT)
 - SELECT * FROM emp WHERE deptno=10 AND job=’MANAGER’;
 - SELECT * FROM WHERE deptno=10 OR job=’MANAGER’;
 - SELECT * FROM emp WHERE NOT deptno = 10; 
 - SELECT * FROM emp WHERE sal>=2000 AND sal<=3000;
   - 2000이상3000이하인 사원정보 출력
    
5. 산술표현식   
 - SELECT ename, sal, comm, sal*12, sal*12+comm FROM emp;
   - null값의 처리. 행이 특정열에 대한 데이터값이 없다면 그 값은 null이 됩니다. null값은 이용할 수 없거나 지정되지 않았거나 알 수 없거나 적용할 수 없는 값. Null은 1이나 공백과는 다르다. 0은 숫자이며 공백은 문자로 볼 수 있다. 어떠한 datatype도 null값을 포함할 수 있으나 주키는 null값이 허용되지 않는다. 
   - null값을 포함한 산술표현식 결과는 NULL이 된다. 그래서 다음과 같이 NVL함수 를 이용한다. 
 - SELECT ename, sal, comm, sal*12, sal*12+NVL(comm,0) FROM emp; 
   - NVL(expr1, expr2)  expr1은 null값을 포함하고 있는 column이나 표현식                       
   - expr2는 null변환을 위한 목표값
   - expr1과 expr2는 같은 타입이여야 한다
 - NVL(hiredate , ‘99/01/01’) DATA형에서의 예
 - NVL(job, ‘업무없음’)      char형에서의 예
   
6. 연결연산자 || : 열이나 문자를 다른 열에 연결하는 연산  
 - SELECT ename || ‘ is a ‘ || job as “employees Details” FROM emp; 
   - 컬럼명에 별칭은 프로그램 영역에서 중요한 의미를 가진다. 대다수 프로그램은 별칭으로 간편하게 사용하는 경우가 많다. Ex. rs.getString(“별칭/컬럼명”);
 - SELECT ‘사번은 ‘ || empno || ‘입니다’ show FROM emp;
   - 실제 어플리케이션에서는 rs.getString(“show”)로 처리
   
7. 중복제거 DISTINCT 필드; 필드 중복제거   
DISTINCT를 사용하여 나타나는 결과는 기본적으로 오름차순 정렬  
 - SELECT DISTINCT DEPTNO, JOB FROM emp;
   - emp테이블에서 deptno별로 job를 한번씩 출력 
   
8. SQL 연산자 (BETWEEN, IN, LIKE, IS NULL)   
 - BETWEEN a AND b : a부터 b (a,b값 포함. a가 작은 값) 
 - IN(list) : list의 값 중 어느 하나와 일치한다
 - LIKE pattern : 검색하고자 하는 값을 정확히 모를 경우 pattern과 일치하는지를 검색하며 pattern에는 다음의 두가지 와일드카드가 사용된다.
   - % : 문자가 없거나, 하나 이상의 문자가 어떤 값이 와도 상관없다.
   - _ : 하나의 문자가 어떤 값이 와도 상관없다
 - IS NULL                : null값을 가졌다
 - NOT BETWEEN a AND b : a와 b 사이에 있지 않다(a,b값 포함되지 않음)
 - NOT IN (list)            : list의 값과 일치하지 않는다.
 - NOT LIKE pattern       : 문자형태가 pattern과 일치하지 않는다.
 - NOT IS NULL           : null값을 갖지 않다
   
9. 정렬을 위한 ORDER BY 절   
유일하게 컬럼의 별칭을 사용 가능한 곳이다. WHERE절이나 GROUP BY절에서는 사용 불가능  
 - ASC 오름차순. Default
 - DESC 내림차순
 - SELECT *  FROM emp ORDER BY SAL; -- 오름차순 정렬(기본) ASC
 - SELECT * FROM emp ORDER BY sal ASC; -- ASC 오름차순
 - SELECT * FROM emp ORDER BY sal DESC; -- 내림차순 정렬

