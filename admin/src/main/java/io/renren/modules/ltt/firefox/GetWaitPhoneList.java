package io.renren.modules.ltt.firefox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GetWaitPhoneList {
	private String code;
	private List<GetWaitPhoneListDaum> data;

	public GetWaitPhoneList(String code, List<GetWaitPhoneListDaum> data) {
		this.code = code;
		this.data = data;
	}

	public String getCode() {
		return this.code;
	}

	public List<GetWaitPhoneListDaum> getData() {
		return this.data;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setData(List<GetWaitPhoneListDaum> data) {
		this.data = data;
	}
}

