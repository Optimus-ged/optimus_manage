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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import static pack.controller.ctrl_DetailAppro.txtDesignation1;
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
    private cls_controller ctrl;

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
    @FXML
    private StackPane containerAppro;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        ctrl.ChargememtCompression(txtNomFsseur, "fournisseur", "nom");
        ctrl.ChargememtCompression(txtDesiProduit, "produit", "designation");
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
        String query = "SELECT MAX(id) x FROM entete_appro";
        resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("x");
        }
        return null;
    }

    public void initData(String ids) {
        try {
            String query = "SELECT entete_appro.id,designation,detail_appro.qte,produit.pu FROM `detail_appro` INNER JOIN produit ON produit.id=detail_appro.idProduit INNER JOIN entete_appro ON entete_appro.id=detail_appro.idEnteteAppro where entete_appro.id='" + ids + "'";
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
            
            txtDesignation1 = designation.get(index).toString();
            txtPu1 = punitaire.get(index).toString() ;
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
                    initCard();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ctrl_Appros.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
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

    String telephone(String nom) throws ClassNotFoundException, SQLException {
        String query = "SELECT telephone FROM fournisseur WHERE nom = '" + nom + "'";
        resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("telephone");
        }
        return null;
    }

    String pu(String desiProduit) throws ClassNotFoundException, SQLException {
        String query = "SELECT pu FROM produit WHERE designation = '" + desiProduit + "'";
        resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("pu");
        }
        return null;
    }

    @FXML
    private void telephoneFsseur(KeyEvent event) throws ClassNotFoundException, SQLException {
        txtTelephoneFsseur.setText(telephone(txtNomFsseur.getText()));
    }

    @FXML
    private void puProduit(KeyEvent event) throws ClassNotFoundException, SQLException {
        txtPuProduit.setText(pu(txtDesiProduit.getText()));
    }

    @FXML
    private void shoFsseurDialog(MouseEvent event) throws IOException {
        ctrl.dialogue(containerAppro, "/pack/ui/ui_Fsseur.fxml");
    }

    @FXML
    private void showProduitDialog(MouseEvent event) throws IOException {
        ctrl.dialogue(containerAppro, "/pack/ui/ui_produit.fxml");
    }
}
