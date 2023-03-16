package com.lec.ex.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.BoardDao;
import com.lec.ex.dto.BoardDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardReplyService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletContext().getRealPath("fileBoardUp");
		int maxSize = 1024 * 1024*10;
		String ffilename = null;
		int result = BoardDao.FAIL;
		try {
			// 첨부한 파일을 서버에 저장하고, 파일이름 가져오기
			MultipartRequest mRequest = new MultipartRequest(request,path,maxSize,"utf-8",new DefaultFileRenamePolicy());
			ffilename = mRequest.getFilesystemName("ffilename");
			// mRequest로 파라미터 정보(원글의 fgroup,fstep,findent 답글의 mid, ftitle, fcontent, fip) 가져와서 DB에 저장(답글쓰기)
			int fgroup = Integer.parseInt(request.getParameter("fgroup"));
			int fstep = Integer.parseInt(request.getParameter("fstep"));
			int findent = Integer.parseInt(request.getParameter("findent"));
			String mid = mRequest.getParameter("mid");
			String ftitle = mRequest.getParameter("ftitle");
			String fcontent = mRequest.getParameter("fcontent");
			String fip = request.getRemoteAddr();	// ip구하는 메소드는 기존 리퀘스트객체로
			BoardDao bDao = new BoardDao();
			result = bDao.reply(new BoardDto(0, mid, ftitle, fcontent, ffilename, null, 0, fgroup, fstep, findent, fip, null));
			// pageNum받아서 리퀘스트속성으로 전송
			request.setAttribute("pageNum", mRequest.getParameter("pageNum"));
			if(result==BoardDao.SUCCESS) {
				request.setAttribute("boardResult", "답글쓰기 성공");				
			}else {
				request.setAttribute("boardResult", "답글쓰기 실패");				
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			request.setAttribute("joinErrorMsg", "첨부파일의 용량이 너무 큽니다. 1MG이하로 첨부하세요");
		}
		
		// 이클립스에도 서버첨부파일 복사
		File serverFile = new File(path+"/"+ffilename);
		if(ffilename!=null && result==BoardDao.SUCCESS){
			InputStream is = null;
			OutputStream os = null;
			try{
				is = new FileInputStream(serverFile);
				os = new FileOutputStream("D:/webPro/source/07_jQuery/medel2ex/WebContent/fileBoardUp/"+ffilename);
				byte[] bs = new byte[(int)serverFile.length()];
				int readByteCnt;
				while((readByteCnt = is.read(bs)) != -1){
					os.write(bs,0,readByteCnt);
				}
				System.out.println(ffilename+"복사함");
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
		
	}

}
