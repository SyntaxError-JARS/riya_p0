package com.revature.banking.models;

public class Account {

    private int id;

    private String account_type;
    private int balance;
    private String email;


    public Account() { super(); }

    public Account(String account_number, int account_balance, String account_type, String user_email, String memo) {
        super();
        this.id = id;

        this.account_type = account_type;
        this.balance = balance;
        this.email = email;

    }

    // public makes the class accessible and visible to all other classes.
// void means that this method does not have a return value.

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }



    public void setAccount_type(String account_type) {
        this.account_type=account_type;
    }
    public String getAccount_type() {
        return account_type;
    }


    public void setBalance(int balance) {
        this.balance = balance;
    }
    public int getBalance() {
        return balance;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", account_type='" + account_type + '\'' +
                ", account_balance='" + balance + '\'' +
                ", user_email='" + email + '\'' +
                '}';
    }
}

