package com.project.downloadmanager;

import com.project.downloadmanager.model.DownloadDto;
import com.project.downloadmanager.model.enums.DownloadStatus;
import com.project.downloadmanager.util.DownloadManager;
import com.project.downloadmanager.util.command.Command;
import com.project.downloadmanager.util.command.CommandInvoker;
import com.project.downloadmanager.util.command.impl.DeleteDownloadCommand;
import com.project.downloadmanager.util.command.impl.PauseDownloadCommand;
import com.project.downloadmanager.util.command.impl.ResumeDownloadCommand;
import com.project.downloadmanager.util.command.impl.StartDownloadCommand;
import com.project.downloadmanager.util.template.AbstractDownloadManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

    }

    public static void main(String[] args) {
        DownloadManager downloadManager = new DownloadManager();
        CommandInvoker commandInvoker = new CommandInvoker();
        List<DownloadDto> activeDownloads = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        Thread inputThread = new Thread(() -> {
            while (true) {
                System.out.println("\nНатисніть 'a' для додавання завантаження, 'p' для паузи, 'r' для продовження, 'q' для виходу:");
                String command = scanner.nextLine();

                switch (command.toLowerCase()) {
                    case "a":
                        System.out.println("Введіть URL для завантаження:");
                        String url = scanner.nextLine();
                        DownloadDto download = downloadManager.downloadStart(url);
                        activeDownloads.add(download);
                        startProgressMonitor(download);
                        break;
                    case "p":
                        System.out.println("Введіть URL для паузи:");
                        String pauseUrl = scanner.nextLine();
                        executeCommandForUrl(activeDownloads, pauseUrl, new PauseDownloadCommand(downloadManager, pauseUrl), commandInvoker);
                        break;
                    case "r":
                        System.out.println("Введіть URL для продовження:");
                        String resumeUrl = scanner.nextLine();
                        executeCommandForUrl(activeDownloads, resumeUrl, new ResumeDownloadCommand(downloadManager, resumeUrl), commandInvoker);
                        break;
                    case "q":
                        System.out.println("Завершення роботи.");
                        System.exit(0);
                    default:
                        System.out.println("Невідома команда. Спробуйте ще раз.");
                }
            }
        });
        inputThread.start();
    }

    private static void executeCommandForUrl(List<DownloadDto> downloads, String url, Command command, CommandInvoker invoker) {
        boolean exists = downloads.stream().anyMatch(d -> d.getUrl().equals(url));
        if (exists) {
            invoker.setCommand(command);
            invoker.executeCommand();
        } else {
            System.out.println("Завантаження з таким URL не знайдено.");
        }
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
