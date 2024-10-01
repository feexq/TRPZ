package com.project.downloadmanager.model;

public class DownloadStatistic {
    private long id;
    private int downloads;
    private long downloadsSize;
//    private float avgSpeed; // maybe!?
    private long downloadTotalTime;

    private long userId;

    //............... maybe something else!!?

    public DownloadStatistic(long id,int downloads, long downloadBytes, float avgSpeed, long downloadTotalTime) {
        this.id = id;
        this.downloads = downloads;
        this.downloadsSize = downloadBytes;
//        this.avgSpeed = avgSpeed;
        this.downloadTotalTime = downloadTotalTime;
    }

    public DownloadStatistic() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public long getDownloadsSize() {
        return downloadsSize;
    }

    public void setDownloadsSize(long downloadsSize) {
        this.downloadsSize = downloadsSize;
    }

//    public float getAvgSpeed() {
//        return avgSpeed;
//    }
//
//    public void setAvgSpeed(float avgSpeed) {
//        this.avgSpeed = avgSpeed;
//    }

    public long getDownloadTotalTime() {
        return downloadTotalTime;
    }

    public void setDownloadTotalTime(long downloadTotalTime) {
        this.downloadTotalTime = downloadTotalTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }




}
