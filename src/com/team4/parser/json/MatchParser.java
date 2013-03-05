package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;

public class MatchParser implements IJsonParser<IBaseType> {

	private IJsonParser<IBaseType> mSubParser;
	
	public MatchParser(IJsonParser<IBaseType> subParser) {
		mSubParser = subParser;
	}
	
	@Override
	public IBaseType parse(JSONObject json) throws T4Exception {
		try {
			if (json.has("investment")) {
				return mSubParser.parse(json.getJSONObject("investment"));
			} else if (json.has("financing")) {
				return mSubParser.parse(json.getJSONObject("financing"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IBaseType parse(JSONArray array) throws T4Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
