package com.lec.ex;

import java.util.Scanner;

//5! = 5*4*3*2*1
//s! = s*(s-1)*(s-2)...
//1! = 1 (factorial ����� ����� �Է��ϴ� ������ �Ѵ�)
public class Ex05_factorial {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("�� !�� ����ұ��? ");
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
