 JSP 란 JavaServer Pages 의 약자이며 
 HTML 코드에 JAVA 코드를 넣어 동적인 웹페이지를 생성하는 웹어플리케이션 도구입니다.
 
## 문서 작성하기

#### JSP 특징
- 동적 웹어플리케이션 컴포넌트(컴포넌트는 JSP, HTML 등 웹 어플리케이션을 이루는 요소를 컴포넌트라 하고 그 컴포넌트를 담고 있는 것을 컨테이너라고 합니다)
- .jsp 확장자.
- 클라이언트의 요청에 동적으로 작동하고, 응답은 html을 이용
- jsp는 서블릿으로 변환되어 실행(컴파일되면 톰캣서버가 변환)

![jsp1](https://blog.kakaocdn.net/dn/rO9Fe/btrZkSg8m58/GICALurOi5pZtJ4HxPvkKk/img.png)

- MVC패턴(각자의 역할을 분담)에서 View로 이용됩니다

![jsp2](https://blog.kakaocdn.net/dn/AWAZS/btrZkSuFvL6/LZzhaHvyRioXzT7LDq7kQ0/img.png)

Request – Controller-Model(DB연동 포함) –View-response

#### 프로젝트 생성

- Dynamic Web Project ; Generate web.xml deployment descriptor 체크
- Context root : 서버 입장에서 각 프로젝트를 구분해 주는 이름(프로젝트명은 첫문자를 소문자로) 
- index.html에 대해 – web.xml에 의해 프로젝트를 실행시키면 첫페이지로 실행됩니다 (디폴트 페이지)


## JSP 아키텍처

- 아키텍처  
.jsp 파일(hello.jsp) -–(java파일로변환)--> java file(hello_jsp.java) --(컴파일)--> class 파일(hello_jsp.class)

- 생성파일위치  
D:\bigdata\IDE\apache-tomcat-10.0.12\work\Catalina\localhost\ch02_semiJSP\org\apache\jsp


## html 문서에서 자바코드 작성하는법은?

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <%-- 지시자(<%@ ~ %> : 페이지의 속성 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% 
		long result = power(2,3);
	%> <%-- 스크립트릿 : 자바 코드 --%>
	<p>2의 3승은 <%=result %> <%-- 표현식 : 자바코드에서 실행한 결과를 포함한 식 --%></p>
	<p>9의 19승은 <%=power(9,19) %></p>
	<%!	private long power(int a, int b){ // a의 b승 리턴할 power함수 선언
			long temp = 1;
			for(int i=1 ; i<= b ; i++){
				temp *= a; //temp = temp * a;
			}
			return temp;
		}
	
	%> <%-- 선언부 : 함수 및 변수 선언 --%>
</body>
</html>
```
위와 같이 JSP에서만 사용할수 있는 특수한 태그들이 있습니다.
html 문서내에서 자바코드를 사용하고 싶다면 우선 파일을 생성할 때 확장자를 .jsp로 설정해야 하고 <% %> 스크립트릿(scriptlet) 이라는 특수한 태그를 사용합니다. 후에 조금 더 자세히 살펴 볼 것입니다.


