package com.project.downloadmanager.model;

import java.util.Date;

public class DownloadError {
    private long id;
    private Download downloadId;
    private String errorMessage;
    private String errorType;

    public DownloadError(long id, Download downloadId, String errorMessage, String errorType) {
        this.id = id;
        this.downloadId = downloadId;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public DownloadError(Download downloadId, String errorMessage, String errorType) {
        this.downloadId = downloadId;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Download getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Download downloadId) {
        this.downloadId = downloadId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

}