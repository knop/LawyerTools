package com.team4.exceptions;

import com.team4.utils.exceptions.T4Code;

public class ErrorCode extends T4Code {
	
	//json解析相关错误Code
	public static final int JSON_FORMAT_INVALID = T4Code.PARSE_ERROR + 1;
	public static final int JSON_PARSER_INVALID = T4Code.PARSE_ERROR + 2;
}
