package com.team4.type;

import java.io.Serializable;

import com.team4.utils.type.IBaseType;

public class TInfomationEntity implements IBaseType, Serializable{

	private static final long serialVersionUID = 3578379770058820612L;
	
	private int mId;
	private String mName;
	
	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		this.mName = name;
	}
}
