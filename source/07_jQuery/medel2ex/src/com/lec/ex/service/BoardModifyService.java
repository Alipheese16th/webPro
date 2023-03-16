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

public class BoardModifyService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletContext().getRealPath("fileBoardUp");
		int maxSize = 1024 * 1024*10;
		String ffilename = null, oldFile = null;
		int result = BoardDao.FAIL;
		try {
			// 첨부한 파일을 서버에 저장하고, 파일이름 가져오기
			MultipartRequest mRequest = new MultipartRequest(request,path,maxSize,"utf-8",new DefaultFileRenamePolicy());
			ffilename = mRequest.getFilesystemName("ffilename");
			oldFile = mRequest.getParameter("oldFile");
			ffilename = (ffilename==null? oldFile : ffilename);	// 파일첨부 안했으면 기존파일 유지
			int fid = Integer.parseInt(mRequest.getParameter("fid"));
			String ftitle = mRequest.getParameter("ftitle");
			String fcontent = mRequest.getParameter("fcontent");
			String fip = request.getRemoteAddr();	// ip구하는 메소드는 기존 리퀘스트객체로
			
			// pageNum받아서 리퀘스트속성으로 전송
			request.setAttribute("pageNum", mRequest.getParameter("pageNum"));
			
			BoardDao bDao = new BoardDao();
			result = bDao.modify(new BoardDto(fid, null, ftitle, fcontent, ffilename, null, 0, 0, 0, 0, fip, null));
			if(result==BoardDao.SUCCESS) {
				request.setAttribute("boardResult", "글수정 성공");				
			}else {
				request.setAttribute("boardResult", "글수정 실패");				
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			request.setAttribute("joinErrorMsg", "첨부파일의 용량이 너무 큽니다. 1MG이하로 첨부하세요");
		}
		
		//이클립스에도 서버첨부파일 복사
		File serverFile = new File(path+"/"+ffilename);
		if(oldFile!=null && !ffilename.equals(oldFile)){
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
