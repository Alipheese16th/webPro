package com.lec.ex;

import java.util.Scanner;

//5! = 5*4*3*2*1
//s! = s*(s-1)*(s-2)...
//1! = 1 (factorial 계산은 양수만 입력하는 것으로 한다)
public class Ex05_factorial {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("몇 !을 계산할까요? ");
		int num = scanner.nextInt();
		long result = factorial(num);
		System.out.println(num+"!="+result);
		scanner.close();
	}
	private static long factorial(int num) {
		long result = 1;
		for(int i=num;i>0;i--) {
			result *= i;
		}
		return result;
	}
}
