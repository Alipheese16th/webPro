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
		
		int result = mDao.login(mid, mpw);
		if(result==MemberDao.LOGIN_SUCCESS) { // 로그인 성공
			request.getSession().setAttribute("member", mDao.getMember(mid));
		}else { // 로그인 실패
			request.setAttribute("loginErrorMsg", "아이디와 비번을 확인하세요");
		}
		
	}

}
