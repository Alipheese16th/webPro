package com.lec.exam;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/join")
public class join extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String birth = request.getParameter("birth");
		String[] hobbys = request.getParameterValues("hobby");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String[] mailSend = request.getParameterValues("mailSend");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<link href=\"css/join.css\" rel=\"stylesheet\">");
		
		out.println("<div id=\"join_title\">회원가입정보</div>");
		out.println("<body id=\"joinForm_wrap\">");
		
		out.println("<p>");
		out.println("이름 : "+name);
		out.println("</p>");
		
		out.println("<p>");
		out.println("아이디 : "+id);
		out.println("</p>");
		
		out.println("<p>");
		out.println("비번 : "+pw.replaceAll(".", "*"));
		out.println("</p>");
		
		out.println("<p>");
		out.println("생년월일 : "+birth);
		out.println("</p>");
		
		
		out.println("<p>");
		out.println("취미 : ");
		if(hobbys == null) {
			out.println("없음");
		}else {
			for(int i=0; i<hobbys.length; i++){
				out.println(hobbys[i]);
			}
		}
		out.println("</p>");
		
		out.println("<p>");
		out.println("성별 : "+gender);
		out.println("</p>");
		
		out.println("<p>");
		out.println("이메일 : "+email);
		out.println("</p>");
		
		out.println("<p>");
		out.println("메일수신동의 : ");
		for(int i=0; i<mailSend.length; i++){
			out.println(mailSend[i]);
		}
		out.println("</p>");
		
		out.println("</body>");
		
		out.close();
		
	}

}
