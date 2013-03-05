package com.team4.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.team4.exceptions.ErrorCode;
import com.team4.parser.json.CaseParser;
import com.team4.parser.json.CompanyParser;
import com.team4.parser.json.ComunicationParser;
import com.team4.parser.json.FinancingParser;
import com.team4.parser.json.InfomationsParser;
import com.team4.parser.json.InvestmentParser;
import com.team4.parser.json.JsonParser;
import com.team4.parser.json.MatchParser;
import com.team4.parser.json.T4ListParser;
import com.team4.type.TComunicationEntity;
import com.team4.type.TInfomationsEntity;
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
	public TInfomationsEntity getInfomation(Context context, String type, String countPrePage, String pageNum) throws T4Exception { 
		List<BasicNameValuePair> params = getParamList(
				new BasicNameValuePair("record_perpage", countPrePage), 
				new BasicNameValuePair("page_number", pageNum));
		HttpGet get = HttpUtility.createHttpGet(fillUrl(GET_INFOMATION, type)+"/", userAgent, params);
		IJsonParser<IBaseType> subParser = null;
		if (type.equalsIgnoreCase(HttpManager.TYPE_COMPANY)){
			subParser = new CompanyParser();
		} else if (type.equalsIgnoreCase(HttpManager.TYPE_CASE)){
			subParser = new CaseParser();
		} else if (type.equalsIgnoreCase(HttpManager.TYPE_FINANCING)){
			subParser = new FinancingParser();
		} else if (type.equalsIgnoreCase(HttpManager.TYPE_INVESTMENT)){
			subParser = new InvestmentParser();
		} else {
			throw new T4Exception(ErrorCode.APP_ERROR_PARAM_INVALID, type+"不可用于该请求");
		}
		InfomationsParser parser = new InfomationsParser(subParser);
		JsonParser jsonParser = new JsonParser(parser);		
		return (TInfomationsEntity)HttpUtility.executeHttpRequest(context, get, jsonParser);
	}
	
	@SuppressWarnings("unchecked")
	public T4List<TComunicationEntity> getComunication(Context context, String type, int id) throws T4Exception {
		 
		List<BasicNameValuePair> params = getParamList(
				new BasicNameValuePair("id", String.valueOf(id)));
		HttpGet get = HttpUtility.createHttpGet(fillUrl(GET_COMUNICATION, type), userAgent, params);
		T4ListParser listParser = new T4ListParser(new ComunicationParser());
		JsonParser jsonParser = new JsonParser(listParser);
		
		return (T4List<TComunicationEntity>)HttpUtility.executeHttpRequest(context, get, jsonParser);
	}
	
	public IBaseType getMatch(Context context, String type, int id) throws T4Exception {
		 
		List<BasicNameValuePair> params = getParamList(
				new BasicNameValuePair("id", String.valueOf(id)));
		HttpGet get = HttpUtility.createHttpGet(fillUrl(GET_MATCH, type), userAgent, params);
		IJsonParser<IBaseType> subParser = null;
		//如果是资金，则要匹配项目。
		//如果是项目，就需要匹配资金。
		//所以解析类类型是相反的。
		if (type.equalsIgnoreCase(HttpManager.TYPE_FINANCING)){
			subParser = new InvestmentParser();
		} else if (type.equalsIgnoreCase(HttpManager.TYPE_INVESTMENT)){
			subParser = new FinancingParser();
		} else {
			throw new T4Exception(ErrorCode.APP_ERROR_PARAM_INVALID, type+"不可用于该请求");
		}
		T4ListParser lParser = new T4ListParser(new MatchParser(subParser));
		JsonParser jsonParser = new JsonParser(lParser);
		
		return HttpUtility.executeHttpRequest(context, get, jsonParser);
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
