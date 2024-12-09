package com.project.downloadmanager.util.observer.impl;


import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.util.observer.Observer;

public class LogObserver implements Observer {

    @Override
    public void update(DownloadDto dto) {
//        System.out.println("LOG -> Download " + dto.getUrl() + " status: " + dto.getStatus());
    }
}
