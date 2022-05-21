package com.revature.banking.daos;

import com.revature.banking.models.User;
import com.revature.banking.util.ConnectionFactory;

import java.io.IOException;
import java.sql.*;

public class UsersDao<user> implements Crudable<User> {
    @Override
    public User create(User newUser) {

        System.out.println("Here is the newUser as it enters our DAO layer: " + newUser);//Java knows to invoke the toString() method when printing the object to the terminal
        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {// try with resources bcoz auto closeable
// string that matches sql command
            String sql = "insert into users (first_name, last_name,email, username, userpassword) values (?, ?, ?, ?, ?)"; // incomplete sql statement

            PreparedStatement ps = conn.prepareStatement(sql);
//setting strings
            // 1-indexed, so first ? starts are 1
            ps.setString(1, newUser.getFirstName());
            ps.setString(2, newUser.getLastName());
            ps.setString(3, newUser.getEmail());
            ps.setString(4, newUser.getUsername());
            ps.setString(5, newUser.getUserpassword());

            int checkInsert = ps.executeUpdate();
            System.out.println("Test Print" + checkInsert);

            if (checkInsert == 0) {
                throw new RuntimeException();
            }

        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
            return null;
        }

        return newUser;
    }

    @Override
    public User[] findAll() throws IOException {

        User[] users = new User[20];// making an array of Trainer classes, and setting it to a max size of 10
        int index = 0;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "select * from users";
            Statement s = conn.createStatement();


            ResultSet rs = s.executeQuery(sql);// result set is interface

            while (rs.next()) { //next will allow another entry
                User user = new User();

                user.setFirstName(rs.getString("first_name")); // this column lable MUST MATCH THE DB
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setUserpassword(rs.getString("userpassword"));

                System.out.println("Inserted user into index" + index);
                System.out.println(user);
                users[index] = user;
                index++; // increment the index by 1, must occur after the trainer[index] re-assignment

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("Returning user infomation.");
        return users;

    }

    @Override
    public User findById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "select * from users where email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id); // Wrapper class example
            ResultSet rs = ps.executeQuery(); // remember dql, bc selects are the keywords
            System.out.println(ps);

            if (rs.next() != false) {

                User user = new User();

                user.setFirstName(rs.getString("first_name")); // this column lable MUST MATCH THE DB
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setUserpassword(rs.getString("userpassword"));


                return user;
            } else {
                System.out.println("User not found");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public User findByEmail(String email) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
            System.out.println(email+ "made it to userDao");

            String sql = "select * from users where email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email); // Wrapper class example
            ResultSet rs = ps.executeQuery(); // remember dql, bc selects are the keywords

            if (rs.next() != false) {

                User user = new User();

                user.setFirstName(rs.getString("first_name")); // this column lable MUST MATCH THE DB
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setUserpassword(rs.getString("userpassword"));


                return user;
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
    public boolean update(User updatedObj) {
        return false;
    }


    public user updateFName(user updatedUser) {
        return updatedUser;
    }

    public user updateLName(user updatedUser) {
        return updatedUser;
    }

    public user updateEmail(user updatedUser) {
        return updatedUser;
    }

    public user updateUsername(user updatedUser) {
        return updatedUser;
    }

    public user updatePassword(user updatedUser) {
        return updatedUser;
    }


    @Override
    public boolean delete(String email) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "delete from users where email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email); // Wrapper class example
            ResultSet rs = ps.executeQuery(); // remember dql, bc selects are the keywords
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        }

    public User authenticateUser(String email, String password) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from users where email = ? and userpassword = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            User user = new User();

            user.setFirstName(rs.getString("first_name")); // this column lable MUST MATCH THE DB
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setUserpassword(rs.getString("userpassword"));


            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean checkEmail(String email) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "select email from users where email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email); // Wrapper class example
            ResultSet rs = ps.executeQuery(); // remember dql, bc selects are the keywords

            if (!rs.isBeforeFirst()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPassword(String email, String password) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "select * from users where email = ? and userpassword = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery(); // remember dql, bc selects are the keywords
            if (rs.next() == true) {
                return true;
            } else {
                return false;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }






    }
}

