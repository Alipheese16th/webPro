package com.lec.ch03.method2;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lec.ch03.Student;
import com.lec.ch03.StudentInfo;

@Configuration
public class Method2_ApplicationConfig {
	@Bean
	public Student student1() {
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("태권도"); hobbies.add("마라톤");
		Student student = new Student("홍길동",15,hobbies);
		student.setHeight(172.1);
		student.setWeight(64);
		return student;
	}
	@Bean
	public StudentInfo studentInfo() {
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setStudent(student1());
		return studentInfo;
	}
}
