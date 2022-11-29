package com.lec.ex;
// 비트연산자 &(and),|(or), ^(XOR;서로다른값일때만 1  서로같으면0)
// 하드웨어 제어할때 쓰는 연산자 
// 사실 자바로 비트연산자는 크게 쓸일 없지만 그래도 배우자. 
public class Ex07 {
	public static void main(String[] args) {
		int n1 = 10; //0 ~ 0 1 0 1 0
		int n2 = 6; // 0 ~ 0 0 1 1 0
		//----------------------------
		//         & : 0 ~ 0 0 0 1 0 => 2    둘다 일치하는 값이 2번째 자리 딱한개
		//         | : 0 ~ 0 1 1 1 0 => 14  둘중 하나라도 1이 있으면 1로 생각
		//         ^ : 0 ~ 0 1 1 0 0 => 12  서로 다를때만 1로 처리한다.
		
		System.out.println("n1 & n2 는 " +(n1&n2));
		System.out.println("n1 | n2 는 " +(n1|n2));
		System.out.println("n1 ^ n2 는 " +(n1^n2));


	}
}
