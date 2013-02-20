package com.team4.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import com.team4.parser.json.CompaniesParser;
import com.team4.parser.json.ComunicationParser;
import com.team4.parser.json.JsonParser;
import com.team4.parser.json.T4ListParser;
import com.team4.type.TCompaniesEntity;
import com.team4.type.TComunicationEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.http.HttpUtility;
import com.team4.utils.type.T4List;

public class HttpManager {

	private final static String host = "http://dulawyer.project.team4.us";
	private final static String userAgent = "Team4.US/Android";
	
	//API接口名称
	//获取基本信息API
	private final static String GET_COMPANY_LIST = "/lawyertools/information/company/";
	
	//获取联络信息API
	private final static String GET_COMPANY_COMUNICATION = "/lawyertools/comunication/company/";
	
	private static HttpManager sInstance;
	
	private HttpManager() {
		
	}
	
	public static HttpManager instance() {
		if (sInstance == null) {
			sInstance = new HttpManager();
		} 
		
		return sInstance;
	}
	
	//Http请求调用
	public TCompaniesEntity getCompanies(String countPrePage, String pageNum) throws T4Exception {
		 
		List<BasicNameValuePair> params = getParamList(
				new BasicNameValuePair("record_perpage", countPrePage), 
				new BasicNameValuePair("page_number", pageNum));
		HttpGet get = HttpUtility.createHttpGet(fillUrl(GET_COMPANY_LIST), userAgent, params);
		
		JsonParser parser = new JsonParser(new CompaniesParser());
		
		return (TCompaniesEntity)HttpUtility.executeHttpRequest(get, parser);
	}
	
	@SuppressWarnings("unchecked")
	public T4List<TComunicationEntity> getCompanyComunication(String id) throws T4Exception {
		 
		List<BasicNameValuePair> params = getParamList(
				new BasicNameValuePair("id", id));
		HttpGet get = HttpUtility.createHttpGet(fillUrl(GET_COMPANY_COMUNICATION), userAgent, params);
		
		T4ListParser lParser = new T4ListParser(new ComunicationParser());
		JsonParser parser = new JsonParser(lParser);
		
		return (T4List<TComunicationEntity>)HttpUtility.executeHttpRequest(get, parser);
	}
	
	//私有函数
	private String fillUrl(String api) {
		return host + api;
	}
	
	private List<BasicNameValuePair> getParamList(BasicNameValuePair... nameValuePairs) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		for (BasicNameValuePair obj : nameValuePairs) {
			if (obj.getValue() == null || obj.getValue().length() <= 0)
				continue;
			params.add(obj);
		}

		Collections.sort(params, new Comparator<BasicNameValuePair>() {

			public int compare(BasicNameValuePair lhs, BasicNameValuePair rhs) {
				return lhs.getName().compareTo(rhs.getName());
			}
		});
		return params;
	}
}
