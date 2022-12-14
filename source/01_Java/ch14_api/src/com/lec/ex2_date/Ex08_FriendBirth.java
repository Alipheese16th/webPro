package com.lec.ex2_date;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lec.ex4_object.Friend;

// ���� ������ ģ���� ���
public class Ex08_FriendBirth {
	public static void main(String[] args) {
		
		Friend[] friends = {
				new Friend("ȫ�浿","010-9999-9999","12-14","���� ���빮"),
				new Friend("��浿","010-8888-9999","12-14","���� ����"),
				new Friend("�ű浿","010-9999-7777","02-10","���� ���"),
				new Friend("���浿","010-6666-6666","03-03","��õ �۵�"),
				};
		// �ý������κ��� ������ ������ �޾ƿͼ� "��-��"������ ��Ʈ������ ����� 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String today = sdf.format(date);
		
		boolean none = true;	//�ѹ��̶� �˻��Ȱ� �ִ��� Ȯ���ϴ� �Ҹ��𺯼�
		System.out.println("���� ������ ģ�� ����� �˻��մϴ�");
		
		for(Friend friend : friends) {
			if(today.equals(friend.getBirth())) {
				System.out.println(friend);
				none = false;
			}
		}
		
//		for (int i = 0; i < friends.length; i++) {
//			String birth = friends[i].getBirth();
//			if(today.equals(birth)) {
//				System.out.println(friends[i]);
//				none = false; //�Ѹ��̻� ��½� none�� false
//			}//if
//		}//for
		
		if(none) {
			System.out.println("���� ������ ģ���� �����ϴ�");
		}
		
	}//main
}//class
