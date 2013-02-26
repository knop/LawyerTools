package com.team4.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import com.team4.exceptions.ErrorCode;
import com.team4.parser.json.CasesParser;
import com.team4.parser.json.CompaniesParser;
import com.team4.parser.json.ComunicationParser;
import com.team4.parser.json.FinancingsParser;
import com.team4.parser.json.InvestmentsParser;
import com.team4.parser.json.JsonParser;
import com.team4.parser.json.T4ListParser;
import com.team4.type.TComunicationEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.http.HttpUtility;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class HttpManager {

	private final static String host = "http://dulawyer.project.team4.us";
	private final static String userAgent = "Team4.US/Android";
	
	//API接口名称
	//获取基本信息API
	private final static String GET_INFOMATION = "/lawyertools/information/";	
	//获取联络信息API
	private final static String GET_COMUNICATION = "/lawyertools/communication/";
	//获取联络信息API
	private final static String GET_MATCH = "/lawyertools/match/";
	
	//接口类型名称
	public final static String TYPE_COMPANY = "company";	
	public final static String TYPE_FINANCING = "financing";
	public final static String TYPE_INVESTMENT = "investment";
	public final static String TYPE_CASE = "case";
	
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
	public IBaseType getInfomation(String type, String countPrePage, String pageNum) throws T4Exception {
		 
		List<BasicNameValuePair> params = getParamList(
				new BasicNameValuePair("record_perpage", countPrePage), 
				new BasicNameValuePair("page_number", pageNum));
		HttpGet get = HttpUtility.createHttpGet(fillUrl(GET_INFOMATION, type)+"/", userAgent, params);
		IJsonParser<IBaseType> parser = null;
		if (type.equalsIgnoreCase(HttpManager.TYPE_COMPANY)){
			parser = new CompaniesParser();
		} else if (type.equalsIgnoreCase(HttpManager.TYPE_CASE)){
			parser = new CasesParser();
		} else if (type.equalsIgnoreCase(HttpManager.TYPE_FINANCING)){
			parser = new FinancingsParser();
		} else if (type.equalsIgnoreCase(HttpManager.TYPE_INVESTMENT)){
			parser = new InvestmentsParser();
		} else {
			throw new T4Exception(ErrorCode.APP_ERROR_PARAM_INVALID, type+"不可用于该请求");
		}
		JsonParser jsonParser = new JsonParser(parser);
		return HttpUtility.executeHttpRequest(get, jsonParser);
	}
	
	@SuppressWarnings("unchecked")
	public T4List<TComunicationEntity> getComunication(String type, int id) throws T4Exception {
		 
		List<BasicNameValuePair> params = getParamList(
				new BasicNameValuePair("id", String.valueOf(id)));
		HttpGet get = HttpUtility.createHttpGet(fillUrl(GET_COMUNICATION, type), userAgent, params);
		T4ListParser listParser = new T4ListParser(new ComunicationParser());
		JsonParser jsonParser = new JsonParser(listParser);
		
		return (T4List<TComunicationEntity>)HttpUtility.executeHttpRequest(get, jsonParser);
	}
	
	public IBaseType getMatch(String type, int id) throws T4Exception {
		 
//		List<BasicNameValuePair> params = getParamList(
//				new BasicNameValuePair("id", String.valueOf(id)));
//		HttpGet get = HttpUtility.createHttpGet(fillUrl(GET_MATCH, type), userAgent, params);
//		
//		T4ListParser lParser = new T4ListParser(new ComunicationParser());
//		JsonParser parser = new JsonParser(lParser);
		
		return null;
	}
	
	//私有函数
	private String fillUrl(String api, String type) {
		return host + api + type;
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
