package com.project.downloadmanager.util.command.impl;


import com.project.downloadmanager.util.DownloadManager;
import com.project.downloadmanager.util.command.Command;

public class ResumeDownloadCommand implements Command {

    private DownloadManager downloadManager;
    private String url;

    public ResumeDownloadCommand(DownloadManager downloadManager, String url) {
        this.url = url;
        this.downloadManager = downloadManager;
    }

    @Override
    public void execute() {
        downloadManager.resume(url);
        System.out.println("Download resumed");
    }

    @Override
    public void undo() {
        downloadManager.pause(url);
    }
}
