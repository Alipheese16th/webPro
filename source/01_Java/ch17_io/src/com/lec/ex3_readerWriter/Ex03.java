package com.lec.ex3_readerWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Ex03 {
	public static void main(String[] args) {

		Writer writer = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("����� ���� �̸��� ?");
		String fileName = scanner.next(); // abc => txtFile/abc.txt
		scanner.nextLine(); // ���� ����� �뵵

		try {
			writer = new FileWriter("txtFile/" + fileName + ".txt");

			while (true) {
				System.out.print("���Ϸ� ����� �޼��� (����:x):");
				String msg = scanner.nextLine();
				if (msg.equalsIgnoreCase("x")) break;
				writer.write(msg + "\r\n");	//window���๮�� /r/n  �ٸ� jdk1.8 ���ʹ� �׳� \n�ص��ȴ� 
			}
			System.out.println("txtFile/" + fileName + ".txt ���ϸ����� ����Ϸ�");
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
