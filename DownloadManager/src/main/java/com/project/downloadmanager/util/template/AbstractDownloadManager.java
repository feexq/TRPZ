package com.project.downloadmanager.util.template;

import com.project.downloadmanager.model.DownloadDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractDownloadManager {

    protected final Map<String, DownloadDto> downloads = new ConcurrentHashMap<>();
    protected final ExecutorService executorService = Executors.newCachedThreadPool();

    public final void handleDownload(String url) {
        if (validate(url)) {
            if (!beforeDownload(url)) {
                return;
            }
            System.out.println("Start download from abstract template method");
            DownloadDto download = downloadStart(url);
            downloads.put(url, download);
            executorService.submit(download);
        }
    }

    protected boolean validate(String url) {
        System.out.println("Validate url: " + url);
        if (downloads.containsKey(url)) {
            System.out.println("Download already exists for: " + url);
            return false;
        }
        System.out.println("Url validate:" + url);
        return true;
    }

    protected boolean beforeDownload(String url) {
        System.out.println("Preparing to download: " + url);
        return true;
    }

    protected abstract DownloadDto downloadStart(String url);

}
