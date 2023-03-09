package com.lec.ex.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;

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
		int maxSize = 1024*1024*10;
		String image = null;
		MultipartRequest mRequest = null;
		try {
			mRequest = new MultipartRequest(request,path,maxSize,"utf-8",new DefaultFileRenamePolicy());
			image = mRequest.getFilesystemName("mphoto");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
// 이클립스에도 서버첨부파일 복사
		if(image!=null){
			InputStream is = null;
			OutputStream os = null;
			try{
				File serverFile = new File(path+"/"+image);
				is = new FileInputStream(serverFile);
				os = new FileOutputStream("D:/webPro/source/06_jsp/ch19_mvcMember/WebContent/memberPhotoUp/"+image);
				byte[] bs = new byte[(int)serverFile.length()];
				int readByteCnt;
				while((readByteCnt = is.read(bs)) != -1){
					os.write(bs,0,readByteCnt);
				}
				System.out.println(image+"복사함");
			}catch(IOException e){
				System.out.println(e.getMessage());
			}finally{
				if(os!=null)
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if(is!=null)
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		
		// 파라미터 정보(mid, mpw, mname, memail, mphoto, mbirth, maddress) 가져오기
		String mid = mRequest.getParameter("mid");
		String mpw = mRequest.getParameter("mpw");
		String mname = mRequest.getParameter("mname");
		String memail = mRequest.getParameter("memail");
		String mphoto = image==null?"nothing.png":image;
		
		Date mbirth = null;
		String paramBirth = mRequest.getParameter("mbirth");
		if(!paramBirth.equals("")) {
			mbirth = Date.valueOf(paramBirth);
		}
		
		String maddress = mRequest.getParameter("maddress");

		MemberDto dto = new MemberDto(mid, mpw, mname, memail, mphoto, mbirth, maddress, null);
		MemberDao mDao = new MemberDao();
		
		String joinResult = mDao.join(dto) == 1 ? "가입 성공":"가입 실패";
		request.getSession().setAttribute("mid", mid);
		request.setAttribute("result", joinResult);
	}

}
