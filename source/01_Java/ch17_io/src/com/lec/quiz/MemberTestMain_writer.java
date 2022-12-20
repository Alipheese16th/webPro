package com.lec.quiz;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MemberTestMain_writer {

	public static void main(String[] args) {
		ArrayList<Member> members = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Date date = new Date();
		String today = sdf.format(date);
		String answer = "start";
		
		while(!(answer.equalsIgnoreCase("n"))) {
			System.out.print("회원가입을 하시겠습니까? (y/n):");
			answer = scanner.next();
			
			if(answer.equalsIgnoreCase("y")) {
				Member member = new Member();
				System.out.print("이름을 입력하세요");
				member.setName(scanner.next());
				System.out.print("전화번호를 입력하세요");
				member.setTel(scanner.next());
				System.out.print("주소를 입력하세요");
				scanner.nextLine();
				member.setAddress(scanner.nextLine());
				
				System.out.print("생일을 입력하세요");
				String birth = scanner.next();
				StringTokenizer st = new StringTokenizer(birth, "-");
				if(st.countTokens()==3) {
					int year = Integer.parseInt(st.nextToken());
					int month = Integer.parseInt(st.nextToken());
					int day = Integer.parseInt(st.nextToken());
					member.setBirthday(new Date(new GregorianCalendar(year,month-1,day).getTimeInMillis()));
					System.out.println(today);
					System.out.println(sdf.format(member.getBirthday()));
					if(today.equals(sdf.format(member.getBirthday()))) {
						System.out.println("오늘 생일이시군요 축하드려요!!");
					}
					
					members.add(member);
				}else {
					System.out.println("생일을 잘못 입력하셨습니다. 빠꾸");
				}
				
			}//answer y
		}//while
		
		if(members.size()==0) {
			System.out.println("가입한 회원이 없습니다");
		}else {
			Writer writer = null;
			try {
				writer = new FileWriter("src/com/lec/quiz/member.txt",true);
				for(Member member : members) {
					writer.write(member.toString());
					System.out.println(member);
				}
				writer.write("이하 "+members.size()+"명 가입\n");
				System.out.println("이하 "+members.size()+"명 가입");
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
				
			} finally {
				try {
					if(writer != null) writer.close();
					
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}//가입한회원이 있을때만 실행되는 출력문
		scanner.close();
	}//main

}
