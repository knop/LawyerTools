package com.team4.type;

import java.io.Serializable;

import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;

public class TInvestmentsEntity implements IBaseType, Serializable{

	private static final long serialVersionUID = -9014710518921685216L;
	
	private int mTotalCount;
	private T4List<TInvestmentEntity> mRecords;
	
	public int getTotalCount() {
		return mTotalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.mTotalCount = totalCount;
	}

	public T4List<TInvestmentEntity> getRecords() {
		return mRecords;
	}

	public void setRecords(T4List<TInvestmentEntity> records) {
		this.mRecords = records;
	}
	
}
