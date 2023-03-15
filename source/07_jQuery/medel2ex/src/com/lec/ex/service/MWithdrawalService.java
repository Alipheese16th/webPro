package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.BoardDao;
import com.lec.ex.dao.MemberDao;
import com.lec.ex.dto.MemberDto;

public class MWithdrawalService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDto member = (MemberDto)request.getSession().getAttribute("member");
		String mid = null;
		if(member != null) {
			mid = member.getMid();
		}
		// 탈퇴 진행하기전에 게시글 작성한것부터 삭제
		BoardDao bDao = new BoardDao();
		bDao.preDeleteMember(mid);
		// 탈퇴 진행
		MemberDao mDao = new MemberDao();
		int result = mDao.withdrawalMember(mid);
		if(result == MemberDao.SUCCESS) {
			request.getSession().invalidate();
			request.setAttribute("withdrawalResult", "회원 탈퇴 성공. 안녕히가세요");
		}else {
			request.setAttribute("withdrawalResult", "회원 탈퇴가 정상적으로 처리되지 않았습니다");
		}
	}

}
