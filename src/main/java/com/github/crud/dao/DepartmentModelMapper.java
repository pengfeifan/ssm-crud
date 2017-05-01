package com.github.crud.dao;

import java.util.List;

import com.github.crud.bean.DepartmentModel;

public interface DepartmentModelMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(DepartmentModel record);

    int insertSelective(DepartmentModel record);

    DepartmentModel selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(DepartmentModel record);

    int updateByPrimaryKey(DepartmentModel record);

	List<DepartmentModel> selectAllDepts();
}