package com.project.downloadmanager.model.enums;

import java.io.Serializable;

public enum DownloadStatus implements Serializable {
    PENDING,
    DOWNLOADING,
    PAUSED,
    ERROR,
    COMPLETED,
    CANCELLED
}
