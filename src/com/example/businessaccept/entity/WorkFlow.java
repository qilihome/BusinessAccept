package com.example.businessaccept.entity;

import java.util.Date;

/**
 * 流程节点表tb_fun_WorkFlow
 * 
 * @author liqi phone 18771970972 qq 197399622
 */
public class WorkFlow
{
	/* 流程节点ID */
	private int workFlowId;
	/* 业务ID */
	private int businessId;
	/* 上级流程节点ID */
	private int pWorkFlowId;
	/* 流程节点ID明细 */
	private String pWorkFlowIds;
	/* 流程节点受理人ID */
	private int operatorId;
	/* 流程节点受理人部门ID */
	private int departId;
	/* 流程节点创建时间 */
	private Date createTime;
	/* 流程节点受理意见 */
	private String dealContent;
	/* 流程节点受理时间 */
	private Date dealTime;
	/* 注销时间 */
	private Date overTime;
	/* 注销原因 */
	private String overContext;
	/**
	 * 状态标志 0：待处理；1：已处理；2：注销；
	 */
	private int status;
	public int getWorkFlowId()
	{
		return workFlowId;
	}
	public void setWorkFlowId(int workFlowId)
	{
		this.workFlowId = workFlowId;
	}
	public int getBusinessId()
	{
		return businessId;
	}
	public void setBusinessId(int businessId)
	{
		this.businessId = businessId;
	}
	public int getpWorkFlowId()
	{
		return pWorkFlowId;
	}
	public void setpWorkFlowId(int pWorkFlowId)
	{
		this.pWorkFlowId = pWorkFlowId;
	}
	public String getpWorkFlowIds()
	{
		return pWorkFlowIds;
	}
	public void setpWorkFlowIds(String pWorkFlowIds)
	{
		this.pWorkFlowIds = pWorkFlowIds;
	}
	public int getOperatorId()
	{
		return operatorId;
	}
	public void setOperatorId(int operatorId)
	{
		this.operatorId = operatorId;
	}
	public int getDepartId()
	{
		return departId;
	}
	public void setDepartId(int departId)
	{
		this.departId = departId;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public String getDealContent()
	{
		return dealContent;
	}
	public void setDealContent(String dealContent)
	{
		this.dealContent = dealContent;
	}
	public Date getDealTime()
	{
		return dealTime;
	}
	public void setDealTime(Date dealTime)
	{
		this.dealTime = dealTime;
	}
	public Date getOverTime()
	{
		return overTime;
	}
	public void setOverTime(Date overTime)
	{
		this.overTime = overTime;
	}
	public String getOverContext()
	{
		return overContext;
	}
	public void setOverContext(String overContext)
	{
		this.overContext = overContext;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	
}
