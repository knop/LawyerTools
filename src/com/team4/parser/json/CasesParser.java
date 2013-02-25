package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.type.TCaseEntity;
import com.team4.type.TCasesEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class CasesParser implements IJsonParser<IBaseType> {

	@SuppressWarnings("unchecked")
	@Override
	public TCasesEntity parse(JSONObject json) throws T4Exception {
		// TODO Auto-generated method stub
		try {
			TCasesEntity obj = new TCasesEntity();
			if (json.has("total_count")) {
				obj.setTotalCount(json.getInt("total_count"));
			}
			
			if (json.has("records")) {
				T4ListParser arrayParser = new T4ListParser(new CaseParser());
				obj.setRecords(((T4List<TCaseEntity>)arrayParser.parse(json.getJSONArray("records"))));
			}
		
			return obj;
		} catch (Exception e) {
			throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "JSON格式错误");
		}
	}

	@Override
	public TCasesEntity parse(JSONArray array) throws T4Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
