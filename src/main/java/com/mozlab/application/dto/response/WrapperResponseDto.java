package com.mozlab.application.dto.response;

public class WrapperResponseDto<T> {

	private boolean result;

	private T payLoad;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public T getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(T payLoad) {
		this.payLoad = payLoad;
	}

}
