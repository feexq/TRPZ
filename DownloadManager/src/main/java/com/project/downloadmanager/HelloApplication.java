package com.project.downloadmanager;

import com.project.downloadmanager.util.DownloadManager;
import com.project.downloadmanager.util.command.Command;
import com.project.downloadmanager.util.command.CommandInvoker;
import com.project.downloadmanager.util.command.impl.DeleteDownloadCommand;
import com.project.downloadmanager.util.command.impl.PauseDownloadCommand;
import com.project.downloadmanager.util.command.impl.ResumeDownloadCommand;
import com.project.downloadmanager.util.command.impl.StartDownloadCommand;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
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
}