package com.lec.exam;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/quiz3")
public class quiz3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int number = Integer.parseInt(request.getParameter("number"));
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		int sum=0;
		for(int i=1;i<=number;i++) {
			sum += i;
		}
		
		out.println("<h2>");
		out.println("1부터 "+number+"까지 누적합은 "+sum);
		out.println("</h2>");
		
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
