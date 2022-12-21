package com.lec.quiz;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class MemberTestMain_outputStream {

	public static void main(String[] args) {
		ArrayList<Member> members = new ArrayList<Member>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String today = sdf.format(date);
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("회원가입을 하시겠습니까? (시작:아무 키/종료:n) :");
			String answer = scanner.next();
			if (answer.equalsIgnoreCase("n")) {
				break;
			}
			System.out.print("이름을 입력하세요 :");
			String name = scanner.next();
			System.out.print("전화번호를 입력하세요 :");
			String tel = scanner.next();
			System.out.print("생년월일을 입력하세요(xxxx-xx-xx):");
			String birthday = scanner.next();
			scanner.nextLine();
			System.out.print("주소를 입력하세요 :");
			String address = scanner.nextLine();
			
			// string 생년월일을 쪼개서 데이트형식에 담기
			String[] br = birthday.split("-");
			Date birth;
			
			if(br.length == 3) {
				int year 	= Integer.parseInt(br[0]);
				int month 	= Integer.parseInt(br[1]);
				int day 	= Integer.parseInt(br[2]);
				birth = new Date(new GregorianCalendar(year, month-1, day).getTimeInMillis());
				
			}else {
				System.out.println("생년월일을 잘못입력하셨습니다. 처음부터 다시 입력하세요");
				continue;
			}
			
			if (sdf.format(birth).equals(today)) {
				System.out.println("축하드려요 오늘 생일이시군요?");
			}
			members.add(new Member(name, tel, birth, address)); // 배열에 추가

		} // while
		
		if(members.isEmpty()) {
			System.out.println("가입한 회원이 없습니다");
		}else {
			OutputStream os = null;
			try {
				os = new FileOutputStream("src/com/lec/quiz/member.txt");
				
				System.out.println("이름\t전화번호\t생일\t주소");
				for (int i = 0; i < members.size(); i++) {
					os.write(members.get(i).toString().getBytes());
					System.out.print(members.get(i));
				}
				String mamuri = "....이하 "+members.size()+"명 가입\n";
				os.write(mamuri.getBytes());
				System.out.print(mamuri);

			} catch (IOException e) {
				System.out.println(e.getMessage());
				
			} finally {
				try {
					if (os != null)
						os.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}//finally
		
		}//if members is Empty? else
		
		scanner.close();
	}// main

}
