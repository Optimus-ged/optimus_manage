/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.main;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Optimus
 */
public class cls_controller {
    double xOffset, yOffset;
    
     public void _interface(StackPane stp, String fichierFXML) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fichierFXML));
        stp.getChildren().removeAll();
        stp.getChildren().setAll(root);
    }

    public void _interface(String fichierFXML, String str) throws IOException {
        Stage stg = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(fichierFXML));
        stg.setTitle(str);
        stg.setScene(new Scene(root));
        stg.show();
    }
    
    public void _interfaceNoBoarder(String fichierFXML) throws IOException { 
        Stage stg = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(fichierFXML));
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stg.show();
        
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
               stg.setX(event.getScreenX() - xOffset);
               stg.setY(event.getScreenY() - yOffset);
           }
       });
    }
}
