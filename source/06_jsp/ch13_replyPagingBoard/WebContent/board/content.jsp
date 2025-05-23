<%@page import="com.lec.dto.BoardDto"%>
<%@page import="com.lec.dao.BoardDao"%>
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
		String num = request.getParameter("num"); // null 이거나 "3" 숫자스트링이 올것이다
		BoardDao bDao = BoardDao.getInstance();
		BoardDto dto = bDao.getBoardOneLine(num);
		/* pageNum 추가 */
		String pageNum = request.getParameter("pageNum");
		if(dto==null){
			response.sendRedirect(conPath+"/board/list.jsp");
		}else{
			bDao.readCountUp(Integer.parseInt(num)); // hit수 올리기
			%>
			<table>
				<caption><%=num %>번 글 상세보기</caption>
				<tr>
					<th>글번호</th>
					<td><%=dto.getNum() %></td>
				</tr>
				<tr>
					<th>글제목</th>
					<td><%=dto.getSubject() %></td>
				</tr>
				<tr>
					<th>본문</th>
					<td><pre><%=dto.getContent() %></pre></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><%=dto.getEmail()==null?"":dto.getEmail() %></td>
				</tr>
				<tr>
					<th>조회수</th>
					<td><%=dto.getReadcount()+1 %></td>
				</tr>
				<tr>
					<th>IP</th>
					<td><%=dto.getIp() %></td>
				</tr>
				<tr>
					<th>작성</th>
					<td><%=dto.getRdate() %></td>
				</tr>
				<tr>
					<td colspan="2">
						<button onclick="location.href='<%=conPath%>/board/updateForm.jsp?num=<%=num%>&pageNum=<%=pageNum%>'">
							수정
						</button>
						<button onclick="location.href='<%=conPath%>/board/deleteForm.jsp?num=<%=num%>&pageNum=<%=pageNum%>'">
							삭제
						</button>
						<button onclick="location.href='<%=conPath%>/board/writeForm.jsp?num=<%=num%>&pageNum=<%=pageNum%>'">
							답변
						</button>
						<button onclick="location.href='<%=conPath%>/board/list.jsp?pageNum=<%=pageNum%>'">
							목록
						</button>
					</td>
				</tr>
			</table>
			<%
		}
	%>
</body>
</html>