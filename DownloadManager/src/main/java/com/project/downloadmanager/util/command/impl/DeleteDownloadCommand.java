package com.project.downloadmanager.util.command.impl;


import com.project.downloadmanager.util.DownloadManager;
import com.project.downloadmanager.util.command.Command;

public class DeleteDownloadCommand implements Command {
    private DownloadManager downloadManager;
    private String url;

    public DeleteDownloadCommand(DownloadManager downloadManager, String url) {
        this.downloadManager = downloadManager;
        this.url = url;
    }

    @Override
    public void execute() {
        downloadManager.delete(url);
        System.out.println("Download deleted!");
    }

    @Override
    public void undo() {

    }
}
