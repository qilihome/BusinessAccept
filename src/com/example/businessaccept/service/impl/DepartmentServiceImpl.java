package com.example.businessaccept.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.businessaccept.entity.Department;
import com.example.businessaccept.service.IDepartmentService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.util.Constant;
import com.example.businessaccept.util.JsonHepler;

public class DepartmentServiceImpl implements IDepartmentService
{

	@Override
	public List<Department> list() throws Exception
	{
		List<Department> list = null;
		// get
		HttpClient client = new DefaultHttpClient();
		/**
		 * 真机与wifi在同一个网段
		 */
		String uri = "http://10.0.2.2:8080/ba/departmentAction_list.action";
		HttpGet get = new HttpGet(uri);
		// 响应
		HttpResponse response = client.execute(get);

		int status = response.getStatusLine().getStatusCode();
		if (status != HttpStatus.SC_OK)
		{
			throw new ServiceRulesException(
					String.format(Constant.REQUEST_ERROR, "查询部门信息", status));
		}

		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		if (null != result && !"".equals(result))
		{
			list = (List<Department>)JsonHepler.parseCollection(result, ArrayList.class, Department.class);
		}
		else
		{
			throw new ServiceRulesException(String.format(Constant.RESPOSE_ERROR, "查询部门信息"));
		}
		return list;
		
	}

}
