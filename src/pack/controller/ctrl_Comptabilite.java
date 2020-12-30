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
import pack.model.MdlConnexion;
import pack.model.MdlDetailFacture;
import pack.model.MdlDetailsAppro;
import pack.model.MdlEnteteAppro;
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
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    @FXML
    private TextField txtPuProduit;
    @FXML
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void enregAppros(ActionEvent event) {
    }

    @FXML
    private void ajouterProduit(ActionEvent event) {
        if (idFacture.getText().equals("0")) {
            System.out.println("insertion entete et details");
            // Commentaire
            // Insertion dans la table entete
            entete = new MdlEnteteFacture(txtNomClient.getText());
            try {
                if (getInstance().isSave(entete, 1) == true) {
                    idFacture.setText(initNum());
                    System.out.println("entettttttttttttttttttttttt");
                    System.out.println(initNum());
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ctrl_Comptabilite.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Commentaire
            // Insertion dans la table details
            detail = new MdlDetailFacture(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idFacture.getText()));
            try {
                if (getInstance().isSave(detail, 1) == true) {
                    initCard();
                    System.out.println("detaiiiiiiiiiiiiiiiiiiiiiiillll");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ctrl_Comptabilite.class.getName()).log(Level.SEVERE, null, ex);
            }   
        } else {
//             Commentaire
//             Insertion dans la table details
             System.out.println("Etape une ");
            detail = new MdlDetailFacture(txtDesiProduit.getText(), Float.parseFloat(txtQteProduit.getText()), Integer.parseInt(idFacture.getText()));
            System.out.println("Etape deux ");
            try {
                System.out.println("Dans try catch ");
                if (getInstance().isSave(detail, 1) == true) {
                    System.out.println("Dans get Instance ");
                    initCard();
                    System.out.println("detaiiiiiiiiiiiiiiiiiiiiiiillll");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ctrl_Comptabilite.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    String initNum() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(id) x FROM entete_facture";
        resultSet = MdlConnexion.getCnx().createStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("x");
        }
        return null;
    }

    public void initData(String ids) {
        try {
            String query = "SELECT entete_facture.id,designation,detailsappro.qte,produit.pu FROM `detailsappro` INNER JOIN "
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
        initData(idFacture.getText());
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

}
