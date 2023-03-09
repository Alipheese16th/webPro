package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MLogoutService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		String logoutResult = "로그아웃 했습니다";
		request.setAttribute("result", logoutResult);
	}
}
