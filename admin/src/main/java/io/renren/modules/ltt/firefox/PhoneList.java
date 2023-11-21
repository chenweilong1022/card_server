package io.renren.modules.ltt.firefox;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/11/21 12:00
 */
public class PhoneList {
    @JsonProperty("Country_ID")
    private String countryId;
    @JsonProperty("Phone_Num")
    private String phoneNum;

    public PhoneList(String countryId, String phoneNum) {
        this.countryId = countryId;
        this.phoneNum = phoneNum;
    }

    public String getCountryId() {
        return this.countryId;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
