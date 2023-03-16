package com.lec.ex.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.service.ALoginService;
import com.lec.ex.service.BoardContentService;
import com.lec.ex.service.BoardDeleteService;
import com.lec.ex.service.BoardListService;
import com.lec.ex.service.BoardModifyService;
import com.lec.ex.service.BoardModifyViewService;
import com.lec.ex.service.BoardReplyService;
import com.lec.ex.service.BoardReplyViewService;
import com.lec.ex.service.BoardWriteService;
import com.lec.ex.service.MAllViewService;
import com.lec.ex.service.MJoinService;
import com.lec.ex.service.MLoginService;
import com.lec.ex.service.MLogoutService;
import com.lec.ex.service.MModifyService;
import com.lec.ex.service.MWithdrawalService;
import com.lec.ex.service.MemailConfirmService;
import com.lec.ex.service.MidConfirmService;
import com.lec.ex.service.Service;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		actionDo(request,response);
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String command = uri.substring(conPath.length());
		String viewPage = null;
		Service service = null;
		
		if(command.equals("/main.do")) {	// 첫화면
			viewPage = "main/main.jsp";
			
			
		/* * * * * * * * * * * * * * * * * * * * * *
		 * * * * * * * member 관련 요청 * * * * * * * * 
		 * * * * * * * * * * * * * * * * * * * * * */
		}else if(command.equals("/joinView.do")) {	// 회원가입 뷰 페이지
			viewPage = "member/join.jsp";
			
		}else if(command.equals("/midConfirm.do")) {	// 아이디 중복확인
			service = new MidConfirmService();
			service.execute(request, response);
			viewPage = "member/midConfirm.jsp";
			
		}else if(command.equals("/memailConfirm.do")) {	// 이메일 중복확인
			service = new MemailConfirmService();
			service.execute(request, response);
			viewPage = "member/memailConfirm.jsp";
			
		}else if(command.equals("/join.do")) {	// 회원가입 진행 후 로그인 페이지로
			service = new MJoinService();
			service.execute(request, response);
			viewPage = "loginView.do";
			
		}else if(command.equals("/loginView.do")) {	// 로그인 뷰 페이지
			viewPage = "member/login.jsp";
			
		}else if(command.equals("/login.do")) {	// 로그인 진행 후 메인페이지로
			service = new MLoginService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
			
		}else if(command.equals("/logout.do")) {	// 로그아웃 진행 후 메인페이지로 
			service = new MLogoutService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
			
		}else if(command.equals("/modifyView.do")) { // 정보수정 뷰 페이지
			viewPage = "member/modify.jsp";
			
		}else if(command.equals("/modify.do")) {	// 정보수정 진행 후 메인페이지로
			service = new MModifyService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
			
		}else if(command.equals("/withdrawal.do")) {	// 회원탈퇴 진행 후 메인페이지로
			service = new MWithdrawalService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
			
			
		/* * * * * * * * * * * * * * * * * * * * * *
		 * * * * * * *  admin 관련 요청 * * * * * * * * 
		 * * * * * * * * * * * * * * * * * * * * * */
		}else if(command.equals("/adminLoginView.do")) {	// 관리자로그인 뷰 페이지
			viewPage = "admin/adminLogin.jsp";
			
		}else if(command.equals("/adminLogin.do")) {	// 관리자 로그인 진행후 멤버리스트페이지
			service = new ALoginService();
			service.execute(request, response);
			viewPage = "allView.do";
			
		}else if(command.equals("/allView.do")) {	// 멤버리스트 뷰 페이지
			service = new MAllViewService();
			service.execute(request, response);
			viewPage = "member/mAllView.jsp";
			
			
		/* * * * * * * * * * * * * * * * * * * * * *
		 * * * * * 파일첨부 게시판 관련 요청 * * * * * * * * 
		 * * * * * * * * * * * * * * * * * * * * * */
		}else if(command.equals("/boardList.do")) {	//	게시판리스트 뷰페이지
			service = new BoardListService();
			service.execute(request, response);
			viewPage = "fileBoard/boardList.jsp";
			
		}else if(command.equals("/boardWriteView.do")) {	// 게시판 글쓰기 뷰페이지
			viewPage = "fileBoard/boardWrite.jsp";
			
		}else if(command.equals("/boardWrite.do")) {	// 글쓰기 진행 후 게시판 리스트
			service = new BoardWriteService();
			service.execute(request, response);
			viewPage = "boardList.do";
			
		}else if(command.equals("/boardContent.do")) {	// 글 상세보기 페이지
			service = new BoardContentService();
			service.execute(request, response);
			viewPage = "fileBoard/boardContent.jsp";
			
		}else if(command.equals("/boardModifyView.do")) {	// 글수정 뷰페이지
			service = new BoardModifyViewService();
			service.execute(request, response);
			viewPage = "fileBoard/boardModify.jsp";
			
		}else if(command.equals("/boardModify.do")) {	// 글수정 진행후 게시판 리스트
			service = new BoardModifyService();
			service.execute(request, response);
			viewPage = "boardList.do";
			
		}else if(command.equals("/boardDelete.do")) {	// 글삭제 실행후 게시판 페이지
			service = new BoardDeleteService();
			service.execute(request, response);
			viewPage = "boardList.do";
			
		}else if(command.equals("/boardReplyView.do")) {	// 답글쓰기 뷰 페이지
			service = new BoardReplyViewService();
			service.execute(request, response);
			viewPage = "fileBoard/boardReply.jsp";
			
		}else if(command.equals("/boardReply.do")) {	// 답글쓰기 진행후 게시판 페이지
			service = new BoardReplyService();
			service.execute(request, response);
			viewPage = "boardList.do";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
	}

}
