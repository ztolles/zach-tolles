package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountUserName;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import javax.security.auth.login.AccountNotFoundException;
import javax.sql.DataSource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Transfer addNewTransfer(Transfer transfer, double myBalance) {
        //TODO comeback and handle exception

                String sql = "INSERT INTO transfer(transfer_amount, sender_name, receiver_name) " +
                        "Values  (?,?,?) RETURNING transfer_id";

                Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferAmount(),
                        transfer.getSenderName(), transfer.getReceiverName());

                transfer.setTransferId(newTransferId);

                if (transfer.getTransferAmount() > 0 && !transfer.getSenderName().equals(transfer.getReceiverName())) {

                    if (myBalance >= transfer.getTransferAmount()){


                        String mySql = "UPDATE account SET balance = balance - ? " +
                        "WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username ILIKE ?);";


                        String mySql2 = "UPDATE account SET balance = balance + ? " +
                                "WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username ILIKE ?);";


                        int rowsAffected = jdbcTemplate.update(mySql, transfer.getTransferAmount(), transfer.getSenderName());

                        System.out.println(rowsAffected);

                        int rowsAffected2 = jdbcTemplate.update(mySql2, transfer.getTransferAmount(), transfer.getReceiverName());

                         System.out.println(rowsAffected2);
                        if (rowsAffected == 0 || rowsAffected2 == 0) {
                        }

                   }
                }
        return transfer;
    }

    @Override
    public Transfer updateBalance(Transfer transfer) {
        return null;
    }

    @Override
    public Transfer getTransferById(int id) {
        Transfer transfer = null;
        String sql = "SELECT * from transfer WHERE transfer_id = ?;";

        try {
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

        if (result.next()) {
            transfer = mapRowToTransfer(result);
        }

        } catch (CannotGetJdbcConnectionException e){
            throw new ResourceAccessException("Transfer Id cannot be found");
        }

        return transfer;

    }

    @Override
    public List<Transfer> getAllTransfersFromUsers(String username) {
        String sql = "SELECT transfer_id, transfer_amount, sender_name, receiver_name FROM transfer WHERE sender_name = ?;";

        List<Transfer> transferList = new ArrayList<>();
        SqlRowSet transferResults = jdbcTemplate.queryForRowSet(sql, username);
        while (transferResults.next()) {
            transferList.add(mapRowToTransfer(transferResults));

        }
        return transferList;
    }

    @Override
    public List<Transfer> getAllTransfersForUsers() {
        return null;
    }

    public Transfer mapRowToTransfer(SqlRowSet result) {

        Transfer transfer = new Transfer();
        transfer.setTransferId(result.getInt("transfer_id"));
        transfer.setTransferAmount(result.getDouble("transfer_amount"));
        transfer.setSenderName(result.getString("sender_name"));
        transfer.setReceiverName(result.getString("receiver_name"));

        return transfer;
    }

}
