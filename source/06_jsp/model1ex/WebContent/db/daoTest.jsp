<%@page import="com.lec.ex.dto.BookDto"%>
<%@page import="com.lec.dao.BookDao"%>
<%@page import="com.lec.dao.FileboardDao"%>
<%@page import="com.lec.ex.dto.FileboardDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.lec.ex.dto.CustomerDto"%>
<%@page import="com.lec.dao.CustomerDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="<%=conPath %>/css/style.css" rel="stylesheet">
</head>
<body>
	<%
// 		CustomerDao cdao = CustomerDao.getInstance();
// 		int result = cdao.duplicateCheck("qwer");
// 		out.print(result==CustomerDao.SUCCESS?"아이디사용가능":"아이디중복있음");
// 		out.print("<br>");
		
// 		CustomerDto cdto = new CustomerDto("newjoin2", "테스트비번", "테스트이름", "테스트전화", "테스트이메일", "테스트주소",
// 				"m", null, null);
// 		result = cdao.joinCustomer(cdto);
// 		out.print(result==CustomerDao.SUCCESS?"회원가입성공":"회원가입실패");
// 		out.print("<br>");
		
// 		result = cdao.login("aaa", "111");
// 		out.print(result==CustomerDao.SUCCESS?"로그인성공":"로그인실패");
// 		out.print("<br>");
		
// 		CustomerDto detail = cdao.detailCustomer("aaa");
// 		out.print(detail);
		
// 		CustomerDto sujong = new CustomerDto("aaa", "111", "수정", "수정", "수정", "수정",
// 				"f", null, null);
// 		result = cdao.updateCustomer(sujong);
// 		out.print(result==CustomerDao.SUCCESS?"수정성공":"수정실패");
// 		out.print("<br>");
		
// 		ArrayList<CustomerDto> cdtos = cdao.listCustomer(3, 5);
// 		for(CustomerDto c : cdtos){
// 			out.print(c);
// 			out.print("<br>");
// 		}
		
// 		out.print("총 회원수는 : "+cdao.totalCountCustomer());
		
// 		out.print("<hr/><hr/>");
	%>
	<%
// 		FileboardDao fdao = FileboardDao.getInstance();
// 		ArrayList<FileboardDto> fdtos = fdao.listFileboard(2, 3);
// 		for(FileboardDto f : fdtos){
// 			out.print(f);
// 			out.print("<br>");
// 		}
		
// 		out.print("총 파일게시판 글수는 : "+fdao.totalCountFileboard());
// 		out.print("<br>");
		
// 		FileboardDto fdto = new FileboardDto("aaa", "제목", "본문", "파일명", "111", 0,
// 				0, 0, "121.12.12.12");
// 		int result = fdao.writeFileboard(fdto);
// 		out.print(result==CustomerDao.SUCCESS?"원글쓰기성공":"원글쓰기실패");
// 		out.print("<br>");
		
// 		FileboardDto fdtodd = new FileboardDto("aaa", "글2-1-1", "본문", null, "111", 2,
// 				2, 1, "192.168.32.1");
// 		result = fdao.reply(fdtodd);
// 		out.print(result==CustomerDao.SUCCESS?"답글쓰기성공":"답글쓰기실패");
// 		out.print("<br>");
		
// 		fdao.hitUp(1); fdao.hitUp(1); fdao.hitUp(1);
		
// 		FileboardDto detailfile = fdao.detailFileboard(1);
// 		out.print(detailfile);
// 		out.print("<br>");
		
// 		FileboardDto fupdate = new FileboardDto(1, "aaa", "수정", "수정", "수정", "111", 0,
// 				1, 0, 0, "수정",null, null, null);
// 		int result = fdao.updateFileboard(fupdate);
// 		out.print(result==CustomerDao.SUCCESS?"글수정성공":"글수정실패");
// 		out.print("<br>");
		
// 		int result = fdao.deleteFileboard(1, "111");

// 		out.print(result==CustomerDao.SUCCESS?"글삭제성공":"글삭제실패");
// 		out.print("<br>");
	%>
	<%
		BookDao bdao = BookDao.getInstance();
		ArrayList<BookDto> booklist = bdao.listBook(3, 5);
		for(BookDto b : booklist){
			out.print(b);
			out.print("<br>");
		}
		
		// 책 1번까지만 테스트함 2번3번4번 아직안함
		
		out.print(bdao.getBookTotCnt());
		
		
		
		
		
	%>
	
</body>
</html>