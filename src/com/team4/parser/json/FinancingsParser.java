package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.type.TFinancingEntity;
import com.team4.type.TFinancingsEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class FinancingsParser implements IJsonParser<IBaseType> {

	@SuppressWarnings("unchecked")
	@Override
	public TFinancingsEntity parse(JSONObject json) throws T4Exception {
		// TODO Auto-generated method stub
		try {
			TFinancingsEntity obj = new TFinancingsEntity();
			if (json.has("total_count")) {
				obj.setTotalCount(json.getInt("total_count"));
			}
			
			if (json.has("records")) {
				T4ListParser arrayParser = new T4ListParser(new FinancingParser());
				obj.setRecords(((T4List<TFinancingEntity>)arrayParser.parse(json.getJSONArray("records"))));
			}
		
			return obj;
		} catch (Exception e) {
			throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "JSON格式错误");
		}
	}

	@Override
	public TFinancingsEntity parse(JSONArray array) throws T4Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
