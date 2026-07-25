package com.orthocare.service;

import com.orthocare.dao.UserDAO;
import com.orthocare.model.User;
import com.orthocare.util.PasswordUtil;

import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) throws SQLException {
        if (username == null || password == null) return null;
        User u = userDAO.findByUsername(username.trim());
        if (u == null || !u.isActive()) return null;
        return PasswordUtil.matches(password, u.getPasswordHash()) ? u : null;
    }

    public int register(User u, String plainPassword) throws SQLException {
        u.setPasswordHash(PasswordUtil.sha256(plainPassword));
        return userDAO.insert(u);
    }
}
