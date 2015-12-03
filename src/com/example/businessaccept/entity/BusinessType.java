package com.example.businessaccept.entity;

import java.io.Serializable;

/**
 * 业务类别表tb_fun_BusinessType
 * 
 * @author liqi
 * phone 18771970972
 * qq 197399622
 *
 */
public class BusinessType implements Serializable
{
	/* 业务类别ID */
	private int businessTypeID;
	/* 业务类别名称 */
	private String businessTypeName;
	/**
	 * 状态标志 正常为1,删除为0
	 */
	private int status;

	public int getBusinessTypeID()
	{
		return businessTypeID;
	}

	public void setBusinessTypeID(int businessTypeID)
	{
		this.businessTypeID = businessTypeID;
	}

	public String getBusinessTypeName()
	{
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName)
	{
		this.businessTypeName = businessTypeName;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
	
	@Override
	public String toString()
	{
		return businessTypeName;
	}
}
