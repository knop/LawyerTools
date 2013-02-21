package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.type.TComunicationEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;

public class ComunicationParser implements IJsonParser<IBaseType> {
    
    public IBaseType parse(JSONObject json) throws T4Exception {
		try {
			TComunicationEntity obj = new TComunicationEntity();
			if (json.has("comment")) {
				obj.setComments(json.getString("comment"));
			}

			if (json.has("contact_time")) {
				obj.setTime(json.getString("contact_time"));
			}

			if (json.has("content")) {
				obj.setContent(json.getString("content"));
			}

			if (json.has("direction")) {
				obj.setDirection(json.getInt("direction"));
			}

			if (json.has("contact_type")) {
				obj.setContactType(json.getString("contact_type"));
			}

			return obj;
		} catch (JSONException e) {
			throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "JSON格式错误");
		}	
    }
    
	@Override
	public IBaseType parse(JSONArray array) throws T4Exception {
		return null;
	}
}