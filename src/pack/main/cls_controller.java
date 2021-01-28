/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.main;

import animatefx.animation.ZoomInLeft;
import animatefx.animation.ZoomInRight;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import pack.model.MdlConnexion;

/**
 *
 * @author Optimus
 */
public class cls_controller {

    double xOffset, yOffset;
    private PreparedStatement pst;
    private ResultSet rs;

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

    public void chargeCmbSexe(ComboBox<String> c) {
        c.getItems().addAll("M", "F");
    }

    public static void alerteInformation(String titre, String message) {

        Notifications notificationBuilder = Notifications.create()
                .title(titre)
                .text("\n                               " + message)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BASELINE_RIGHT)
                .onAction((ActionEvent event) -> {
                });

        notificationBuilder.showInformation();
    }

    public void _interfaceNoBoarder(String fichierFXML) throws IOException {
        Stage stg = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(fichierFXML));
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stg.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stg.setX(event.getScreenX() - xOffset);
                stg.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public void showMssge(Label lab, FontAwesomeIconView icon, String mssg, int etat) {
        if (etat == 1) {
            lab.setVisible(true);
            icon.setVisible(true);
            icon.setIcon(FontAwesomeIcon.CHECK);
            icon.setFill(Paint.valueOf("#a7c9b0"));
            lab.setTextFill(Color.web("#a7c9b0"));
            lab.setText(mssg);
            new ZoomInRight(lab).play();
            new ZoomInRight(icon).play();
        } else {
            lab.setVisible(true);
            icon.setVisible(true);
            icon.setIcon(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
            icon.setFill(Paint.valueOf("#ff0000"));
            lab.setTextFill(Color.RED);
            lab.setText(mssg);
            new ZoomInRight(lab).play();
            new ZoomInRight(icon).play();
        }
        try {
            lab.setVisible(true);

            Thread th = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        new ZoomInLeft(lab).play();
                        new ZoomInLeft(icon).play();
                        lab.setVisible(false);
                        icon.setVisible(false);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(cls_controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            th.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChargememtCompression(TextField textFied, String Table, String Colonne) {
        textFied.setOnMouseClicked((e) -> {
            try {
                TextFields.bindAutoCompletion(textFied, AutoCompression(Table, Colonne));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(cls_controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public ObservableList AutoCompression(String Table, String Colone) throws ClassNotFoundException {
        ObservableList list = FXCollections.observableArrayList();
        try {
            pst = MdlConnexion.getCnx().prepareStatement("Select " + Colone + " From " + Table);
            rs = pst.executeQuery();
            while (rs.next()) {
                list.addAll(rs.getString(Colone));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }
    
    public void dialogue(StackPane stp, String fichierFXML) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fichierFXML));
        JFXDialogLayout dl = new JFXDialogLayout();
        dl.setBody(root);

        JFXDialog dialog = new JFXDialog(stp, dl, JFXDialog.DialogTransition.CENTER);
        dialog.show(stp);
    }
    
     public boolean isValidMail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }
     
     
    public boolean isNumber(String txtString) {
        char[] tab = txtString.toCharArray();
        boolean estUnNombre = true;
        for (int i = 0; i < tab.length; i++) {
            if (Character.isDigit(tab[i])) {
            } // ne rien faire ;-)
            else {
                if (tab[i] != '.' && tab[i] != '-' && tab[i] != '*') {
                    estUnNombre = false;
                }
            } // remplace le point '.' par virgule ',' ou ajoute un caractère comme '+' ;-)
        }
        return estUnNombre;
    }
    
    private static Pattern p;
    private static Matcher m;

    public boolean isValideTel(String txtString) {
        p = Pattern.compile("^[+0-9-\\(\\)\\s]*{6,14}$");
        m = p.matcher(txtString);
        return m.matches();
    }
}
