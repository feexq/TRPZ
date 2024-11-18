package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.DownloadStatistic;
import com.project.downloadmanager.repo.interfaces.Repository;
import com.project.downloadmanager.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DownloadStatisticRepository implements Repository<DownloadStatistic> {

    @Override
    public Optional<DownloadStatistic> findById(Long id) throws SQLException {
       return Optional.empty();
    }

    @Override
    public List<DownloadStatistic> findAll() throws SQLException {
        return null;
    }

    @Override
    public DownloadStatistic save(DownloadStatistic statistic) {

        return statistic;
    }


    @Override
    public void deleteById(Long id) throws SQLException {

    }

    @Override
    public void delete(DownloadStatistic downloadStatistic) throws SQLException {
        deleteById(downloadStatistic.getId());
    }

    public Optional<DownloadStatistic> findByUserId(Long userId) {

        return Optional.empty();
    }
}
