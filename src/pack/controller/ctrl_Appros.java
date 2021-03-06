/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import static pack.controller.ctrl_produitDetail.txtPu_;
import static pack.controller.ctrl_produitDetail.txtQte_;
import static pack.controller.ctrl_produitDetail.txtDesignation_;
import static pack.controller.ctrl_tousLesAppros.addresseFsseir_;
import static pack.controller.ctrl_tousLesAppros.nomFsseur_;
import static pack.controller.ctrl_tousLesAppros.idFsseur_;
import static pack.controller.ctrl_tousLesAppros.contactA_;
import static pack.controller.ctrl_Principal.HomeContainer2;
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
    private TextField txtPuProduit;
    @FXML
    private JFXButton btnEnre;

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
    @FXML
    private JFXListView<?> lstview_produit;
    @FXML
    private JFXListView<?> list_tousLesAppros;

    public static JFXListView<?> list_tousLesAppros2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list_tousLesAppros2 = list_tousLesAppros;
        ctrl = new cls_controller();
        ctrl.ChargememtCompression(txtNomFsseur, "fournisseur", "nom");
        ctrl.ChargememtCompression(txtDesiProduit, "produit", "designation");
        initListView(
                list_tousLesAppros,
                2,
                "/pack/composants/ui_tousLesAppros.fxml",
                " SELECT ent.id, nom, telephone, addresse FROM entete_appro AS ent\n"
                + "INNER JOIN fournisseur AS f ON f.id = ent.idFournisseur"
        );
//        initListView(
//                lstview_produit,
//                1,
//                "/pack/composants/ui_produitDetail.fxml",
//                "SELECT entete_appro.id,designation,detail_appro.qte, pu_d_achat FROM `detail_appro` INNER JOIN produit ON produit.id=detail_appro.idProduit INNER JOIN entete_appro ON entete_appro.id=detail_appro.idEnteteAppro where entete_appro.id= 23 "
//        );
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
        initListView(
                list_tousLesAppros,
                2,
                "/pack/composants/ui_tousLesAppros.fxml",
                " SELECT ent.id, nom, telephone, addresse FROM entete_appro AS ent\n"
                + "INNER JOIN fournisseur AS f ON f.id = ent.idFournisseur"
        );
    }

    String initNum() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(id) x FROM entete_appro";
        resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("x");
        }
        return null;
    }

    @FXML
    private void ajouterProduit(ActionEvent event) {
        if (champs.champs_vide.isFieldsempty(txtNomFsseur, txtTelephoneFsseur, txtPuProduit, txtDesiProduit, txtPuProduit, txtQteProduit)) {
            ctrl.alerteInformation("Erreur", "Veuillez completer tous les champs SVP !!!");
        } else {
            if (ctrl.isNumber(txtPuProduit.getText()) && ctrl.isNumber(txtQteProduit.getText())) {
                System.out.println("ce sont tous des nombres");
                if (!idAppro.getText().equals("0")) {
                    // Commentaire
                    // Insertion dans la table details
                    detail = new MdlDetailsAppro(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idAppro.getText()), Float.parseFloat(txtPuProduit.getText()));
                    try {
                        if (getInstance().isSave(detail, 2) == true) {
//                    initCard();
                            initListView(
                                    lstview_produit,
                                    1,
                                    "/pack/composants/ui_produitDetail.fxml",
                                    "SELECT entete_appro.id,designation,detail_appro.qte, pu_d_achat FROM `detail_appro` INNER JOIN produit ON produit.id=detail_appro.idProduit INNER JOIN entete_appro ON entete_appro.id=detail_appro.idEnteteAppro where entete_appro.id='" + idAppro.getText() + "' "
                            );
//                            initListView(
//                                    list_tousLesAppros,
//                                    2,
//                                    "/pack/composants/ui_tousLesAppros.fxml",
//                                    " SELECT ent.id, nom, telephone, addresse FROM entete_appro AS ent\n"
//                                    + "INNER JOIN fournisseur AS f ON f.id = ent.idFournisseur"
//                            );
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
                    detail = new MdlDetailsAppro(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idAppro.getText()), Float.parseFloat(txtPuProduit.getText()));
                    try {
                        if (getInstance().isSave(detail, 2) == true) {

                            initListView(
                                    lstview_produit,
                                    1,
                                    "/pack/composants/ui_produitDetail.fxml",
                                    "SELECT entete_appro.id,designation,detail_appro.qte, pu_d_achat FROM `detail_appro` INNER JOIN produit ON produit.id=detail_appro.idProduit INNER JOIN entete_appro ON entete_appro.id=detail_appro.idEnteteAppro where entete_appro.id='" + idAppro.getText() + "' "
                            );
//                            initListView(
//                                    list_tousLesAppros,
//                                    2,
//                                    "/pack/composants/ui_tousLesAppros.fxml",
//                                    " SELECT ent.id, nom, telephone, addresse FROM entete_appro AS ent\n"
//                                    + "INNER JOIN fournisseur AS f ON f.id = ent.idFournisseur"
//                            );
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ctrl_Appros.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                ctrl.alerteInformation("Erreur", "Pu ou Quantite invalide\nVerifier svp !!!");
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
        ctrl.dialogue(HomeContainer2, "/pack/ui/ui_Fsseur.fxml");
    }

    @FXML
    private void showProduitDialog(MouseEvent event) throws IOException {
        ctrl.dialogue(HomeContainer2, "/pack/ui/ui_produit.fxml");
    }

    private void initListView(JFXListView list, int btn, String uiFx, String requette) {
        list.getItems().clear();
        System.out.println("55555555555555555555555555555");
        System.out.println("55555555555555555555555555555");
        System.out.println("555555555555555555555555555555555");

        try {
            resultSet = MdlConnexion.getCnx().createStatement().executeQuery(requette);
            if (btn == 1) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("designation") + "55555555555555555555555555555");
                    System.out.println(resultSet.getString("pu_d_achat") + "55555555555555555555555555555");
                    System.out.println(resultSet.getString("qte") + "555555555555555555555555555555555");

                    txtDesignation_ = resultSet.getString("designation");
                    txtPu_ = resultSet.getString("pu_d_achat") + "Fc";
                    txtQte_ = resultSet.getString("qte");
                    list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
                }
            } else {
                while (resultSet.next()) {
                    idFsseur_ = resultSet.getString("id");
                    nomFsseur_ = resultSet.getString("nom");
                    contactA_ = resultSet.getString("telephone");
                    addresseFsseir_ = resultSet.getString("addresse");
                    list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
                }
            }
        } catch (Exception e) {
        }
    }
}
