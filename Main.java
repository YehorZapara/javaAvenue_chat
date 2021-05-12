package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Сетевой чат");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}



/* --module-path
"C:\Program Files\Java\javafx-sdk-11.0.2\lib"
--add-modules
javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.web
--add-exports
javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED */