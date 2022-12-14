package com.lec.ex2_date;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.lec.ex4_object.Friend;

// ���� ������ ģ���� ���
public class Ex05_FriendBirth {
	public static void main(String[] args) {
		
		Friend[] friends = {
				new Friend("ȫ�浿","010-9999-9999","12-14","���� ���빮"),
				new Friend("��浿","010-8888-9999","12-26","���� ����"),
				new Friend("�ű浿","010-9999-7777","02-10","���� ���"),
				new Friend("���浿","010-6666-6666","03-03","��õ �۵�"),
				};
		
		GregorianCalendar now = new GregorianCalendar();
		
		int month = now.get(Calendar.MONTH)+1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		
		String monthStr = month<10 ? "0"+month : ""+month;
//		String monthStr = month<10 ? "0"+month : String.valueOf(month); //String.valueOf ��Ʈ������ ����ȯ  (�� �Ⱦ��� ǥ��)
		String dayStr = day<10 ? "0"+day : ""+day;
		String today = monthStr+"-"+dayStr;
		System.out.println(today);
		
		boolean none = true;	//�ѹ��̶� �˻��Ȱ� �ִ��� Ȯ���ϴ� �Ҹ��𺯼�
		System.out.println("���� ������ ģ�� ����� �˻��մϴ�");
		
		for (int i = 0; i < friends.length; i++) {
			String birth = friends[i].getBirth();
			if(today.equals(birth)) {
				System.out.println(friends[i]);
				none = false; //�Ѹ��̻� ��½� none�� false
			}//if
		}//for
		
		if(none) {
			System.out.println("���� ������ ģ���� �����ϴ�");
		}
		
	}//main
}//class
