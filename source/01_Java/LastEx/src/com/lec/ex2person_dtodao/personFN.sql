-- ���α׷� �䱸���׿� �ʿ��� DML

-- ��ɺ� query �ۼ� (1, 2, 3)
-- 0 ������ �̸� ��� 
SELECT JNAME FROM JOB;

-- 1 �޾ƿ� �Է°� INSERT
INSERT INTO PERSON VALUES(PSEQ.NEXTVAL,'�̸�',(SELECT JNO FROM JOB WHERE JNAME = '���'),99,99,99);

-- 2 ���ϴ� ������ �Է¹޾�, ������ ��ȸ�� ���� �߰��Ͽ� ������ ���� ������ �̸�(��ȣ)�� ���
SELECT ROWNUM RN, A.* 
    FROM (SELECT PNAME || '(' || PNO || ')' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT
        FROM PERSON P, JOB J WHERE P.JNO = J.JNO  AND P.JNO = (SELECT JNO FROM JOB WHERE JNAME = '���')
        ORDER BY TOT DESC) A;

-- 3 �����ͺ��̽��� �Էµ� ��� ��ü�� ��ȸ �� ������ �߰��Ͽ� ������ ���� ������ ����Ѵ�
SELECT ROWNUM RN, A.* 
    FROM (SELECT PNAME || '(' || PNO || ')' PNAME, JNAME, KOR, ENG, MAT, KOR+ENG+MAT TOT
        FROM PERSON P, JOB J WHERE P.JNO = J.JNO
        ORDER BY TOT DESC) A;
