/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static pack.controller.ctrl_Appros.list_tousLesAppros2;
import static pack.controller.ctrl_Comptabilite.list_tousLesFactures2;
import static pack.controller.ctrl_tousLesAppros.addresseFsseir_;
import static pack.controller.ctrl_tousLesAppros.contactA_;
import static pack.controller.ctrl_tousLesAppros.idFsseur_;
import static pack.controller.ctrl_tousLesAppros.nomFsseur_;
import static pack.controller.ctrl_tousLesFactures.contact_;
import static pack.controller.ctrl_tousLesFactures.idFacture_;
import static pack.controller.ctrl_tousLesFactures.nomClient_;
import static pack.controller.ctrl_tousLesFactures.sexe_;
import pack.model.MdlConnexion;
import pack.other.cls_imprimer;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Principal implements Initializable {

    // Inportation objets
    private pack.main.cls_controller ctrl;
    private ResultSet resultSet;

    @FXML
    private StackPane principalContainer;
    @FXML
    private AnchorPane anp_principal;
    @FXML
    private StackPane HomeContainer;

    public static StackPane HomeContainer2;
    @FXML
    private Label lbl_user_connect;
    @FXML
    private Label lbl_post_user;

    public static String lbl_user_connect_;
    public static String lbl_post_user_;
    @FXML
    private Label lbl_pour_apprp;
    @FXML
    private TextField txtRecherche;
    @FXML
    private TextField txtDate;
    @FXML
    private JFXDatePicker datePicker;
    private String formatedDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialisation objets
        ctrl = new pack.main.cls_controller();
        HomeContainer2 = HomeContainer;
        lbl_user_connect.setText(lbl_user_connect_);
        lbl_post_user.setText(lbl_post_user_);
    }

    @FXML
    private void deconnection(ActionEvent event) throws IOException {
        ((Stage) anp_principal.getScene().getWindow()).close();
        ctrl._interfaceNoBoarder("/pack/ui/ui_Login.fxml");
    }

    @FXML
    private void onAccueilCliked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Accueil.fxml");
    }

    @FXML
    private void onComptabiliteCliked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Comptabilite.fxml");
        lbl_pour_apprp.setText("2");
    }

    @FXML
    private void onRapportsClicked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Rapports.fxml");
    }

    @FXML
    private void onParametresClicked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Parametres.fxml");
    }

    @FXML
    private void onNouvClientCliked(MouseEvent event) throws IOException {
        ctrl.dialogue(HomeContainer2, "/pack/ui/ui_Client.fxml");
    }

    @FXML
    private void onNouvAgentClicked(MouseEvent event) throws IOException {
        ctrl.dialogue(HomeContainer2, "/pack/ui/ui_Agent.fxml");
    }

    @FXML
    private void onApproClicked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Appros.fxml");
        lbl_pour_apprp.setText("1");
    }

    private void onCreditCliked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_CreditVente.fxml");
    }

    @FXML
    private void onPaiementCliked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Paiement.fxml");
    }

    @FXML
    private void recherche_all(KeyEvent event) {
        if (lbl_pour_apprp.getText().equals("1")) {
            initListView(
                    list_tousLesAppros2,
                    1,
                    "/pack/composants/ui_tousLesAppros.fxml",
                    " SELECT ent.id, nom, telephone, addresse FROM entete_appro AS ent"
                    + " INNER JOIN fournisseur AS f ON f.id = ent.idFournisseur WHERE nom LIKE '%" + txtRecherche.getText() + "%' ORDER BY ent.id ASC"
            );
        } else {
            initListView(
                    list_tousLesFactures2,
                    2,
                    "/pack/composants/ui_tousLesFactures.fxml",
                    "SELECT ent.id, nom, sexe, telephone FROM entete_facture AS ent INNER JOIN client AS cli ON ent.idClient = cli.id WHERE nom LIKE '%" + txtRecherche.getText() + "%' ORDER BY ent.id ASC"
            );
        }
    }

    private void initListView(JFXListView<?> list, int btn, String uiFx, String requette) {
        list.getItems().clear();
        try {
            resultSet = MdlConnexion.getCnx().createStatement().executeQuery(requette);
            if (btn == 1) {
                while (resultSet.next()) {
                    idFsseur_ = resultSet.getString("id");
                    nomFsseur_ = resultSet.getString("nom");
                    contactA_ = resultSet.getString("telephone");
                    addresseFsseir_ = resultSet.getString("addresse");
                    list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
                }
            } else {
                while (resultSet.next()) {
                    idFacture_ = resultSet.getString("id");
                    nomClient_ = resultSet.getString("nom");
                    sexe_ = resultSet.getString("sexe");
                    contact_ = resultSet.getString("telephone");
                    list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
                }
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void pickDate(ActionEvent event) {
        String date = datePicker.getValue().toString();
        String jour = date.substring(8, 10);
        String mois = date.substring(5, 7);
        String annee = date.substring(0, 4);
        formatedDate = jour + "/" + mois + "/"+ annee;
        txtDate.setText(formatedDate);    
        
    }

    private void printDoc(MouseEvent event) throws ClassNotFoundException, SQLException {
       
    }

    @FXML
    private void printDoc(ActionEvent event) throws ClassNotFoundException, SQLException {
         cls_imprimer._impresion(
                "SELECT * FROM view_facture_finale WHERE id_entete = 91", 
                HomeContainer2, 
                "F:\\projects\\java-desk\\OptimusManage\\src\\pack\\my_reports\\client_facture.jrxml"
        );
    }
     
}
