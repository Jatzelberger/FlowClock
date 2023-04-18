package de.jatzelberger.flowclock.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.util.Objects;

public class ClockApplication extends Application {
        private Parent root;
    @Override
    public void start(Stage primaryStage) {
        // load gui and controller
        try {
            this.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/flowclock.fxml")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR: could not load window gui!");
        }

        // load window icon
        try {
            //primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon/icon.png"))));
            System.out.println("TODO: implement icon");
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
        primaryStage.setScene(scene);

        primaryStage.setTitle("FlowClock");
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }
}