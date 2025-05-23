package com.lec.ex;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Ex2")
//@WebServlet(name = "Ex2", urlPatterns = { "/Ex2" })
public class Ex2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numStr = request.getParameter("num");
		if(numStr==null) {
			response.getWriter().print("넘어온 파라미터가 없습니다");
		}else {
			int num = Integer.parseInt(numStr);
			int sum = 0;
			for(int i=0; i<num; i++) {
				sum += i;
			}
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter(); // 스트림 생성
			out.println("<html>");
			out.println("<head>");
			out.println("<link href=\"css/ex2.css\" rel=\"stylesheet\">");
			out.println("</head>");
			out.println("<body>");
			out.println("<h2>1부터 "+num+"까지의 누적합은 "+sum+"입니다</h2>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}
