package io.renren.modules.ltt.firefox;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PhoneAddBatch {
	private String act;
    @JsonProperty("PhoneList")
	private List<PhoneList> phoneList;

	public PhoneAddBatch(String act, List<PhoneList> phoneList) {
		this.act = act;
		this.phoneList = phoneList;
	}

	public String getAct() {
		return this.act;
	}

	public List<PhoneList> getPhoneList() {
		return this.phoneList;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public void setPhoneList(List<PhoneList> phoneList) {
		this.phoneList = phoneList;
	}
}

