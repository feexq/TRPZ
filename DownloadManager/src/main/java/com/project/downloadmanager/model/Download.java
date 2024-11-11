package com.project.downloadmanager.model;

import com.project.downloadmanager.config.ConfigLoader;
import com.project.downloadmanager.model.User;
import com.project.downloadmanager.model.enums.DownloadStatus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class Download implements Runnable{

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


    public Download(long id, String url, long user, double size ,
                    Date startTime , DownloadStatus status, Date endTime) {
        this.id = id;
        this.url = url;
        this.userId = user;
        this.size = size;
        this.startTime = startTime;
        this.status = status;
        this.endTime = endTime;
    }

    public Download(String url){
        this.url = url;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(getUrl());
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            setSize((double) huc.getContentLengthLong());
            BufferedInputStream bis = new BufferedInputStream(huc.getInputStream());

            // Отримуємо шлях до директорії завантаження з ConfigLoader
            String downloadDirectory = ConfigLoader.getDownloadDirectory();
            String fileName = new File(url.getPath()).getName();
            String filePath = downloadDirectory + File.separator + fileName;

            // Створюємо папку, якщо вона не існує
            File directory = new File(downloadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int read;

            while ((read = bis.read(buffer, 0, buffer.length)) >= 0) {
                bos.write(buffer, 0, read);
                setDownloaded(getDownloaded() + read);
                System.out.println("Downloaded " + getDownloaded() + " bytes");
            }

            bos.close();
            bis.close();
            System.out.println("Download completed: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(double downloaded) {
        this.downloaded = downloaded;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void endTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date date) {
        this.endTime = date;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long user) {
        this.userId = user;
    }

    @Override
    public String toString(){
        return "Download id: " + id + " url: " + url + " size: " + size + " downloaded: ";
    }
}
