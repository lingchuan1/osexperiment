package memoryAllocation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,600,600);
        primaryStage.setTitle("动态分区分配方式的模拟");
        primaryStage.setScene(scene);
        primaryStage.show();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(JavaFX.class.getResource("view/test.fxml"));
        FlowPane pane = loader.load();
        root.setCenter(pane);
    }
}
