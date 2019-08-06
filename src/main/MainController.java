/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import webviewContainer.WebviewContainerController;

/**
 *
 * @author Gabriel Nogueira
 */
public class MainController implements Initializable {

    File selectedWebpageFile = null;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private FlowPane pane_webviewContainers;
    @FXML
    private TextField txt_addressBar;
    @FXML
    private ScrollPane scrollPane_webviewContainers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            try {
                pane_webviewContainers.prefWrapLengthProperty().bind(scrollPane_webviewContainers.widthProperty());
                createWebview();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void createWebview() throws MalformedURLException, IOException {
        File path = new File("src/webviewContainer/webviewContainer.fxml");
        FXMLLoader loader = new FXMLLoader(path.getAbsoluteFile().toURI().toURL());
        AnchorPane root = loader.load();

        pane_webviewContainers.getChildren().add(root);
        root.toFront();
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);

        loader.<WebviewContainerController>getController().setWindowSize(360, 480);
        loader.<WebviewContainerController>getController().setURL(getUrlFiltered());
    }

    private String getUrlFiltered() {
        String url = txt_addressBar.getText();
        if (!url.contains("http")) {
            System.out.println("colocando http no link");
            url = "http://" + url;
            txt_addressBar.setText(url);
        }
        return url;
    }

    @FXML
    private void handle_loadWebsite(ActionEvent event) {
        List<Node> nodes = paneNodes(pane_webviewContainers);
        String url = txt_addressBar.getText();

        //a função de pegar panes não pega o "get content" do scroll pane
        if (url.contains("C:\\")) {
            loadWebpageFromFile(url);
        } else {
            loadWebpageFromUrl();
        }
    }

    private void loadWebpageFromUrl() {
        List<Node> nodes = paneNodes(pane_webviewContainers);
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getClass().toString().contains("javafx.scene.control.ScrollPane")) {
                //a função de pegar panes não pega o "get content" do scroll pane
                ((WebView) ((ScrollPane) nodes.get(i)).getContent()).getEngine().load(getUrlFiltered());
            }
        }
    }

    private <T extends Pane> List<Node> paneNodes(T parent) {
        return paneNodes(parent, new ArrayList<Node>());
    }

    private <T extends Pane> List<Node> paneNodes(T parent, List<Node> nodes) {
        for (Node node : parent.getChildren()) {
            if (node instanceof Pane) {
                paneNodes((Pane) node, nodes);
            } else {
                nodes.add(node);
            }
        }
        return nodes;
    }

    @FXML
    private void handle_addNewContainer(ActionEvent event) throws IOException {
        createWebview();
    }

    @FXML
    private void handle_loadWebpageFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html"));
        selectedWebpageFile = fileChooser.showOpenDialog(null);

        if (selectedWebpageFile != null) {
            loadWebpageFromFile(selectedWebpageFile);
        }
    }

    private void loadWebpageFromFile(File file) {
        List<Node> nodes = paneNodes(pane_webviewContainers);

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getClass().toString().contains("javafx.scene.control.ScrollPane")) {
                //a função de pegar panes não pega o "get content" do scroll pane
                ((WebView) ((ScrollPane) nodes.get(i)).getContent()).getEngine().load(file.toURI().toString());
            }
        }
        txt_addressBar.setText("" + file.getAbsolutePath());
    }

    private void loadWebpageFromFile(String url) {
        selectedWebpageFile = new File(url);
        List<Node> nodes = paneNodes(pane_webviewContainers);

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getClass().toString().contains("javafx.scene.control.ScrollPane")) {
                //a função de pegar panes não pega o "get content" do scroll pane
                ((WebView) ((ScrollPane) nodes.get(i)).getContent()).getEngine().load(selectedWebpageFile.toURI().toString());
            }
        }
        txt_addressBar.setText("" + selectedWebpageFile.getAbsolutePath());
    }

}
