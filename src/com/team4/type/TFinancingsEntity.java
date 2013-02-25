package com.team4.type;

import java.io.Serializable;

import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class TFinancingsEntity implements IBaseType, Serializable{

	private static final long serialVersionUID = 1113285216209453435L;
	
	private int mTotalCount;
	private T4List<TFinancingEntity> mRecords;
	
	public int getTotalCount() {
		return mTotalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.mTotalCount = totalCount;
	}

	public T4List<TFinancingEntity> getRecords() {
		return mRecords;
	}

	public void setRecords(T4List<TFinancingEntity> records) {
		this.mRecords = records;
	}
	
}
