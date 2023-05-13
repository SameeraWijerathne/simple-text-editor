package lk.wsrp.sameera.io.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class TextEditorSceneController {

    @FXML
    private Button btnFind;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnReplace;

    @FXML
    private Label lblResults;

    @FXML
    private MenuItem mnClose;

    @FXML
    private AnchorPane root;

    @FXML
    private TextArea txtEditor;

    @FXML
    private TextField txtReplace;

    @FXML
    private TextField txtSearch;
    private Stage stage;
    private File currentFile;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(event -> {
            mnClose.fire();
            event.consume();

        });
    }

    public void initialize() {
        txtEditor.textProperty().addListener((value, previous, current) -> {
            if (current == null) return;

            if (currentFile == null) {
                stage.setTitle("*Untitled Document");
                return;
            }
            stage.setTitle("*" + currentFile.getName());
        });
    }

    @FXML
    void btnFindOnAction(ActionEvent event) {

    }

    @FXML
    void btnNextOnAction(ActionEvent event) {

    }

    @FXML
    void btnPreviousOnAction(ActionEvent event) {

    }

    @FXML
    void btnReplaceOnAction(ActionEvent event) {

    }

    @FXML
    void mnAboutOnAction(ActionEvent event) throws IOException {
        Stage aboutStage = new Stage();
        URL fxmlFile = getClass().getResource("/view/AboutScene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
        AnchorPane anchorPane = fxmlLoader.load();

        aboutStage.setScene(new Scene(anchorPane));
        aboutStage.setTitle("About");
        aboutStage.initModality(Modality.WINDOW_MODAL);
        aboutStage.initOwner(stage);
        aboutStage.show();
        aboutStage.centerOnScreen();
    }

    @FXML
    void mnCloseOnAction(ActionEvent event) {

    }

    @FXML
    void mnNewOnAction(ActionEvent event) {

    }

    @FXML
    void mnOpenOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a text file");
        File file = fileChooser.showOpenDialog(txtEditor.getScene().getWindow());
        if (file == null) return;

        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        currentFile = file;
        txtEditor.setText(new String(bytes));
        stage.setTitle(file.getName());
    }

    @FXML
    void mnPrintOnAction(ActionEvent event) {

    }

    @FXML
    void mnSaveAsOnAction(ActionEvent event) {

    }

    @FXML
    void mnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void rootOnDragDropped(DragEvent event) {

    }

    @FXML
    void rootOnDragOver(DragEvent event) {

    }
}
