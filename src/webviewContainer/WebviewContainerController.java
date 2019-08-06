/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webviewContainer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Gabriel Nogueira
 */
public class WebviewContainerController implements Initializable {

    WebEngine webEngine;
    private int webviewW = 360;
    private int webviewH = 480;

    @FXML
    private TextField txt_windowWidth;
    @FXML
    private TextField txt_windowHeight;
    @FXML
    private WebView webview;
    @FXML
    private AnchorPane pane_webviewAnchorPane;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pane pane_menuButtonDeviceIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        webEngine = webview.getEngine();
        Platform.runLater(() -> {
            rootPane.prefWidthProperty().bind(pane_webviewAnchorPane.widthProperty());
            rootPane.prefHeightProperty().bind(pane_webviewAnchorPane.heightProperty());

            txt_windowWidth.setText("" + webviewW);
            txt_windowHeight.setText("" + webviewH);
        });
    }

    public void setURL(String url) {
        webEngine.load(url);
    }

    public void setWindowSize(int w, int h) {
        webviewW = w;
        webviewH = h;
        setWindowSize();
    }

    @FXML
    private void handle_setWindowSize(ActionEvent event) {
        webviewW = Integer.parseInt(txt_windowWidth.getText());
        webviewH = Integer.parseInt(txt_windowHeight.getText());
        setWindowSize();
    }

    private void setWindowSize() {
        if (webviewW < 300) {
            webviewW = 300;
        }

        if (webviewH < 300) {
            webviewH = 300;
        }

        pane_webviewAnchorPane.setMinSize(webviewW, webviewH);
        pane_webviewAnchorPane.setPrefSize(webviewW, webviewH);
        pane_webviewAnchorPane.setMaxSize(webviewW, webviewH);
        rootPane.setMaxSize(webviewW + 28, webviewH + 28);

        txt_windowWidth.setText("" + webviewW);
        txt_windowHeight.setText("" + webviewH);
    }

    @FXML
    private void handle_rotate(ActionEvent event) {
        int x = webviewW;
        webviewW = webviewH;
        webviewH = x;
        setWindowSize();
    }

    @FXML
    private void btn_deleteContainer(ActionEvent event) {
        ((FlowPane) rootPane.getParent()).getChildren().remove(rootPane);
    }

    private void test(ActionEvent event) {
        System.out.println("hehe: " + webview.getClass().toString().contains("javafx.scene.web.WebView"));
    }

    @FXML
    private void handle_setPhoneSize(ActionEvent event) {
        webviewW = 360;
        webviewH = 480;
        setWindowSize();
        pane_menuButtonDeviceIcon.getStyleClass().clear();
        pane_menuButtonDeviceIcon.getStyleClass().add("icon");
        pane_menuButtonDeviceIcon.getStyleClass().add("devicePhoneIcon");
        pane_menuButtonDeviceIcon.setPrefWidth(10);
        pane_menuButtonDeviceIcon.getStyleClass().clear();
        pane_menuButtonDeviceIcon.getStyleClass().add("icon");
        pane_menuButtonDeviceIcon.getStyleClass().add("devicePhoneIcon");
    }

    @FXML
    private void handle_setTabletSize(ActionEvent event) {
        webviewW = 768;
        webviewH = 1024;
        setWindowSize();
        pane_menuButtonDeviceIcon.setPrefWidth(15);
        pane_menuButtonDeviceIcon.getStyleClass().clear();
        pane_menuButtonDeviceIcon.getStyleClass().add("icon");
        pane_menuButtonDeviceIcon.getStyleClass().add("devicePhoneIcon");

    }

    @FXML
    private void handle_setDesktopSize(ActionEvent event) {
        webviewW = 1920;
        webviewH = 1080;
        setWindowSize();
        pane_menuButtonDeviceIcon.setPrefWidth(15);
        pane_menuButtonDeviceIcon.getStyleClass().clear();
        pane_menuButtonDeviceIcon.getStyleClass().add("icon");
        pane_menuButtonDeviceIcon.getStyleClass().add("deviceDesktopIcon");
    }

}
