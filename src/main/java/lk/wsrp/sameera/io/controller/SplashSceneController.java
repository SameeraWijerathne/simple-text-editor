package lk.wsrp.sameera.io.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.wsrp.sameera.io.controller.TextEditorSceneController;

import java.io.IOException;
import java.net.URL;


public class SplashSceneController {

    @FXML
    private Label lblLoader;

    public void initialize() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(500), event ->{
            lblLoader.setText("Application is being initialized...");
        });

        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1500), event -> {
            lblLoader.setText("Setting up Tools...");
        });

        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(2500), event -> {
            lblLoader.setText("Setting up UI...");
        });

        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(3000), event -> {
            URL fxmlFile = getClass().getResource("/view/TextEditorScene.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
            try {
                AnchorPane root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                TextEditorSceneController controller = fxmlLoader.getController();
                controller.setStage(stage);

                stage.setScene(scene);
                stage.setTitle("Untitled Document");
                stage.show();
                stage.centerOnScreen();
                lblLoader.getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4);
        timeline.playFromStart();
    }
}
