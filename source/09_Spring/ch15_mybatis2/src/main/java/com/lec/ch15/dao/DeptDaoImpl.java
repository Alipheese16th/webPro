package com.lec.ch15.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lec.ch15.model.Dept;

@Repository
public class DeptDaoImpl implements DeptDao {
	
	@Autowired
	private SqlSession sessionTemplate; 
// org.mybatis.spring.SqlSessionTemplate 타입으로 해도 되지만 
// SqlSession 인터페이스를 구현하고있기때문에 해당 인터페이스 타입으로 가능
	
	@Override
	public List<Dept> deptList() {
		return sessionTemplate.selectList("deptList");
	}

}
