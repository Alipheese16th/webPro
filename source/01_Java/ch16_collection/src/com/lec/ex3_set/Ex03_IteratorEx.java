package com.lec.ex3_set;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.lec.ex1_list.Friend;

public class Ex03_IteratorEx {

	public static void main(String[] args) {
		
		//1. List �迭
		ArrayList<String> list = new ArrayList<String>();
		list.add("SAMPLE1"); list.add("SAMPLE2");
		Iterator<String> iterator = list.iterator();
		
		while(iterator.hasNext()) {
			System.out.print(iterator.next()+"\t");
		}
		System.out.println();
		
		for(int i=0;i<list.size();i++) {
			System.out.print(list.get(i)+"\t");
		}
		System.out.println();
		for(String temp : list) {
			System.out.print(temp+"\t");
		}
		
		//2. Map �迭
		System.out.println("\n = = = 2. Map �迭 = = =");
		HashMap<String,Friend> map = new HashMap<String,Friend>();
		
		map.put("ȫ�浿", new Friend("ȫ�浿","010-9999-9999"));
		map.put("������", new Friend("������","010-8888-8888",new Date(new GregorianCalendar(1995, 0, 1).getTimeInMillis())));
		map.put("�ű浿", new Friend("�ű浿","010-7777-9999"));
		Iterator<String> iterator2 = map.keySet().iterator();
		
		while(iterator2.hasNext()) {
			String key = iterator2.next();
			System.out.println(key+" : "+map.get(key));
		}
		
		System.out.println("= = = 3. Set �迭 = = =");
		HashSet<Friend> set = new HashSet<Friend>();
		
		set.add(new Friend("ȫ�浿","02-717-7777"));
		set.add(new Friend("ȫ�浿","02-717-7777"));	//hashcode�� equals�� �������̵� ���������� �ߺ��� �ƴϰԵȴ� 
		set.add(new Friend("�ű浿","02-717-8888"));
		set.add(new Friend("�̼���","010-7917-8888"));
		Iterator<Friend> iterator3 = set.iterator();
		
		while(iterator3.hasNext()) {
			System.out.println(iterator3.next());
		}
		
		
		
	}

}
