package fr.epsi.rennes.poec.hadf.exception;

public class BusinessException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public BusinessException(String code) {
		super("Error with code : " + code);
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

}
