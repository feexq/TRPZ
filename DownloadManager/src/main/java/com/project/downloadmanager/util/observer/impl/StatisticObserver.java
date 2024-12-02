package com.project.downloadmanager.util.observer.impl;

import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.util.observer.Observer;

public class StatisticObserver implements Observer {

    @Override
    public void update(DownloadDto dto) {
        for (DownloadStatus status : DownloadStatus.values()) {
            if (status == dto.getStatus()) {
                System.out.println("STATISTICS -> " + status + " download: " + dto.getUrl());
            }
        }
    }
}
