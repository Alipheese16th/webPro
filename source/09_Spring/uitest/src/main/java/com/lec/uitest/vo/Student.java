package com.lec.uitest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	private String name;
	private int kor;
	private int eng;
	private int mat;
	public int total() {
		return kor + eng + mat;
	}
	public double avg() {
		return total()/3.0;
	}
	
}
