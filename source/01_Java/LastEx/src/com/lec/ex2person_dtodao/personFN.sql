-- 프로그램 요구사항에 필요한 DML

-- 기능별 query 작성 (1, 2, 3)
-- 0 직업별 이름 출력 
SELECT JNAME FROM JOB;

-- 1 받아온 입력값 INSERT
INSERT INTO PERSON VALUES(PSEQ.NEXTVAL,'이름',(SELECT JNO FROM JOB WHERE JNAME = '배우'),99,99,99);

-- 2 원하는 직업명 입력받아, 직업별 조회후 총점 추가하여 총점이 높은 순으로 이름(번호)로 출력
SELECT ROWNUM RN, A.* 
    FROM (SELECT PNAME || '(' || PNO || ')' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT
        FROM PERSON P, JOB J WHERE P.JNO = J.JNO  AND P.JNO = (SELECT JNO FROM JOB WHERE JNAME = '배우')
        ORDER BY TOT DESC) A;

-- 3 데이터베이스에 입력된 사람 전체를 조회 후 총점을 추가하여 총점이 높은 순으로 출력한다
SELECT ROWNUM RN, A.* 
    FROM (SELECT PNAME || '(' || PNO || ')' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT
        FROM PERSON P, JOB J WHERE P.JNO = J.JNO
        ORDER BY TOT DESC) A;
