package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.MemberDao;

public class MLoginService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		MemberDao mDao = new MemberDao();
		
		String loginResult = "로그인 실패";
		if(mDao.login(mid, mpw)==1) {
			loginResult = "로그인 성공";
			request.getSession().setAttribute("member", mDao.getMember(mid));
		}
		request.setAttribute("result", loginResult);
		
	}

}
