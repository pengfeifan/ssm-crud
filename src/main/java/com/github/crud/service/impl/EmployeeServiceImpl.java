package com.github.crud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.crud.bean.EmployeeModel;
import com.github.crud.dao.EmployeeModelMapper;
import com.github.crud.service.EmployeeService;
/**
 * 
 * @author williamfan
 * pengfei.fan@hotmail.com
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeModelMapper employeeModelMapper;
	@Override
	public List<EmployeeModel> getAll() {
		return employeeModelMapper.selectAllWithDept();
	}
	@Override
	public void saveEmp(EmployeeModel employee) {
		employeeModelMapper.insertSelective(employee);
	}
	/**
	 * 检验用户名是否可用
	 * true 可用
	 * false 不可用
	 */
	@Override
	public boolean checkName(String empName) {
		int count = employeeModelMapper.countByEmpName(empName);
		return count == 0;
	}
	@Override
	public EmployeeModel getEmp(Integer empId) {
		return employeeModelMapper.selectByPrimaryKey(empId);
	}
	@Override
	public void updateEmp(EmployeeModel employee) {
		employeeModelMapper.updateByPrimaryKeySelective(employee);
	}
	@Override
	public void delEmp(Integer id) {
		employeeModelMapper.deleteByPrimaryKey(id);
	}
	@Override
	public void batchDel(List<Integer> del_ids) {
		employeeModelMapper.batchDelEmps(del_ids);
	}

}
