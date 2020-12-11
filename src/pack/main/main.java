/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.main;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Optimus
 */
public class main extends Application {
    Parent root;
    double xOffset, yOffset;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       root = FXMLLoader.load(getClass().getResource("/pack/ui/ui_Login.fxml"));
       Scene scene = new Scene(root);
       primaryStage.initStyle(StageStyle.TRANSPARENT);
       primaryStage.setScene(scene);
       primaryStage.show();
       scene.setFill(Color.TRANSPARENT);
       root.setOnMousePressed(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event){
               xOffset = event.getSceneX();
               yOffset = event.getSceneY();
           }
       });
       root.setOnMouseDragged(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event){
               primaryStage.setX(event.getScreenX() - xOffset);
               primaryStage.setY(event.getScreenY() - yOffset);
           }
       });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
