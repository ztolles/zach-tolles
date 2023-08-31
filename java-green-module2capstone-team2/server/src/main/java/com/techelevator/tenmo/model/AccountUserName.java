package com.techelevator.tenmo.model;

public class AccountUserName {

    private String username;
    private double balance;

    public AccountUserName() {
    }

    public AccountUserName(String username, double balance) {
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountUserName{" +
                "username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
