package com.project.downloadmanager.util.observer.impl;

import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.util.composite.DownloadCategory;
import com.project.downloadmanager.util.observer.Observer;

public class GUIObserver implements Observer {
    DownloadCategory activeDownload = new DownloadCategory("Active Download");
    DownloadCategory completeDownloads = new DownloadCategory("Complete Downloads");
    DownloadCategory pausedDownloads = new DownloadCategory("Paused Downloads");

    @Override
    public void update(DownloadDto dto) {
        activeDownload.remove(dto);
        completeDownloads.remove(dto);
        pausedDownloads.remove(dto);

        switch (dto.getStatus()) {
            case DOWNLOADING:
                if(!activeDownload.existsDownload(dto)){
                    activeDownload.add(dto);
                }
                break;
            case PAUSED:
                if(!pausedDownloads.existsDownload(dto)){
                    pausedDownloads.add(dto);
                }
                break;
            case COMPLETED:
                if(!completeDownloads.existsDownload(dto)){
                    completeDownloads.add(dto);
                }
                break;
        }
        System.out.println("=== Current Downloads ===");
        activeDownload.display();
        pausedDownloads.display();
        completeDownloads.display();
    }
}
