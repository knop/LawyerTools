package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class ArrayListParser<T extends IBaseType> {
	
	private AbstractParser<T> mSubParser;

	public ArrayListParser(AbstractParser<T> subParser) {
		mSubParser = subParser;
	}

	public T4List<T> parse(JSONArray jsonArray) throws JSONException {

		T4List<T> list = new T4List<T>();
		
        for (int i = 0; i < jsonArray.length(); i++) {
            Object element = jsonArray.get(i);
            if (element instanceof JSONObject) {
                T item = mSubParser.parse((JSONObject)element);
                list.add(item);
            } else {
            	throw new JSONException("Could not parse data.");
            }         
        }

		return list;
	}	
	
}
