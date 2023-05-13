package lk.wsrp.sameera.io.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

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
    void mnCloseOnAction(ActionEvent event) throws IOException {
        if (currentFile == null && stage.getTitle().contains("*")) {
            saveFileBeforeClose(event);
        } else if (currentFile != null && stage.getTitle().contains("*")) {
            saveFileBeforeClose(event);
        } else {
            stage.close();
        }
    }

    private void saveFileBeforeClose(ActionEvent event) throws IOException {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION,
                String.format("Save changes to document %s before closing?", stage.getTitle()),
                ButtonType.NO, ButtonType.CANCEL, ButtonType.YES);
        Optional<ButtonType> optButton = confirmAlert.showAndWait();

        if (optButton.isEmpty() || optButton.get() == ButtonType.CANCEL) {
            event.consume();
            return;
        }

        if (optButton.get() == ButtonType.YES) {
            mnSaveOnAction(event);
            return;
        }
        stage.close();
    }

    @FXML
    void mnNewOnAction(ActionEvent event) throws IOException {
        if (stage.getTitle().contains("*")) {
            Optional<ButtonType> optButton = new Alert(Alert.AlertType.CONFIRMATION,
                    String.format("Do you want to save the current file?"),
                    ButtonType.YES, ButtonType.NO,ButtonType.CANCEL).showAndWait();

            if (optButton.isEmpty() || optButton.get() == ButtonType.CANCEL) {
                return;
            }

            if (optButton.get() == ButtonType.NO) {
                txtEditor.clear();
                stage.setTitle("Untitled Document");
                return;
            }
            mnSaveOnAction(event);
            txtEditor.clear();
            stage.setTitle("Untitled Document");
        }
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
        Printer defaultPrinter = Printer.getDefaultPrinter();
        if (defaultPrinter == null) {
            new Alert(Alert.AlertType.ERROR, "No default printer has been configured!").showAndWait();
            return;
        }
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob.showPrintDialog(txtEditor.getScene().getWindow())) {
            PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
            printerJob.getJobSettings().setPageLayout(pageLayout);
            boolean success = printerJob.printPage(txtEditor);
            if (success) {
                printerJob.endJob();
            }
        }
    }

    @FXML
    void mnSaveAsOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the text file");
        fileChooser.setInitialFileName("Untitled Document.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Text Files (*.txt, *.html)", "*.txt", "*.html"));
        File file = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
        if (file == null) return;

        FileOutputStream fos = new FileOutputStream(file);
        String text= txtEditor.getText();
        byte[] bytes = text.getBytes();
        fos.write(bytes);

        currentFile = file;

        stage.setTitle(file.getName());
        fos.close();
    }

    @FXML
    void mnSaveOnAction(ActionEvent event) throws IOException {
        if (currentFile == null) {
            mnSaveAsOnAction(event);
        } else {
            FileOutputStream fos = new FileOutputStream(currentFile);
            String text = txtEditor.getText();
            byte[] bytes = text.getBytes();
            fos.write(bytes);
            stage.setTitle(currentFile.getName());
            fos.close();
        }
    }

    @FXML
    void rootOnDragDropped(DragEvent event) throws IOException {
        event.setDropCompleted(true);
        File droppedFile = event.getDragboard().getFiles().get(0);
        FileInputStream fis = new FileInputStream(droppedFile);
        byte[] bytes = fis.readAllBytes();
        fis.close();
        txtEditor.setText(new String(bytes));
    }

    @FXML
    void rootOnDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
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
}
