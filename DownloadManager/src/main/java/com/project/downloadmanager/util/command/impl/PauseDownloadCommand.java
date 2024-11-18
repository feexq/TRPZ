package com.project.downloadmanager.util.command.impl;


import com.project.downloadmanager.util.DownloadManager;
import com.project.downloadmanager.util.command.Command;

public class PauseDownloadCommand implements Command {
    private DownloadManager downloadManager;
    private String url;

    public PauseDownloadCommand(DownloadManager downloadManager, String url) {
        this.downloadManager = downloadManager;
        this.url = url;
    }

    @Override
    public void execute() {
        downloadManager.pause(url);
        System.out.println("Download paused");
    }

    @Override
    public void undo() {
        downloadManager.resume(url);
    }
}
