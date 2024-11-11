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
    public void downloadStart() { // Все що реалізовано в методі використовано і написано виключно для того щоб продемонструвати роботу патерну Ітератор
                                                        // Потім цей метод буде приймати певні значення і розпочинати завнтаження
        List<Thread> downloadThreads = new ArrayList<>();

        Aggregate<Download> aggregate = new DownloadAggregateImpl(new DownloadRepository().findAll()); // -- Взяти завантаження для тест ітератора з репозиторію
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


    //.............



}
