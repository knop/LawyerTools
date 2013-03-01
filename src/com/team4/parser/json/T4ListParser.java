package com.team4.parser.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.team4.exceptions.ErrorCode;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.parser.IJsonParser;
import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class T4ListParser implements IJsonParser<IBaseType> {

	private IJsonParser<IBaseType> mSubParser;

	public T4ListParser(IJsonParser<IBaseType> subParser) {
		mSubParser = subParser;
	}

	@Override
	public IBaseType parse(JSONArray jsonArray) throws T4Exception {
		try {
			T4List<IBaseType> list = new T4List<IBaseType>();

			for (int i = 0; i < jsonArray.length(); i++) {
				Object element = jsonArray.get(i);
				if (element instanceof JSONObject) {
					IBaseType item = mSubParser.parse((JSONObject) element);
					list.add(item);
				} else {
					throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "Could not parse data.");
				}
			}

			return list;
		} catch (Exception e) {
			throw new T4Exception(ErrorCode.PARSE_ERROR_FORMAT_INVALID, "Could not parse data.");
		}
	}

	@Override
	public T4List<? extends IBaseType> parse(JSONObject json) throws T4Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
