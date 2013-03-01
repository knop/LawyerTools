package com.team4.type;

import java.io.Serializable;

import com.team4.utils.type.IBaseType;

/**
 *  @Project       : LawyerTools
 *  @Program Name  : com.team4.type.TInfomations.java
 *  @Class Name    : TInfomationsEntity
 *  @Description   : 类描述
 *  @Author        : Xiaohui Chen
 *  @Creation Date : 2013-3-1 下午1:24:47 
 *  @ModificationHistory  
 *  Who            When          What 
 *  ------------   -----------   ------------------------------------
 *  Xiaohui Chen   2013-3-1       Create
 *
 */
public class TInfomationsEntity implements IBaseType, Serializable {
	
	private static final long serialVersionUID = -4185875435916799065L;
	
	private int mTotalCount;
	private int mBeginIndex;
	private int mEndIndex;
	private IBaseType mRecords;
	
	public int getTotalCount() {
		return mTotalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.mTotalCount = totalCount;
	}

	public IBaseType getRecords() {
		return mRecords;
	}

	public void setRecords(IBaseType records) {
		this.mRecords = records;
	}

	public int getBeginIndex() {
		return mBeginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.mBeginIndex = beginIndex;
	}

	public int getEndIndex() {
		return mEndIndex;
	}

	public void setEndIndex(int endIndex) {
		this.mEndIndex = endIndex;
	}
}
