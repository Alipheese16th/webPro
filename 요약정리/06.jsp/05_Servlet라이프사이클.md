# Servlet 라이프 사이클 (생명주기)

언어를 할 때 라이프사이클은 호출할 시점의 정확한 시점을 알 필요가 있습니다.

Servlet의 사용도가 높은 이유는 빠른 응답 속도 때문입니다.  
Servlet은 최초 요청시 객체가 만들어져 메모리에 로딩되고,  
이후 요청시에는 기존의 객체를 재활용하게 됩니다. 따라서 동작 속도가 빠릅니다.

## Servlet의 라이프사이클

1. 실행시기 : Servlet 객체 생성(최초 한번)
2. 최초요청, 컨테이너 시작 : init() 호출(최초 한번)
3. 모든 요청과 응답 : service(), doGet(), doPost() 호출  
(요청시 매번, doGet이나 doPost없으면 service실행. 통상적으로 doGet과 doPost를 둡니다)
4. 컨테이너 종료 / 어플리케이션 종료 : destroy() 호출(마지막 한번 자원 해제 : Servlet수정, 서버재가동 등)

## Servlet 선처리, 후처리

Servlet의 라이프 사이클중 init()과 destroy()메소드와 관련하여 선처리(init()전)와 후처리(destroy()후) 작업이 가능합니다.

- init()호출 전 선처리 : @PostConstruct 작성후 메소드 생성
- destroy()호출 후 후처리 : @PreDestroy 작성후 메소드 생성

```
@PostConstruct
private void postConstruct() {  //메소드이름은 내마음대로 // 서블릿 객체가 생성되기직전 실행
	System.out.println("서블릿 객체 생성될 때 수행 -1");
}
public void init(ServletConfig config) throws ServletException {  // 서블릿 객체가 생성될 때 실행
	System.out.println("서블릿 객체가 생성(메모리에 load)될 때 수행 -2");
}
public void destroy() {  //메소드이름은 내마음대로 // 서블릿 객체가 소멸될 때 실행
	System.out.println("EX3_LifeCycle 서블릿 객체가 소멸(메모리에서 사라질)될 때 수행 -3");
}
@PreDestroy
private void preDestroy() {  // 서블릿 객체가 소멸되기 직후
	System.out.println("Ex3_LifeCycle 서블릭 객체 소멸되기 직후 수행 -4");
}
```

## 웹 어플리케이션 감시 : ServletContextListener

웹 어플리케이션의 생명주기를 감시하는 리스너(Listener)인 ServletContextListener가 있습니다.

리스너의 해당 메소드가 웹 어플리케이션의 시작과 종료시 호출됩니다.  
(contextInitialized()메소드와 contextDestroyed()메소드)

ServletContextListener를 implements 받은 리스너 클래스 제작하고,  
web.xml 파일에 리스너 클래스를 기술합니다.  
```
<listener>
  	<listener-class>com.tj.ex2.ListenerEx</listener-class>
</listener>
```

Web.xml에 명시하지 않으면 리스너 클래스에 어노테이션(@WebListener)으로 처리할 수 있습니다.

```
@WebListener
public class ListenerEx implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("시작될 때");
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("종료될 때");
	}
}
```


### 처음 웹 어플리케이션 실행 될 때
1. contextInitialized() 실행 ->
2. 선처리작업 @PostConstruct ->
3. init()메소드 실행 ->
4. doGet()이나 doPost() 실행

### 종료될 때
1. Destroye()실행 ->
2. 후처리작업 @PreDestroy ->
3. contextDestroyed()실행

