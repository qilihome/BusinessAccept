package com.example.businessaccept.ui.vo;

import com.example.businessaccept.entity.BusinessInfo;

public class BusinessInfoVo extends BusinessInfo
{
	private String BusinessTypeName;
	/*操作人name*/
	private String name;

	public String getBusinessTypeName()
	{
		return BusinessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName)
	{
		BusinessTypeName = businessTypeName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	
}
