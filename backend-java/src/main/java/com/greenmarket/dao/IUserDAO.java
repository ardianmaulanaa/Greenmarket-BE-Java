package com.greenmarket.dao;

import com.greenmarket.model.User;
import java.util.List;

public interface IUserDAO {
    User getUserByEmail(String email);
    User getUserById(int id);
    List<User> getAllUsers();
    boolean registerUser(User user);
    boolean updateUser(User user);
    boolean updatePassword(int id, String newPassword);
    boolean deleteUser(int id);
    boolean isEmailExists(String email);
    boolean upgradeUserRole(int id);
}
