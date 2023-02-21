
## JSP 동작 원리

클라이언트가 웹브라우저로 a.jsp라는 파일을 요청하게 되면 JSP컨테이너가 JSP파일을 Servlet파일(a_jsp.java)로 변환시킵니다.

그리고 Servlet파일(.java)은 컴파일 된 후 클래스 파일(.class)로 변환되고, 요청한 클라이언트한테 html파일 형태로 응답합니다.

[웹 브라우저 요청] →  
[a.jsp 를 a_ jsp.java로 변환] →  
[a_ jsp.java를 a_ jsp.class로 변환] →  
[웹브라우저에 HTML 형태로 응답]

jsp요청이 들어오면 톰캣에서 해당하는 Servlet이 존재하는지 탐색 후 없으면 생성합니다.  
컴파일해서.class를 만들고 읽어서 메모리에 올리고 실행, 응답합니다.  
그 후 또 요청이 들어오면 이미 자바와 클래스 객체가 있으므로 그 객체를 재사용합니다.

소스 : d:/webPro/source/06_jsp/ch05_jsp/src/main/webapp/ex/a.jsp  
(내가 이클립스로 작업하면서 저장)

http://localhost:8181/ch05_jsp/ex/a.jsp 로 요청

1. d:/webPro/IDE/톰캣폴더/wtpwebapps/ch05_jsp/웹소스  
(*.html, *.jsp, *.js, *.css, *.xml)  
(실행하는 소스 : 톰캣이 웹소스들을 옮겨 저장)

2. d:/webPro/IDE/톰캣폴더/work/~/a_jsp.java 전환

3. d:/webPro/IDE/톰캣폴더/work/~/a_jsp.class컴파일

4. 메모리에 객체 생성



