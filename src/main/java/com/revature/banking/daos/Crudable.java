package com.revature.banking.daos;

import java.io.IOException;
import java.sql.SQLException;

public interface Crudable<T> {

    T create(T newUser) throws SQLException;

    T[] findAll() throws IOException;
    T findById(String id);

    boolean update(T updatedObj);

    boolean delete(String id);
}
