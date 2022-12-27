-- [ III ] join : 2�� �̻��� ���̺��� �����Ͽ� �����͸� �˻��ϴ� ���
SELECT * FROM EMP WHERE ENAME = 'SCOTT'; -- 1��
SELECT * FROM DEPT; -- 4��

-- CROSS JOIN (FROM ���� ���̺��� 2�� �̻�) //�ƿ� �Ⱦ��̴� JOIN
SELECT * FROM EMP, DEPT WHERE ENAME = 'SCOTT'; -- 1.(EMP���̺��� ����)*(DEPT���̺���)

-- �� 1. EQUI JOIN (���� �ʵ� DEPTNO���� ��ġ�Ǵ� ���Ǹ� JOIN)
SELECT * FROM EMP, DEPT 
    WHERE ENAME = 'SCOTT' AND EMP.DEPTNO = DEPT.DEPTNO;
    -- ex. ��� ����� ���, �̸�, job, �����, �μ���ȣ, �μ��̸�, �ٹ���
SELECT EMPNO, ENAME, JOB, MGR, EMP.DEPTNO, DNAME, LOC 
    FROM EMP, DEPT
    WHERE EMP.DEPTNO = DEPT.DEPTNO;
SELECT EMPNO, ENAME, JOB, MGR, E.DEPTNO, DNAME, LOC
    FROM EMP E,DEPT D
    WHERE E.DEPTNO = D.DEPTNO;
    -- ex. �޿��� 2000�̻��� ������ �̸�, ��å, �޿�, �μ���, �ٹ��� ���
SELECT ENAME, JOB, SAL, DNAME, LOC
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND SAL >= 2000;
    -- ex. 20�� �μ��� ������ �̸�, �μ���ȣ, �ٹ��� ���
SELECT ENAME, E.DEPTNO, LOC
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND E.DEPTNO = 20;
    -- ex. LOC�� CHICAGO�� ����� �̸�, ����, �޿�, �μ���, �ٹ��� ���
SELECT ENAME, JOB, SAL, DNAME, LOC 
    FROM EMP E, DEPT D 
    WHERE E.DEPTNO = D.DEPTNO AND LOC = 'CHICAGO';
    -- ex. �μ���ȣ�� 10�̰ų� 20���� ����� �̸�, ����, �ٹ����� ���(�޿���)
SELECT ENAME, JOB, LOC
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND E.DEPTNO IN (10,20) ORDER BY SAL DESC;
    -- ex. JOB�� SALESMAN�̰ų� MANAGER�� ����� �̸�, �޿�, ��, ����((SAL + COMM)*12), 
    -- �μ���, �ٹ����� ��� ( ������ ū ������ ����)
SELECT ENAME, SAL, COMM, (SAL + NVL(COMM,0))*12 ANNUALSAL, DNAME, LOC
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND JOB IN ('SALESMAN','MANAGER') 
    ORDER BY ANNUALSAL DESC;
    -- ex. COMM�� NULL�̰� SAL�� 1200�̻��� ����� �̸�, �޿�, �Ի���, �μ���ȣ, �μ���
        --(�μ��� ��, �޿� ū �� ����)
SELECT ENAME, SAL, HIREDATE, E.DEPTNO, DNAME
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND COMM IS NULL AND SAL >= 1200
    ORDER BY DNAME, SAL DESC;

-- źź ������
    -- ���忡�� �ٹ��ϴ� ����� �̸��� �޿��� ����Ͻÿ�
SELECT ENAME, SAL
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND LOC = 'NEW YORK';
    -- ACCOUNTING �μ� �Ҽ� ����� �̸��� �Ի����� ����Ͻÿ�
SELECT ENAME, HIREDATE
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND DNAME = 'ACCOUNTING';
    -- ������ MANAGER�� ����� �̸�, �μ����� ����Ͻÿ�
SELECT ENAME, DNAME
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND JOB = 'MANAGER';
    -- Comm�� null�� �ƴ� ����� �̸�, �޿�, �μ��ڵ�, �ٹ����� ����Ͻÿ�.
SELECT ENAME, SAL, E.DEPTNO, LOC
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND COMM IS NOT NULL;

-- �� 2. NON-EQUI JOIN
SELECT * FROM EMP WHERE ENAME = 'SCOTT'; -- ��������
SELECT * FROM SALGRADE; -- �޿� ��� ����
SELECT * FROM EMP, SALGRADE WHERE ENAME = 'SCOTT';
SELECT * FROM EMP, SALGRADE WHERE SAL BETWEEN LOSAL AND HISAL AND ENAME = 'SCOTT';
    -- ex. ������� ���, �̸�, ��å, �����, �޿�, �޿����(1���, 2���, ...)
SELECT EMPNO, ENAME, JOB, MGR, SAL, GRADE || '���' GRADE
    FROM EMP, SALGRADE
    WHERE SAL BETWEEN LOSAL AND HISAL;
    
-- źź������
    -- Comm�� null�� �ƴ� ����� �̸�, �޿�, ���, �μ���ȣ, �μ��̸�, �ٹ����� ����Ͻÿ�.
SELECT ENAME, SAL, GRADE, E.DEPTNO, DNAME, LOC
    FROM EMP E, DEPT D, SALGRADE
    WHERE E.DEPTNO = D.DEPTNO 
    AND SAL BETWEEN LOSAL AND HISAL 
    AND NOT COMM IS NULL;
    -- �̸�, �޿�, �Ի���, �޿����
SELECT ENAME, SAL, HIREDATE, GRADE
    FROM EMP, SALGRADE
    WHERE SAL BETWEEN LOSAL AND HISAL;
    -- �̸�, �޿�, �Ի���, �޿����, �μ���, �ٹ���
SELECT ENAME, SAL, HIREDATE, GRADE, DNAME, LOC
    FROM EMP E, SALGRADE, DEPT D
    WHERE E.DEPTNO = D.DEPTNO
    AND SAL BETWEEN LOSAL AND HISAL;
    -- �̸�, �޿�, ���, �μ��ڵ�, �ٹ���. �� comm �� null�ƴ� ���
SELECT ENAME, SAL, GRADE, E.DEPTNO, LOC 
    FROM EMP E, DEPT D, SALGRADE
    WHERE E.DEPTNO = D.DEPTNO AND SAL BETWEEN LOSAL AND HISAL
    AND NOT COMM IS NULL;
    -- �̸�, �޿�, �޿����, ����, �μ���, �μ��� ���, �μ��� ������ ������. ����=(sal+comm)*12 comm�� null�̸� 0
SELECT ENAME, SAL, GRADE, (SAL+NVL(COMM,0))*12 ANNUALSAL, DNAME
    FROM EMP E, SALGRADE, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND SAL BETWEEN LOSAL AND HISAL
    ORDER BY DNAME, ANNUALSAL;
    -- �̸�, ����, �޿�, ���, �μ��ڵ�, �μ��� ���. �޿��� 1000~3000����. �������� : �μ���, �μ������� ������, ���������� �޿� ū��
SELECT ENAME, JOB, SAL, GRADE, E.DEPTNO, DNAME
    FROM EMP E, DEPT D, SALGRADE
    WHERE E.DEPTNO = D.DEPTNO AND SAL BETWEEN LOSAL AND HISAL
    AND SAL BETWEEN 1000 AND 3000
    ORDER BY DNAME, JOB, SAL DESC;
    -- �̸�, �޿�, ���, �Ի���, �ٹ���. 81�⿡ �Ի��� ���. ��� ū��
SELECT ENAME, SAL, GRADE, HIREDATE, LOC
    FROM EMP E,SALGRADE,DEPT D
    WHERE E.DEPTNO = D.DEPTNO 
    AND SAL BETWEEN LOSAL AND HISAL
    AND HIREDATE LIKE '%81%'
    ORDER BY GRADE DESC;

-- �� 3. SELF-JOIN
SELECT EMPNO, ENAME, MGR FROM EMP WHERE ENAME = 'SMITH';
SELECT EMPNO, ENAME FROM EMP WHERE EMPNO = 7902;

SELECT WORKER.EMPNO, WORKER.ENAME, WORKER.MGR, MANAGER.EMPNO, MANAGER.ENAME
    FROM EMP WORKER, EMP MANAGER
    WHERE WORKER.ENAME = 'SMITH' AND WORKER.MGR = MANAGER.EMPNO;

    -- ex. ��� ����� ���, �̸�, ����� ���, ����� �̸�
SELECT W.EMPNO, W.ENAME, W.MGR, M.ENAME
    FROM EMP W, EMP M
    WHERE W.MGR = M.EMPNO; -- 13�� (KING�� MGR�� NULL�̶�. EMPNO�� NULL �Ұ�)
DESC EMP;
    -- ex. 'SMITH�� ���� FORD��' �������� ���
SELECT W.ENAME || '�� ���� ' || M.ENAME || '��' MESSAGE
    FROM EMP W, EMP M
    WHERE W.MGR = M.EMPNO;
    
    -- źźex1. �Ŵ����� KING�� ������� �̸��� ������ ����Ͻÿ�.
SELECT W.ENAME, W.JOB FROM EMP W, EMP M 
    WHERE W.MGR = M.EMPNO AND M.ENAME = 'KING';
    -- źźex2. SCOTT�� ������ �μ���ȣ���� �ٹ��ϴ� ����� �̸��� ����Ͻÿ�
SELECT E2.ENAME
    FROM EMP E1, EMP E2
    WHERE E1.ENAME = 'SCOTT'
    AND E1.DEPTNO = E2.DEPTNO
    AND E2.ENAME != 'SCOTT';
 
    -- źźex3. SCOTT�� ������ �ٹ������� �ٹ��ϴ� ����� �̸��� ����Ͻÿ�(2�ܰ� ��������)
SELECT E2.ENAME
    FROM EMP E1, DEPT D1, EMP E2, DEPT D2  --������ ��Ţ ���̺�� ���������� ��Ţ���̺��� �ٹ��� ��
    WHERE E1.DEPTNO = D1.DEPTNO AND E2.DEPTNO = D2.DEPTNO -- ��Ţ���̺� �ΰ��� ��
    AND E1.ENAME = 'SCOTT' AND D1.LOC = D2.LOC AND E2.ENAME <> 'SCOTT'; --�������̺� ���ش���� ����, ���� ���̺��� �ٹ����� �� 
-- ȫ������ 50�� �μ� ������ ���󺹱�(����)
INSERT INTO DEPT VALUES (50, 'IT', 'DALLAS');
INSERT INTO EMP VALUES (9999, 'ȫ', NULL, NULL, NULL, 6000, NULL, 50);
ROLLBACK;
SELECT * FROM DEPT;

-- �� 4. OUTER JOIN ;  EQUI JOIN & SELF JOIN�� ���ǿ� �������� �ʴ� ����� ��Ÿ����
-- (1) SELF JOIN ������ OUTER JOIN
SELECT W.ENAME, W.MGR, M.EMPNO, M.ENAME
    FROM EMP W, EMP M
    WHERE W.MGR = M.EMPNO(+); -- EMPNO�� �����̸Ӹ�Ű �� NULL���� ������. NULL���� ���Եɼ��ְ� (+)
    -- ex. "SMITH�� ���� FORD" ... "KING�� ���� ����"
SELECT W.ENAME || '�� ���� ' || NVL(M.ENAME, '����')
    FROM EMP W, EMP M
    WHERE W.MGR = M.EMPNO(+);
    -- ex. ���ܻ��
SELECT M.EMPNO, M.ENAME
    FROM EMP W, EMP M
    WHERE W.MGR(+) = M.EMPNO AND W.ENAME IS NULL;
-- (2) EQUI JOIN������ OUTER JOIN
SELECT * FROM EMP; -- 14��
SELECT * FROM DEPT; -- 4�� (10,20,30,40 �μ�)
SELECT * FROM EMP E, DEPT D WHERE E.DEPTNO = D.DEPTNO; --40�� �μ� ��� �ȵ�
SELECT * FROM EMP E, DEPT D WHERE E.DEPTNO(+) = D.DEPTNO;
    
-- �� <��������> PART1
--1. �̸�, ���ӻ���
SELECT E1.ENAME WORKER, E2.ENAME MANAGER
    FROM EMP E1, EMP E2 
    WHERE E1.MGR = E2.EMPNO;
--2. �̸�, �޿�, ����, ���ӻ���
SELECT E1.ENAME WORKER, E1.SAL SAL, E1.JOB JOB, E2.ENAME MANAGER
    FROM EMP E1, EMP E2 
    WHERE E1.MGR = E2.EMPNO;
--3. �̸�, �޿�, ����, ���ӻ��� . (��簡 ���� �������� ��ü ���� �� ���.
    --��簡 ���� �� '����'���� ���)
SELECT E1.ENAME WORKER, E1.SAL SAL, E1.JOB JOB, NVL(E2.ENAME,'����') MANAGER
    FROM EMP E1, EMP E2 
    WHERE E1.MGR = E2.EMPNO(+);
--4. �̸�, �޿�, �μ���, ���ӻ���
SELECT E1.ENAME WORKER, E1.SAL SAL, DNAME DNAME, E2.ENAME MANAGER
    FROM EMP E1, DEPT D, EMP E2
    WHERE E1.DEPTNO = D.DEPTNO
    AND E1.MGR = E2.EMPNO;
--5. �̸�, �޿�, �μ��ڵ�, �μ���, �ٹ���, ���ӻ���, (��簡 ���� �������� ��ü ���� �� ���)
SELECT E1.ENAME WORKER, E1.SAL SAL, D.DEPTNO DEPTNO, DNAME DNAME, E2.ENAME MANAGER
    FROM EMP E1, DEPT D, EMP E2
    WHERE E1.DEPTNO = D.DEPTNO
    AND E1.MGR = E2.EMPNO(+);
--6. �̸�, �޿�, ���, �μ���, ���ӻ���. �޿��� 2000�̻��� ���
SELECT E1.ENAME WORKER, E1.SAL SAL, GRADE GRADE, DNAME DNAME, E2.ENAME MANAGER
    FROM EMP E1, EMP E2, DEPT D, SALGRADE
    WHERE E1.SAL BETWEEN LOSAL AND HISAL
    AND E1.DEPTNO = D.DEPTNO
    AND E1.MGR = E2.EMPNO
    AND E1.SAL >= 2000;
--7. �̸�, �޿�, ���, �μ���, ���ӻ���, (���ӻ�簡 ���� �������� ��ü���� �μ��� �� ����)
SELECT E1.ENAME WORKER, E1.SAL SAL, GRADE GRADE, DNAME DNAME, E2.ENAME MANAGER
    FROM EMP E1, EMP E2, DEPT D, SALGRADE
    WHERE E1.SAL BETWEEN LOSAL AND HISAL
    AND E1.DEPTNO = D.DEPTNO
    AND E1.MGR = E2.EMPNO(+)
    ORDER BY D.DNAME;
--8. �̸�, �޿�, ���, �μ���, ����, ���ӻ���. ����=(�޿�+comm)*12 �� comm�� null�̸� 0
SELECT E1.ENAME WORKER, E1.SAL SAL, GRADE GRADE, DNAME DNAME,(E1.SAL+NVL(E1.COMM,0))*12 ANNUALSAL, E2.ENAME MANAGER
    FROM EMP E1, EMP E2, DEPT D, SALGRADE
    WHERE E1.SAL BETWEEN LOSAL AND HISAL
    AND E1.DEPTNO = D.DEPTNO
    AND E1.MGR = E2.EMPNO;
--9. 8���� �μ��� �� �μ��� ������ �޿��� ū �� ����
SELECT E1.ENAME WORKER, E1.SAL SAL, GRADE GRADE, DNAME DNAME,(E1.SAL+NVL(E1.COMM,0))*12 ANNUALSAL, E2.ENAME MANAGER
    FROM EMP E1, EMP E2, DEPT D, SALGRADE
    WHERE E1.SAL BETWEEN LOSAL AND HISAL
    AND E1.DEPTNO = D.DEPTNO
    AND E1.MGR = E2.EMPNO
    ORDER BY DNAME, SAL DESC;

--  PART2
--1.EMP ���̺��� ��� ����� ���� �̸�, �μ���ȣ, �μ����� ����ϴ� SELECT ������ �ۼ��Ͽ���.
SELECT ENAME, E.DEPTNO, DNAME
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO;
--2. EMP ���̺��� NEW YORK���� �ٹ��ϰ� �ִ� ����� ���Ͽ� �̸�, ����, �޿�, �μ����� ���
SELECT ENAME, JOB, SAL, DNAME
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND LOC = 'NEW YORK';
--3. EMP ���̺��� ���ʽ��� �޴� ����� ���Ͽ� �̸�,�μ���,��ġ�� ���
SELECT ENAME, DNAME, LOC
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND COMM IS NOT NULL AND COMM != 0;
--4. EMP ���̺��� �̸� �� L�ڰ� �ִ� ����� ���Ͽ� �̸�,����,�μ���,��ġ�� ���
SELECT ENAME, JOB, DNAME, LOC
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND ENAME LIKE '%L%';
--5. ���, �����, �μ��ڵ�, �μ����� �˻��϶�. ������������ ������������
SELECT EMPNO, ENAME, E.DEPTNO, DNAME
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO ORDER BY ENAME;
--6. ���, �����, �޿�, �μ����� �˻��϶�. 
    --�� �޿��� 2000�̻��� ����� ���Ͽ� �޿��� �������� ������������ �����Ͻÿ�
SELECT EMPNO, ENAME, SAL, DNAME
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND SAL >= 2000 ORDER BY SAL DESC;
--7. ���, �����, ����, �޿�, �μ����� �˻��Ͻÿ�. �� ������ MANAGER�̸� �޿��� 2500�̻���
-- ����� ���Ͽ� ����� �������� ������������ �����Ͻÿ�.
SELECT EMPNO, ENAME, JOB, SAL, DNAME
    FROM EMP E, DEPT D
    WHERE E.DEPTNO = D.DEPTNO AND JOB = 'MANAGER' AND SAL >= 2500 
    ORDER BY EMPNO;
--8. ���, �����, ����, �޿�, ����� �˻��Ͻÿ�. ��, �޿����� ������������ �����Ͻÿ�
SELECT EMPNO, ENAME, JOB, SAL, GRADE
    FROM EMP E, DEPT D, SALGRADE
    WHERE E.DEPTNO = D.DEPTNO AND SAL BETWEEN LOSAL AND HISAL
    ORDER BY SAL DESC;
--9. ������̺��� �����, ����� ��縦 �˻��Ͻÿ�(��簡 ���� �������� ��ü)
SELECT E1.ENAME WORKER, NVL(E2.ENAME,'����') MANAGER
    FROM EMP E1, EMP E2
    WHERE E1.MGR = E2.EMPNO(+);
--10. �����, ����, ����� ������ �˻��Ͻÿ�
SELECT E1.ENAME WORKER, E2.ENAME MANAGER, E3.ENAME KING
    FROM EMP E1, EMP E2, EMP E3
    WHERE E1.MGR = E2.EMPNO AND E2.MGR = E3.EMPNO;
--11. ���� ������� ���� ��簡 ���� ��� ������ �̸��� ��µǵ��� �����Ͻÿ�
SELECT E1.ENAME WORKER, NVL(E2.ENAME,'����') MANAGER, NVL(E3.ENAME,'����') KING
    FROM EMP E1, EMP E2, EMP E3
    WHERE E1.MGR = E2.EMPNO(+) AND E2.MGR = E3.EMPNO(+);

