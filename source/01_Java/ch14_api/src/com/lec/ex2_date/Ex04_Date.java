package com.lec.ex2_date;

import java.util.Date;

public class Ex04_Date {
	public static void main(String[] args) {
		
		Date now = new Date(); //2022�� - 1900��  : 122   //������ 1998���̸� 98 �̶�� �־���. 20���⿡ ��������⶧��
		
		System.out.println(now);
		
		int year = now.getYear() + 1900;
		int month = now.getMonth() + 1;
		int day = now.getDate();	//�� ���� �ʴ� ����̶� �̷��� ǥ���ȴ� 
		
		System.out.println(year+"�� "+month+"�� "+day+"�� ");
		
		System.out.printf("%1$tY�� %1$tm�� %1$td�� %1$ta���� %1$tH�� %1$tM�� %1$tS��(%1$tp %1$tl��)\n",now);
	}
}
