package com.example.businessaccept.service;

import java.util.List;

import com.example.businessaccept.entity.BusinessInfo;
import com.example.businessaccept.ui.vo.BusinessCondition;
import com.example.businessaccept.ui.vo.BusinessInfoVo;

public interface IBusinessInfoService
{
	BusinessInfo save(BusinessInfo businessInfo) throws Exception;
	
	BusinessInfo update(BusinessInfo businessInfo) throws Exception;
	
	List<BusinessInfoVo> query(BusinessCondition businessCondition,int pageNo) throws Exception;
	
	List<BusinessInfoVo> queryToMyBusinessInfo(int operatorID) throws Exception;
	
	BusinessInfoVo queryById(int businessInfoId) throws Exception;
}
