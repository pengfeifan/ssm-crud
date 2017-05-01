package com.github.crud.bean;

import java.io.Serializable;

public class DepartmentModel implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer deptId;

    private String deptName;

    public DepartmentModel() {
    	super();
	}
    public DepartmentModel(Integer deptId, String deptName) {
    	super();
		this.deptId = deptId;
		this.deptName = deptName;
	}

	public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }
}