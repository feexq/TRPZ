module com.project.downloadmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires static lombok;
    requires jdk.compiler;

    opens com.project.downloadmanager to javafx.fxml;
    exports com.project.downloadmanager;
}