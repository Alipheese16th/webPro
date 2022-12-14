package com.lec.ex1_string;

//전화번호 뒷자리를 입력받아 검색된 전화번호(중복된 뒷자리 가능)까지 출력 
import java.util.Scanner;

public class Ex07_searchPostTel {
	public static void main(String[] args) {
		
		String[] tels = {"010-9999-9999","02-717-8888","010-7777-8888","010-6666-8888"};
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			
			boolean searchOk = false; //검색한 결과가 있다면 true
			System.out.print("검색하고자 하는 전화번호 뒷자리(종료는 x)?");
			String searchTel = sc.next();
			
			if(searchTel.equalsIgnoreCase("x"))
				break;
			
			for (int i = 0; i < tels.length; i++) {
	//			int idx = tels[i].lastIndexOf("-"); 
	//			String postTel = tels[i].substring(idx+1);
	//			System.out.println(i+"번째 뒷자리 : "+postTel);
				String postTel = tels[i].substring(tels[i].lastIndexOf("-")+1);
				if(postTel.equals(searchTel)) {
					System.out.println("검색하신 전화번호는 "+tels[i]);
					searchOk = true; //전화번호 출력여부
				}
			}
			if(!searchOk) {
				System.out.println("검색하신 전화번호 뒷자리는 없습니다");
			}
		
		}//while
		sc.close();
	}//main
}//class
