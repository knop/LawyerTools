package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;

public class ComunicationParser implements IJsonParser<IBaseType> {
    
    public IBaseType parse(JSONObject json) throws T4Exception {
    	return null;  	
    }
    
	@Override
	public IBaseType parse(JSONArray array) throws T4Exception {
		return null;
	}
}