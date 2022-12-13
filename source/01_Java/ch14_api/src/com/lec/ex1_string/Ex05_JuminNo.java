package com.lec.ex1_string;

import java.util.Scanner;

public class Ex05_JuminNo {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String jumin;
		
		//방법1. char로 추출
		char temp;
		System.out.println("주민번호를 입력하세요 : ");	//931101-1155222   index7
		jumin = scanner.next();
		temp = jumin.charAt(7);
		System.out.println((temp=='1' || temp=='3')?"남자입니다": (temp=='2' || temp=='4')?"여자입니다":"무성이세요?");
	
		//방법2. String으로 추출
		String genderStr = jumin.substring(7,8);
		if(genderStr.equals("1")||genderStr.equals("3")) {
			System.out.println("남성이시군요");
		}else if(genderStr.equals("2")||genderStr.equals("4")) {
			System.out.println("여성이시군요");
		}else {
			System.out.println("유효하지 않은 주민번호입니다");
		}
		
		//방법3. genderStr을 정수로 전환
		int genderInt = Integer.parseInt(genderStr);	//정수스트링을 정수로 ("2"를 2로)
		if(genderInt==1||genderInt==3) {
			System.out.println("남성이시군요");
		}else if(genderInt==2||genderInt==4) {
			System.out.println("여성이시군요");
		}else {
			System.out.println("유효하지 않은 주민번호입니다");
		}
		
		
		scanner.close();
	}
	
}
