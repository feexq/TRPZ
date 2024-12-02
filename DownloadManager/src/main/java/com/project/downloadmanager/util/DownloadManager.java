package com.project.downloadmanager.util;

import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.util.observer.impl.GUIObserver;
import com.project.downloadmanager.util.observer.impl.LogObserver;
import com.project.downloadmanager.util.observer.impl.StatisticObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {

    private final Map<String, DownloadDto> downloads = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void resume(String url) {
        DownloadDto download = downloads.get(url);
        if (download == null) {
            System.out.println("No download found for: " + url);
            return;
        }

        if (download.getStatus() != DownloadStatus.PAUSED) {
            System.out.println("Download is not paused for: " + url);
            return;
        }

        System.out.println("Resuming download: " + url);
        download.resume();
        executorService.submit(download);
    }

    public void pause(String url) {
        DownloadDto download = downloads.get(url);
        if (download == null) {
            System.out.println("No download found for: " + url);
            return;
        }

        if (download.getStatus() != DownloadStatus.DOWNLOADING) {
            System.out.println("Download is not active for: " + url);
            return;
        }

        System.out.println("Pausing download: " + url);
        download.pause();

    }

    public void delete(String url) {
        DownloadDto download = downloads.get(url);
        if (download == null) {
            System.out.println("No download to cancel for: " + url);
            return;
        }

        System.out.println("Cancelling download: " + url);
        download.setStatus(DownloadStatus.CANCELLED);
        downloads.remove(url);
    }

    public void downloadStart(String url) {
        if (downloads.containsKey(url)) {
            System.out.println("Download already in progress or completed for: " + url);
            return;
        }

        System.out.println("Starting download: " + url);
        DownloadDto download = new DownloadDto(url);

        download.attach(new LogObserver());
        download.attach(new GUIObserver());
        download.attach(new StatisticObserver());

        downloads.put(url, download);
        executorService.submit(download);
    }


    //.............



}
