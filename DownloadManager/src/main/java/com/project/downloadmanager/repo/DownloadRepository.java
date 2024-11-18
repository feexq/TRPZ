package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.model.User;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.repo.interfaces.Repository;
import com.project.downloadmanager.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DownloadRepository implements Repository<Download> {

    @Override
    public Download save(Download download) throws SQLException {
        return null;
    }
    @Override
    public Optional<Download> findById(Long id) throws SQLException {

        return Optional.empty();
    }

    @Override
    public List<Download> findAll() {
        List<Download> downloads = new ArrayList<>();

        Download download1 = new Download(
                1001L,
                "https://file-examples.com/wp-content/storage/2017/02/file-sample_1MB.docx",
                123L,
                1524.50,
                new java.util.Date(System.currentTimeMillis() - 3600000),
                DownloadStatus.COMPLETED,
                new java.util.Date(System.currentTimeMillis())
        );
        Download download2 = new Download(
                1002L,
                "https://file-examples.com/wp-content/storage/2018/04/file_example_AVI_1920_2_3MG.avi",
                456L,
                2048.75,
                new java.util.Date(System.currentTimeMillis() - 7200000),
                DownloadStatus.DOWNLOADING,
                null
        );
        Download download3 = new Download(
                1003L,
                "https://drive.usercontent.google.com/u/0/uc?id=1vCi-Q1KBUJwiD54_DRqWIuzl4JqiNe09&export=download",
                789L,
                15.25,
                new java.util.Date(System.currentTimeMillis() - 900000),
                DownloadStatus.ERROR,
                new Date(System.currentTimeMillis() - 600000)
        );

        downloads.add(download1);
        downloads.add(download2);
        downloads.add(download3);
        return downloads;
    }

    @Override
    public void delete(Download download) throws SQLException {
        deleteById(download.getId());
    }

    @Override
    public void deleteById(Long id) throws SQLException {
    }

    public List<Download> findByStatus(DownloadStatus status) throws SQLException {
        return null;
    }

}
