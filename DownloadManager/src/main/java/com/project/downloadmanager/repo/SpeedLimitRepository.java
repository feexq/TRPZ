package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.SpeedLimit;
import com.project.downloadmanager.repo.interfaces.Repository;

import java.sql.SQLException;
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
