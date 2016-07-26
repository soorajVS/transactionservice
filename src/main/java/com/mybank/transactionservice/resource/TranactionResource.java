package com.mybank.transactionservice.resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mybank.transactionservice.beans.Transaction;
import com.mybank.transactionservice.dao.TransactionDAO;
import com.mybank.transactionservice.exceptions.TransactionValidationException;
import com.mybank.transactionservice.validator.TransactionValidator;

@Path("/transaction")
public class TranactionResource {
	
	private TransactionDAO transactionDAO = new TransactionDAO();
	
	@GET
	@Path("/{transaction_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionForId(@PathParam("transaction_id") long transactionId) {
		Transaction transaction = new Transaction();
        try {
            
            if (transactionDAO.isExistingTransactionId(transactionId)) {
                transaction = transactionDAO.getTransaction(transactionId);
            } else {
                throw new TransactionValidationException("TRANSACTION NOT FOUND", Response.Status.NOT_FOUND);
            }
        } catch (TransactionValidationException e) {
            return e.getResponse();
        }
        return Response.ok(transaction).build();
	}
	
	@PUT
	@Path("/{transaction_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTransaction(@PathParam("transaction_id") long transactionId, Transaction transaction){			
		try {
			
			if (transactionId <= 0) {
              throw new TransactionValidationException("INVALID TRANSACTIONID", Response.Status.BAD_REQUEST);
			}
			
			if(transactionDAO.isExistingTransactionId(transactionId)){
				//as per the requirement PUT is for adding transaction.
				//update transaction is not required , so the error
				throw new TransactionValidationException("TRANSACTIONID EXISTS", Response.Status.BAD_REQUEST);
			}
			
			transaction.setId(transactionId);
			
			new TransactionValidator(transaction).validate();
			
			transactionDAO.addTransaction(transaction);
			
			Map<String, String> responseVal = new HashMap<String, String>();
			responseVal.put("status", "ok");

			return Response.ok(responseVal).build();
			//return Response.ok("{ \"status\": \"ok\" }").build();
			
		} catch (TransactionValidationException e) {
			 return e.getResponse();
		}
	}

}
