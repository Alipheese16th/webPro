package com.lec.ex1_inputStreamOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Ex04_OutputStream {
	public static void main(String[] args) {
		
		OutputStream os = null;
		
		try {
			os = new FileOutputStream("txtFile/outTest.txt");//(1)
			String msg = "Hello, Java \n 많이 춥네요. 안전한 귀가 되세요";
			byte[] bs = msg.getBytes();	//스트링을 byte배열로 바꾸는 함수
			os.write(bs);	//(2)
			System.out.println("파일 출력 성공");
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			
		} finally {
			try {
				if(os!=null) os.close();	//(3)
			} catch (IOException ignore) {	}
		}
		
		
		
		
		
		
	}

}
