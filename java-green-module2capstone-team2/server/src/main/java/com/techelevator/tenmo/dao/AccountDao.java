package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountUserName;
import com.techelevator.tenmo.model.userName;

import java.util.List;

public interface AccountDao {



    public double getBalance(String username);

    AccountUserName getBalanceByUsername(String username);

    List<userName> getAllUserName(String username);


}
