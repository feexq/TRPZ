package com.project.downloadmanager.util;

import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.model.enums.DownloadStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {

    private final Map<String, Download> downloads = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void resume(String url) {
        Download download = downloads.get(url);
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
        Download download = downloads.get(url);
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
        Download download = downloads.get(url);
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
        Download download = new Download(url);
        downloads.put(url, download);
        executorService.submit(download);
    }


    //.............



}
