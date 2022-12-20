package com.lec.ex1_inputStreamOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
// JSP나 Spring 에서 파일첨부게시판 작성시 copy할 로직
// d:/webPro/note/bts.mp4 => d:/webPro/note/bts_copy.mp4
public class Ex05_FileCopyStep3 {
	public static void main(String[] args) {
		
		InputStream is = null;
		OutputStream os = null;

		try {
			File file = new File("d:\\webPro\\note\\bts.mp4");
			is = new FileInputStream(file); // 1. 스트림 객체 생성
			os = new FileOutputStream("d:/webPro/note/bts_copy.mp4");
			
			byte[] bs = new byte[(int) file.length()];	//파일 크기 만큼
			while(true) {	// 2 번 수행
				int count = is.read(bs);	// 2. read(bs에 읽는다) & write	: 
				if(count == -1) break;
				os.write(bs, 0 , count);	// bs배열을 0번 index부터 count바이트 만큼 write
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("파일이나 폴더를 못찾음" + e.getMessage());
		} catch (IOException e) {
			System.out.println("read & write 오류 : " + e.getMessage());
			
		} finally {
			try {
				if(os!=null) os.close();	//3. 파일 닫기(stream 해제)
				if(is!=null) is.close();
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}//try-catch-finally
	}//main
}
