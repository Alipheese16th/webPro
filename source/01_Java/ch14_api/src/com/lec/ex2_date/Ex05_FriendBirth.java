package com.lec.ex2_date;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.lec.ex4_object.Friend;

// 오늘 생일인 친구들 출력
public class Ex05_FriendBirth {
	public static void main(String[] args) {
		
		Friend[] friends = {
				new Friend("홍길동","010-9999-9999","12-14","서울 서대문"),
				new Friend("김길동","010-8888-9999","12-26","수원 영통"),
				new Friend("신길동","010-9999-7777","02-10","서울 용산"),
				new Friend("마길동","010-6666-6666","03-03","인천 송도"),
				};
		
		GregorianCalendar now = new GregorianCalendar();
		
		int month = now.get(Calendar.MONTH)+1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		
		String monthStr = month<10 ? "0"+month : ""+month;
//		String monthStr = month<10 ? "0"+month : String.valueOf(month); //String.valueOf 스트링으로 형변환  (잘 안쓰는 표현)
		String dayStr = day<10 ? "0"+day : ""+day;
		String today = monthStr+"-"+dayStr;
		System.out.println(today);
		
		boolean none = true;	//한번이라도 검색된게 있는지 확인하는 불리언변수
		System.out.println("오늘 생일인 친구 목록을 검색합니다");
		
		for (int i = 0; i < friends.length; i++) {
			String birth = friends[i].getBirth();
			if(today.equals(birth)) {
				System.out.println(friends[i]);
				none = false; //한명이상 출력시 none은 false
			}//if
		}//for
		
		if(none) {
			System.out.println("오늘 생일인 친구가 없습니다");
		}
		
	}//main
}//class
