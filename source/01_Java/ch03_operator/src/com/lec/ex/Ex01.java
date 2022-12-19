package com.lec.ex;
// 산술연산 : + - * / %(나머지 연산자)
public class Ex01 {
	public static void main(String[] args) {
		
		int n1=33, n2=10;
		int result; double resultDouble;
		
		result = n1 + n2;
		System.out.printf("%d %c %d = %d%n",n1,'+',n2,result);
		
		result = n1 / n2;
		System.out.printf("%d %c %d = %d%n",n1,'/',n2,result); //몫
		
		result = n1 % n2;
		System.out.printf("%d %c %d = %d%n",n1,'%',n2,result); //나머지
		// 나머지 연산자의 용도 : 짝홀수 판별, 배수 판별 ....
		
		if(n1%2 == 0) {
			System.out.println("n1은 짝수");
		}else {
			System.out.println("n1은 홀수");
		}
		
	}
}
//if(n1%2 == 1) 이렇게 홀수를 구하는건 옳지 않다. 양수의 홀수면 상관없으나 음수의 홀수면 남는 값이 -1 이기때문에
// 홀수여도 false 가 된다. if(n1%2 == 1 || n1%2 ==-1) 이렇게 두가지로 구하거나  짝수를 구해서 else로 확인하는게 낫다.
