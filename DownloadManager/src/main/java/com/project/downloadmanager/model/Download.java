package com.project.downloadmanager.model;

import com.project.downloadmanager.model.User;
import com.project.downloadmanager.model.enums.DownloadStatus;

import java.util.Date;

public class Download {
//    private final long id;
//    private final String url;\

    private long id;
    private String url;

    private long size;
    private long downloaded;

    private DownloadStatus status;

    private Date startTime;
    private Date endTime;
    private long elapsedTime;
    private long remainingTime;
    private int speed;
    private User userId;


    public Download(long id, String url, User user) {
        this.id = id;
        this.url = url;
        this.userId = user;
    }

    public Download(long id, String url, User user, long size ,
                    Date startTime , DownloadStatus status, Date endTime) {
        this.id = id;
        this.url = url;
        this.userId = user;
        this.size = size;
        this.startTime = startTime;
        this.status = status;
        this.endTime = endTime;
    }

//    public Download(long size , long downloaded ,
//                    Date startTime , long elapsedTime , long remainingTime , float speed, DownloadStatus status,Date endTime) {
//        this.size = size;
//        this.downloaded = downloaded;
//        this.startTime = startTime;
//        this.elapsedTime = elapsedTime;
//        this.remainingTime = remainingTime;
//        this.speed = speed;
//        this.status = status;
//        this.endTime = endTime;
//    }

    public Download(Date startTime , Date endTime){
        this.startTime = startTime;
        this.endTime = endTime;
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(long downloaded) {
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }
}
