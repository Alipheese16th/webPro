package com.lec.ex3_set;

import java.util.HashSet;
import java.util.Iterator;

public class Ex01_HashSet {
	public static void main(String[] args) {
		
		HashSet<String> hashset = new HashSet<>();	//인덱스가 없다. 순서도 없다. 데이터만 넣는다. 중복은 허용안함
		
		hashset.add("str0");
		hashset.add("str1");
		hashset.add("str2");
		
		System.out.println(hashset);
		System.out.println("hashset 데이터 갯수 : "+hashset.size());
		
		hashset.add("str2");
		System.out.println("중복데이터 입력 후 : "+hashset);
		System.out.println("중복데이터 입력후 데이터 갯수 : "+hashset.size());
		
		Iterator<String> iterator = hashset.iterator();
		
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		
	}
	
}
