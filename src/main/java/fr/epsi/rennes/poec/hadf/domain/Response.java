package fr.epsi.rennes.poec.hadf.domain;

public class Response <T> {
	
	private T data;
	private boolean success;
	
	public Response(T data) {
		this.data = data;
		this.success = true;
	}
	
	public Response(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
