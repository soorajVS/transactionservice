package com.mybank.transactionservice.validator;

import javax.ws.rs.core.Response;

import com.mybank.transactionservice.beans.Transaction;
import com.mybank.transactionservice.dao.TransactionDAO;
import com.mybank.transactionservice.exceptions.TransactionValidationException;

/**
 * Validates the transaction
 * @author sooraj
 *
 */
public class TransactionValidator {

	private Transaction transaction;
	
	private TransactionDAO transactionDAO = new TransactionDAO();
	
	public TransactionValidator(Transaction transaction){
		this.transaction = transaction;
	}
	
	public void validate(){
		
		//no transaction type mentioned
		if(transaction.getType() == null || transaction.getType().isEmpty()){
			throw new TransactionValidationException("INVALID TRANSACTION TYPE", Response.Status.BAD_REQUEST);
		}
		
		//invalid parent transation id
		if(transaction.getParentId() > 0 && !transactionDAO.isExistingTransactionId(transaction.getParentId())){
			throw new TransactionValidationException("INVALID PARENT TRANSACTION", Response.Status.BAD_REQUEST);
		}
	}
}
