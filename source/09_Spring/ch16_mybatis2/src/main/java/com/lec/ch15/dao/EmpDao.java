package com.lec.ch15.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lec.ch15.model.Emp;
@Mapper
public interface EmpDao {
	
	public List<Emp> empList(Emp emp);
	public List<Emp> empDeptList(Emp emp);
	public int totCnt();
	public Emp detail(int empno);
	public List<Emp> managerList();
	public int insert(Emp emp);
	public int update(Emp emp);
	public int delete(int empno);
	
}
