package fr.epsi.rennes.poec.hadf.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseTest {
	
	@Test
	public void successTrue() {
		// given
		Response<String> response = new Response<>(true);
		
		// when
		boolean isSuccess = response.isSuccess();
		
		// then
		Assertions.assertTrue(isSuccess);
	}
	
	@Test
	public void successFalse() {
		// given
		Response<String> response = new Response<>(false);
		
		// when
		boolean isSuccess = response.isSuccess();
		
		// then
		Assertions.assertFalse(isSuccess);
	}

}
