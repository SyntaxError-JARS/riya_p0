package com.revature.banking.services;

import com.revature.banking.daos.AccountDao;
import com.revature.banking.daos.Crudable;
import com.revature.banking.daos.UsersDao;
import com.revature.banking.exceptions.InvalidRequestException;
import com.revature.banking.exceptions.ResourcePersistanceException;
import com.revature.banking.models.User;
import com.revature.banking.util.logging.Logger;

import javax.jws.soap.SOAPBinding;
import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserServices implements Serviceable<User> {

    private UsersDao usersDao;
    private Logger logger = Logger.getLogger();

    public UserServices(UsersDao usersDao) {
        this.userDao = usersDao;
    }


    private UsersDao userDao = new UsersDao();

    public UserServices() {

    }

    public void readUsers() {
        System.out.println("Begin reading Users in our file database.");
        User[] users = new User[0];
        try {
            users = userDao.findAll();
            System.out.println("All users have been found here are the results: \n");
            for (int i = 0; i < users.length; i++) {
                User user = users[i];
                System.out.println(user);
            }
        } catch (IOException | NullPointerException e) {
            // e.printStackTrace();
        }
    }

    // TODO: Implement me to check that the email is not already in our database.
    public boolean validateEmail(String email) {
        return userDao.checkEmail(email);
    }

    public boolean validatePassword(String email, String password) {
        return userDao.checkPassword(email, password);
    }

    public boolean validateEmailNotUsed(String email) {
        userDao.checkEmail(email);
        return false;
    }

    public void getAccounts(String email) {

    }

    public boolean registerUser(User newUser) {
        System.out.println("User trying to be registered: " + newUser);
        if (!validateUserInput(newUser)) { // checking if false
            System.out.println("User was not validated");
            throw new InvalidRequestException("User input was not validated, either empty String or null values");
        }

        // TODO: Will implement with JDBC (connecting to our database)
        validateEmailNotUsed(newUser.getEmail());

        User persistedUser = userDao.create(newUser);

        if (persistedUser == null) {
            throw new ResourcePersistanceException("User was not persisted to the database upon registration");
        }
        System.out.println("Trainer has been persisted: " + newUser);
        return true;
    }

    private boolean validateUserInput(User newUser) {
        logger.debug("Validating User: " + newUser);
        if (newUser == null) return false;
        if (newUser.getFirstName() == null || newUser.getFirstName().trim().equals("")) return false;
        if (newUser.getLastName() == null || newUser.getLastName().trim().equals("")) return false;
        if (newUser.getEmail() == null || newUser.getEmail().trim().equals("")) return false;
        if (newUser.getUsername() == null || newUser.getUsername().trim().equals("")) return false;
        return newUser.getUserpassword() != null || !newUser.getUserpassword().trim().equals("");
    }


    @Override
    public User create(User newUser) {

        logger.info("User trying to be registered: " + newUser);
        if (!validateInput(newUser)) { // checking if false
            // TODO: throw - what's this keyword?
            throw new InvalidRequestException("User input was not validated, either empty String or null values");
        }

        // TODO: Will implement with JDBC (connecting to our database)
        if (validateEmailNotUsed(newUser.getEmail())) {
            throw new InvalidRequestException("User email is already in use. Please try again with another email or login into previous made account.");
        }

        User persistedUser = userDao.create(newUser);

        if (persistedUser == null) {
            throw new ResourcePersistanceException("User was not persisted to the database upon registration");
        }
        logger.info("Trainer has been persisted: " + newUser);
        return persistedUser;

    }

    @Override
    public User[] readAll() {
        logger.info("Begin reading Users in our file database.");


        try {
            // TODO: What trainerDao intellisense telling me?
            User[] users = userDao.findAll();
            logger.info("All users have been found here are the results: \n");
//
            return users;

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User readById(String email) {
        Logger logger = null;
        User user = new User();
        try {
            user = userDao.findById(email);
        } catch (ResourcePersistanceException e) {
            logger.warn("Id was not found");
        }
        return user;
    }


    @Override
    public User update(User updatedObject) {
        return null;
    }

    @Override
    public boolean delete(String email) {
      boolean work = userDao.delete(email);
        return true;
    }

    @Override
    public boolean validateInput(User object) {
        return true;
    }


    public User readByEmail(String email) throws ResourcePersistanceException {
        User user = userDao.findByEmail(email);
        return user;

    }

    public User authenticateUser(String email, String userpassword) throws AuthenticationException {

        if (userpassword == null || userpassword.trim().equals("") || userpassword == null || userpassword.trim().equals("")) {
            throw new InvalidRequestException("Either username or password is an invalid entry. Please try logging in again");
        }
        User authenticateUser = userDao.authenticateUser(email, userpassword);

        if (authenticateUser == null) {
            throw new AuthenticationException("Unauthenticated user, information provided was not consistent with our database.");
        }
        return authenticateUser;

    }
}





