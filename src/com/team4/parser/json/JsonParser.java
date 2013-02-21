package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.parser.IParser;
import com.team4.utils.type.IBaseType;
import com.team4.utils.util.T4Log;

public class JsonParser implements IParser<IBaseType> {

	private IJsonParser<IBaseType> mParser;
	
	public JsonParser(IJsonParser<IBaseType> parser) {
		mParser = parser;
	}
	
	@Override
	public IBaseType parse(String content) throws T4Exception {
    	T4Log.v("http response: " + content);
    	
    	if (mParser == null)
    		throw new T4Exception(ErrorCode.PARSE_ERROR_PARSER_INVALID, "Json解析器不可用");
    	
        try {        	
            JSONObject json = new JSONObject(content);
        	if(json.has("code")){
        		int code = (Integer)json.get("code");
        		if(code != ErrorCode.OK) {
        			String description = (String)json.get("description");
                	throw new T4Exception(code, description);
        		}
        	} else {
        		throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "Json格式错误");
        	}
        	Object obj = json.get("data");
        	if(obj instanceof JSONObject) {
        		return mParser.parse((JSONObject)obj);
        	} else if(obj instanceof JSONArray) {
        		return mParser.parse((JSONArray)obj);
        	} else {
        		throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "Json格式错误");
        	}
        } catch (JSONException ex) {
            throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "Json格式错误");
        }
	}

}
