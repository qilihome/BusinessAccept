package com.example.businessaccept.entity;

import java.util.Date;

/**
 * 业务信息表tb_fun_Business_Info
 * 
 * @author liqi
 * phone 18771970972
 * qq 19739962
 *
 */
public class BusinessInfo
{
	/*业务ID*/
	private int businessID;
	/*立户编号*/
	private String meterCode;
	/*户主名*/
	private String customerName;
	/*地址*/
	private String address;
	/*电话*/
	private String telephone;
	/*业务内容*/
	private String businessContent;
	/*业务创建人ID*/
	private int operatorID;
	/*业务创建时间*/
	private Date createTime;
	/*结束或注销时间*/
	private Date overTime;
	
	private int departID;
	/**
	 * 状态标志
	 * 0：新建；1：流转；2：注销(逻辑删除)；3：结束
	 * */
	private int status = 0;
	/*业务类别ID*/
	private int businessTypeID;
	/*业务编号*/
	private String businessNo;
	public int getBusinessID()
	{
		return businessID;
	}
	public void setBusinessID(int businessID)
	{
		this.businessID = businessID;
	}
	public String getMeterCode()
	{
		return meterCode;
	}
	public void setMeterCode(String meterCode)
	{
		this.meterCode = meterCode;
	}
	public String getCustomerName()
	{
		return customerName;
	}
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getTelephone()
	{
		return telephone;
	}
	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}
	public String getBusinessContent()
	{
		return businessContent;
	}
	public void setBusinessContent(String businessContent)
	{
		this.businessContent = businessContent;
	}
	public int getOperatorID()
	{
		return operatorID;
	}
	public void setOperatorID(int operatorID)
	{
		this.operatorID = operatorID;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public Date getOverTime()
	{
		return overTime;
	}
	public void setOverTime(Date overTime)
	{
		this.overTime = overTime;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public int getBusinessTypeID()
	{
		return businessTypeID;
	}
	public void setBusinessTypeID(int businessTypeID)
	{
		this.businessTypeID = businessTypeID;
	}
	public String getBusinessNo()
	{
		return businessNo;
	}
	public void setBusinessNo(String businessNo)
	{
		this.businessNo = businessNo;
	}
	public int getDepartID() {
		return departID;
	}
	public void setDepartID(int departID) {
		this.departID = departID;
	}
	
}
