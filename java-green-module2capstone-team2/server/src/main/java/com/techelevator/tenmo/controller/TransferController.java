package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class TransferController {

    private JdbcTransferDao jdbcTransferDao;
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao, JdbcTransferDao jdbcTransferDao){
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.jdbcTransferDao = jdbcTransferDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/posttransfer", method = RequestMethod.POST)
    public Transfer createMessage(@Valid @RequestBody Transfer transfer, Principal principal){
       // System.out.println(principal.getName());

        double myBalance = accountDao.getBalance(principal.getName());

        if (myBalance < transfer.getTransferAmount()){
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        if (transfer.getTransferAmount() <= 0){
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "Invalid option, can only send a positive amount of funds");
        }

        if (transfer.getSenderName().equals(transfer.getReceiverName())) {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "Invalid option, cannot send money to yourself");
        }

        return this.transferDao.addNewTransfer(transfer, myBalance);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(path = "/tranfersfromuser/{id}", method = RequestMethod.GET)
    public Transfer listTransferById(@PathVariable int id, Principal principal){

        Transfer transfer = transferDao.getTransferById(id);

        if (transfer == null) {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "Transaction ID does not exist, transactions start at 3001");
        }
            return transfer;
        }

    }

