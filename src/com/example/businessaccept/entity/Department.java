package com.example.businessaccept.entity;

import java.util.Date;

/**
 * 部门 
 * @author liqi
 * phone 18771970972
 * qq 197399622
 *
 */
public class Department
{
	/*部门编号*/
	private int departID;
	/*部门名称*/
	private String departName;
	/*建档人ID*/
	private int createID;
	/*建档时间*/
	private Date createTime;
	/*状态标志*/
	private int status;
	/*部门序列*/
	private int departNO;
	public int getDepartID()
	{
		return departID;
	}
	public void setDepartID(int departID)
	{
		this.departID = departID;
	}
	public String getDepartName()
	{
		return departName;
	}
	public void setDepartName(String departName)
	{
		this.departName = departName;
	}
	public int getCreateID()
	{
		return createID;
	}
	public void setCreateID(int createID)
	{
		this.createID = createID;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public int getDepartNO()
	{
		return departNO;
	}
	public void setDepartNO(int departNO)
	{
		this.departNO = departNO;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return departName;
	}
}
