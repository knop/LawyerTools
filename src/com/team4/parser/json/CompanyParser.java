package com.team4.parser.json;

import org.json.JSONException;
import org.json.JSONObject;

import com.team4.type.TCompanyEntity;

public class CompanyParser extends AbstractParser<TCompanyEntity> {
    
    public TCompanyEntity parse(JSONObject json) throws JSONException {
    	TCompanyEntity obj = new TCompanyEntity();
		if (json.has("id")) {
			obj.setId(json.getInt("id"));
		}

		if (json.has("company_name")) {
			obj.setCompanyName(json.getString("company_name"));
		}
		
		if (json.has("contact_name")) {
			obj.setContactName(json.getString("contact_name"));
		}

		return obj;    	
    }
}