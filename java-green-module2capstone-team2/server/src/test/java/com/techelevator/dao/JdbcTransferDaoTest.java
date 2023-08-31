package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests {
    Transfer transfer1 = new Transfer(3001, 125, "zach", "katie");
    Transfer transfer2 = new Transfer(3002, 135, "zach", "ben");
    Transfer transfer3 = new Transfer(3003, 140, "viv", "katie");
    Transfer transfer4 = new Transfer(3004, 125, "dean", "katie");

    private JdbcTransferDao transferDao;
    private Transfer testTransfer;
    private UserDao userDao;


    @Before
    public void setup() {
        transferDao = new JdbcTransferDao(dataSource);
        userDao = new JdbcUserDao(new JdbcTemplate(dataSource));
        testTransfer = new Transfer(5, 200, "zach", "viv");

    }

    @Test
    public void getTransferByIdReturnsCorrectTransfer() {
        userDao.create("katie", "password");
        userDao.create("zach", "password");
        transferDao.addNewTransfer(transfer1, 1000);
        Transfer transfer = transferDao.getTransferById(3001);
        assertTransfersMatch(transfer1, transfer);
        // what the hell is going on here, runs by itself,
        // but not when you do all of them together

    }

    @Test
    public void get_Transfer_By_Id_returns_null_when_id_not_found() {
        userDao.create("katie", "password");
        userDao.create("zach", "password");
        transferDao.addNewTransfer(transfer1, 1000);
        Transfer transfer = transferDao.getTransferById(3099);
        Assert.assertNull(transfer);
    }

    @Test
    public void getTransfersForCertainUsers() {

        userDao.create("ben", "password");
        userDao.create("katie", "password");
        userDao.create("zach", "password");

        transferDao.addNewTransfer(transfer1, 1000);
        transferDao.addNewTransfer(transfer2, 1000);

        List<Transfer> transfers = transferDao.getAllTransfersFromUsers("zach");
        Assert.assertEquals(2, transfers.size());
        assertTransfersMatch(transfer1, transfers.get(0));
        assertTransfersMatch(transfer2, transfers.get(1));
    }

    @Test
    public void getTransfersForUSerIsEmptyWhenThereIsNoUser() {
        List<Transfer> transfers = transferDao.getAllTransfersFromUsers("apple");
        Assert.assertEquals(0, transfers.size());

    }

    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getReceiverName(), actual.getReceiverName());
        Assert.assertEquals(expected.getSenderName(), actual.getSenderName());
        Assert.assertEquals(expected.getTransferAmount(), actual.getTransferAmount(), 0.0);
    }



}

