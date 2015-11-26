package com.example.businessaccept.service;

import java.util.List;

import com.example.businessaccept.entity.BusinessType;

public interface IBusinessTypeService
{
	List<BusinessType> list() throws Exception;
}
