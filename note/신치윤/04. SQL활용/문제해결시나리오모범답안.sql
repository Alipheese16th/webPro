DROP TABLE STUDENT;
DROP TABLE MAJOR;

CREATE TABLE MAJOR(
    MAJOR_CODE       NUMBER(4) PRIMARY KEY,
    MAJOR_NAME       VARCHAR2(30),
    MAJOR_OFFICE_LOC VARCHAR2(30)    
);
SELECT * FROM MAJOR;
INSERT INTO MAJOR 
    VALUES(1,'컴퓨터공학','A101호');
INSERT INTO MAJOR 
    VALUES(2,'빅데이터','A102호');
    
CREATE TABLE STUDENT(
    STUDENT_CODE NUMBER(4) PRIMARY KEY,
    STUDENT_NAME VARCHAR2(30),
    SCORE NUMBER(3) CHECK(100>=SCORE AND SCORE>=0),
    MAJOR_CODE NUMBER(4),
    FOREIGN KEY(MAJOR_CODE) REFERENCES MAJOR(MAJOR_CODE)
);
SELECT * FROM STUDENT;
INSERT INTO STUDENT (STUDENT_CODE,STUDENT_NAME,SCORE,MAJOR_CODE)
    VALUES(101,'홍길동',99,1);
INSERT INTO STUDENT (STUDENT_CODE,STUDENT_NAME,SCORE,MAJOR_CODE)
    VALUES(102,'신길동',100,2);
    
SELECT STUDENT_CODE 학번,STUDENT_NAME 이름,SCORE 점수,S.MAJOR_CODE 학과코드,
        MAJOR_NAME 학과명,MAJOR_OFFICE_LOC 학과사무실
    FROM STUDENT S, MAJOR M
    WHERE S.MAJOR_CODE = M.MAJOR_CODE;