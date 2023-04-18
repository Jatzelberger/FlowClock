package de.jatzelberger.flowclock.gui;

import de.jatzelberger.flowclock.helper.WindowHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.StageStyle;
import java.util.Objects;

public class ClockApplication extends Application {

    private Parent root;

    @Override
    public void start(Stage primaryStage) {
        // load gui and controller
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/flowclock.fxml"));
            fxmlLoader.setControllerFactory((Class<?> controllerType) -> {
                if (controllerType == ClockController.class) {
                    return new ClockController();
                } else {
                    try {
                        return controllerType.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            });
            this.root = fxmlLoader.load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR: could not load window gui!");
        }

        // load window icon
        try {
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/tray/trayIcon.png"))));
        } catch (Exception e) {
            System.out.println("ERROR: could not load window icon!");
        }

        // load stylesheet
        Scene scene = new Scene(this.root, 500, 300);
        try {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/flowclock-style.css")).toExternalForm());  // load stylesheet
        } catch (Exception e) {
            System.out.println("ERROR: could not load stylesheet");
        }

        this.root.applyCss();  // apply style
        scene.setFill(Color.TRANSPARENT);  // default background color
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);  // transparent window (enable shadows)

        primaryStage.setTitle("FlowClock");
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(300);
        primaryStage.show();

        // set WS_EX_NOACTIVATE tag to window
        WindowHelper.setWindowLong("FlowClock", 0x08000000);  // see https://learn.microsoft.com/en-us/windows/win32/winmsg/extended-window-styles
    }
}