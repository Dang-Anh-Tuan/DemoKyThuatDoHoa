/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoktdh;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane parent;

    private boolean brushSelect;
    private boolean eraserSelect;

    @FXML
    private ColorPicker colorpicker;

    @FXML
    private Button clearbtn;

    @FXML
    private ComboBox cbbox;

    // Line
    @FXML
    private TextField linexpoint1;
    @FXML
    private TextField lineypoint1;
    @FXML
    private TextField linexpoint2;
    @FXML
    private TextField lineypoint2;
    @FXML
    private Button drawlinebtn;

    // Circle
    @FXML
    private TextField xc;
    @FXML
    private TextField yc;
    @FXML
    private TextField radius;
    @FXML
    private Button drawcirbtn;

    // Ellclipse
    @FXML
    private TextField xe;
    @FXML
    private TextField ye;
    @FXML
    private TextField ae;
    @FXML
    private TextField be;
    @FXML
    private Button drawelbtn;

    // other button
    @FXML
    private Button brushbtn;

    @FXML
    private Button eraserbtn;

    @FXML
    private Button byebyebtn;

    // other
    @FXML
    private Canvas canvas;

    GraphicsContext brushTool;
    
    
//    @FXML
//    private void handleButtonAction(ActionEvent event) throws IOException {
//        Parent main_page = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        Scene main_page_screen = new Scene(main_page);
//        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        app_stage.hide();
//        app_stage.setScene(main_page_screen);
//        app_stage.show();
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        brushSelect = false;
        eraserSelect = false;
        cbbox.getItems().addAll("Bresenham", "Midpoint");
        cbbox.setValue("Bresenham");
        brushTool = canvas.getGraphicsContext2D();
        brushTool.setFill(javafx.scene.paint.Color.WHITE);
        brushTool.fillRect(0, 0, 1244, 543);
        // Clear Board 
        clearbtn.setOnMousePressed(e -> {
            brushTool.setFill(javafx.scene.paint.Color.WHITE);
            brushTool.fillRect(0, 0, 1244, 543);
        });

        // Draw Line
        try {
            drawlinebtn.setOnMousePressed(e -> {
                int xStart = Integer.parseInt(linexpoint1.getText());
                int yStart = Integer.parseInt(lineypoint1.getText());
                int xEnd = Integer.parseInt(linexpoint2.getText());
                int yEnd = Integer.parseInt(lineypoint2.getText());

                brushTool.setFill(colorpicker.getValue());

                List<Point> points = new ArrayList<>();
                if (cbbox.getValue() == "Bresenham") {
                    points = Service.drawLineByBresemham(xStart, yStart, xEnd, yEnd);
                } else if (cbbox.getValue() == "Midpoint") {
                    points = Service.drawLineByMidpoint(xStart, yStart, xEnd, yEnd);
                }

                Service.paintGeometry(points, brushTool);
                clearTextField();
            });

        } catch (Exception e) {
        }

        // Draw Circle
        try {
            drawcirbtn.setOnMousePressed(e -> {
                int xCenter = Integer.parseInt(xc.getText());
                int yCenter = Integer.parseInt(yc.getText());
                int r = Integer.parseInt(radius.getText());

                brushTool.setFill(colorpicker.getValue());

                List<Point> points = new ArrayList<>();
                if (cbbox.getValue() == "Bresenham") {
                    points = Service.drawCircleByBresemham(xCenter, yCenter, r);
                } else if (cbbox.getValue() == "Midpoint") {
                    points = Service.drawCircleByMidpoint(xCenter, yCenter, r);
                }

                Service.paintGeometry(points, brushTool);
                clearTextField();
            });
        } catch (Exception e) {
        }

        // Draw Ellipse
        try {
            drawelbtn.setOnMousePressed(e -> {
                int xCenter = Integer.parseInt(xe.getText());
                int yCenter = Integer.parseInt(ye.getText());
                int a = Integer.parseInt(ae.getText());
                int b = Integer.parseInt(be.getText());

                brushTool.setFill(colorpicker.getValue());

                List<Point> points = new ArrayList<>();
                points = Service.drawEllipse(xCenter, yCenter, a, b);

                Service.paintGeometry(points, brushTool);
                clearTextField();
            });

        } catch (Exception e) {
        }

        // Brush Tool
        try {
            brushbtn.setOnMousePressed(e -> {
                this.brushSelect = true;
                this.eraserSelect = false;
            });
        } catch (Exception e) {
        }
        // Eraser Tool
        try {
            eraserbtn.setOnMousePressed(e -> {
                this.brushSelect = false;
                this.eraserSelect = true;
            });
        } catch (Exception e) {
        }

        // Draw
        try {
            canvas.setOnMouseDragged(e -> {
                if (this.eraserSelect == true) {
                    double size = 20;
                    double x = e.getX() - size / 2;
                    double y = e.getY() - size / 2;
                    brushTool.setFill(javafx.scene.paint.Color.WHITE);
                    brushTool.fillRoundRect(x, y, size, size, size, size);
                } else if (this.brushSelect == true) {
                    double size = 20;
                    double x = e.getX() - size / 2;
                    double y = e.getY() - size / 2;
                    brushTool.setFill(colorpicker.getValue());
                    brushTool.fillRoundRect(x, y, size, size, size, size);
                }
            });
        } catch (Exception e) {
        }

        try {
            byebyebtn.setOnMousePressed(e -> {
                Service.byebye(brushTool);
            });
        } catch (Exception e) {
        }

    }

    public void clearTextField() {
        List<TextField> textFields = new ArrayList<>();
        textFields.add(be);
        textFields.add(ae);
        textFields.add(ye);
        textFields.add(xe);
        textFields.add(radius);
        textFields.add(yc);
        textFields.add(xc);
        textFields.add(lineypoint2);
        textFields.add(linexpoint2);
        textFields.add(lineypoint1);
        textFields.add(linexpoint1);
        textFields.forEach(item -> {
            item.setText("");
        });
    }

//    public void loadWelcomScreen() {
//        try {
//            StackPane pane = FXMLLoader.load(getClass().getResource("WelcomeView.fxml"));
//
//      
//
//            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
//            fadeIn.setFromValue(0);
//            fadeIn.setToValue(1);
//            fadeIn.setCycleCount(1);
//            System.out.println("Ok");
//            fadeIn.play();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
}
