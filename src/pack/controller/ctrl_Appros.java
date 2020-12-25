/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import pack.model.MdlConnexion;
import pack.model.MdlEnteteAppro;
import static pack.traitement.TttAppros.getInstance;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Appros implements Initializable {

    @FXML
    private TextField txtNomFsseur;
    @FXML
    private TextField txtTelephoneFsseur;
    @FXML
    private TextField txtDesiProduit;
    @FXML
    private TextField txtQteProduit;
    @FXML
    private Label txtId;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    @FXML
    private TextField txtPuProduit;
    @FXML
    private JFXButton btnEnre;
    @FXML
    private JFXButton btnValider;
    @FXML
    private VBox vboxDetail;

    private ResultSet resultSet;
    private Statement statement;
    private MdlEnteteAppro entete;
    @FXML
    private Label idAppro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initEvent();
    }

    @FXML
    private void enregAppros(ActionEvent event) {
    }

    void initEvent() {
        txtDesiProduit.setOnKeyReleased((e) -> {
            if (e.getCode().ENTER == KeyCode.ENTER) {
                if (!idAppro.getText().equals("0")) {

                } else {
                    entete = new MdlEnteteAppro(txtNomFsseur.getText());
                    try {
                        if (getInstance().isSave(entete) == true) {
                            idAppro.setText(initNum());
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ctrl_Appros.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        });
    }

    String initNum() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(id) x FROM approentete";
        resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("x");
        }
        return null;
    }
}
