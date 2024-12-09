package com.project.downloadmanager.model;

import com.project.downloadmanager.config.ConfigLoader;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.util.composite.DownloadGroup;
import com.project.downloadmanager.util.observer.Observer;
import com.project.downloadmanager.util.observer.Subject;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


@Getter
@Setter
public class DownloadDto implements Runnable, Subject, Serializable, DownloadGroup {

    private final List<Observer> observers = new ArrayList<>();

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;
    private String url;

    private double size;
    private double downloaded;

    private DownloadStatus status;

    private Date startTime;
    private Date endTime;
    private long elapsedTime;
    private long remainingTime;
    private float speed;
    private long userId;

    private final AtomicBoolean pause = new AtomicBoolean(false);
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private FileOutputStream fos;
    private HttpURLConnection huc;

    public DownloadDto(long id, String url, long user, double size ,
                       Date startTime , DownloadStatus status, Date endTime) {
        this.id = id;
        this.url = url;
        this.userId = user;
        this.size = size;
        this.startTime = startTime;
        this.status = status;
        this.endTime = endTime;
    }

    public DownloadDto(String url){
        this.url = url;
    }

    @Override
    public void run() {
        try {
            setStatus(DownloadStatus.DOWNLOADING);
            setStartTime(new Date());

            URL urlObj = new URL(getUrl());
            String downloadDirectory = ConfigLoader.getDownloadDirectory();
            String fileName = new File(urlObj.getPath()).getName();
            String filePath = downloadDirectory + File.separator + fileName;

            File directory = new File(downloadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // If download is starting fresh
            if (downloaded == 0) {
                huc = (HttpURLConnection) urlObj.openConnection();
                setSize((double) huc.getContentLengthLong());
                bis = new BufferedInputStream(huc.getInputStream());
                fos = new FileOutputStream(filePath);
                bos = new BufferedOutputStream(fos);
            } else {
                // Resume download
                huc = (HttpURLConnection) urlObj.openConnection();
                huc.setRequestProperty("Range", "bytes=" + (long)downloaded + "-");
                bis = new BufferedInputStream(huc.getInputStream());
                fos = new FileOutputStream(filePath, true);  // append mode
                bos = new BufferedOutputStream(fos);
            }

            byte[] buffer = new byte[1024];
            int read;
            long startTimeMillis = System.currentTimeMillis();

            while ((read = bis.read(buffer, 0, buffer.length)) >= 0) {
                if (status == DownloadStatus.CANCELLED) {
                    cleanup();
                    return;
                }

                if (pause.get()) {
                    setStatus(DownloadStatus.PAUSED);
                    cleanup();
                    return;
                }

                bos.write(buffer, 0, read);
                setDownloaded(getDownloaded() + read);

                long elapsedMillis = System.currentTimeMillis() - startTimeMillis;
                setSpeed((float) (getDownloaded() / (elapsedMillis / 1000.0)));
                setRemainingTime((long) ((getSize() - getDownloaded()) / getSpeed()));
            }

            setStatus(DownloadStatus.COMPLETED);
            cleanup();
            setEndTime(new Date());


        } catch (Exception e) {
            setStatus(DownloadStatus.ERROR);
            e.printStackTrace();
        }
    }

    private void cleanup() {
        try {
            if (bos != null) bos.close();
            if (bis != null) bis.close();
            if (fos != null) fos.close();
            if (huc != null) huc.disconnect();
            notifyObservers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        pause.set(true);
    }

    public void resume() {
        pause.set(false);
    }

    @Override
    public String toString(){
        return "Download id: " + id + " url: " + url + " size: " + size + " downloaded: ";
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(this);
        }
    }

    // In DownloadDto
    public boolean isSerializable() {
        // Check if download is in a state that can be safely serialized
        return status == DownloadStatus.PAUSED;
    }

    @Override
    public void display() {
        System.out.println("Download: " + url);
    }
}
