package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.BoardDao;

public class BoardContentService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String _fid = request.getParameter("fid");
		int fid = Integer.parseInt(_fid);
		BoardDao bDao = new BoardDao();
		bDao.hitUp(fid);
		request.setAttribute("board", bDao.content(fid));
	}
}
