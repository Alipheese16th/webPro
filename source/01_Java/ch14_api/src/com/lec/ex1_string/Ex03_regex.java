package com.lec.ex1_string;

public class Ex03_regex {
	public static void main(String[] args) {
		
		/* 	Á¤±ÔÇ¥Çö½Ä(regex)
		 	1.	ÂüÁ¶ (https://goo.gl/HLntbd À§Å°¹é°ú)
		 	
		  	2.	=	°£·«¹®¹ı[a-zA-Z°¡-ÆR0-9]
		  	\d	=	¼ıÀÚ¿Í ¸ÅÄ¡, [0-9]¿Í µ¿ÀÏ
		  	\D	=	¼ıÀÚ°¡ ¾Æ´Ñ °Í
		  	\w	=	¿µ¹®ÀÚ³ª ¼ıÀÚ, [a-zA-Z0-9]¿Í µ¿ÀÏ
		  	\W	=	¿µ¹®ÀÚ³ª ¼ıÀÚ°¡ ¾Æ´Ñ ¹®ÀÚ
		  	\s	=	white space
		  	. 	=	¹®ÀÚ(\nÀ» Á¦¿ÜÇÑ ¹®ÀÚ)
		  	\.	=	.
		  	{3}	=	3¹ø ¹İº¹
		  	{2,4}	=	2~4È¸ ¹İº¹
		  	+	=	1¹ø ÀÌ»ó ¹İº¹
		  	*	=	0¹ø ÀÌ»ó ¹Ùº¹
		  	?	=	0¹øÀÌ³ª 1¹ø ¹İº¹
		  	
		  	
		  	3.Á¤±ÔÇ¥Çö½Ä ¿¬½ÀÀå : https://regexpal.com
		  		ex1. ipÁÖ¼Ò : 0.0.0.0 ~ 255.255.255.255
		  		=> ([0-9]{1,3}\.){3}[0-9]{1,3}
		  		ex2. ÀüÈ­¹øÈ£ : 02 712 6100 / 010 9999 9999 / 010-0000-0000 / 010.010.0101 / 02)717.2323
		  		\d{2,3}.?\d{3,4}.?\d{4}
		  		ex3. ÁÖ¹Îµî·Ï¹øÈ£ : 981212-2054545 / 9812122054545
		  		\d{2}[0-1]\d[0-3]\d-?[1-4]\d{6}
		  		ex4. ÀÌ¸ŞÀÏ : shwo@naver.com / sgsg@sec.co.kr
		  		\w+@\w+\.\w+(\.\w+)?
		  		\w+@\w+(\.\w+){1,2}
		  		
		  	4.¼Ò½º ±¸Çö
		 */
		String juminNo = "981212-2054545";
		//Æ¯Á¤ Á¤±ÔÇ¥Çö½ÄÀÇ ¹®ÀÚ¿­ º¯°æ
		System.out.println("ÁÖ¹Î¹øÈ£ : "+juminNo.replaceAll("-[1-4]\\d{6}","-*******"));
		
		String str = "010-9999-9999 yisy0703@naver.com ¹İ°©½À´Ï´Ù. 981212-2054545¤»¤»¤¾¤¾";
		
		System.out.println("ÀÌ¸ŞÀÏÀ» Áö¿î str : "+str.replaceAll("\\w+@\\w+(\\.\\w+){1,2}",""));
		
		System.out.println("ÀüÈ­¹øÈ£ »èÁ¦µÈ str : "+str.replaceAll("\\d{2,3}.?\\d{3,4}.?\\d{4}",""));
		
		System.out.println("ÀÚÀ½ Á¦°ÅµÈ str : "+str.replaceAll("[¤¡-¤¾]", ""));
		
		System.out.println("ÇÑ±Û Á¦°ÅµÈ str : "+str.replaceAll("[¤¡-¤¾°¡-ÆR]", ""));
		
	}
}
