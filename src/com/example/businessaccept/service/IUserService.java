package com.example.businessaccept.service;

import java.util.List;

import com.example.businessaccept.entity.Admin;

public interface IUserService
{
	public void userLogin(String loginName, String loginPassword)
			throws Exception;
	
	public List<Admin> queryByDeptId(int deptId) throws Exception;
}
