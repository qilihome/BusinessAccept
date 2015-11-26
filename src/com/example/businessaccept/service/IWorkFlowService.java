package com.example.businessaccept.service;

import com.example.businessaccept.entity.WorkFlow;

public interface IWorkFlowService
{
	WorkFlow saveOrUpdate(WorkFlow workFlow) throws Exception;
}
