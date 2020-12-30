/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import static pack.controller.ctrl_DetailAppro.txtDesignation1;
import static pack.controller.ctrl_DetailAppro.txtId1;
import static pack.controller.ctrl_DetailAppro.txtPu1;
import static pack.controller.ctrl_DetailAppro.txtQte1;
import pack.main.cls_controller;
import pack.model.MdlConnexion;
import pack.model.MdlDetailsAppro;
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
    private VBox vboxDetail;
    
    private cls_controller ctrl;

    private ResultSet resultSet;
    private Statement statement;
    private MdlEnteteAppro entete;
    private MdlDetailsAppro detail;
    @FXML
    private Label idAppro;
    public ArrayList id = new ArrayList();
    public ArrayList designation = new ArrayList();
    public ArrayList punitaire = new ArrayList();
    public ArrayList quantite = new ArrayList();
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void enregAppros(ActionEvent event) {
        idAppro.setText("0");
        txtNomFsseur.setText("");
        txtDesiProduit.setText("");
        txtPuProduit.setText("");
        txtTelephoneFsseur.setText("");
        txtQteProduit.setText("");
       ctrl.alerteInformation("Information", "Enregistre avec succes !!!");
    }

//    void initEvent() {
//        initCard();
//        txtQteProduit.setOnKeyReleased((e) -> {
//            if (e.getCode().ENTER == KeyCode.ENTER) {
//                if (!idAppro.getText().equals("0")) {
//                    detail = new MdlDetailsAppro(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idAppro.getText()));
//                    try {
//                        if (getInstance().isSave(detail) == true) {
//                            initCard();
//                        }
//                    } catch (SQLException | ClassNotFoundException ex) {
//                        Logger.getLogger(ctrl_Appros.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } else {
//                    entete = new MdlEnteteAppro(txtNomFsseur.getText());
//                    try {
//                        if (getInstance().isSave(entete) == true) {
//                            idAppro.setText(initNum());
//                        }
//                    } catch (SQLException | ClassNotFoundException ex) {
//                        Logger.getLogger(ctrl_Appros.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//
//            }
//
//        });
//    }

    String initNum() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(id) x FROM approentete";
        resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("x");
        }
        return null;
    }

    public void initData(String ids) {
        try {
            String query = "SELECT approentete.id,designation,detailsappro.qte,produit.pu FROM `detailsappro` INNER JOIN "
                    + "                    produit ON produit.id=detailsappro.idProduit "
                    + "                    INNER JOIN approentete "
                    + "                    ON approentete.id=detailsappro.idEnteteAppro where approentete.id='" + ids + "'";
            resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id"));
                id.add(resultSet.getString("id"));
                designation.add(resultSet.getString("designation"));
                punitaire.add(resultSet.getString("pu"));
                quantite.add(resultSet.getString("qte"));
            }

        } catch (ClassNotFoundException | SQLException ex) {
        }
    }


    public void initCard() {

        id.clear();
        designation.clear();
        punitaire.clear();
        quantite.clear();
        initData(idAppro.getText());
        Node[] node = new Node[id.size()];
        vboxDetail.getChildren().clear();
        vboxDetail.setSpacing(2);

        for (int index = 0; index < id.size(); index++) {
            txtId1 = id.get(index).toString();
            txtDesignation1 = designation.get(index).toString();
            txtPu1 = punitaire.get(index).toString();
            txtQte1 = quantite.get(index).toString();
            try {
                node[index] = FXMLLoader.load(getClass().getResource("/pack/composants/ui_DetailAppro.fxml"));
            } catch (IOException ex) {
//                    Logger.getLogger(CtrlUsers.class.getName()).log(Level.SEVERE, null, ex);
            }
            vboxDetail.getChildren().add(node[index]);
        }

    }

    @FXML
    private void ajouterProduit(ActionEvent event) {
        if (!idAppro.getText().equals("0")) {
            // Commentaire
            // Insertion dans la table details
            detail = new MdlDetailsAppro(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idAppro.getText()));
            try {
                if (getInstance().isSave(detail, 2) == true) {
                    System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                    initCard();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ctrl_Appros.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("insertion entete et details");
            // Commentaire
            // Insertion dans la table entete
            entete = new MdlEnteteAppro(txtNomFsseur.getText());
            try {
                if (getInstance().isSave(entete, 2) == true) {
                    idAppro.setText(initNum());
                    
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ctrl_Appros.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Commentaire
            // Insertion dans la table details
            detail = new MdlDetailsAppro(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idAppro.getText()));
            try {
                if (getInstance().isSave(detail, 2) == true) {
                    initCard();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ctrl_Appros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
