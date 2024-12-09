package com.project.downloadmanager;

import com.project.downloadmanager.util.DownloadManager;
import com.project.downloadmanager.util.command.Command;
import com.project.downloadmanager.util.command.CommandInvoker;
import com.project.downloadmanager.util.command.impl.DeleteDownloadCommand;
import com.project.downloadmanager.util.command.impl.PauseDownloadCommand;
import com.project.downloadmanager.util.command.impl.ResumeDownloadCommand;
import com.project.downloadmanager.util.command.impl.StartDownloadCommand;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HelloApplication extends Application {

    public void start(Stage stage) {
        // Графічний інтерфейс вимкнено для спрощення.
    }

    public static void main(String[] args) {
        DownloadManager dm = new DownloadManager();

        Command startCommand = new StartDownloadCommand(dm, "https://sabnzbd.org/tests/internetspeed/50MB.bin");
        Command pauseCommand = new PauseDownloadCommand(dm, "https://sabnzbd.org/tests/internetspeed/50MB.bin");
        Command resumeCommand = new ResumeDownloadCommand(dm, "https://sabnzbd.org/tests/internetspeed/50MB.bin");
        Command deleteCommand = new DeleteDownloadCommand(dm, "https://sabnzbd.org/tests/internetspeed/50MB.bin");

        CommandInvoker commandInvoker = new CommandInvoker();

        commandInvoker.setCommand(startCommand);
        commandInvoker.executeCommand();

        try {
            Thread.sleep(1000);
            commandInvoker.setCommand(pauseCommand);
            commandInvoker.executeCommand();
            Thread.sleep(4000);
            commandInvoker.setCommand(resumeCommand);
            commandInvoker.executeCommand();
            Thread.sleep(500);
            commandInvoker.setCommand(deleteCommand);
            commandInvoker.executeCommand();
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch();
    }

    private static void startProgressMonitor(DownloadDto download) {
        Thread progressThread = new Thread(() -> {
            long lastDownloaded = 0;
            long lastTime = System.currentTimeMillis();

            while (true) {
                if (download.getStatus() == DownloadStatus.COMPLETED || download.getStatus() == DownloadStatus.ERROR) {
                    System.out.println("\nЗавантаження завершено для URL: " + download.getUrl());
                    break;
                }

                long currentDownloaded = download.getDownloaded();
                long size = download.getSize();

                if (size > 0) {
                    long currentTime = System.currentTimeMillis();
                    long timeElapsed = currentTime - lastTime;

                    if (timeElapsed > 0) {
                        long bytesDownloaded = currentDownloaded - lastDownloaded;
                        double speedKbps = (bytesDownloaded / 1024.0) / (timeElapsed / 1000.0); // Кілобайти в секунду

                        System.out.print("\rПрогрес для " + download.getUrl() + ": " + getProgressBar(download) + " " +
                                (int) ((currentDownloaded / (double) size) * 100) + "% " +
                                String.format("Швидкість: %.2f KB/s", speedKbps));
                    }

                    lastDownloaded = currentDownloaded;
                    lastTime = currentTime;
                } else {
                    System.out.print("\rПрогрес для " + download.getUrl() + ": [Очікування...] Швидкість: невідома");
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        progressThread.start();
    }

    private static String getProgressBar(DownloadDto download) {
        int totalBars = 30;

        if (download.getSize() == 0) {
            return "[Завантаження...]";
        }

        int completedBars = (int) ((download.getDownloaded() / (double) download.getSize()) * totalBars);
        StringBuilder progressBar = new StringBuilder("[");
        for (int i = 0; i < totalBars; i++) {
            if (i < completedBars) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("]");
        return progressBar.toString();

    }
}
