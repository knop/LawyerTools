package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.type.TCompaniesEntity;
import com.team4.type.TCompanyEntity;

public class CompaniesParser extends AbstractParser<TCompaniesEntity> {

	@Override
	public TCompaniesEntity parse(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		
		TCompaniesEntity obj = new TCompaniesEntity();
		if (json.has("total_count")) {
			obj.setTotalCount(json.getInt("total_count"));
		} else if (json.has("records")) {
			ArrayListParser<TCompanyEntity> arrayParser = new ArrayListParser<TCompanyEntity>(new CompanyParser());
			obj.setRecords(arrayParser.parse(json.getJSONArray("records")));
		}

		return obj;
	}

	@Override
	public TCompaniesEntity parse(JSONArray array) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
}
