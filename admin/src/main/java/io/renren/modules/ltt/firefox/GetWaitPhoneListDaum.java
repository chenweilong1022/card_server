package io.renren.modules.ltt.firefox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/11/21 18:50
 */

@NoArgsConstructor
@Data
public class GetWaitPhoneListDaum {
    @JsonProperty("Item_ID")
    private Long itemId;
    @JsonProperty("Phone_GetTime")
    private String phoneGetTime;
    @JsonProperty("Phone_Num")
    private String phoneNum;

    public GetWaitPhoneListDaum(Long itemId, String phoneGetTime, String phoneNum) {
        this.itemId = itemId;
        this.phoneGetTime = phoneGetTime;
        this.phoneNum = phoneNum;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public String getPhoneGetTime() {
        return this.phoneGetTime;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setPhoneGetTime(String phoneGetTime) {
        this.phoneGetTime = phoneGetTime;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
