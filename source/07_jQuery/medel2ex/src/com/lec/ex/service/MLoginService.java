package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.MemberDao;

public class MLoginService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("next", request.getParameter("next"));
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		MemberDao mDao = new MemberDao();
		int result = mDao.login(mid, mpw);
		if(result == MemberDao.LOGIN_FAIL) {
			request.setAttribute("loginErrorMsg", "로그인 실패. 아이디와 비밀번호 확인 요망");
		}else if(result == MemberDao.LOGIN_SUCCESS) {
			request.getSession().setAttribute("member", mDao.getMember(mid));
		}
	}
}
