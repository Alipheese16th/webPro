package com.lec.exam;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/quiz1")
public class quiz1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		String[] su = request.getParameterValues("su");
		PrintWriter out = response.getWriter();
		out.println("<link href=\"css/quiz1.css\" rel=\"stylesheet\">");
		
		for(int i=0;i<su.length;i++) {
			int num = Integer.parseInt(su[i]);
			
			out.println("<table class=\"dansu\">");
			out.println("<tr>");
			out.println("<td>");
			out.println(num+"단");
			out.println("</td>");
			out.println("</tr>");
			
			for(int j=1;j<10;j++) {
				out.println("<tr>");
				out.println("<td>");
				out.println(num+" * "+j+" = "+(num*j));
				out.println("</td>");
				out.println("</tr>");
			} // for (1~9)
			
			out.println("</table>");
			
		} // for (su)
		
		out.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
