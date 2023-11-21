package io.renren.modules.ltt.firefox;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneDeleteAllResponse {
	private String code;
	private String data;

	public PhoneDeleteAllResponse(String code, String data) {
		this.code = code;
		this.data = data;
	}

	public String getCode() {
		return this.code;
	}

	public String getData() {
		return this.data;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setData(String data) {
		this.data = data;
	}
}

