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
		
		//1. List 계열
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
		
		//2. Map 계열
		System.out.println("\n = = = 2. Map 계열 = = =");
		HashMap<String,Friend> map = new HashMap<String,Friend>();
		
		map.put("홍길동", new Friend("홍길동","010-9999-9999"));
		map.put("성춘향", new Friend("성춘향","010-8888-8888",new Date(new GregorianCalendar(1995, 0, 1).getTimeInMillis())));
		map.put("신길동", new Friend("신길동","010-7777-9999"));
		Iterator<String> iterator2 = map.keySet().iterator();
		
		while(iterator2.hasNext()) {
			String key = iterator2.next();
			System.out.println(key+" : "+map.get(key));
		}
		
		System.out.println("= = = 3. Set 계열 = = =");
		HashSet<Friend> set = new HashSet<Friend>();
		
		set.add(new Friend("홍길동","02-717-7777"));
		set.add(new Friend("홍길동","02-717-7777"));	//hashcode와 equals를 오버라이드 하지않으면 중복이 아니게된다 
		set.add(new Friend("신길동","02-717-8888"));
		set.add(new Friend("이순신","010-7917-8888"));
		Iterator<Friend> iterator3 = set.iterator();
		
		while(iterator3.hasNext()) {
			System.out.println(iterator3.next());
		}
		
		
		
	}

}
