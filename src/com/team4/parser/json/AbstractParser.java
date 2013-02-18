package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IParser;
import com.team4.utils.type.IBaseType;
import com.team4.utils.util.T4Log;

public abstract class AbstractParser<T extends IBaseType> implements IParser<T> {

    public T parse(String content) throws T4Exception {
    	T4Log.v("http response: " + content);

        try {        	
            JSONObject json = new JSONObject(content);
        	if(json.has("code")){
        		String code = (String)json.get("code");
        		if(!String.valueOf(ErrorCode.OK).equals(code)) {
        			String description = (String)json.get("description");
                	throw new T4Exception(Integer.parseInt(code), description);
        		}
        	} else {
        		throw new T4Exception(ErrorCode.JSON_FORMAT_INVALID, "Json格式错误");
        	}
        	Object obj = json.get("data");
        	if(obj instanceof JSONObject) {
        		return this.parse((JSONObject)obj);
        	} else if(obj instanceof JSONArray) {
        		return this.parse((JSONArray)obj);
        	} else {
        		throw new T4Exception(ErrorCode.JSON_FORMAT_INVALID, "Json格式错误");
        	}
        } catch (JSONException ex) {
            throw new T4Exception(ErrorCode.JSON_FORMAT_INVALID, "Json格式错误");
        }
    }
    
    public T parse(JSONObject json) throws JSONException {
    	throw new JSONException("解析Json数据错误");
    }
    
    public T parse(JSONArray array) throws JSONException {
    	throw new JSONException("解析Json数据错误");
    }
}