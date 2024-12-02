package com.project.downloadmanager.repo;

import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.repo.interfaces.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DownloadRepository implements Repository<DownloadDto> {

    @Override
    public DownloadDto save(DownloadDto download) throws SQLException {
        return null;
    }
    @Override
    public Optional<DownloadDto> findById(Long id) throws SQLException {

        return Optional.empty();
    }

    @Override
    public List<DownloadDto> findAll() {
        List<DownloadDto> downloads = new ArrayList<>();

        DownloadDto download1 = new DownloadDto(
                1001L,
                "https://file-examples.com/wp-content/storage/2017/02/file-sample_1MB.docx",
                123L,
                1524.50,
                new java.util.Date(System.currentTimeMillis() - 3600000),
                DownloadStatus.COMPLETED,
                new java.util.Date(System.currentTimeMillis())
        );
        DownloadDto download2 = new DownloadDto(
                1002L,
                "https://file-examples.com/wp-content/storage/2018/04/file_example_AVI_1920_2_3MG.avi",
                456L,
                2048.75,
                new java.util.Date(System.currentTimeMillis() - 7200000),
                DownloadStatus.DOWNLOADING,
                null
        );
        DownloadDto download3 = new DownloadDto(
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
    public void delete(DownloadDto download) throws SQLException {
        deleteById(download.getId());
    }

    @Override
    public void deleteById(Long id) throws SQLException {
    }

    public List<DownloadDto> findByStatus(DownloadStatus status) throws SQLException {
        return null;
    }

}
