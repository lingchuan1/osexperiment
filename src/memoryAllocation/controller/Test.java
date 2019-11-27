package memoryAllocation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Test implements Initializable {

    @FXML
    private  TextField memory;

    @FXML
    private Button myButton;

    @FXML
    private TextField myField;

    @FXML
    private Group myGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void show(ActionEvent actionEvent) {
        Rectangle rectangle = new Rectangle(0,0,100,200);
        Rectangle rectangle1 = new Rectangle(0,200,100,100);
        rectangle.setFill(Color.GREEN);
        myGroup.getChildren().addAll(rectangle,rectangle1);
    }


}
