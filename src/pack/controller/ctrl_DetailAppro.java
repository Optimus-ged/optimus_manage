/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_DetailAppro implements Initializable {

    @FXML
    private Label txtId;
    @FXML
    private Label txtDesignation;
    @FXML
    private Label txtPu;
    @FXML
    private Label txtQte;
    @FXML
    public static FontAwesomeIconView close;
    public static String txtDesignation1;
    public static String txtPu1;
    public static String txtQte1;
    public static FontAwesomeIconView close1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtDesignation.setText(txtDesignation1);
        txtPu.setText(txtPu1);
        txtQte.setText(txtQte1);
        close1 = close;

    }

}
