package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.type.TCompaniesEntity;
import com.team4.type.TCompanyEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class CompaniesParser implements IJsonParser<IBaseType> {

	@SuppressWarnings("unchecked")
	@Override
	public TCompaniesEntity parse(JSONObject json) throws T4Exception {
		// TODO Auto-generated method stub
		try {
			TCompaniesEntity obj = new TCompaniesEntity();
			if (json.has("total_count")) {
				obj.setTotalCount(json.getInt("total_count"));
			}
			
			if (json.has("records")) {
				T4ListParser arrayParser = new T4ListParser(new CompanyParser());
				obj.setRecords(((T4List<TCompanyEntity>)arrayParser.parse(json.getJSONArray("records"))));
			}
		
			return obj;
		} catch (Exception e) {
			throw new T4Exception(ErrorCode.JSON_FORMAT_INVALID, "JSON格式错误");
		}
	}

	@Override
	public TCompaniesEntity parse(JSONArray array) throws T4Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
