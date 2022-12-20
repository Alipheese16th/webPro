package com.lec.quiz;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MemberTestMain_outputStream {

	public static void main(String[] args) {
		ArrayList<Member> members = new ArrayList<Member>();
		Date date = new Date();	//오늘
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String today = sdf.format(date);
		Scanner scanner = new Scanner(System.in);
		String answer;
		
		while(true) {
			System.out.print("회원가입을 하시겠습니까? (시작:아무 키/종료:n) :");
			answer = scanner.next();
			if(answer.equalsIgnoreCase("n")) {
				break;
			}
			System.out.print("이름을 입력하세요 :");
			String name = scanner.next();
			System.out.print("전화번호를 입력하세요 :");
			String tel = scanner.next();
			System.out.print("생일을 입력하세요(xxxx-xx-xx):");
			String birthday = scanner.next();
			System.out.print("주소를 입력하세요 :");
			String address = scanner.next();
			
			members.add(new Member(name,tel,birthday,address));	//배열에 추가
			String temp = birthday.substring(birthday.indexOf("-")+1);	//생일 월일
			
			
			
			if(sdf.format(date).equals(temp)) {	//만약 오늘 월일과 생일 월일이 같다면
				System.out.println("오늘 생일이시군요 축하드려요!!");
			}
			
		}//while
		
		
		
		OutputStream os = null;
		try {
			os = new FileOutputStream("com/lec/quiz/member.txt");
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		scanner.close();
	}//main

}
