package com.example.businessaccept.service;

import java.util.List;

import com.example.businessaccept.entity.BusinessInfo;
import com.example.businessaccept.ui.vo.BusinessCondition;

public interface IBusinessInfoService
{
	BusinessInfo save(BusinessInfo businessInfo) throws Exception;
	
	List<BusinessInfo> query(BusinessCondition businessCondition) throws Exception;
}
