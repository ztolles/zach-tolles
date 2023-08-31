package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;

import com.techelevator.tenmo.model.AccountUserName;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.userName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDaoTest extends BaseDaoTests{

    // this is where the test data will go
    AccountUserName account1 = new AccountUserName ("zach", 1000);
    AccountUserName account2 = new AccountUserName ("katie", 1000);
    AccountUserName account3 = new AccountUserName ("viv", 1000);
    AccountUserName account4 = new AccountUserName ("ben", 1000);


    private JdbcAccountDao accountDao;
    private AccountUserName testAccount;
    private UserDao userDao;

    @Before
    public void setup() {
        accountDao = new JdbcAccountDao(dataSource);
        testAccount = new AccountUserName("dean", 1000);
        userDao = new JdbcUserDao(new JdbcTemplate(dataSource));
    }

    @Test
    public void getBalanceByUSerName(){
        userDao.create("zach", "password");

        double zachsBalance = accountDao.getBalance("zach"); // this is our actual
        Assert.assertEquals(1000, zachsBalance, 0.0);

    }

    @Test
    public void get_all_usernames_returns_all_usernames(){
        userDao.create("zach", "password");
        userDao.create("katie", "password");
        userDao.create("viv", "password");
        userDao.create("ben", "password");

        List<User> myUsers = userDao.findAll();

        Assert.assertEquals(6, myUsers.size());

    }

    @Test


    private void assertAccountsMatch(AccountUserName expected, AccountUserName actual){
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getBalance(), actual.getBalance(), 0.0);

    }




}
