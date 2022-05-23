package fr.epsi.rennes.poec.hadf.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fr.epsi.rennes.poec.hadf.domain.Response;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;

@RestControllerAdvice
public class ExceptionController {
	
	private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);
	
	@ExceptionHandler(BusinessException.class)
	public Response<String> onBusinessException(BusinessException e) {
		Response<String> response = new Response<>(e.getCode());
		response.setSuccess(false);
		
		return response;
	}
	
	@ExceptionHandler(Exception.class)
	public Response<Void> onTechnicalException(Exception e) {
		logger.error(e.getMessage(), e);
		return new Response<>(false);
	}

}
