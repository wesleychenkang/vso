package com.vsoyou.sdk.main.entity;

import java.io.Serializable;

public class UrlEntity implements Serializable{

	private static final long serialVersionUID = 26140028856611567L;
	public String userLogin_url;
	public String regLogin_url;
	public String adCallback_url;
	public String adOper_url;
	public String adTopic_url;
	public String cmdPayment_url;
	public String cmdCallback_url;
	public ProductEntity productEntity;
	public String thesInfo_url;
	public String userTerms_url;
	public String addMail_url;
	public String findPass_url;
    public String userOut_url;
    public String addOrders_url;
    public String cancelOrders_url;
    public String servicePhone;
    public String serviceQQ;
    public String userBBS_url;
    public String userCenter_url;
    public String userUpdatepwd_url;
    public String userRechargelist_url;
    public String userEmailcode_url;
    public String userBindemail_url;
    public String userPhonenumbercode_url;
    public String userBindphonenumber_url;
    public String userQuestionlist_url;
    public String userQuestionsubmit_url;
    
	@Override
	public String toString() {
		return "UrlEntity [userLogin_url=" + userLogin_url + ", regLogin_url="
				+ regLogin_url + ", adCallback_url=" + adCallback_url
				+ ", adOper_url=" + adOper_url + ", adTopic_url=" + adTopic_url
				+ ", cmdPayment_url=" + cmdPayment_url + ", cmdCallback_url="
				+ cmdCallback_url + ", productEntity=" + productEntity
				+ ", thesInfo_url=" + thesInfo_url + ", userTerms_url="
				+ userTerms_url + ", addMail_url=" + addMail_url
				+ ", findPass_url=" + findPass_url + ", userOut_url="
				+ userOut_url + ", addOrders_url=" + addOrders_url
				+ ", cancelOrders_url=" + cancelOrders_url + ", servicePhone="
				+ servicePhone + ", serviceQQ=" + serviceQQ + ", userBBS_url="
				+ userBBS_url + ", userCenter_url=" + userCenter_url
				+ ", userUpdatepwd_url=" + userUpdatepwd_url
				+ ", userRechargelist_url=" + userRechargelist_url
				+ ", userEmailcode_url=" + userEmailcode_url
				+ ", userBindemail_url=" + userBindemail_url
				+ ", userPhonenumbercode_url=" + userPhonenumbercode_url
				+ ", userBindphonenumber_url=" + userBindphonenumber_url
				+ ", userQuestionlist_url=" + userQuestionlist_url
				+ ", userQuestionsubmit_url=" + userQuestionsubmit_url + "]";
	}

}
