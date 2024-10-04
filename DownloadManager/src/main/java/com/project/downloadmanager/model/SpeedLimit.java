package com.project.downloadmanager.model;

import java.security.Timestamp;

public class SpeedLimit {
    private long id;
    private Download downloadId;
    private int speedLimitKbps;
    private boolean isActive;

    public SpeedLimit() {}

    public SpeedLimit(long id, Download downloadId, int speedLimitKbps, boolean isActive) {
        this.id = id;
        this.downloadId = downloadId;
        this.speedLimitKbps = speedLimitKbps;
        this.isActive = isActive;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Download getDownloadId() { return downloadId; }
    public void setDownloadId(Download downloadId) { this.downloadId = downloadId; }

    public int getSpeedLimitKbps() { return speedLimitKbps; }
    public void setSpeedLimitKbps(int speedLimitKbps) { this.speedLimitKbps = speedLimitKbps; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

}
