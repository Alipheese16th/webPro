package com.lec.ch19.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lec.ch19.dao.BookDao;
import com.lec.ch19.model.Book;
import com.lec.ch19.util.Paging;
@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDao bookDao;

	@Override
	public List<Book> mainList() {
		return bookDao.mainList();
	}
	@Override
	public List<Book> bookList(String pageNum) {
		Paging paging = new Paging(totCntBook(), pageNum, 3, 3);
		return bookDao.bookList(paging);
	}
	@Override
	public int totCntBook() {
		return bookDao.totCntBook();
	}
	@Override
	public Book getDetailBook(int bnum) {
		return bookDao.getDetailBook(bnum);
	}
	
	@Override
	public int registerBook(MultipartHttpServletRequest mRequest, Book book) {
		
		String uploadPath = mRequest.getSession().getServletContext().getRealPath("bookImgFileUpload/");
		String backupPath = "D:/webPro/source/09_Spring/ch19_bookupMember/src/main/webapp/bookImgFileUpload/";
		
		Iterator<String> params = mRequest.getFileNames(); // tempBimg1 tempBimg2
		String[] bimg = new String[2];
		int idx = 0;
		while(params.hasNext()) {
			
			String param = params.next();
			MultipartFile mFile = mRequest.getFile(param); // 파라미터에 첨부된 파일 객체
			bimg[idx] = mFile.getOriginalFilename();
			
			if(bimg[idx]!=null && !bimg[idx].equals("")) { // 첨부함
				
				if(new File(uploadPath + bimg[idx]).exists()) {
					// 서버에 같은 파일이름이 있을 경우(첨부파일과)
					bimg[idx] = System.currentTimeMillis() + "_" + bimg[idx];
				} // if 이름중복
				
				try {
					mFile.transferTo(new File(uploadPath + bimg[idx])); // 서버에 저장
					System.out.println("서버파일 : " + uploadPath + bimg[idx]);
					System.out.println("백업파일 : " + backupPath + bimg[idx]);
					boolean result = fileCopy(uploadPath + bimg[idx], backupPath + bimg[idx]);
					System.out.println(result ? idx+"번째 백업성공":idx+"번째 백업실패");
					
				}catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}else {
				// 파일 첨부 안 하면 bimg[idx] = ""
				// bimg[idx] = "";
			}//if
			idx++;
		}//while
		book.setBimg1(bimg[0]);
		book.setBimg2(bimg[1]);
		return bookDao.registerBook(book);
	}
	
	private boolean fileCopy(String serverFile, String backupFile) {
		boolean isCopy = false;
		InputStream is = null; 
		OutputStream os = null;
		try {
			File file = new File(serverFile);
			is = new FileInputStream(file);
			os = new FileOutputStream(backupFile);
			byte[] buff = new byte[(int) file.length()];
			while(true) {
				int nReadByte = is.read(buff);
				if(nReadByte == -1) break;
				os.write(buff, 0, nReadByte);
			}
			isCopy = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(os!=null) os.close();
				if(is!=null) is.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return isCopy;
	}

	
	@Override
	public int modifyBook(MultipartHttpServletRequest mRequest, Book book) {
		
		String uploadPath = mRequest.getSession().getServletContext().getRealPath("bookImgFileUpload/");
		String backupPath = "D:/webPro/source/09_Spring/ch19_bookupMember/src/main/webapp/bookImgFileUpload/";
		
		Iterator<String> params = mRequest.getFileNames(); // tempBimg1, tempBimg2
		String[] bimg = new String[2];
		int idx = 0;
		
		while(params.hasNext()) {
			String param = params.next();
			MultipartFile mFile = mRequest.getFile(param); // 파라미터에 첨부된 파일 객체
			bimg[idx] = mFile.getOriginalFilename(); 
			if(bimg[idx]!=null && !bimg[idx].equals("")) { // 첨부함
				if(new File(uploadPath + bimg[idx]).exists()) {
					// 서버에 같은 파일이름이 있을 경우(첨부파일과)
					bimg[idx] = System.currentTimeMillis() + "_" + bimg[idx];
				}//if
				try {
					mFile.transferTo(new File(uploadPath + bimg[idx])); // 서버에 저장
					System.out.println("서버파일 : " + uploadPath + bimg[idx]);
					System.out.println("백업파일 : " + backupPath + bimg[idx]);
					boolean result = fileCopy(uploadPath + bimg[idx], backupPath + bimg[idx]);
					System.out.println(result ? idx+"번째 백업성공":idx+"번째 백업실패");
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}else {
				// 파일 첨부 안 하면 bimg[idx] = ""
				// bimg[idx] = "";
			}//if
			idx++;
		}//while - bimg배열에 파일이름 저장
		book.setBimg1(bimg[0]);
		book.setBimg2(bimg[1]);
		return bookDao.modifyBook(book);

	}

}
