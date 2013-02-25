package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.type.TCaseEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;

public class CaseParser implements IJsonParser<IBaseType> {

	public IBaseType parse(JSONObject json) throws T4Exception {
		try {
			TCaseEntity obj = new TCaseEntity();
			if (json.has("id")) {
				obj.setId(json.getInt("id"));
			}

			if (json.has("reply_limit")) {
				obj.setReplyLimit(json.getString("reply_limit"));
			}

			if (json.has("opposite_lawyer_address")) {
				obj.setOppositeLawyerAddress(json.getString("opposite_lawyer_address"));
			}

			if (json.has("opposite_lawyer_phone")) {
				obj.setOppositeLawyerPhone(json.getString("opposite_lawyer_phone"));
			}

			if (json.has("juger_phone")) {
				obj.setJugerPhone(json.getString("juger_phone"));
			}

			if (json.has("receive_notice_at")) {
				obj.setReceiveNoticeAt(json.getString("receive_notice_at"));
			}

			if (json.has("clerk_name")) {
				obj.setClerkName(json.getString("clerk_name"));
			}

			if (json.has("court_name")) {
				obj.setCourtName(json.getString("court_name"));
			}

			if (json.has("client_phone")) {
				obj.setClientPhone(json.getString("client_phone"));
			}

			if (json.has("client_address")) {
				obj.setClientAddress(json.getString("client_address"));
			}

			if (json.has("contract_no")) {
				String contractNo = json.getString("contract_no");
				obj.setContractNo(contractNo);
				obj.setName(contractNo);
			}

			if (json.has("juger_name")) {
				obj.setJugerName(json.getString("juger_name"));
			}

			if (json.has("client_received")) {
				obj.setClientReceived(json.getString("client_received"));
			}

			if (json.has("client_name")) {
				obj.setClientName(json.getString("client_name"));
			}

			if (json.has("accept_at")) {
				obj.setAcceptAt(json.getString("accept_at"));
			}

			if (json.has("modify_at")) {
				obj.setModifyAt(json.getString("modify_at"));
			}
			
			if (json.has("opposite_client_address")) {
				obj.setOppositeClientAddress(json.getString("opposite_client_address"));
			}
			
			if (json.has("cause")) {
				obj.setCause(json.getString("cause"));
			}

			if (json.has("court_address")) {
				obj.setCourtAddress(json.getString("court_address"));
			}

			if (json.has("court_case_no")) {
				obj.setCourtCaseNo(json.getString("court_case_no"));
			}

			if (json.has("clerk_phone")) {
				obj.setClerkPhone(json.getString("clerk_phone"));
			}

			if (json.has("opposite_client_phone")) {
				obj.setOppositeClientPhone(json.getString("opposite_client_phone"));
			}

			if (json.has("judge_at")) {
				obj.setJudgeAt(json.getString("judge_at"));
			}

			if (json.has("open_at")) {
				obj.setOpenAt(json.getString("open_at"));
			}

			if (json.has("appeal_limit")) {
				obj.setAppealLimit(json.getString("appeal_limit"));
			}

			if (json.has("level")) {
				obj.setLevel(json.getString("level"));
			}
			
			if (json.has("type")) {
				obj.setType(json.getString("type"));
			}
			
			if (json.has("opposite_client_name")) {
				obj.setOppositeClientName(json.getString("opposite_client_name"));
			}

			if (json.has("create_at")) {
				obj.setCreateAt(json.getString("create_at"));
			}

			if (json.has("proof_limit")) {
				obj.setProofLimit(json.getString("proof_limit"));
			}

			if (json.has("opposite_lawyer_name")) {
				obj.setOppositeLawyerName(json.getString("opposite_lawyer_name"));
			}

			if (json.has("receive_at")) {
				obj.setReceiveAt(json.getString("receive_at"));
			}

			if (json.has("receive_judge_at")) {
				obj.setReceiveJudgeAt(json.getString("receive_judge_at"));
			}
			
			if (json.has("type")) {
				obj.setType(json.getString("type"));
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