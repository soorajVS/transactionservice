package com.mybank.transactionservice.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mybank.transactionservice.beans.Transaction;

/**
 * Transaction Store.
 * TransactionDAO provides method to save and retrieve the transaction from the DB/Map
 * @author sooraj
 *
 */
public class TransactionDAO {
		
	private static Map<Long,Transaction> transactionStore = new HashMap<Long,Transaction>();
	//Type Vs Transacion
	private static Map<String,Set<Long>> typeAndtransactionIdMapStore = new HashMap<String,Set<Long>>();
	//Parent And Child Transaction
	private static Map<Long,Set<Long>> parentTransactionAndChildTransactionMap = new HashMap<Long,Set<Long>>();
	
	
	/**
	 * Adds the transaction to DB/Map
	 * @param transaction
	 */
	public void addTransaction(Transaction transaction){
		if(transactionStore.containsKey(transaction.getId())){
			throw new IllegalArgumentException("Invalid transaction id");
		}
		
		transactionStore.put(transaction.getId(), transaction);
		
		Set<Long> transactionIds = typeAndtransactionIdMapStore.get(transaction.getType());
		if(transactionIds == null){
			transactionIds = new HashSet<Long>();
			typeAndtransactionIdMapStore.put(transaction.getType(), transactionIds);
		}
		transactionIds.add(transaction.getId());
		
		
		if(transaction.getParentId() > 0){
			Set<Long> siblingTransactions = parentTransactionAndChildTransactionMap.get(transaction.getParentId());
			
			if(siblingTransactions == null){
				siblingTransactions = new HashSet<Long>();
				parentTransactionAndChildTransactionMap.put(transaction.getParentId(), siblingTransactions);
			}
			
			siblingTransactions.add(transaction.getId());
		}
		
	}
	
	public boolean isExistingTransactionId(Long transactionId){
		return transactionStore.containsKey(transactionId);
		
	}
	
	public Transaction getTransaction(long transactionId){
		return transactionStore.get(transactionId);
	}
	
	public Set<Long> getAllTransactionOfType(String transactionType){
		return typeAndtransactionIdMapStore.get(transactionType);
	}		
	
	/**
	 * get total sum of the given transactionId and its child transactions
	 * @param transactionId
	 * @return
	 */
	public double getTransactionSum(long transactionId){
		Transaction transaction = transactionStore.get(transactionId);
		double sum = 0.0;
		if(transaction != null){			
			double childTransationSum = 0.0;
			Set<Long> childTransactions = parentTransactionAndChildTransactionMap.get(transactionId);
			if(childTransactions != null){				
				for(Long childTransactionId : childTransactions){
					childTransationSum += getTransactionSum(childTransactionId);
				}
			}			
			sum = childTransationSum + transaction.getAmount();
		}				
		return sum;
	}

}
