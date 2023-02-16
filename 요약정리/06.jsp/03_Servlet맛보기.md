# Servlet
Servlet이란 클라이언트의 요청을 처리하고, 그 결과를 반환하는 
Servlet 클래스의 구현 규칙을 지킨 자바 웹 프로그래밍 기술  
자바에 html을 얹었다고 생각하면 됩니다. JSP는 html에 Java를 추가했다는 느낌.

## Servlet 문서 작성하기

### Servlet의 특징
- 동적 웹어플리케이션 컴포넌트 ; JSP랑 같습니다
- .java 확장자 ; JAVA 언어를 사용하여 웹 프로그램을 제작하는 것

Servlet (Interface)  
      ↑(구현)  
GenericServlet (abstract)  
      ↑(상속)  
HttpServlet  
↑(상속)  
우리가 작성한 서블릿 클래스  

- 클라이언트의 요청에 동적으로 작동하고, 응답은 html을 이용합니다.
- java thread이용하여 동작 ; 서블릿을 이용하는 가장 큰 장점. 웹컴포넌트를 구성하는 다른 언어에 비해, 요청이 많이 들어와도 서버의 부하가 많이 걸리지 않는 장점
- MVC패턴에서 Controller 부분에 이용됩니다.

![controller](https://blog.kakaocdn.net/dn/AWAZS/btrZkSuFvL6/LZzhaHvyRioXzT7LDq7kQ0/img.png)

### Servlet 파일 생성
서블릿 생성 위치 확인해 봅시다(cf. 프로젝트 생성시 context root는 프로젝트명과 동일하게 이클립스가 만들어 준 대로 쓰는게 좋습니다)

- Java package : com.lec.ex / Class Name : HelloWorld / 
- SupperClass : javax.servlet.http.HttpServlet ; 서블릿은 일반 클래스와 다르게 상속받아야 하는 클래스가 있다. HttpServlet 클래스를 상속 받아야 서블릿 클래스가 됩니다!
- 매핑 단계 : 웹상에서 접근해야 하는 주소 (어노테이션 혹은 xml을 이용합니다. 후에 기술)

### 어노테이션을 이용한 서블릿 맵핑

```
@WebServlet(name = "HelloWorld", description = "test", urlPatterns = { "/HelloWorld" })
```

- name : 서블릿 이름을 지정할수 있습니다. 클래스 이름과는 별개로 지정가능하나 대부분 동일하게 작성합니다.
- description : 해당 서블릿에 대한 설명. 굳이 중요하진 않습니다.
- urlPatterns : 매핑url을 지정합니다. 해당 프로젝트의 context path 후의 경로를 지정하는 부분으로, 슬래시('/')로 시작합니다.
  
간단하게 url만 지정해서 사용할수도 있습니다.
```
@WebServlet("/HelloWorld")  
```

### Web.xml에서 서블릿 맵핑

web.xml에 아래의 부분 추가
```
<servlet>
	<servlet-name>helloworld</servlet-name>  
	<servlet-class>com.lec.ex.HelloWorld</servlet-class>
</servlet>
<servlet-mapping>
  	<servlet-name>helloworld</servlet-name>
  	<url-pattern>/hw</url-pattern>
</servlet-mapping>
```
- servlet-class : 매핑할 클래스 파일명을 '패키지명'을 포함하여 입력
- servlet-name : 서블릿 이름은 위아래가 반드시 동일해야한다.
- url-pattern : '/'로 시작해야 합니다.

### 요청처리객체 및 응답처리 객체

![was](https://blog.kakaocdn.net/dn/K2Yax/btrZtq6gBXe/90is8QX7HZUlZIiDXEhoBk/img.png)

- 클라이언트 측 → WAS ; 톰캣에서 request 객체 생성. 요청을 하는 객체
- 클라이언트 측 ← WAS ; 톰캣에서 response 객체 생성. 응답을 하는 객체

요청을 하는 객체는 클라이언트로부터 정보를 갖고 올 수 있습니다. 
예를 들어 로그인 페이지에 들어가서 아이디와 패스워드를 넣고 버튼을 누르면(회원이면 로그인이 되고 비회원이면 회원가입페이지나 거절페이지로 가도록 한다.) 
응답하는 객체에서는 회원이 맞는지 아닌지 결과값을 response객체에 실어 보냅니다.


