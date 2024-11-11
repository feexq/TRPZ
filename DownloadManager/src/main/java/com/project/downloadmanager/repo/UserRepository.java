package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.User;
import com.project.downloadmanager.repo.interfaces.Repository;
import com.project.downloadmanager.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User> {

    @Override
    public User save(User user) throws SQLException {

        return user;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {

        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws SQLException {

        return null;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
    }

    @Override
    public void delete(User user) throws SQLException {
        deleteById(user.getId());
    }


}
