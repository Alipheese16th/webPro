package com.lec.ex;
//논리연산자 : &&(AND) , ||(or) , !(반대)
public class Ex04 {
	public static void main(String[] args) {
		
		int i = 1 , j = 10 , h = 10;
		
		System.out.println("&& : (i<j) && (++j>h) 는 "+((i<j) && (++j>h)));
		System.out.println("j = "+j); //j=11
		// &&연산의 경우 좌측값이 false일 경우 우항의 값은 보지 않는다.
		
		System.out.println("&& : (i>j) && (++j>h) 는 "+((i>j) && (++j>h)));
		System.out.println("j = "+j);
		// 여기서 좌항이 false가 나왔기에 우항의 연산인 ++증감연산자가 실행조차 안되고 끝난다
		
		
		System.out.println("|| : (i<j) || (++j>h) 는 "+((i<j) || (++j>h)));
		System.out.println("j = "+j);				//true        true

		System.out.println("|| : (i>j) || (++j>h) 는 "+((i>j) || (++j>h)));
		System.out.println("j = "+j);				//false		  true
		//|| or연산자는 좌항이 false여도 우항이 true면 true
		
	}
}
