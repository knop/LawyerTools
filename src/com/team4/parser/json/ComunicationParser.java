package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.type.TComunicationEntity;

public class ComunicationParser extends AbstractParser<TComunicationEntity> {
    
    public TComunicationEntity parse(JSONObject json) throws JSONException {
    	return null;  	
    }
    
	@Override
	public TComunicationEntity parse(JSONArray array) throws JSONException {
		return null;
	}
}