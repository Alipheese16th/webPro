package com.lec.ex1_string;

import java.util.StringTokenizer;

public class Ex12_stringTokenArray {
	public static void main(String[] args) {
		
		String str = "�ں��� ���� ���� ��ҿ� �嵿�� ����";
		StringTokenizer tokenizer = new StringTokenizer(str);
		
		String[] arr = new String[tokenizer.countTokens()];
		
		int i=0;
		while(tokenizer.hasMoreTokens()) {
			arr[i++] = tokenizer.nextToken();
		}
//		for (int i = 0; i < arr.length; i++) {
//			arr[i] = tokenizer.nextToken();
//		}
		
		System.out.println("�迭�� ����� ������");
		for(String a : arr) {
			System.out.println(a);
		}
		
	}
}
