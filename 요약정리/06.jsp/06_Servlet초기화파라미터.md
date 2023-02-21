
# Servlet 초기화 파라미터

특정 Servlet이 생성될 때 초기에 필요한 데이터들이 있습니다.  
예를 들어 특정 경로(db driver) 및 아이디 정보(db계정) 등이 있습니다.

이러한 데이터들을 초기화 파라미터라고 하며, 어노테이션을 이용하거나,  
web.xml에 기술하고 Servlet파일에서는 getInitParameter(“파라미터”) 메서드를 이용해서 접근(사용)합니다.

## Servlet파일에 초기화 파라미터 기술하는 법

Servlet 클래스 생성 -> @WebInitParam에 초기화 파라미터 기술 -> ServletConfig 메서드 이용해서 데이터 불러오기  

Servlet소스 내  
```
@WebServlet(urlPatterns = {"/Ex4_initParam"},
  initParams = {@WebInitParam(name="id",value="root"),
		@WebInitParam(name="pw",value="mysql"),
		@WebInitParam(name="jdbcdriver",value="com.mysql.jdbcDriver")}
)
public class Ex4_initParam extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String id = getInitParameter("id");
		String pw = getInitParameter("pw");
		String driver = getInitParameter("jdbcdriver");
	}
}
```

## Web.xml에 초기화 파라미터 기술하는 법

Servlet 클래스 생성 -> web.xml파일에 초기화 파라미터 기술 -> GenericServlet 메소드 이용해서 데이터 불러오기

소스  
```
<servlet>
<servlet-name>Ex4_InitParam</servlet-name>
<servlet-class>com.lec.lect.Ex4_initParam</servlet-class>
<init-param>
	<param-name>id</param-name>
	<param-value>root</param-value>
</init-param>
<init-param>
	<param-name>pw</param-name>
	<param-value>mysql</param-value>
</init-param>
<init-param> 
	<param-name>jdbcdriver</param-name>
	<param-value>com.mysql.Driver</param-value>
</init-param>
</servlet>
<servlet-mapping>
	<servlet-name>Ex4_InitParam</servlet-name>
	<url-pattern>/Ex4</url-pattern>
</servlet-mapping>
```

## 데이터 공유 : ServletContext의 메소드 이용

여러 Servlet에서 특정 데이터를 공유해야 할 경우 context parameter를 이용해서 web.xml에 데이터를 기술하고, Servlet에서 공유하면서 사용 할 수 있습니다.  
Servlet에 대한 기술보다는 위에 기술해야 합니다.

```
<context-param>
	<param-name>contextId</param-name>
	<param-value>scott</param-value>
</context-param>
<context-param>
	<param-name>contextPw</param-name>
	<param-value>tiger</param-value>
</context-param>
```
String contextId = getServletContext().getInitParameter("contextId");  
String contextPw = getServletContext().getInitParameter("contextPw");

