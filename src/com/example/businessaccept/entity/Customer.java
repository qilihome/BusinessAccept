package com.example.businessaccept.entity;

public class Customer
{
	/* 自动表号 */
	private String autoNo;
	/* 立户编号 */
	private String customerNo;
	/* 户主名 */
	private String customerName;
	/* 地址 */
	private String address;
	/* 电话 */
	private String phone;
	public String getAutoNo()
	{
		return autoNo;
	}
	public void setAutoNo(String autoNo)
	{
		this.autoNo = autoNo;
	}
	public String getCustomerNo()
	{
		return customerNo;
	}
	public void setCustomerNo(String customerNo)
	{
		this.customerNo = customerNo;
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
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return customerName;
	}
}
