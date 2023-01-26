package com.lec.ex1_string;

public class Ex03_regex {
	public static void main(String[] args) {
		
		/* 	����ǥ����(regex)
		 	1.	���� (https://goo.gl/HLntbd ��Ű���)
		 	
		  	2.	=	��������[a-zA-Z��-�R0-9]
		  	\d	=	���ڿ� ��ġ, [0-9]�� ����
		  	\D	=	���ڰ� �ƴ� ��
		  	\w	=	�����ڳ� ����, [a-zA-Z0-9]�� ����
		  	\W	=	�����ڳ� ���ڰ� �ƴ� ����
		  	\s	=	white space
		  	. 	=	����(\n�� ������ ����)
		  	\.	=	.
		  	{3}	=	3�� �ݺ�
		  	{2,4}	=	2~4ȸ �ݺ�
		  	+	=	1�� �̻� �ݺ�
		  	*	=	0�� �̻� �ٺ�
		  	?	=	0���̳� 1�� �ݺ�
		  	
		  	
		  	3.����ǥ���� ������ : https://regexpal.com
		  		ex1. ip�ּ� : 0.0.0.0 ~ 255.255.255.255
		  		=> ([0-9]{1,3}\.){3}[0-9]{1,3}
		  		ex2. ��ȭ��ȣ : 02 712 6100 / 010 9999 9999 / 010-0000-0000 / 010.010.0101 / 02)717.2323
		  		\d{2,3}.?\d{3,4}.?\d{4}
		  		ex3. �ֹε�Ϲ�ȣ : 981212-2054545 / 9812122054545
		  		\d{2}[0-1]\d[0-3]\d-?[1-4]\d{6}
		  		ex4. �̸��� : shwo@naver.com / sgsg@sec.co.kr
		  		\w+@\w+\.\w+(\.\w+)?
		  		\w+@\w+(\.\w+){1,2}
		  		
		  	4.�ҽ� ����
		 */
		String juminNo = "981212-2054545";
		//Ư�� ����ǥ������ ���ڿ� ����
		System.out.println("�ֹι�ȣ : "+juminNo.replaceAll("-[1-4]\\d{6}","-*******"));
		
		String str = "010-9999-9999 yisy0703@naver.com �ݰ����ϴ�. 981212-2054545��������";
		
		System.out.println("�̸����� ���� str : "+str.replaceAll("\\w+@\\w+(\\.\\w+){1,2}",""));
		
		System.out.println("��ȭ��ȣ ������ str : "+str.replaceAll("\\d{2,3}.?\\d{3,4}.?\\d{4}",""));
		
		System.out.println("���� ���ŵ� str : "+str.replaceAll("[��-��]", ""));
		
		System.out.println("�ѱ� ���ŵ� str : "+str.replaceAll("[��-����-�R]", ""));
		
	}
}
