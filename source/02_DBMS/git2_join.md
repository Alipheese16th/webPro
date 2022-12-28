 # JOIN  
  
 1. Join의 필요성  
 - 특정부서번호에 대한 부서이름은 무엇인지 dept 테이블에 있고 특정 사원에 대한 부서명을 알아내기 위해서는 부서 테이블에서 정보를 얻어와야 합니다. Join을 하지 않는 경우 다음의 두 SQL문을 통해 알아내야 하는 번거로움이 있다.
   - SELECT ename, deptno FROM emp ORDER BY deptno;
   - SELECT deptno, dname FROM dept;
 - 다행히도 SQL에서는 두 개 이상의 테이블을 결합햐야만 원하는 결과를 얻을 수 있을 때 한번의 질의로 원하는 결과를 얻어내는 Join 기능을 제공.
  
 2. Cross Join  
 - Cross Join은 특별한 키워드 없이 다음과 같이 FROM절에 두 개 이상의 테이블을 기술하는 것.
   - SELECT * FROM EMP, DEPT; 
 - Cross Join의 결과 얻어지는 컬럼의 수는 사원 테이블의 컬럼의 수(8)와 부서 테이블의 컬럼의 수(3)를 더한 것이므로 11이 됩니다. 로우 수는 사원 한 명에 대해서 DEPT 테이블의 4개의 로우와 결합되기에 56개(14*4). 
 - Cross Join의 결과를 보면 사원 테이블에 부서에 대한 상세정보가 결합되긴 했지만, 조인 될 때 아무런 조건을 제시하지 않았기에 사원 한명에 대해서 DEPT 테이블의 4개의 로우와 결합된 형태이기에 Cross Join의 결과는 아무런 의미를 갖지 못 한다.
   - SELECT * FROM EMP, DEPT WHERE ENAME='SCOTT';
   - SELECT * FROM DEPT WHERE DEPTNO=20;
 - 조인 결과가 의미를 갖으려면 조인할 때 조건을 지정. 조인 조건에 따라 조인의 종류가 결정.





