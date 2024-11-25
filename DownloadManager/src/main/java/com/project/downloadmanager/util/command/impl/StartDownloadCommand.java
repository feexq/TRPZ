package com.project.downloadmanager.util.command.impl;

import com.project.downloadmanager.util.DownloadManager;
import com.project.downloadmanager.util.command.Command;

public class StartDownloadCommand implements Command {

    private DownloadManager manager;
    private String url;

    public StartDownloadCommand(DownloadManager manager, String url) {
        this.manager = manager;
        this.url = url;
    }

    @Override
    public void execute() {
        manager.downloadStart(url);
        System.out.println("Download started");
    }

    @Override
    public void undo() {
        manager.delete(url);
    }
}
