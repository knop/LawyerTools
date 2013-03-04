package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.type.TFinancingEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;

public class FinancingParser implements IJsonParser<IBaseType> {

	public IBaseType parse(JSONObject json) throws T4Exception {
		try {
			TFinancingEntity obj = new TFinancingEntity();
			if (json.has("id")) {
				obj.setId(json.getInt("id"));
			}

			if (json.has("contact_person")) {
				obj.setContactPerson(json.getString("contact_person"));
			}

			if (json.has("project_name")) {
				String projectName = json.getString("project_name");
				obj.setProjectName(projectName);
				obj.setName(projectName);
			}

			if (json.has("telephone")) {
				obj.setTelephone(json.getString("telephone"));
			}

			if (json.has("email")) {
				obj.setEmail(json.getString("email"));
			}

			if (json.has("area")) {
				obj.setArea(json.getString("area"));
			}

			if (json.has("company_type")) {
				obj.setCompanyType(json.getString("company_type"));
			}

			if (json.has("info_source")) {
				obj.setInfoSource(json.getString("info_source"));
			}

			if (json.has("rate")) {
				obj.setRate(json.getString("rate"));
			}

			if (json.has("guarantee_type")) {
				obj.setGuaranteeType(json.getString("guarantee_type"));
			}

			if (json.has("company_name")) {
				obj.setCompanyName(json.getString("company_name"));
			}

			if (json.has("industry")) {
				obj.setIndustry(json.getString("industry"));
			}

			if (json.has("has_commission")) {
				obj.setHasCommission(json.getBoolean("has_commission"));
			}

			if (json.has("commission_proportion")) {
				obj.setCommissionProportion(json.getString("commission_proportion"));
			}

			if (json.has("create_at")) {
				obj.setCreateAt(json.getString("create_at"));
			}

			if (json.has("modify_at")) {
				obj.setModifyAt(json.getString("modify_at"));
			}
			
			if (json.has("comment")) {
				obj.setComment(json.getString("comment"));
			}

			if (json.has("style")) {
				obj.setStyle(json.getString("style"));
			}

			if (json.has("quota_floor")) {
				obj.setQuotaFloor(json.getString("quota_floor"));
			}

			if (json.has("horizon_upper")) {
				obj.setHorizonUpper(json.getString("horizon_upper"));
			}

			if (json.has("quota_upper")) {
				obj.setQuotaUpper(json.getString("quota_upper"));
			}

			if (json.has("payment")) {
				obj.setPayment(json.getString("payment"));
			}

			if (json.has("is_out")) {
				obj.setIsOut(json.getString("is_out"));
			}

			if (json.has("horizon_floor")) {
				obj.setHorizonFloor(json.getString("horizon_floor"));
			}
			
			if (json.has("expiry_date")) {
				obj.setExpiryDate(json.getString("expiry_date"));
			}
			
			return obj;
		} catch (JSONException e) {
			throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "JSON格式错误");
		}
	}

	@Override
	public IBaseType parse(JSONArray array) throws T4Exception {
		// TODO Auto-generated method stub
		return null;
	}
}