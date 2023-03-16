package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.AdminDao;

public class ALoginService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String aid = request.getParameter("aid");
		String apw = request.getParameter("apw");
		AdminDao aDao = new AdminDao();
		int result = aDao.adminLogin(aid, apw);
		if(result==AdminDao.SUCCESS) {
			request.getSession().setAttribute("admin", aDao.getAdmin(aid));
			request.setAttribute("adminLoginResult", "관리자계정으로 접속");
		}else {
			request.setAttribute("adminLoginErrorMsg", "관리자로그인 실패");
		}
	}
}
