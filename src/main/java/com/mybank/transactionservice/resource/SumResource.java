package com.mybank.transactionservice.resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mybank.transactionservice.dao.TransactionDAO;
import com.mybank.transactionservice.exceptions.TransactionValidationException;

/**
 * resource for sum calculation
 * @author sooraj
 *
 */
@Path("/sum")
public class SumResource {
	
	//inject transactionDAO
	private TransactionDAO transactionDAO = new TransactionDAO();
	
	@GET
	@Path("/{transaction_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTotalAmount(
			@PathParam("transaction_id") long transactionId) {
		try {
			if (!transactionDAO.isExistingTransactionId(transactionId)) {
				throw new TransactionValidationException(
						"TRANSACTION NOT FOUND", Response.Status.NOT_FOUND);
			}

			double sum = transactionDAO.getTransactionSum(transactionId);

			Map<String, Double> responseVal = new HashMap<String, Double>();
			responseVal.put("sum", sum);

			return Response.ok(responseVal).build();
		} catch (TransactionValidationException e) {
			return e.getResponse();
		}
	}

}
