package com.team4.type;

import java.io.Serializable;

import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class TCompaniesEntity implements IBaseType, Serializable{
	
	private static final long serialVersionUID = 2638430017551323892L;
	
	private int mTotalCount;
	private T4List<TCompanyEntity> mRecords;
	
	public int getTotalCount() {
		return mTotalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.mTotalCount = totalCount;
	}

	public T4List<TCompanyEntity> getRecords() {
		return mRecords;
	}

	public void setRecords(T4List<TCompanyEntity> records) {
		this.mRecords = records;
	}
	
}
