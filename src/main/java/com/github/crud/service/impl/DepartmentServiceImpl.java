package com.github.crud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.crud.bean.DepartmentModel;
import com.github.crud.dao.DepartmentModelMapper;
import com.github.crud.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	DepartmentModelMapper departmentModelMapper;
	@Override
	public List<DepartmentModel> getDepts() {
		return departmentModelMapper.selectAllDepts();
	}

}
