package com.lec.exam;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/quiz5")
public class quiz5 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String text = request.getParameter("text");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<link href=\"css/quiz5.css\" rel=\"stylesheet\">");
		out.println("<body>");
		out.println("<table>");
		
		out.println("<tr>");
		out.println("<td colspan='2'>");
		out.println("반갑습니다 "+writer+"님");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th>");
		out.println("글제목");
		out.println("</th>");
		out.println("<td>");
		out.println(" "+title+" ");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th>");
		out.println("글내용");
		out.println("</th>");
		out.println("<td>");
		out.println("<pre>"+text+"</pre>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</body>");
		
		out.close();
	}

}
