package com.project.downloadmanager.util.iterator.impl;


import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.util.iterator.Aggregate;
import com.project.downloadmanager.util.iterator.Iterator;

import java.util.List;

public class DownloadAggregateImpl implements Aggregate<Download> {

    private List<Download> downloads;

    public DownloadAggregateImpl(List<Download> downloads) {
        this.downloads = downloads;
    }

    @Override
    public void add(Download download) {
        downloads.add(download);
    }

    @Override
    public void remove(Download download) {
        downloads.remove(download);
    }

    @Override
    public Iterator<Download> createIterator() {
        return new DownloadIteratorImpl(downloads);
    }
}
