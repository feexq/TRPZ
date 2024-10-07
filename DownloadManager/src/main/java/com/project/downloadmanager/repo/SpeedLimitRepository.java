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

    public SpeedLimitRepository() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
            CREATE TABLE IF NOT EXISTS download_speeds (
                id INT PRIMARY KEY AUTO_INCREMENT,
                download_id INT UNIQUE,
                speed_limit_kbps INT,
                is_active BOOLEAN,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                FOREIGN KEY (download_id) REFERENCES downloads(id)
            )
            """;
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    private SpeedLimit createSpeedLimitFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long download_id = rs.getLong("download_id");

        int speed_limit_kbps = rs.getInt("speed_limit_kbps");
        boolean is_active = rs.getBoolean("is_active");

        return new SpeedLimit(id, new Download(1,"txt",new User()),speed_limit_kbps,is_active);
    }
}
