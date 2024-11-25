package com.project.downloadmanager.model;

import com.project.downloadmanager.config.ConfigLoader;
import com.project.downloadmanager.model.User;
import com.project.downloadmanager.model.enums.DownloadStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;


@Getter
@Setter
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

    private final AtomicBoolean pause = new AtomicBoolean(false);
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private FileOutputStream fos;
    private HttpURLConnection huc;

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
                    cleanup();
                    setStatus(DownloadStatus.PAUSED);
                    return;
                }

                bos.write(buffer, 0, read);
                setDownloaded(getDownloaded() + read);

                long elapsedMillis = System.currentTimeMillis() - startTimeMillis;
                setSpeed((float) (getDownloaded() / (elapsedMillis / 1000.0)));
                setRemainingTime((long) ((getSize() - getDownloaded()) / getSpeed()));

                System.out.printf("Downloaded: %.2f%%, Speed: %.2f KB/s, Remaining: %d sec\n",
                        (getDownloaded() / getSize()) * 100,
                        getSpeed() / 1024,
                        getRemainingTime());
            }

            cleanup();
            setEndTime(new Date());
            setStatus(DownloadStatus.COMPLETED);
            System.out.println("Download completed: " + filePath);

        } catch (Exception e) {
            setStatus(DownloadStatus.ERROR);
            System.err.println("Error during download: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cleanup() {
        try {
            if (bos != null) bos.close();
            if (bis != null) bis.close();
            if (fos != null) fos.close();
            if (huc != null) huc.disconnect();
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
}
