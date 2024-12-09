package com.project.downloadmanager.util.composite;


import java.util.ArrayList;
import java.util.List;

public class DownloadCategory implements DownloadGroup{
    private String name;
    private List<DownloadGroup> downloads = new ArrayList<>();

    public DownloadCategory(String name) {
        this.name = name;
    }

    public void add(DownloadGroup component) {
        downloads.add(component);
    }

    public void remove(DownloadGroup component) {
        downloads.remove(component);
    }

    public boolean existsDownload(DownloadGroup component) {
        return downloads.contains(component);
    }

    @Override
    public void display() {
        System.out.println("Category: " + name);
        for (DownloadGroup download : downloads) {
            download.display();
        }
    }
}
