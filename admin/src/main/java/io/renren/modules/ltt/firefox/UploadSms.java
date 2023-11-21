package io.renren.modules.ltt.firefox;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadSms {
	private String act;
    @JsonProperty("Country_ID")
	private String countryId;
    @JsonProperty("Phone_Num")
	private String phoneNum;
    @JsonProperty("Phone_SmsContent")
	private String phoneSmsContent;

	public UploadSms(String act, String countryId, String phoneNum, String phoneSmsContent) {
		this.act = act;
		this.countryId = countryId;
		this.phoneNum = phoneNum;
		this.phoneSmsContent = phoneSmsContent;
	}

	public String getAct() {
		return this.act;
	}

	public String getCountryId() {
		return this.countryId;
	}

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public String getPhoneSmsContent() {
		return this.phoneSmsContent;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public void setPhoneSmsContent(String phoneSmsContent) {
		this.phoneSmsContent = phoneSmsContent;
	}
}

