package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

public class Transfer {

    private int transferId;
    private double transferAmount;

    @JsonProperty("from")
    private String senderName;
    @JsonProperty("to")
    private String receiverName;

    public Transfer() {

    }

    public Transfer(int transferId, double transferAmount, String senderName, String receiverName) {
        this.transferId = transferId;
        this.transferAmount = transferAmount;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", transferAmount=" + transferAmount +
                ", from='" + senderName + '\'' +
                ", to='" + receiverName + '\'' +
                '}';
    }
}
