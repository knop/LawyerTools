package com.team4.type;

import java.io.Serializable;

import com.team4.utils.type.IBaseType;

public class TCompanyEntity implements IBaseType, Serializable{
	    
	private static final long serialVersionUID = -1314186495706064341L;
	
	private int mId;
	private String mCompanyName;
	private String mContactName;
	private String mPhoneNumber;
	private String mMail;
	private String mSource;
	private String mCompanyType;
	private String mAddress;
	private String mHaveCommission;
	private String mCommissionRatio;	
	private String mCapitalSource;
	private String mRating;
	private String mInvestmentIndustry;
	private String mInvestmentWay;
	private String mFlowArea;
	private String mComments;
	private String mAttachments;

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}
	
	public String getCompanyName() {
		return mCompanyName;
	}
	
	public void setCompanyName(String name) {
		this.mCompanyName = name;
	}

	public String getContactName() {
		return mContactName;
	}

	public void setContactName(String contactName) {
		this.mContactName = contactName;
	}

	public String getPhoneNumber() {
		return mPhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.mPhoneNumber = phoneNumber;
	}

	public String getMail() {
		return mMail;
	}

	public void setMail(String mail) {
		this.mMail = mail;
	}

	public String getSource() {
		return mSource;
	}

	public void setSource(String source) {
		this.mSource = source;
	}

	public String getCompanyType() {
		return mCompanyType;
	}

	public void setCompanyType(String companyType) {
		this.mCompanyType = companyType;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		this.mAddress = address;
	}

	public String getHaveCommission() {
		return mHaveCommission;
	}

	public void setHaveCommission(String haveCommission) {
		this.mHaveCommission = haveCommission;
	}

	public String getCommissionRatio() {
		return mCommissionRatio;
	}

	public void setCommissionRatio(String mCommissionRatio) {
		this.mCommissionRatio = mCommissionRatio;
	}

	public String getCapitalSource() {
		return mCapitalSource;
	}

	public void setCapitalSource(String capitalSource) {
		this.mCapitalSource = capitalSource;
	}

	public String getRating() {
		return mRating;
	}

	public void setRating(String rating) {
		this.mRating = rating;
	}

	public String getInvestmentIndustry() {
		return mInvestmentIndustry;
	}

	public void setInvestmentIndustry(String investmentIndustry) {
		this.mInvestmentIndustry = investmentIndustry;
	}

	public String getInvestmentWay() {
		return mInvestmentWay;
	}

	public void setInvestmentWay(String investmentWay) {
		this.mInvestmentWay = investmentWay;
	}

	public String getFlowArea() {
		return mFlowArea;
	}

	public void setFlowArea(String flowArea) {
		this.mFlowArea = flowArea;
	}

	public String getComments() {
		return mComments;
	}

	public void setComments(String comments) {
		this.mComments = comments;
	}

	public String getAttachments() {
		return mAttachments;
	}

	public void setAttachments(String attachments) {
		this.mAttachments = attachments;
	}
}
