package com.example.businessaccept.service.impl;

import java.util.ArrayList;
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

import com.example.businessaccept.entity.WorkFlow;
import com.example.businessaccept.service.IWorkFlowService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.ui.vo.WorkFlowVo;
import com.example.businessaccept.util.Constant;
import com.example.businessaccept.util.JsonHepler;

public class WorkFlowServiceImpl implements IWorkFlowService
{

	@Override
	public WorkFlow saveOrUpdate(WorkFlow workFlow) throws Exception
	{
		// post
				String uri = "http://192.168.3.3:8080/ba/workflowAction_saveOrUpdate.action";

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
				NameValuePair businessInfoJson = new BasicNameValuePair("workflowStr",
						JsonHepler.toJSON(workFlow));
				List<NameValuePair> paramters = new ArrayList<NameValuePair>();
				paramters.add(businessInfoJson);
				post.setEntity(new UrlEncodedFormEntity(paramters, HTTP.UTF_8));
				HttpResponse response = client.execute(post);

				int status = response.getStatusLine().getStatusCode();
				if (status != HttpStatus.SC_OK)
				{
					throw new ServiceRulesException(
							String.format(Constant.REQUEST_ERROR, "保存或更新流程节点", status));
				}

				String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

				if (null != result && !"".equals(result))
				{
					workFlow.setWorkFlowId(Integer.valueOf(result));
					return workFlow;
				}
				else
				{
					throw new ServiceRulesException(String.format(Constant.RESPOSE_ERROR, "保存或更新流程节点"));
				}
	}

	@Override
	public List<WorkFlowVo> queryByBusinessInfoId(int businessInfoId)
			throws Exception
	{
		List<WorkFlowVo> list = null;
		// post
				String uri = "http://192.168.3.3:8080/ba/workflowAction_queryByBusinessInfoId.action";

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
				NameValuePair businessInfoJson = new BasicNameValuePair("businessInfoId",
						businessInfoId + "");
				List<NameValuePair> paramters = new ArrayList<NameValuePair>();
				paramters.add(businessInfoJson);
				post.setEntity(new UrlEncodedFormEntity(paramters, HTTP.UTF_8));
				HttpResponse response = client.execute(post);

				int status = response.getStatusLine().getStatusCode();
				if (status != HttpStatus.SC_OK)
				{
					throw new ServiceRulesException(
							String.format(Constant.REQUEST_ERROR, "查询工作流信息", status));
				}

				String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

				if (result != null && !"null".equals(result) && "" != result)
				{
					list = (List<WorkFlowVo>)JsonHepler.parseCollection(result, ArrayList.class, WorkFlowVo.class);
				}
				else
				{
					throw new ServiceRulesException(String.format(Constant.RESPOSE_ERROR, "查询工作流信息"));
				}
				return list;
	}

}
