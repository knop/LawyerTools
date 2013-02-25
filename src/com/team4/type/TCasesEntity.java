package com.team4.type;

import java.io.Serializable;

import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class TCasesEntity implements IBaseType, Serializable{
	
	private static final long serialVersionUID = -3923818824434173331L;
	
	private int mTotalCount;
	private T4List<TCaseEntity> mRecords;
	
	public int getTotalCount() {
		return mTotalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.mTotalCount = totalCount;
	}

	public T4List<TCaseEntity> getRecords() {
		return mRecords;
	}

	public void setRecords(T4List<TCaseEntity> records) {
		this.mRecords = records;
	}
	
}
