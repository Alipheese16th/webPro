# Servlet 웹 프로그래밍

서블릿은 JSP 표준이 나오기 전에 만들어진 표준으로 자바에서 웹 애플리케이션을 개발할 수 있도록 하기 위해 만들어 졌으며, 
자바 클래스를 웹에서 실행 할 수 있도록 한 웹 프로그래밍입니다.

일반 클래스인 자바 파일을 서블릿으로서 구현하려면 javax.servlet.http.HttpServlet 클래스로부터 상속받아서 작성합니다.  
위의 클래스는 톰캣의 servlet-api.jar 에 포함되어 있습니다.

### 작성과정
1. 서블릿 규칙에 따라 자바 코드 작성
2. 경우에 따라서 web.xml 파일에서 서블릿을 주소와 매핑
3. 웹 컨테이너 재실행

## 서블릿 요청 처리
- 요청 방식에 따라 doGet이나 doPost 메서드를 재정의해서 처리합니다.
- service 메서드를 재정의해서 사용할 수는 있지만 이 메서드는 get 방식이나 post 방식 상관없이 호출되며 이 메서드가 호출되면 doGet이나 doPost 메서드는 호출되지 않습니다.
- 서블릿에서 요청을 처리하기 위해 오버라이딩한 메서드는 request객체를 이용해서 웹 브라우저의 요청 정보를 읽어 오던가 아니면 response를 이용해서 응답을 전송할 수 있습니다.
- 응답을 전송하고자 하는 경우는 response 객체의 setContentType() 메서드를 이용해서 타입과 인코딩 방식을 지정해주어야 한글이 깨지지 않습니다.
- 웹 브라우저에 데이터를 전송하려면 getWriter()를 호출해서 문자열 데이터를 출력할 수 있는 PrintWriter를 가져오고 print()메서드를 사용하여 전송합니다.

## GET 과 POST
클라이언트측에서 WAS로 요청하는 방식이 대표적으로 위의 두가지입니다.
- GET 방식  
URL값으로 정보가 전송되어 보안에 약합니다.  
  - 주소와 매개변수를 붙여서 주소 표시줄에 입력하는 방법(?로 구분)
  - a 태그를 이용해서 페이지를 요청하는 경우
  - form태그에서 명시적으로 get방식으로 요청하는 경우(method="get")
  - 매개변수의 데이터는 255자 이내이며 보안성이 없습니다

- POST방식  
header를 이용해 정보가 전송되어 보안에 강합니다.  
  - form태그에서 명시적으로 post방식으로 요청(method="post")
  - 데이터의 크기에 제한이 없으며 url에 표시되지 않으므로 보안성이 우수
  - 한글 파라미터 전달시 request.setCharacterEncoding("utf-8") 추가 필요

## Context Path
WAS(Web Application Server)에서 웹어플리케이션을 구분하기 위한 path.  
해당 웹 어플리케이션이 이름. 이클립스에서 프로젝트를 생성하면, 자동으로 server.xml에 추가됩니다.  
그러므로, 프로젝트를 삭제하고 다시 같은 이름의 프로젝트를 생성할 수 없습니다.

"ch07_cookie"란 이름의 프로젝트 생성시 아래의 코드가 server.xml에 추가  
<Context docBase="ch07_cookie" path="/ch07_cookie" reloadable="true" source="org.eclipse.jst.jee.server:ch07_cookie"/>

## Servlet 작동순서
클라이언트에서 servlet 요청이 들어오면 서버에서는 servlet컨테이너를 만들고, 요청이 있을 때마다 쓰레드가 생성됩니다.

**웹브라우저 -> 웹서버 -> 웹어플리케이션서버(WAS) -> Servlet 컨테이너 servlet 객체 생성, 쓰레드 생성**

## Servlet Parameter
Form태그의 submit 버튼을 클릭하여 데이터를 서버로 전송하면, 해당파일(Servlet)에서는 HttpServletRequest객체를 이용하여 Parameter값을 얻을 수 있습니다.
- request.getParameter("파라미터이름");
- request.getParameterValues("파라미터배열이름");
- request.getParameterNames(); 파라미터 이름들이 배열로

## 한글처리

1. Tomcat 서버의 기본 문자 처리 방식은 IOS-8859-1 방식 입니다. 따라서 개발자가 별도의 한글 인코딩을 하지 않으면 한글이 깨져 보이는 현상이 있습니다.

2. response객체인지, request객체인지 따라서 한글처리 방식에 차이가 있습니다.
   - response객체 : response.setContentType("text/html;charset=utf-8");
   - request객체(POST방식) : request.setCharacterEncoding("utf-8");


