package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountUserName;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.userName;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {


    private JdbcTransferDao jdbcTransferDao;
    private JdbcAccountDao jdbcAccountDao;


    public AccountController(JdbcTransferDao jdbcTransferDao, JdbcAccountDao jdbcAccountDao){
        this.jdbcTransferDao = jdbcTransferDao;
        this.jdbcAccountDao = jdbcAccountDao;

    }

    @RequestMapping(path = "/balanceofuser", method = RequestMethod.GET)
    public AccountUserName showBalanceOfUser(Principal principal){
        return this.jdbcAccountDao.getBalanceByUsername(principal.getName());

    }

    @RequestMapping(path = "/listofuseers", method = RequestMethod.GET)
    public List<userName> showListofAll(Principal principal){
        return this.jdbcAccountDao.getAllUserName(principal.getName());
    }

    @RequestMapping(path = "/tranfersfromuser", method = RequestMethod.GET)
    public List<Transfer> myAccountHistory(Principal principal) {
        return this.jdbcTransferDao.getAllTransfersFromUsers(principal.getName());


    }




}
