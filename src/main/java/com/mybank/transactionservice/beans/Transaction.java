package com.mybank.transactionservice.beans;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Transaction bean
 * @author sooraj
 *
 */
public class Transaction {
	
	private long id;
	
	@JsonProperty("parent_id")	
	private long parentId;	
	private double amount;
	private String type;
	
	public Transaction(){
		
	}
	
	public Transaction(long id, double amount, String type){
		setId(id);
		setType(type);
		setAmount(amount);
	}
	
	public Transaction(long id, double amount, String type, long parendId){
		this(id, amount, type);
		setParentId(parendId);
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getParentId() {
		return parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", type=" + type + ", amount=" + amount + ", parentId=" + parentId + "]";
	}

}
