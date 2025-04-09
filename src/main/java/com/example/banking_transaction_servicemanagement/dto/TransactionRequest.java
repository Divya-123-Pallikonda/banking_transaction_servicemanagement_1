package com.example.banking_transaction_servicemanagement.dto;

import java.time.LocalDateTime;



public class TransactionRequest {
	private int treid;
    private Double amount;
    private String type; // "credit" or "debt"
    private LocalDateTime timestamp; 

    
    public int getTreid() {
		return treid;
	}

	public void setTreid(int treid) {
		this.treid = treid;
	}

	public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

