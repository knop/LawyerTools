package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.type.TInfomationsEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;

/**
 *  @Project       : LawyerTools
 *  @Program Name  : com.team4.parser.json.InfomationsParser.java
 *  @Class Name    : InfomationsParser
 *  @Description   : 信息通用解析类
 *  @Author        : Xiaohui Chen
 *  @Creation Date : 2013-3-1 下午1:40:13 
 *  @ModificationHistory  
 *  Who            When          What 
 *  ------------   -----------   ------------------------------------
 *  Xiaohui Chen   2013-3-1       Create
 *
 */
public class InfomationsParser implements IJsonParser<IBaseType> {

	private IJsonParser<IBaseType> mSubParser;
	
	public InfomationsParser(IJsonParser<IBaseType> subParser) {
		mSubParser = subParser;
	}
	
	@Override
	public TInfomationsEntity parse(JSONObject json) throws T4Exception {
		try {
			TInfomationsEntity obj = new TInfomationsEntity();
			if (json.has("total_count")) {
				obj.setTotalCount(json.getInt("total_count"));
			}
			
			if (json.has("start_index")) {
				obj.setBeginIndex(json.getInt("start_index"));
			}
			
			if (json.has("end_index")) {
				obj.setEndIndex(json.getInt("end_index"));
			}
			
			if (json.has("records")) {
				T4ListParser arrayParser = new T4ListParser(mSubParser);
				obj.setRecords(arrayParser.parse(json.getJSONArray("records")));
			}
		
			return obj;
		} catch (Exception e) {
			throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "JSON格式错误");
		}
	}

	@Override
	public IBaseType parse(JSONArray array) throws T4Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
