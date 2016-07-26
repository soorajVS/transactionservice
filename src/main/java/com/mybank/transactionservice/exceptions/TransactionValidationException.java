package com.mybank.transactionservice.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Transaction Validation Exception
 * @author sooraj
 *
 */
public class TransactionValidationException extends WebApplicationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	public TransactionValidationException(String errorMessage , Response.Status responseStatus){
		 super(Response.status(responseStatus).entity(errorMessage).type(MediaType.TEXT_PLAIN_TYPE).build());
		
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
