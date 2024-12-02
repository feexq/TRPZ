package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.DownloadError;
import com.project.downloadmanager.repo.interfaces.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DownloadErrorRepository implements Repository<DownloadError> {


    @Override
    public DownloadError save(DownloadError entity) throws SQLException {
        return null;
    }

    @Override
    public Optional<DownloadError> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<DownloadError> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void delete(DownloadError entity) throws SQLException {

    }

    @Override
    public void deleteById(Long id) throws SQLException {

    }

}
