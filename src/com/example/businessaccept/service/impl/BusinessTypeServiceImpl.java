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

import com.example.businessaccept.entity.BusinessType;
import com.example.businessaccept.service.IBusinessTypeService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.ui.MainActivity;
import com.example.businessaccept.util.Constant;
import com.example.businessaccept.util.JsonHepler;

public class BusinessTypeServiceImpl implements IBusinessTypeService
{
	
	@Override
	public List<BusinessType> list() throws Exception
	{
		List<BusinessType> list = null;
		// get
		HttpClient client = new DefaultHttpClient();
		/**
		 * 真机与wifi在同一个网段
		 */
		String uri = "http://192.168.3.3:8080/ba/businesstypeAction_list.action";
		HttpGet get = new HttpGet(uri);
		// 响应
		HttpResponse response = client.execute(get);

		int status = response.getStatusLine().getStatusCode();
		if (status != HttpStatus.SC_OK)
		{
			throw new ServiceRulesException(
					String.format(Constant.REQUEST_ERROR, "查询业务类型", status));
		}

		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		if (result != null && !"null".equals(result) && "" != result)
		{
			list = (List<BusinessType>)JsonHepler.parseCollection(result, ArrayList.class, BusinessType.class);
		}
		else
		{
			throw new ServiceRulesException(String.format(Constant.RESPOSE_ERROR, "查询业务类型"));
		}
		return list;
	}

}
