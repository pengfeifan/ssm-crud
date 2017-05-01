package com.github.crud.dao;

import java.util.List;

import com.github.crud.bean.EmployeeModel;

public interface EmployeeModelMapper {
    int deleteByPrimaryKey(Integer empId);

    int insert(EmployeeModel record);

    int insertSelective(EmployeeModel record);

    EmployeeModel selectByPrimaryKey(Integer empId);

    int updateByPrimaryKeySelective(EmployeeModel record);

    int updateByPrimaryKey(EmployeeModel record);
    
    EmployeeModel selectByPrimaryKeyWithDept(Integer empId);
    
    List<EmployeeModel> selectAllWithDept();
    
    Integer countByEmpName(String empName);

	void batchDelEmps(List<Integer> del_ids);
}