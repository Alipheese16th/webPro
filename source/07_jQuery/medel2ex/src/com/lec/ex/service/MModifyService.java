package com.lec.ex.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.MemberDao;
import com.lec.ex.dto.MemberDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MModifyService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletContext().getRealPath("memberPhotoUp");
		int maxSize = 1024 * 1024;
		String mphoto = null;
		int result = MemberDao.FAIL;
		try {
			// mRequest 객체 생성(서버에 업로드된 파일 저장) 후, 파일 이름 받아오기
			MultipartRequest mRequest = new MultipartRequest(request, path, maxSize, "utf-8", new DefaultFileRenamePolicy());
			Enumeration<String> params = mRequest.getFileNames();
			while(params.hasMoreElements()) {
				String param = params.nextElement(); // param이 "mphoto"
				mphoto = mRequest.getFilesystemName(param);
			}
			// 파라미터 받아 DB에 modify
			String dbMpw = mRequest.getParameter("dbMpw");
			String dbMphoto = mRequest.getParameter("dbMphoto");
			String mid = mRequest.getParameter("mid");
			String mpw = mRequest.getParameter("mpw");
			if(mpw.equals("")) { // 새비밀번호를 입력하지 않을 경우, 현 비밀번호로
				mpw = dbMpw;
			}
			String mname = mRequest.getParameter("mname");
			String memail = mRequest.getParameter("memail");
			// 정보수정시 사진첨부 안하면, 기존의 사진유지(dbMphoto)
			mphoto = mphoto==null? dbMphoto : mphoto;
			String mbirthStr = mRequest.getParameter("mbirth");
			Date mbirth = null;
			if(!mbirthStr.equals("")) {
				mbirth = Date.valueOf(mbirthStr);
			}
			String maddress = mRequest.getParameter("maddress");
			
			// 회원정보 수정
			MemberDao mDao = new MemberDao();
			MemberDto mDto = new MemberDto(mid, mpw, mname, memail, mphoto, mbirth, maddress, null);
			result = mDao.modify(mDto);
			if(result == MemberDao.SUCCESS) { // 수정 성공시 세션도 수정
				request.getSession().setAttribute("member", mDto);
				request.setAttribute("modifyResult", "회원정보 수정 성공");
			}else { // 수정 실패시
				request.setAttribute("modifyErrorMsg", "회원정보 수정 실패");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		// 서버에 저장된 파일을 소스폴더로 복사 (파일명이 NOIMG.JPG가 아니거나 result가 FAIL 이 아닐경우)
		File serverFile = new File(path+"/"+mphoto);
		if(!mphoto.equals("nothing.png") && result==MemberDao.SUCCESS) {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(serverFile);
				os = new FileOutputStream("D:/webPro/source/07_jQuery/medel2ex/WebContent/memberPhotoUp/"+mphoto);
				byte[] bs = new byte[(int)serverFile.length()];
				while(true) {
					int readByteCnt = is.read(bs);
					if(readByteCnt==-1) break;
					os.write(bs,0,readByteCnt);
				}
				System.out.println("첨부된파일("+mphoto+") 복사 완료");
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}finally {
				try {
					if(os!=null)os.close();
					if(is!=null)is.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

	}

}
