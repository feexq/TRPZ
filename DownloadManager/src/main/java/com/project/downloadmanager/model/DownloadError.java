package com.project.downloadmanager.model;

public class DownloadError {
    private long id;
    private DownloadDto downloadId;
    private String errorMessage;
    private String errorType;

    public DownloadError(long id, DownloadDto downloadId, String errorMessage, String errorType) {
        this.id = id;
        this.downloadId = downloadId;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public DownloadError(DownloadDto downloadId, String errorMessage, String errorType) {
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

    public DownloadDto getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(DownloadDto downloadId) {
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