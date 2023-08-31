package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface TransferDao {

    Transfer addNewTransfer(Transfer transfer, double myBalance);

    Transfer updateBalance(Transfer transfer);

    Transfer getTransferById(int id);


    List<Transfer> getAllTransfersFromUsers(String username);
    List<Transfer> getAllTransfersForUsers();

}
