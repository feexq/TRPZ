package com.project.downloadmanager.model;

public class SpeedLimit {
    private long id;
    private DownloadDto downloadId;
    private int speedLimitKbps;
    private boolean isActive;

    public SpeedLimit() {}

    public SpeedLimit(long id, DownloadDto downloadId, int speedLimitKbps, boolean isActive) {
        this.id = id;
        this.downloadId = downloadId;
        this.speedLimitKbps = speedLimitKbps;
        this.isActive = isActive;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public DownloadDto getDownloadId() { return downloadId; }
    public void setDownloadId(DownloadDto downloadId) { this.downloadId = downloadId; }

    public int getSpeedLimitKbps() { return speedLimitKbps; }
    public void setSpeedLimitKbps(int speedLimitKbps) { this.speedLimitKbps = speedLimitKbps; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

}
