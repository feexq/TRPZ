package com.project.downloadmanager.util.observer.impl;

import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.util.observer.Observer;

public class GUIObserver implements Observer {


    @Override
    public void update(DownloadDto dto) {
        System.out.printf("GUI -> Downloaded: %.2f%%, Speed: %.2f KB/s, Remaining: %d sec\n",
                        (dto.getDownloaded() / dto.getSize()) * 100,
                        dto.getSpeed() / 1024,
                        dto.getRemainingTime());
    }
}
