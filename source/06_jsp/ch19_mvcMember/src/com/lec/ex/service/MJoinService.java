package com.lec.ex.service;

import java.io.File;
import java.io.FileInputStream;
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

public class MJoinService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String path = request.getServletContext().getRealPath("memberPhotoUp");
		int maxSize = 1024 * 1024;
		String mphoto = null;
		MultipartRequest mRequest = null;
		int result = 0;
		try {
			// 첨부한 파일을 서버에 저장하고, 파일이름 가져오기
			mRequest = new MultipartRequest(request,path,maxSize,"utf-8",new DefaultFileRenamePolicy());
			mphoto = mRequest.getFilesystemName("mphoto");
			
			// mRequest로 파라미터 정보(mid, mpw, mname, memail, mphoto, mbirth, maddress) 가져와서 DB에 저장
			String mid = mRequest.getParameter("mid");
			String mpw = mRequest.getParameter("mpw");
			String mname = mRequest.getParameter("mname");
			String memail = mRequest.getParameter("memail");
			mphoto = mphoto==null ? "nothing.png" : mphoto;
			Date mbirth = null;
			String paramBirth = mRequest.getParameter("mbirth");
			if(!paramBirth.equals("")) {
				mbirth = Date.valueOf(paramBirth);
			}
			String maddress = mRequest.getParameter("maddress");

			MemberDao mDao = new MemberDao();
			// mid 중복체크
			result = mDao.idConfirm(mid);
			if(result == MemberDao.NONE_EXISTENT) { // id 중복없으니까 가입 진행
				MemberDto dto = new MemberDto(mid, mpw, mname, memail, mphoto, mbirth, maddress, null);
				// 회원가입
				result = mDao.join(dto);
				if(result == MemberDao.SUCCESS) { // join 성공
					request.getSession().setAttribute("mid", mid);
					request.setAttribute("joinResult", "회원가입이 완료되었습니다");
				}else { // join 실패
					request.setAttribute("joinErrorMsg", "정보가 너무 길어서 회원가입 실패");
				}
			}else { // id중복
				request.setAttribute("joinErrorMsg", "중복된 ID는 회원가입이 불가합니다");
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			request.setAttribute("joinErrorMsg", "첨부파일의 용량이 너무 큽니다. 1MG이하로 첨부하세요");
		}
		
		// 이클립스에도 서버첨부파일 복사
		File serverFile = new File(path+"/"+mphoto);
		if(serverFile.exists() && !mphoto.equals("nothing.png") && result==MemberDao.SUCCESS){
			InputStream is = null;
			OutputStream os = null;
			try{
				is = new FileInputStream(serverFile);
				os = new FileOutputStream("D:/webPro/source/06_jsp/ch19_mvcMember/WebContent/memberPhotoUp/"+mphoto);
				byte[] bs = new byte[(int)serverFile.length()];
				int readByteCnt;
				while((readByteCnt = is.read(bs)) != -1){
					os.write(bs,0,readByteCnt);
				}
				System.out.println(mphoto+"복사함");
			}catch(IOException e){
				System.out.println(e.getMessage());
			}finally{
				try {
					if(os!=null) os.close();
					if(is!=null) is.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}// 파일복사 if
		
	}//execute

}//MJoinService
