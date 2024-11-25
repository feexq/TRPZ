package com.project.downloadmanager.util;

import com.project.downloadmanager.repo.DownloadRepository;
import com.project.downloadmanager.repo.DownloadStatisticRepository;
import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.model.DownloadStatistic;

import java.sql.SQLException;
import java.util.List;

public class DownloadManagerStatistic {

    private DownloadStatistic statistic;
    private final DownloadRepository downloadRepository;
    private final DownloadStatisticRepository statisticRepository;

    public DownloadManagerStatistic() {
        this.downloadRepository = new DownloadRepository();
        this.statistic = new DownloadStatistic();
        this.statisticRepository = new DownloadStatisticRepository();
    }

    public void calculateStatistics() throws SQLException{
//        List<Download> downloadList = downloadRepository.findAll();
//        downloadCount(downloadList);
//        downloadSizeCount(downloadList);
//        downloadTotalTime(downloadList);
//
//        // Save the statistics to the database
//        try {
//            statisticRepository.save(statistic);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public DownloadStatistic getStatistic(Long id) throws SQLException{
        return statisticRepository.findByUserId(id).orElseThrow();
    }

    public void downloadCount(List<DownloadDto> downloadList) {
        int downloads = downloadList.size();
        statistic.setDownloads(downloads);
    }

    public void downloadSizeCount(List<DownloadDto> downloadList) {
        long size = 0;
        for (DownloadDto download : downloadList) {
            size += download.getSize();
        }
        statistic.setDownloadsSize(size);
    }

    public void downloadTotalTime(List<DownloadDto> downloadList) {
        long totalTime = 0;

        for (DownloadDto download : downloadList) {
            if (download.getStartTime() != null && download.getEndTime() != null) {
                long duration = download.getEndTime().getTime() - download.getStartTime().getTime();
                totalTime += duration;
            }
        }

        statistic.setDownloadTotalTime(totalTime / 3600000); // Convert to hours
    }
}
