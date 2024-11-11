package com.project.downloadmanager.util;

import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.repo.DownloadErrorRepository;
import com.project.downloadmanager.repo.DownloadRepository;
import com.project.downloadmanager.repo.SpeedLimitRepository;
import com.project.downloadmanager.util.iterator.Aggregate;
import com.project.downloadmanager.util.iterator.Iterator;
import com.project.downloadmanager.util.iterator.impl.DownloadAggregateImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DownloadManager {

    private DownloadManagerStatistic statistic;
    private UserService userService;
    private DownloadErrorRepository errorRepository;
    private DownloadRepository downloadRepository;
    private SpeedLimitRepository speedLimitRepository;

    public void resume() {

    }
    public void pause() {

    }
    public void delete() {

    }
    public void error() {

    }
    private void downloadStart() throws SQLException { // Все що реалізовано в методі використовано і написано виключно для того щоб продемонструвати роботу патерну Ітератор
                                                        // Потім цей метод буде приймати певні значення і розпочинати завнтаження
        List<Thread> downloadThreads = new ArrayList<>();

//        Aggregate<Download> aggregate = new DownloadAggregateImpl(new DownloadRepository().findAll()); -- Взяти завантаження для тест ітератора з репозиторію
        Aggregate<Download> aggregate = new DownloadAggregateImpl(downloadsMock()); // Замокані дані
        Iterator<Download> downloadIterator = aggregate.createIterator();
        while (downloadIterator.hasNext()) {
            Download download = downloadIterator.next();
            System.out.println(download.toString());

            Download downloadTask = new Download(download.getUrl());

            Thread downloadThread = new Thread(downloadTask);
            downloadThreads.add(downloadThread);
            downloadThread.start();
        }
        System.out.println("******");

        for (Thread thread : downloadThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All downloads completed.");
    }


    public List<Download> downloadsMock() {
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
    //.............



}
