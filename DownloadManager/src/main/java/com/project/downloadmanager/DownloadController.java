package com.project.downloadmanager;

import com.project.downloadmanager.model.Download;
import com.project.downloadmanager.model.DownloadStatistic;
import com.project.downloadmanager.util.DownloadManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DownloadController {

    @FXML
    private Label welcomeText;

    @FXML
    private Label clickMeLabel;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Hello World!");
    }

    @FXML
    protected void onClickButtonClick() {
        clickMeLabel.setText("Test button");
    }
}