package com.example.banking_transaction_servicemanagement.dto;

import java.time.LocalDateTime;



public class TransactionResponse {

	
    private int trid;
    private Double amount;
    private String type;
    private LocalDateTime timestamp;
    private String status;

   
    public int getTrid() {
        return trid;
    }

   
    public void setTrid(int trid) {
		this.trid = trid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
