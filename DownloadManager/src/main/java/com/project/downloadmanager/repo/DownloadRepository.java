package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.model.User;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.repo.interfaces.Repository;
import com.project.downloadmanager.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DownloadRepository implements Repository<Download> {

    public DownloadRepository() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
            CREATE TABLE IF NOT EXISTS downloads (
                id INT PRIMARY KEY AUTO_INCREMENT,
                url VARCHAR(2048) NOT NULL,
                size BIGINT,
                status VARCHAR(20),
                start_time TIMESTAMP,
                end_time TIMESTAMP,
                user_id INT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
        """;
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Download save(Download download) throws SQLException {
        String insertSql = """
        INSERT INTO downloads
        (url, size, status, start_time, end_time, user_id)
        VALUES (?, ?, ?, ?, ?, ?)
    """;
        String updateSql = """
        UPDATE downloads
        SET url=?, size=?, status=?, start_time=?, end_time=?, user_id=?
        WHERE id=?
    """;

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (download.getId() == 0) {
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    setStatementParameters(pstmt, download);
                    pstmt.setLong(6, 1);

                    pstmt.executeUpdate();

                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            download.setId(generatedKeys.getLong(1));
                        }
                    }
                }
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    setStatementParameters(pstmt, download);
                    pstmt.setLong(6, 1);
                    pstmt.setLong(7, download.getId());

                    pstmt.executeUpdate();
                }
            }
        }

        return download;
    }
    private void setStatementParameters(PreparedStatement pstmt, Download download) throws SQLException {
        pstmt.setString(1, download.getUrl());
        pstmt.setDouble(2, download.getSize());
        pstmt.setString(3, download.getStatus() != null ? download.getStatus().name() : null);
        pstmt.setTimestamp(4, download.getStartTime() != null ? new Timestamp(download.getStartTime().getTime()) : null);
        pstmt.setTimestamp(5, download.getEndTime() != null ? new Timestamp(download.getEndTime().getTime()) : null);
    }

    @Override
    public Optional<Download> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM downloads WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createDownloadFromResultSet(rs));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Download> findAll() throws SQLException {
        List<Download> downloads = new ArrayList<>();
        String sql = "SELECT * FROM downloads ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                downloads.add(createDownloadFromResultSet(rs));
            }
        }

        return downloads;
    }

    @Override
    public void delete(Download download) throws SQLException {
        deleteById(download.getId());
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM downloads WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Download> findByStatus(DownloadStatus status) throws SQLException {
        List<Download> downloads = new ArrayList<>();
        String sql = "SELECT * FROM downloads WHERE status = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.name());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    downloads.add(createDownloadFromResultSet(rs));
                }
            }
        }

        return downloads;
    }

    private Download createDownloadFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String url = rs.getString("url");
        long size = rs.getLong("size");

        Timestamp startTime = rs.getTimestamp("start_time");
        Timestamp endTime = rs.getTimestamp("end_time");

        String statusStr = rs.getString("status");
        DownloadStatus status = statusStr != null ? DownloadStatus.valueOf(statusStr) : null;

        long userId = rs.getLong("user_id");

        return new Download(id, url, new User(), size, startTime, status, endTime);
    }
}
