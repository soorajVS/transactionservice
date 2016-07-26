package com.mybank.transactionservice.resource;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mybank.transactionservice.dao.TransactionDAO;
import com.mybank.transactionservice.exceptions.TransactionValidationException;

/**
 * 
 * @author sooraj
 *
 */
@Path("/types")
public class TypeResource {

	// inject transactionDAO
	private TransactionDAO transactionDAO = new TransactionDAO();

	@GET
	@Path("/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionForType(
			@PathParam("type") String transactionType) {

		try {
			if (transactionType == null || transactionType.isEmpty()) {
				throw new TransactionValidationException(
						"INVALID TRANSACTION TYPE", Response.Status.BAD_REQUEST);
			}

			Set<Long> transactionIds = transactionDAO
					.getAllTransactionOfType(transactionType);
			if (transactionIds == null || transactionIds.isEmpty()) {
				throw new TransactionValidationException(
						"TRANSACTION TYPE NOT FOUND", Response.Status.NOT_FOUND);
			}
			return Response.ok(transactionIds).build();
		} catch (TransactionValidationException e) {
			return e.getResponse();
		}

	}

}
