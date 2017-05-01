package com.github.crud.service;

import java.util.List;

import com.github.crud.bean.EmployeeModel;

public interface EmployeeService {

	List<EmployeeModel> getAll();

	void saveEmp(EmployeeModel employee);

	boolean checkName(String empName);

	EmployeeModel getEmp(Integer empId);

	void updateEmp(EmployeeModel employee);

	void delEmp(Integer id);

	void batchDel(List<Integer> del_ids);

}
