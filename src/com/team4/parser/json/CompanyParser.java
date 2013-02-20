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

		if (json.has("contact_person")) {
			obj.setCompanyName(json.getString("contact_person"));
		}
		
		if (json.has("contact_person")) {
			obj.setContactName(json.getString("contact_person"));
		}
		
		if (json.has("telephone")) {
			obj.setPhoneNumber(json.getString("telephone"));
		}
		
		if (json.has("email")) {
			obj.setMail(json.getString("email"));
		}	
		
		if (json.has("address")) {
			obj.setAddress(json.getString("address"));
		}
		
		if (json.has("company_type")) {
			obj.setCompanyType(json.getString("company_type"));
		}

		if (json.has("capital_source")) {
			obj.setCapitalSource(json.getString("capital_source"));
		}
		
		if (json.has("rate")) {
			obj.setRating(json.getString("rate"));
		}
		
		if (json.has("areas")) {
			obj.setFlowArea(json.getString("areas"));
		}
		
		if (json.has("info_source")) {
			obj.setSource(json.getString("info_source"));
		}
		
		if (json.has("industry")) {
			obj.setInvestmentIndustry(json.getString("industry"));
		}
		
		if (json.has("has_commission")) {
			obj.setHaveCommission(json.getString("has_commission"));
		}
		
		if (json.has("commission_proportion")) {
			obj.setCommissionRatio(json.getString("commission_proportion"));
		}
		
		if (json.has("create_at")) {
			obj.setCreateAt(json.getString("create_at"));
		}
		
		if (json.has("modify_at")) {
			obj.setModifyAt(json.getString("modify_at"));
		}
		
		return obj;    	
    }
}