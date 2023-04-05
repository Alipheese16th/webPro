package com.lec.ch05.quiz;

import java.util.Scanner;

import org.springframework.context.support.GenericXmlApplicationContext;


public class MainClass {
	public static void main(String[] args) {
		
		String red      = "\u001B[31m" ;
		String yellow   = "\u001B[33m" ;
		String exit     = "\u001B[0m" ;
		
		String loc1 = "classpath:/META-INF/quiz/dev.xml";
		String loc2 = "classpath:/META-INF/quiz/run.xml";
		
		String config = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("DEV? RUN?");
		String answer = scanner.next();
		
		if(answer.equalsIgnoreCase("DEV")) {
			config = "dev";
			System.out.println(red);
		}else if(answer.equalsIgnoreCase("RUN")) {
			config = "run";
			System.out.println(yellow);
		}else {
			System.out.println("제대로 된 환경이 입력되지 않았습니다.");
			System.exit(0);
		}
		scanner.close();
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles(config);
		ctx.load(loc1,loc2);
		ctx.refresh();
		EnvInfo info = ctx.getBean("envInfo",EnvInfo.class);
		System.out.println("ipNum="+info.getIpNum());
		System.out.println("portNum="+info.getPortNum());
		System.out.println("userId="+info.getUserId());
		System.out.println("pw="+info.getUserPw());
		System.out.println(exit);
		
	}
}
