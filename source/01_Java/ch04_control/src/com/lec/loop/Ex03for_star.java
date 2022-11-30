package com.lec.loop;
/*
 * *
 * * *
 * * * *
 * * * * */
public class Ex03for_star {
	public static void main(String[] args) {
		for(int i=1;i<6;i++) {
			for(int j=1;j<=i;j++) {  //i가 1일때는j가 1번	i가2일때는j가2번반복
				System.out.print('*');
			}
			System.out.println();//개행을 위해 넣는다
		}
		
	}
}
