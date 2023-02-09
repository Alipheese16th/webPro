package com.lec.exam;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quiz4")
public class quiz4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<p>");
		out.println("GET방식 요청입니다.");
		out.println("</p>");
		
		action(request, response);
		out.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<p>");
		out.println("POST방식 요청입니다.");
		out.println("</p>");
		
		action(request, response);
		out.close();
	}

	private void action(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String[] colors = {"red","orange","yellow","green","blue","navy","violet"};
		int font;
		int bg = Integer.parseInt(request.getParameter("color"));		
		if(bg == 0) {
			font = 6;
		}else {
			font = bg - 1 ;
		}
		
		out.println("<style>");
		out.println("*{background-color: "+colors[bg]+";");
		out.println("color: "+colors[font]+";");
		out.println("font-weight:bold;");
		out.println("}");
		out.println("</style>");
	
		out.println("<p>");
		out.println("글자색은 " +colors[font]);
		out.println("배경색은 "+colors[bg]+"입니다");
		out.println("</p>");
		
	};
	
}
