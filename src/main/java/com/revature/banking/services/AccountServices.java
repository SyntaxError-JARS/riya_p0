package com.revature.banking.services;


import com.revature.banking.daos.AccountDao;
import com.revature.banking.exceptions.InvalidRequestException;
import com.revature.banking.exceptions.ResourcePersistanceException;
import com.revature.banking.models.Account;
import com.revature.banking.util.logging.Logger;

import java.sql.SQLException;

public class AccountServices implements Serviceable<Account> {
    private final AccountDao accountDao;


    private Logger logger = Logger.getLogger();


    public AccountServices(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account create(Account newAccount) {
        logger.info("Account trying to be registered: " + newAccount);
        if (!validateInput(newAccount)) { // checking if false
            // TODO: throw - what's this keyword?
            throw new InvalidRequestException("User input was not validated, either empty String or null values");
        }

        // TODO: Will implement with JDBC (connecting to our database)

      Account persistedAccount = accountDao.create(newAccount);

        if (persistedAccount == null) {
            throw new ResourcePersistanceException("User was not persisted to the database upon registration");
        }
        logger.info("Trainer has been persisted: " + newAccount);
        return persistedAccount;

    }

    @Override
    public Account[] readAll() {
        return accountDao.findAll();
    }


    @Override
    public Account readById(String id) {
        Logger logger = null;
        Account account = new Account();
        try {
            account = accountDao.findById(id);
        } catch (ResourcePersistanceException e) {
            logger.warn("Id was not found");
        }
        return account;
    }


    @Override
    public Account update(Account updatedObject) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }


    public boolean deleteAccount(String id) throws SQLException {
        System.out.println("made it to delete");
        try {
            accountDao.findById(id);
            return accountDao.delete(id);
        } catch (Exception e) {
            logger.warn("Id was not found");
            return false;
        }
    }


    @Override
    public boolean validateInput(Account newAccount) {
        if (newAccount == null) return false;
        if (newAccount.getId() == 0) return false;
        if (newAccount.getAccount_type() == null || newAccount.getAccount_type().trim().equals("")) return false;
        return newAccount.getEmail() != null || !newAccount.getEmail().trim().equals("");

    }

    public void deposit(String value, String id) {
        System.out.println("made it to deposit");
        accountDao.deposit(value, id);
    }


    public boolean registerAccount(Account newAccount) {

        // TODO: Will implement with JDBC (connecting to our database)

        Account persistedAccount = accountDao.create(newAccount);

        if (persistedAccount == null) {
            throw new RuntimeException();
        }
        System.out.println("Account has been registered: " + newAccount);
        return true;

    }

    private boolean validateAccountInput(Account newAccount) {
        return false;
    }


}


