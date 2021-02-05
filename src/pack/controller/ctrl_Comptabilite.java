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
import javafx.scene.layout.VBox;
import static pack.controller.ctrl_DetailAppro.txtDesignation1;
import static pack.controller.ctrl_DetailAppro.txtPu1;
import static pack.controller.ctrl_DetailAppro.txtQte1;
import static pack.controller.ctrl_tousLesFactures.contact_;
import static pack.controller.ctrl_tousLesFactures.idFacture_;
import static pack.controller.ctrl_tousLesFactures.nomClient_;
import static pack.controller.ctrl_tousLesFactures.sexe_;
import static pack.controller.ctrl_Principal.HomeContainer2;
import pack.main.cls_controller;
import pack.model.MdlConnexion;
import pack.model.MdlDetailFacture;
import pack.model.MdlEnteteFacture;
import static pack.traitement.TttAppros.getInstance;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Comptabilite implements Initializable {

    @FXML
    private TextField txtNomClient;
    @FXML
    private TextField txtTelephoneClient;
    @FXML
    private TextField txtDesiProduit;
    @FXML
    private TextField txtQteProduit;
    @FXML
    private JFXButton btnEnre;
    @FXML
    private Label txtId;
    @FXML
    private TextField txtPuProduit;
    private VBox vboxDetail;

    private MdlEnteteFacture entete;
    private MdlDetailFacture detail;
    public ArrayList id = new ArrayList();
    public ArrayList designation = new ArrayList();
    public ArrayList punitaire = new ArrayList();
    public ArrayList quantite = new ArrayList();
    @FXML
    private Label idFacture;
    private ResultSet resultSet;
    private cls_controller ctrl;
    @FXML
    private StackPane containerFact;
    @FXML
    private Label lbl_typeVente;
    @FXML
    private Label lblCategorie;
    @FXML
    private Label lbl_rubrique;
    @FXML
    private JFXListView<?> lstview_produit;
    @FXML
    private JFXListView<?> list_tousLesFactures;
    private boolean cash = true;
    public static JFXListView<?> list_tousLesFactures2;
    @FXML
    private JFXButton btn_typeVente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list_tousLesFactures2 = list_tousLesFactures;
        ctrl = new cls_controller();
        ctrl.ChargememtCompression(txtNomClient, "client", "nom");
        ctrl.ChargememtCompression(txtDesiProduit, "produit", "designation");
        initList(
                list_tousLesFactures,
                2,
                "/pack/composants/ui_tousLesFactures.fxml",
                "SELECT ent.id, nom, sexe, telephone FROM entete_facture AS ent INNER JOIN client as cli WHERE ent.idClient = cli.id ORDER BY ent.id ASC"
        );
    }

    @FXML
    private void enregAppros(ActionEvent event) throws ClassNotFoundException, SQLException {
        txtTelephoneClient.setText(telephone(txtNomClient.getText()));
    }

    @FXML
    private void ajouterProduit(ActionEvent event) {
        if (champs.champs_vide.isFieldsempty(txtNomClient, txtTelephoneClient, txtDesiProduit, txtPuProduit, txtQteProduit)) {
            ctrl.alerteInformation("Erreur", "Veuillez completer tous les champs SVP !!!");
        } else {
            if (ctrl.isNumber(txtPuProduit.getText()) && ctrl.isNumber(txtQteProduit.getText())) {
                if (idFacture.getText().equals("0")) {
                    // Commentaire
                    // Insertion dans la table entete
                    System.out.println("Insertion dans la table entete");
                    entete = new MdlEnteteFacture(txtNomClient.getText(), Integer.parseInt(lbl_typeVente.getText()));
                    try {
                        if (getInstance().isSave(entete, 1) == true) {
                            idFacture.setText(initNum());

                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ctrl_Comptabilite.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // Commentaire
                    // Insertion dans la table details
                    detail = new MdlDetailFacture(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idFacture.getText()));
                    System.out.println("Insertion dans la table detail");
                    try {
                        if (getInstance().isSave(detail, 1) == true) {
//                    initCard();
                            initList(
                                    lstview_produit,
                                    1,
                                    "/pack/composants/ui_DetailAppro.fxml",
                                    "SELECT  entete_facture.id,designation, detail_facture.qte, produit.pu FROM `detail_facture` INNER JOIN produit ON produit.id=detail_facture.idProduit INNER JOIN entete_facture ON entete_facture.id=detail_facture.idEnteteFacture where entete_facture.id = '" + idFacture.getText() + "'"
                            );
                            initList(
                                    list_tousLesFactures,
                                    2,
                                    "/pack/composants/ui_tousLesFactures.fxml",
                                    "SELECT ent.id, nom, sexe, telephone FROM entete_facture AS ent\n"
                                    + "INNER JOIN client as cli WHERE ent.idClient = cli.id ORDER BY id ASC"
                            );
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ctrl_Comptabilite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (!idFacture.getText().equals("0")) {
//             Commentaire
//             Insertion dans la table details
                    detail = new MdlDetailFacture(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idFacture.getText()));
                    try {
                        if (getInstance().isSave(detail, 1) == true) {
                            initList(
                                    lstview_produit,
                                    1,
                                    "/pack/composants/ui_DetailAppro.fxml",
                                    "SELECT  entete_facture.id,designation, detail_facture.qte, produit.pu FROM `detail_facture` INNER JOIN produit ON produit.id=detail_facture.idProduit INNER JOIN entete_facture ON entete_facture.id=detail_facture.idEnteteFacture where entete_facture.id = '" + idFacture.getText() + "'"
                            );
                            initList(
                                    list_tousLesFactures,
                                    2,
                                    "/pack/composants/ui_tousLesFactures.fxml",
                                    "SELECT ent.id, nom, sexe, telephone FROM entete_facture AS ent\n"
                                    + "INNER JOIN client as cli WHERE ent.idClient = cli.id ORDER BY id ASC"
                            );
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ctrl_Comptabilite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                ctrl.alerteInformation("Erreur", "Pu ou Quantite invalide\nVerifier svp !!!");
            }
        }
    }

    String telephone(String nom) throws ClassNotFoundException, SQLException {
        String query = "SELECT telephone FROM client WHERE nom = '" + nom + "'";
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

    String initNum() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(id) x FROM entete_facture";
        resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("x");
        }
        return null;
    }

//    public void initData(String ids) {
//        try {
//            String query = "SELECT  entete_facture.id,designation, detail_facture.qte, produit.pu FROM `detail_facture` INNER JOIN produit ON produit.id=detail_facture.idProduit INNER JOIN entete_facture ON entete_facture.id=detail_facture.idEnteteFacture where entete_facture.id = '" + ids + "'";
//            resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
//            while (resultSet.next()) {
//                id.add(resultSet.getString("id"));
//                designation.add(resultSet.getString("designation"));
//                punitaire.add(resultSet.getString("pu"));
//                quantite.add(resultSet.getString("qte"));
//            }
//
//        } catch (ClassNotFoundException | SQLException ex) {
//        }
//    }
//    public void initCard() {
//
//        id.clear();
//        designation.clear();
//        punitaire.clear();
//        quantite.clear();
//        initData(idFacture.getText());
//        Node[] node = new Node[id.size()];
//        vboxDetail.getChildren().clear();
//        vboxDetail.setSpacing(2);
//
//        for (int index = 0; index < id.size(); index++) {
//            txtDesignation1 = designation.get(index).toString();
//            txtPu1 = punitaire.get(index).toString() + " Fc";
//            txtQte1 = quantite.get(index).toString();
//            try {
//                node[index] = FXMLLoader.load(getClass().getResource("/pack/composants/ui_DetailAppro.fxml"));
//            } catch (IOException ex) {
////                    Logger.getLogger(CtrlUsers.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            vboxDetail.getChildren().add(node[index]);
//        }
//
//    }
    @FXML
    private void NumClient(KeyEvent event) throws ClassNotFoundException, SQLException {
        txtTelephoneClient.setText(telephone(txtNomClient.getText()));
    }

    @FXML
    private void desiProduit(KeyEvent event) throws ClassNotFoundException, SQLException {
        txtPuProduit.setText(pu(txtDesiProduit.getText()));
    }

    @FXML
    private void showProductDialog(MouseEvent event) throws IOException {
        ctrl.dialogue(HomeContainer2, "/pack/ui/ui_produit.fxml");
    }

    @FXML
    private void showClientInterface(MouseEvent event) throws IOException {
        ctrl.dialogue(HomeContainer2, "/pack/ui/ui_Client.fxml");
    }

    @FXML
    private void vendreAcredit(ActionEvent event) {
        cash = !cash;
        if (!cash) {
            lbl_typeVente.setText("2");
            btn_typeVente.setText("Cash");
            lblCategorie.setText("a Credit");
            lbl_rubrique.setText("a credit");
            idFacture.setText("0");
        } else {
            lbl_typeVente.setText("1");
            btn_typeVente.setText("Credit");
            lblCategorie.setText("Cash");
            lbl_rubrique.setText("au comptant");
            idFacture.setText("0");
        }

    }

    void initList(JFXListView list, int btn, String uiFx, String requette) {
        try {
            list.getItems().clear();

            resultSet = MdlConnexion.getCnx().createStatement().executeQuery(requette);
            if (btn == 1) {
                while (resultSet.next()) {
                    txtDesignation1 = resultSet.getString("designation");
                    txtPu1 = resultSet.getString("pu") + " Fc";
                    txtQte1 = resultSet.getString("qte");
                    list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
                }
            } else if (btn == 2) {
                while (resultSet.next()) {
                    idFacture_ = resultSet.getString("id");
                    nomClient_ = resultSet.getString("nom");
                    sexe_ = resultSet.getString("sexe");
                    contact_ = resultSet.getString("telephone");
                    list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
                }
            }

        } catch (Exception ex) {
        }
    }

}
