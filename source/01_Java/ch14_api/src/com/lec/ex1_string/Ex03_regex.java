package com.lec.ex1_string;

public class Ex03_regex {
	public static void main(String[] args) {
		
		/* 	정규표현식(regex)
		 	1.	참조 (https://goo.gl/HLntbd 위키백과)
		 	
		  	2.	=	간략문법[a-zA-Z가-힣0-9]
		  	\d	=	숫자와 매치, [0-9]와 동일
		  	\D	=	숫자가 아닌 것
		  	\w	=	영문자나 숫자, [a-zA-Z0-9]와 동일
		  	\W	=	영문자나 숫자가 아닌 문자
		  	\s	=	white space
		  	. 	=	문자(\n을 제외한 문자)
		  	\.	=	.
		  	{3}	=	3번 반복
		  	{2,4}	=	2~4회 반복
		  	+	=	1번 이상 반복
		  	*	=	0번 이상 바복
		  	?	=	0번이나 1번 반복
		  	
		  	
		  	3.정규표현식 연습장 : https://regexpal.com
		  		ex1. ip주소 : 0.0.0.0 ~ 255.255.255.255
		  		=> ([0-9]{1,3}\.){3}[0-9]{1,3}
		  		ex2. 전화번호 : 02 712 6100 / 010 9999 9999 / 010-0000-0000 / 010.010.0101 / 02)717.2323
		  		\d{2,3}.?\d{3,4}.?\d{4}
		  		ex3. 주민등록번호 : 981212-2054545 / 9812122054545
		  		\d{2}[0-1]\d[0-3]\d-?[1-4]\d{6}
		  		ex4. 이메일 : shwo@naver.com / sgsg@sec.co.kr
		  		\w+@\w+\.\w+(\.\w+)?
		  		\w+@\w+(\.\w+){1,2}
		  		
		  	4.소스 구현
		 */
		String juminNo = "981212-2054545";
		//특정 정규표현식의 문자열 변경
		System.out.println("주민번호 : "+juminNo.replaceAll("-[1-4]\\d{6}","-*******"));
		
		String str = "010-9999-9999 yisy0703@naver.com 반갑습니다. 981212-2054545ㅋㅋㅎㅎ";
		
		System.out.println("이메일을 지운 str : "+str.replaceAll("\\w+@\\w+(\\.\\w+){1,2}",""));
		
		System.out.println("전화번호 삭제된 str : "+str.replaceAll("\\d{2,3}.?\\d{3,4}.?\\d{4}",""));
		
		System.out.println("자음 제거된 str : "+str.replaceAll("[ㄱ-ㅎ]", ""));
		
		System.out.println("한글 제거된 str : "+str.replaceAll("[ㄱ-ㅎ가-힣]", ""));
		
	}
}
