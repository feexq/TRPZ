package com.project.downloadmanager.util.command.impl;

import com.project.downloadmanager.util.DownloadManager;
import com.project.downloadmanager.util.command.Command;
import com.project.downloadmanager.util.template.AbstractDownloadManager;

public class StartDownloadCommand implements Command {

    private final AbstractDownloadManager manager;
    private final String url;

    public StartDownloadCommand(AbstractDownloadManager manager, String url) {
        this.manager = manager;
        this.url = url;
    }

    @Override
    public void execute() {
        manager.handleDownload(url);
        System.out.println("Download started");
    }

    @Override
    public void undo() {

    }


}
