package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.DownloadStatistic;
import com.project.downloadmanager.repo.interfaces.Repository;
import com.project.downloadmanager.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DownloadStatisticRepository implements Repository<DownloadStatistic> {

    public DownloadStatisticRepository() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS download_statistics (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT UNIQUE, 
                    downloads INT NOT NULL,
                    downloads_size DOUBLE NOT NULL,
                    download_total_hours BIGINT NOT NULL,
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
    public Optional<DownloadStatistic> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM download_statistics WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createStatisticFromResultSet(rs));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<DownloadStatistic> findAll() throws SQLException {
        List<DownloadStatistic> statistics = new ArrayList<>();
        String sql = "SELECT * FROM download_statistics";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                statistics.add(createStatisticFromResultSet(rs));
            }
        }

        return statistics;
    }

    @Override
    public DownloadStatistic save(DownloadStatistic statistic) throws SQLException {
        String insertSql = """
            INSERT INTO download_statistics (user_id, downloads, downloads_size, download_total_time)
            VALUES (?, ?, ?, ?)
        """;
        String updateSql = """
            UPDATE download_statistics
            SET downloads = ?, downloads_size = ?, download_total_time = ?
            WHERE user_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (statistic.getId() == 0) {
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setLong(1, 1);
                    setStatementParameters(pstmt, statistic);

                    pstmt.executeUpdate();

                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            statistic.setId(generatedKeys.getLong(1));
                        }
                    }
                }
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    pstmt.setLong(1, 1);
                    setStatementParameters(pstmt, statistic);

                    pstmt.executeUpdate();
                }
            }
        }

        return statistic;
    }

    private void setStatementParameters(PreparedStatement pstmt, DownloadStatistic statistic) throws SQLException {
        pstmt.setInt(2, statistic.getDownloads());
        pstmt.setDouble(3, statistic.getDownloadsSize());
        pstmt.setLong(4, statistic.getDownloadTotalTime());
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM download_statistics WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(DownloadStatistic downloadStatistic) throws SQLException {
        deleteById(downloadStatistic.getId());
    }

    private DownloadStatistic createStatisticFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long userId = rs.getLong("user_id");
        int downloads = rs.getInt("downloads");
        long downloadsSize = rs.getLong("downloads_size");
        long downloadTotalTime = rs.getLong("download_total_time");

        return new DownloadStatistic(id, downloads, downloadsSize, userId, downloadTotalTime);
    }

    // Додати метод для отримання статистики за userId
    public Optional<DownloadStatistic> findByUserId(Long userId) throws SQLException {
        String sql = "SELECT * FROM download_statistics WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createStatisticFromResultSet(rs));
                }
            }
        }

        return Optional.empty();
    }
}
