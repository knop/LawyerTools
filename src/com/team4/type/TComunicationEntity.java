package com.team4.type;

import java.io.Serializable;

import com.team4.utils.type.IBaseType;

public class TComunicationEntity implements IBaseType, Serializable{
	    
	private static final long serialVersionUID = -2803125296226049400L;

	public static interface EnumDirection {
		final int from = 0;
		final int to = 1;
	};
	
	private int mDirection;
	private String mContent;
	private String mTime;
	private String mComments;
	private String mAttachments;
	private String mContactType;
	    
	public int getDirection() {
		return mDirection;
	}
	
	public void setDirection(int direction) {
		this.mDirection = direction;
	}
	    
	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		this.mContent = content;
	}
 
	public String getTime() {
		return mTime;
	}

	public void setTime(String time) {
		this.mTime = time;
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

	public String getContactType() {
		return mContactType;
	}

	public void setContactType(String contactType) {
		this.mContactType = contactType;
	}
}
