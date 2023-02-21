# JSP 내부 객체 (기본 내장 객체)

### 대표적인 내부 객체
- 입출력 객체 : request, response, out
- 서블릿 객체 : application, page, config
- 세션 객체 : session
- 예외 객체 : exception

## request
웹브라우저를 통해 서버에 어떤 정보를 요청하는 것을 request라 하며,  
이러한 요청 정보는 request 내부객체가 관리합니다.

### request.getRemoteAddr();
웹서버에 연결한 클라이언트의 IP주소

예전에는 IPv4 방식의 아이피주소를 사용했었습니다.  
4개의 255(1바이트) 로는 사용할수 있는 아이피 가짓수가 42억개밖에 안됩니다.  
냉전시대에 생긴 옛날 방식이라 한계가 있는 것.
 
그래서 유동 아이피라는 방식도 생겼습니다.  
기기에 고정적으로 ip를 부여하지 않고 ip갱신주기가 되었을 때 기기를 껏다가 키면 ISP(인터넷 서비스 공급자)에서 남아 있는 ip를 골라 기기에 할당해주는 것입니다.  
보통 회사컴퓨터는 고정ip 방식입니다.

말이 딴데로 샜는데 하여간 아이피 가짓수가 너무 부족해서 IPv6 방식이 새로 나왔습니다.  
윈도우7이상은 기본이 IPv6 방식이라고 합니다. 그러다보니 지금 위의 메소드를 사용하면 결과가  
0:0:0:0:0:0:0:1 라고 나올것입니다.

IPv4로 확인하고 싶다면 아래의 과정을 거칩니다.
eclipse > Run > Run Configuration > Apache Tomcat > Tomcat v9.0 Server > Arguiments >  
Program arguments에 “-Djava.net.preferIPv4Stack=true” 입력  
VM arguments:에 "-Djava.net.preferIPv4Stack=true" 입력  

톰캣을 재실행하면 확인이 가능합니다.  
클라이언트의 ip를 얻는 방법을 통해 추후에 게시판을 구현할 때 실제로 글쓴이의 ip를 db에 저장이 가능하게 됩니다.

### request.getContextPath()
contextPath()를 리턴합니다. 프로젝트를 구분할 수 있는 컨택스트 경로

이걸 활용하여 항시 문서 생성시마다 최상단에 String 변수에 지정하여 
link태그 등 (css불러오기) 사용하기 편해집니다.  
"../css/footer.css" 이런식으로 경로를 지정하면 추후에 include등이 필요할 경우 작동이 안되는 경우가 발생할수 있기때문에 이런식으로 하는게 가장 안정성 있습니다.

### request 메서드
- 파라미터 : request.getParameter("name")  
name에 해당하는 파라미터 값을 리턴
- 복수 파라미터 : request.getParameterValues("name")  
name에 해당하는 파라미터값들을 배열로 리턴
- 파라미터 이름들 : request.getParameterNames()  
모든 파라미터 이름을 배열로 return
- 요청 방식 : request.getMethod()  
get방식인지 post방식인지 리턴
- 세션 객체 : request.getSession()
- 요청 URL : request.getRequestURL()  
url 리턴
- 요청 URI : request.getRequestURI()  
uri(context path 이후 경로)
- 서버 이름 : request.getServerName()  
localhost
- 포트번호 : request.getServerPort()  
8090
- 해당 프로토콜 : request.getProtocol()  
HTTP/1.1

기본적으로 톰캣은 post방식으로 데이터 전송시 자동적으로 utf-8로 인코딩되지 않기때문에  
request.setCharacterEncoding("utf-8"); 메서드를 사용해줘야 할 필요가 있습니다.

uri 와 contextpath 를 리턴하는 메서드는 쓰임새가 많습니다.
클라이언트가 어느 페이지를 요청하든 writer.do, join.do, delete.do 등 이름에 공통점을 부여하고,  
하나의 서블릿에 매핑을 (*.do) 로하면 한곳에 모이게 됩니다.
여기서 request(요청)의 uri를 구해서 subString을 하면 어디서 접근했는지 확인이 되기 때문에 분류하고 따로 작업을 처리할수 있습니다.


## response
웹브라우저의 요청에 응답하는 것을 response한다고 하며, 이러한 응답(response)의 정보는 response 내부객체가 갖고 있습니다.

- setCharacterEncoding() : 응답할때 문자의 인코딩을 설정.
- addCookie(Cookie) : 쿠키를 지정합니다.
- sendRedirect(URL) : 지정한 URL로 이동합니다.

페이지 전환을 원한다면 자바스크립트에서는 location객체로 간단하게 사용할수 있지만 jsp에서는 response객체의 메서드로도 사용할수 있습니다.


## out
웹 브라우저로 HTML 코드를 출력하는 기능

PrintWriter 처럼 스트림 형태로 데이터를 출력하는 클래스는 송신측과 수신측 사이에 가상의 통로를 만듭니다.  
out 내장 객체는 이 통로를 관리하는 변수입니다.

page 지시자의 buffer 속성을 이용하면 출력 버퍼의 크기를 바꿀 수 있습니다. buffer속성은 버퍼의 크기를 kb 단위의 정수로 써야 하며, kb라는 단위 표시를 붙여 써야 합니다.

<%@ page buffer="8kb" autoFlush="true" %>

버퍼공간에 작성이 먼저 처리되고 flush작업을 해야 브라우저에 출력이 됩니다. 기본 버퍼공간 (8kb)

버퍼의 크기 : out.getBufferSize()
버퍼의 남은 크기 : out.getRemaining()

out.println은 사실 버퍼공간에 작성을 하는것입니다. flush를 하면 화면에 출력하는 것.  
하지만 flush는 자동적으로 해주니까 특별한 경우가 아니면 직접 신경 쓸필요가 없습니다.





<<<<<<<<<<추가작성>>>>>>>>>>>>>>>

















-----기타
코드 수정시 서블릿은 서버를 재부팅해야하지만 JSP는 알아서 적용이된다.

서블릿은 url 주소에서 .java생략가능
JSP는 생략불가능 



톰캣은 소스폴더가 수정되면 다시 통채로 서버폴더에 복사합니다.
이런식으로 계속 갱신되기때문에 후에 파일첨부게시판을 만들때 파일첨부를 서버폴더에 하면 다시 지워집니다.
따라서 WEB-INF 디렉토리에 저장해야합니다 



