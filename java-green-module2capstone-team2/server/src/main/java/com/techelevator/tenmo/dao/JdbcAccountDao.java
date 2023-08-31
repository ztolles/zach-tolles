package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountUserName;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.userName;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public double getBalance(String username) {
        double balance = 0.0;

        String sql = "SELECT balance FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username); // says its null

        if (results.next()) {
            balance =  results.getDouble("balance");

        }

        return balance;

    }

    @Override
    public AccountUserName getBalanceByUsername(String username) {
        String sql = "SELECT tenmo_user.username, balance FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        if (results.next()) {
            return mapRowToAccount(results);
        }

        throw new UsernameNotFoundException("User " + username + " was not found.");
    }


    public List<userName> getAllUserName(String username) {
        String sql = "SELECT username  \n" +
                "FROM tenmo_user \n" +
                "where username  != ?;";

        List<userName> resultList = new ArrayList<>();

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
        while(result.next()){
            resultList.add(mapRowToAccountUserName(result));
        }
        return resultList;
    }


    public userName mapRowToAccountUserName(SqlRowSet results) {
        userName username = new userName();

        username.setUsername(results.getString("username"));

        return username;
    }

    public AccountUserName mapRowToAccount(SqlRowSet results) {
        AccountUserName account = new AccountUserName();

        account.setUsername(results.getString("username"));
        account.setBalance(results.getDouble("balance"));

        return account;
    }

}
