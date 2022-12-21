package com.lec.quiz;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class MemberTestMain_printWriter {

	public static void main(String[] args) {
		ArrayList<Member> members = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Date date = new Date();
		String today = sdf.format(date);
		String answer = null;
		Date birthday = null;
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.print("회원가입을 하시겠습니까?(종료:n) : ");
			answer = scanner.next();
			if(answer.equalsIgnoreCase("n")) {
				break;
			}
			System.out.print("이름을 입력하세요 : ");
			String name = scanner.next();
			System.out.print("전화번호를 입력하세요 : ");
			String tel = scanner.next();
			System.out.print("생년월일을 입력하세요(xxxx-xx-xx) : ");
			String birth = scanner.next();
			System.out.print("주소를 입력하세요 : ");
			scanner.nextLine();
			String address = scanner.nextLine();
			//생일 형변환
			
			if(birth.contains("-")) {
				int year = Integer.parseInt(birth.substring(0,birth.indexOf("-")));
				int month = Integer.parseInt(birth.substring(birth.indexOf("-")+1, birth.lastIndexOf("-")));
				int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-")+1));
				birthday = new Date(new GregorianCalendar(year,month-1,day).getTimeInMillis());
				
				if(today.equals(sdf.format(birthday))) {
					System.out.println("오늘 생일이시군요 축하드려요~!");
				}
			}	
			members.add(new Member(name,tel,birthday,address));//생일을 제대로 입력못하면 null값
		}
		
		if(members.isEmpty()) {
			System.out.println("회원정보가 없습니다");
		}else {
			PrintWriter pw = null;
			try {
				pw = new PrintWriter("src/com/lec/quiz/member.txt");
				
				System.out.println("이름\t전화번호\t생일\t주소");
				for (int i = 0; i < members.size(); i++) {
					System.out.print(members.get(i));
					pw.print(members.get(i));
				}
				System.out.println("...이하 "+members.size()+"명 가입");
				pw.println("...이하 "+members.size()+"명 가입");
				
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				
			} finally {
				pw.close();
			}
		}
		scanner.close();
	}//main

}
