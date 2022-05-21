package com.revature.banking.services;

import com.revature.banking.daos.UsersDao;
import com.revature.banking.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UserServiceTestSuite {
    UserServices sut;
    UsersDao mockUserDao;

    @BeforeEach
    public void testPrep(){
        // in order to specify and mock a dao
        mockUserDao = mock(UsersDao.class);
        sut = new UserServices(mockUserDao);
    }

    @Test
    public void test_validateInput_givenValidUser_returnTrue(){

        // Arrange
        User user = new User("valid", "valid", "valid","valid", "Valid");

        // Act
        boolean actualResult = sut.validateInput(user);

        // Assert
        Assertions.assertTrue(actualResult);

    }

    @Test
    public void test_create_givenValidUser_returnsUser(){
        // Arrange
        User user = new User("pie", "pie", "pie","pie", "pie");
        // THe below code ensures that the services can continue execution and get expected results from the dao without any issues
        when(mockUserDao.create(user)).thenReturn(user);

        // Act
        User actualUser = sut.create(user);

        // Assert

        Assertions.assertEquals("pie", actualUser.getFirstName());
        Assertions.assertEquals("pie", actualUser.getLastName());
        Assertions.assertEquals("pie", actualUser.getEmail());
        Assertions.assertEquals("pie", actualUser.getUsername());
        Assertions.assertEquals("pie", actualUser.getUserpassword());

        // Mockito is verifying that the creation method was executed only once!
        verify(mockUserDao, times(1)).create(user);
    }

    @Test
    @Disabled
    public void test3(){

    }


}
