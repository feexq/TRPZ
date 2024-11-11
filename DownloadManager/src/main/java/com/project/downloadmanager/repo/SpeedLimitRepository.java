package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.model.DownloadError;
import com.project.downloadmanager.model.SpeedLimit;
import com.project.downloadmanager.model.User;
import com.project.downloadmanager.repo.interfaces.Repository;
import com.project.downloadmanager.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class SpeedLimitRepository implements Repository<SpeedLimit> {

    @Override
    public SpeedLimit save(SpeedLimit entity) throws SQLException {
        return null;
    }

    @Override
    public Optional<SpeedLimit> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<SpeedLimit> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void delete(SpeedLimit entity) throws SQLException {

    }

    @Override
    public void deleteById(Long id) throws SQLException {

    }

}
