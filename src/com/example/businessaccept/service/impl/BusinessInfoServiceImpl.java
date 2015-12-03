package com.example.businessaccept.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.businessaccept.entity.BusinessInfo;
import com.example.businessaccept.entity.BusinessType;
import com.example.businessaccept.service.IBusinessInfoService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.ui.vo.BusinessCondition;
import com.example.businessaccept.util.Constant;
import com.example.businessaccept.util.JsonHepler;

public class BusinessInfoServiceImpl implements IBusinessInfoService
{
	@Override
	public BusinessInfo save(BusinessInfo businessInfo) throws Exception
	{
		businessInfo.setCreateTime(new Date());
		businessInfo.setOverTime(new Date());
		
		// post
		String uri = "http://10.0.2.2:8080/ba/businessinfoAction_save.action";

		// 参数设置
		HttpParams params = new BasicHttpParams();
		// 通过params设置请求的字符集
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		// 设置客户端与服务端连接的超时时间（还没有连接到服务器） ConnectTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		// 设置服务器的响应时间（已经连接到服务器了，对话后的响应时间） SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);

		// 设置https与http都可以访问
		SchemeRegistry sr = new SchemeRegistry();
		sr.register(new Scheme("https", PlainSocketFactory.getSocketFactory(),
				433));
		sr.register(
				new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager conn = new ThreadSafeClientConnManager(params,
				sr);

		HttpClient client = new DefaultHttpClient(conn, params);
		HttpPost post = new HttpPost(uri);
		NameValuePair businessInfoJson = new BasicNameValuePair("businessInfo",
				JsonHepler.toJSON(businessInfo));
		List<NameValuePair> paramters = new ArrayList<NameValuePair>();
		paramters.add(businessInfoJson);
		post.setEntity(new UrlEncodedFormEntity(paramters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);

		int status = response.getStatusLine().getStatusCode();
		if (status != HttpStatus.SC_OK)
		{
			throw new ServiceRulesException(
					String.format(Constant.REQUEST_ERROR, "保存业务信息", status));
		}

		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		if (null != result && !"".equals(result))
		{
			businessInfo.setBusinessID(Integer.valueOf(result));
			return businessInfo;
		}
		else
		{
			throw new ServiceRulesException(String.format(Constant.RESPOSE_ERROR, "保存业务"));
		}
	}

	@Override
	public List<BusinessInfo> query(BusinessCondition businessCondition)
			throws Exception
	{
		List<BusinessInfo> list = null;
		// post
				String uri = "http://10.0.2.2:8080/ba/businessinfoAction_query.action";

				// 参数设置
				HttpParams params = new BasicHttpParams();
				// 通过params设置请求的字符集
				HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
				// 设置客户端与服务端连接的超时时间（还没有连接到服务器） ConnectTimeoutException
				HttpConnectionParams.setConnectionTimeout(params, 3000);
				// 设置服务器的响应时间（已经连接到服务器了，对话后的响应时间） SocketTimeoutException
				HttpConnectionParams.setSoTimeout(params, 3000);

				// 设置https与http都可以访问
				SchemeRegistry sr = new SchemeRegistry();
				sr.register(new Scheme("https", PlainSocketFactory.getSocketFactory(),
						433));
				sr.register(
						new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				ClientConnectionManager conn = new ThreadSafeClientConnManager(params,
						sr);

				HttpClient client = new DefaultHttpClient(conn, params);
				HttpPost post = new HttpPost(uri);
				NameValuePair businessInfoJson = new BasicNameValuePair("businessCondition",
						JsonHepler.toJSON(businessCondition));
				List<NameValuePair> paramters = new ArrayList<NameValuePair>();
				paramters.add(businessInfoJson);
				post.setEntity(new UrlEncodedFormEntity(paramters, HTTP.UTF_8));
				HttpResponse response = client.execute(post);

				int status = response.getStatusLine().getStatusCode();
				if (status != HttpStatus.SC_OK)
				{
					throw new ServiceRulesException(
							String.format(Constant.REQUEST_ERROR, "查询业务信息", status));
				}

				String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

				if (result != null && !"null".equals(result) && "" != result)
				{
					list = (List<BusinessInfo>)JsonHepler.parseCollection(result, ArrayList.class, BusinessInfo.class);
				}
				else
				{
					throw new ServiceRulesException(String.format(Constant.RESPOSE_ERROR, "查询业务信息"));
				}
				return list;
	}

}
