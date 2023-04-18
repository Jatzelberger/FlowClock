package de.jatzelberger.flowclock.gui;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ClockController {
    @FXML
    public Label hour;

    @FXML
    public Label minute;

    @FXML
    public Label date;

    @FXML
    public GridPane background;

    public ClockController() {

    }

    @FXML
    public void initialize() {
        // clock handler
        AnimationTimer clockHandler = new AnimationTimer() {
            @Override
            public void handle(long now) {
                LocalDateTime dateTime = LocalDateTime.now();
                hour.setText(dateTime.format(DateTimeFormatter.ofPattern("HH")));
                minute.setText(dateTime.format(DateTimeFormatter.ofPattern("mm")));
                date.setText(dateTime.format(DateTimeFormatter.ofPattern("cccc, d. LLLL u")));
            }
        };
        clockHandler.start();

        // load tray
        try {
            this.loadTray();
        } catch (IOException | AWTException e) {
            throw new RuntimeException(e.getMessage());
        }

        // move window to back
        Platform.runLater(() -> {
            Stage clockWindow = (Stage) background.getScene().getWindow();
            clockWindow.toBack();
        });


    }

    public void loadTray() throws IOException, AWTException {
        if (!SystemTray.isSupported()) return;
        SystemTray tray = SystemTray.getSystemTray();
        Image icon = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/tray/trayIcon.png")));
        TrayIcon trayIcon = new TrayIcon(icon, "FlowClock", null);
        trayIcon.setImageAutoSize(true);

        PopupMenu menu = new PopupMenu("FlowClock");
        MenuItem exitItem = new MenuItem("Exit FlowClock");
        exitItem.addActionListener(e -> {
            Platform.exit();
            System.exit(0);
        });
        menu.add(exitItem);
        trayIcon.setPopupMenu(menu);
        tray.add(trayIcon);
    }

}