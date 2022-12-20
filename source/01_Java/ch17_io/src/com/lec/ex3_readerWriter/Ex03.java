package com.lec.ex3_readerWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Ex03 {
	public static void main(String[] args) {

		Writer writer = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("저장될 파일 이름은 ?");
		String fileName = scanner.next(); // abc => txtFile/abc.txt
		scanner.nextLine(); // 버퍼 지우는 용도

		try {
			writer = new FileWriter("txtFile/" + fileName + ".txt");

			while (true) {
				System.out.print("파일로 출력할 메세지 (종료:x):");
				String msg = scanner.nextLine();
				if (msg.equalsIgnoreCase("x")) break;
				writer.write(msg + "\r\n");	//window개행문자 /r/n  다만 jdk1.8 부터는 그냥 \n해도된다 
			}
			System.out.println("txtFile/" + fileName + ".txt 파일명으로 저장완료");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (writer != null)	writer.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}//try-catch-finally
		scanner.close();
	}//main
}
