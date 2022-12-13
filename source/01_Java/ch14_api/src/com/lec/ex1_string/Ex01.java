package com.lec.ex1_string;
//new연산자를 사용하지 않고 ""를 사용한 문자열 상수는 heap영역에 String 객체 생성
// 이미 존재하는 문자열 상수를 사용한다면 이미 생성된 문자열을 공유한다.

public class Ex01 {
	public static void main(String[] args) {
		
		int i = 10;
		String str1 = "Java";	//heap메모리에 Java 생성
		
		String str2 = "Java";	//heap메모리에 이미 존재하는지 확인후 존재하면 그 주소값 가져옴
		
		//String = "" 로 생성해서 할당하는 경우는 heap메모리에 중복확인을 거친다.
		
		String str3 = new String("Java");//무조건 heap메모리에 새로 생성
		
		if(str1==str2) {
			System.out.println("str1과 str2는 같은 주소를 참조");
		}else {
			System.out.println("str1과 str2는 다른 주소를 참조");
		}
		
		System.out.println(str1==str3?"str1과 str3은 같은주소":"str1과 str3은 다른주소");
		
		str2 = "Java~"; 	//내용이 바뀌는게 아니라 새로운 메모리를 생성후 주소할당
		
		System.out.println(str1==str2? "수정후 같은 주소 참조":"수정후 다른 주소 참조");
		
		System.out.println("str1 : " + str1);
		System.out.println("str2 : " + str2);
		
		
		
		
	}
}
