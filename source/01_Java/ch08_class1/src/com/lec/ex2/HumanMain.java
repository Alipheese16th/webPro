package com.lec.ex2;
//ctrl + shift+ O : 불필요한 import 제거

import com.lec.ex2_human.*;  // * 이 패키지 안의 모든것을 다 쓰겟다 
import com.lec.ex2_human.kid.Kid;

public class HumanMain {

	public static void main(String[] args) {
		Woman woman1 = new Woman();
		Woman woman2 = new Woman();
//		------------	------------
//	레퍼런스변수(객체변수) 선언	객체 생성(인스턴스 생성)
		System.out.println("woman1과 woman2가 같은지 : "+(woman1 == woman2));
		System.out.println("woman1과 woman2가 같은지 : "+woman1.equals(woman2));
		
		
		Man man = new Man();
		Kid kid = new Kid("홍아기"); 
//		Kid kid2 = new Kid(); //개발자가 생성자함수를 만들면 디폴트생성자는 자동생성 안함
		Man hong = new Man("홍길동");
		Man kim = new Man("김길동",22,180,67);
		
		Man kim2 = kim; //같은 레퍼런스주소를 갖고 있다. 사실상 같다.
		//배열도 같다. 객체변수의 할당은 주소를 할당한다.
		
		kim2.setHeight(185); kim2.setWeight(300);
		System.out.println("kim과 kim2가 같은 객체냐 : "+kim.equals(kim2));
		double bmi = kim.calculateBMI();
		System.out.println(kim2.getName()+"의 bmi지수는 "+bmi);
		
	}
}