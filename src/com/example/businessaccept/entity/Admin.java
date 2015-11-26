package com.example.businessaccept.entity;

/**
 * 操作员明细表tb_sys_ADMIN
 * 
 * @author liqi
 * phone 18771970972
 * qq 197399622
 *
 */
public class Admin
{
	/* id */
	private int id;
	/* 登录账号 */
	private String count;
	/* 操作员名 */
	private String name;
	/* 部门ID */
	private int deptId;
	/* 密码 */
	private String password;
//	/* 登陆IP */
//	private String loginIP;
//	/* 登陆时间 */
//	private Date loginTime;
//	/* 手机号 */
//	private String telephone;
//	/* email */
//	private String email;
//	/* 性别 */
//	private int sex;
//	/* 生日 */
//	private Date birthday;
//	/* 创建时间 */
//	private Date createTime;
//	/* 角色ID */
//	private int roleid;
//	/* 权限页IDS */
//	private String pageIds;
//	/* 系统管理员标识 */
//	private int adminTab;
//	/**
//	 * 状态标志 正常为1,删除为0
//	 */
//	private int accountState;
//	/* 管理等级 */
//	private String powerLeave;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getCount()
	{
		return count;
	}
	public void setCount(String count)
	{
		this.count = count;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getDeptId()
	{
		return deptId;
	}
	public void setDeptId(int deptId)
	{
		this.deptId = deptId;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return name;
	}
}
