package com.example.frauddetection.repository;

import com.example.frauddetection.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // SIGNUP - Insert new user
    public int save(User user) {

        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    // LOGIN - Find user by email
    public User findByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{email},
                rs -> {
                    if (rs.next()) {
                        User user = new User();

                        user.setId(rs.getLong("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));

                        return user;
                    }

                    return null;
                }
        );
    }
}