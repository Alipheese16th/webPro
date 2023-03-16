package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.BoardDao;

public class BoardDeleteService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int fid = Integer.parseInt(request.getParameter("fid"));
		BoardDao bDao = new BoardDao();
		int result = bDao.delete(fid);
		if(result==BoardDao.SUCCESS) {
			request.setAttribute("boardResult", "글삭제 성공");
		}else {
			request.setAttribute("boardResult", "글삭제 실패");
		}

	}

}
