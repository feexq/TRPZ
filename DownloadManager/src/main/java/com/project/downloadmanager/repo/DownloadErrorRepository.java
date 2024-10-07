package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.model.DownloadError;
import com.project.downloadmanager.model.User;
import com.project.downloadmanager.repo.interfaces.Repository;
import com.project.downloadmanager.util.DatabaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DownloadErrorRepository implements Repository<DownloadError> {


    public DownloadErrorRepository() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
            CREATE TABLE IF NOT EXISTS download_errors (
                id INT PRIMARY KEY AUTO_INCREMENT,
                download_id INT NOT NULL,
                error_message VARCHAR(255) NOT NULL,
                error_type VARCHAR(50),
                occurred_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (download_id) REFERENCES downloads(id)
            )
            """;
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    private DownloadError createDownloadErrorFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long download_id = rs.getLong("download_id");

        String errorMessage = rs.getString("error_message");
        String errorType = rs.getString("error_type");

        return new DownloadError(id, new Download(1,"test",new User()),errorMessage,errorType);
    }
}
