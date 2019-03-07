package com.movix.router.dto;

import java.io.Serializable;

public class MvxResponse implements Serializable {
	private static final long serialVersionUID = 1173022259775193071L;
	private String result = "OK";
	private int code = 1;

	public MvxResponse() {
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
