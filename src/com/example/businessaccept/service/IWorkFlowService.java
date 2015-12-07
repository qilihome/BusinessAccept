package com.example.businessaccept.service;

import java.util.List;

import com.example.businessaccept.entity.WorkFlow;
import com.example.businessaccept.ui.vo.WorkFlowVo;

public interface IWorkFlowService
{
	WorkFlow saveOrUpdate(WorkFlow workFlow) throws Exception;
	
	List<WorkFlowVo> queryByBusinessInfoId(int businessInfoId) throws Exception;
}
