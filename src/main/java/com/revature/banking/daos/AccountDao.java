package com.revature.banking.daos;


import com.revature.banking.models.User;
import com.revature.banking.models.Account;
import com.revature.banking.util.ConnectionFactory;
import com.revature.banking.util.logging.Logger;

import java.io.IOException;
import java.sql.*;

public class AccountDao implements Crudable<Account> {

    private Logger logger = Logger.getLogger();

    @Override
    public Account create(Account newAccount) {
        System.out.println("Here is the newUser as it enters our DAO layer: " + newAccount);//Java knows to invoke the toString() method when printing the object to the terminal
        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {// try with resources bcoz auto closeable
// string that matches sql command
            String sql = "insert into account (id, account_type,email, balance) values (?, ?, ?, ?)"; // incomplete sql statement

            PreparedStatement ps = conn.prepareStatement(sql);
//setting strings
            // 1-indexed, so first ? starts are 1
            ps.setInt(1, newAccount.getId());
            ps.setString(2, newAccount.getAccount_type());
            ps.setString(3, newAccount.getEmail());
            ps.setInt(4, newAccount.getBalance());


            int checkInsert = ps.executeUpdate();
            System.out.println("Test Print" + checkInsert);

            if (checkInsert == 0) {
                throw new RuntimeException();
            }

        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
            return null;
        }

        return newAccount;
    }


    @Override
    public Account[] findAll() {
        //TODO: swap users to account

        Account[] accounts = new Account[20];
        int index = 0;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "select * from account";
            Statement s = conn.createStatement();


            ResultSet rs = s.executeQuery(sql);// result set is interface

            while (rs.next()) { //next will allow another entry
                Account account = new Account();

                account.setId(rs.getInt("id")); // this column lable MUST MATCH THE DB
                account.setAccount_type(rs.getString("account_type"));
                account.setBalance(rs.getInt("balance"));
                account.setEmail(rs.getString("email"));


                accounts[index] = account;
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("Returning user infomation.");
        return accounts;
    }

    @Override
    public Account findById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "select * from account where id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(id)); // Wrapper class example
            ResultSet rs = ps.executeQuery(); // remember dql, bc selects are the keywords
            System.out.println(ps);

            if (rs.next() != false) {

                Account account = new Account();

                account.setId(rs.getInt("id")); // this column lable MUST MATCH THE DB
                account.setAccount_type(rs.getString("account_type"));
                account.setBalance(rs.getInt("balance"));
                account.setEmail(rs.getString("email"));


                return account;
            } else {
                System.out.println("User not found");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }


    @Override
    public boolean update(Account updatedObj) {
        return false;
    }

    @Override
    public boolean delete(String id) {

            try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

                String sql = "delete from users where email = ?";

                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, id); // Wrapper class example
                ResultSet rs = ps.executeQuery(); // remember dql, bc selects are the keywords
                return true;


            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }

    public void deposit(String amount, String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "update account set balance=balance+? where id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(amount));
            ps.setInt(2, Integer.parseInt(id));
            int rs = ps.executeUpdate(); // remember dql, bc selects are the keywords

            System.out.println("Deposit of " + amount + " was successful");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
