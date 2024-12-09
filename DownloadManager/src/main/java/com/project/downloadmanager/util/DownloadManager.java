package com.project.downloadmanager.util;

import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.util.observer.Observer;
import com.project.downloadmanager.util.observer.impl.GUIObserver;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DOWNLOAD_PERSISTENCE_FILE = "downloads.ser";

    // Use a thread-safe but serializable map implementation
    private final Map<String, DownloadDto> downloads = Collections.synchronizedMap(new HashMap<>());

    // Transient executor service - will be recreated on deserialization
    private transient ExecutorService executorService = Executors.newCachedThreadPool();

    Observer observer = new GUIObserver();

    // Add readResolve method to handle executor service recreation
    @Serial
    private Object readResolve() throws ObjectStreamException {
        executorService = Executors.newCachedThreadPool();
        return this;
    }

    public DownloadManager() {
        loadDownloads();
    }

    private void saveDownloads() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DOWNLOAD_PERSISTENCE_FILE))) {
            // Filter out non-serializable or incomplete downloads
            Map<String, DownloadDto> serializableDownloads = new HashMap<>();
            for (Map.Entry<String, DownloadDto> entry : downloads.entrySet()) {
                if (entry.getValue().isSerializable()) {
                    serializableDownloads.put(entry.getKey(), entry.getValue());
                }
            }
            oos.writeObject(serializableDownloads);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadDownloads() {
        File persistenceFile = new File(DOWNLOAD_PERSISTENCE_FILE);
        if (!persistenceFile.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(persistenceFile))) {
            Map<String, DownloadDto> savedDownloads = (Map<String, DownloadDto>) ois.readObject();

            // Reinitialize paused downloads
            for (DownloadDto download : savedDownloads.values()) {
                if (download.getStatus() == DownloadStatus.PAUSED) {
                    downloads.put(download.getUrl(), download);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load downloads: " + e.getMessage());
        }
    }

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
        saveDownloads();
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
        saveDownloads();
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

        File persistenceFile = new File(DOWNLOAD_PERSISTENCE_FILE);
        if (persistenceFile.exists()) {
            persistenceFile.delete();
        }
    }

    public DownloadDto downloadStart(String url) {
        System.out.println("Starting download: " + url);
        DownloadDto download = new DownloadDto(url);

//        download.attach(new LogObserver());
        download.attach(observer);
//        download.attach(new StatisticObserver());

        downloads.put(url, download);
        executorService.submit(download);
        saveDownloads();

        return download;
    }
}
