package com.project.downloadmanager.util.iterator.impl;


import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.util.iterator.Iterator;

import java.util.List;
import java.util.NoSuchElementException;

public class DownloadIteratorImpl implements Iterator<Download> {

    private int currentPosition = 0;
    private final List<Download> downloads;

    public DownloadIteratorImpl(List<Download> downloads) {
        this.downloads = downloads;
    }

    @Override
    public boolean hasNext() {
        return currentPosition < downloads.size();
    }

    @Override
    public Download next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return downloads.get(currentPosition++);
    }
}
