package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.type.TInvestmentEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;

public class InvestmentParser implements IJsonParser<IBaseType> {

	public IBaseType parse(JSONObject json) throws T4Exception {
		try {
			TInvestmentEntity obj = new TInvestmentEntity();
			if (json.has("id")) {
				obj.setId(json.getInt("id"));
			}

			if (json.has("contact_person")) {
				obj.setContactPerson(json.getString("contact_person"));
			}

			if (json.has("telephone")) {
				obj.setTelephone(json.getString("telephone"));
			}

			if (json.has("email")) {
				obj.setEmail(json.getString("email"));
			}

			if (json.has("areas")) {
				obj.setAreas(json.getString("areas"));
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
				String name = json.getString("company_name");
				obj.setCompanyName(name);
				obj.setName(name);
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
				obj.setIsOut(json.getBoolean("is_out"));
			}

			if (json.has("horizon_floor")) {
				obj.setHorizonFloor(json.getString("horizon_floor"));
			}

			if (json.has("has_prior_cost" )) {
				obj.setHasPriorCost(json.getBoolean("has_prior_cost"));
			}

			if (json.has("profit_upper")) {
				obj.setProfitUpper(json.getString("profit_upper"));
			}

			if (json.has("profit_floor")) {
				obj.setProfitFloor(json.getString("profit_floor"));
			}

			if (json.has("capital_source")) {
				obj.setCapitalSource(json.getString("capital_source"));
			}

			if (json.has("examine_cycle_upper")) {
				obj.setExamineCycleUpper(json.getString("examine_cycle_upper"));
			}

			if (json.has("examine_cycle_floor")) {
				obj.setExamineCycleFloor(json.getString("examine_cycle_floor"));
			}

			if (json.has("prior_cost")) {
				obj.setPriorCost(json.getString("prior_cost"));
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