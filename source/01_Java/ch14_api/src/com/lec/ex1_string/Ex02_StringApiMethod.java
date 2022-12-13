package com.lec.ex1_string;

public class Ex02_StringApiMethod {
	public static void main(String[] args) {
		
		String str1 = "abcXabc";
		String str2 = new String("abcXabc");
		String str3 = "  ja  va  ";
		
		//여기서 말하는 숫자는 0부터 시작한다. 배열에서의 인덱스와 같다
		
		System.out.println("1."+str1.concat(str2)); //합하는 메소드				:abcXabcabcXabc
		System.out.println("2."+str1.substring(3)); //3번째부터 끝까지 출력		:Xabc
		System.out.println("3."+str1.substring(3,5));//3번째부터 5번째 '앞'까지	:Xa
		System.out.println("4."+str1.length());		//문자열 길이 				:7
		System.out.println("5."+str1.toUpperCase()); //대문자로				:ABCXABC
		System.out.println("6."+str1.toLowerCase()); //소문자로				:abcxabc
		System.out.println("7."+str1.charAt(3)); //3번째 문자 					:X
		System.out.println("8."+str1.indexOf('b')); //첫번째 b가 나오는 인덱스		:1
		System.out.println("9."+str1.lastIndexOf("b"));//마지막 b가 나오는 인덱스	:5
		
		String str4 = "abcXabcXabcXabc";
		
		System.out.println("10."+str4.indexOf("b",3)); //3번째 인덱스부터 나오는 첫 b	:5
		System.out.println("11."+str4.indexOf("cXa")); //처음나오는 cXa의 인덱스 	:2
		System.out.println("12."+str4.indexOf("cXa",9)); //9번째부터 나오는 첫 cXa :10
		System.out.println("13."+str4.indexOf("z"));	//없으면 -1			:-1
		System.out.println("14."+str1.equals(str2));	//같은 스트링은 true		:true
		//string의 equals는 참조변수값을 비교하는게 아니고 그 안의 내용값을 비교한다 
		System.out.println("15."+str1.equalsIgnoreCase("abcxabc"));//대소문자구별없이 비교 :true
		System.out.println("16."+str3.trim());		 //앞뒤space제거 			: ja  va
		System.out.println("17."+str4.replace("abc", "바꿔")); //말그대로 리플레이스 :바꿔X바꿔X바꿔X바꿔
		
		System.out.println("str1="+str1);
		System.out.println("str3="+str3);
		System.out.println("str4="+str4);
		
		
		
	}
}
