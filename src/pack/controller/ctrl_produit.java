/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import pack.main.cls_controller;
import pack.model.MdlProduit;
import static pack.controller.ctrl_ProduitItem.designation_;
import static pack.controller.ctrl_ProduitItem.pu_;
import static pack.controller.ctrl_ProduitItem.idProduit_;
import pack.model.MdlConnexion;


/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_produit implements Initializable {
    private cls_controller ctrl;
    private MdlProduit pro;
    @FXML
    private Label txtId;
    @FXML
    private TextField txtDesignation;
    @FXML
    private TextField txtPu;
    @FXML
    private Label lblInfo;
    private ResultSet res;
    @FXML
    private FontAwesomeIconView font;
    @FXML
    private JFXListView<?> lstview_produit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        pro = new MdlProduit(txtDesignation, txtPu, txtId, lblInfo, font);
        initList(
              lstview_produit,
              "SELECT id, designation, pu FROM produit",
              "/pack/composants/ui_ProduitItem.fxml"
        );
    }    

    @FXML
    private void btnEnre(ActionEvent event) throws ClassNotFoundException, SQLException {
        pro.produitIn(1);
        initList(
              lstview_produit,
              "SELECT id, designation, pu FROM produit",
              "/pack/composants/ui_ProduitItem.fxml"
        );
    }
    
    private void initList(JFXListView list, String requette, String uiFx){
        try {
            list.getItems().clear();
            res = MdlConnexion.getCnx().createStatement().executeQuery(requette);
            while(res.next()){
                idProduit_ = res.getString("id");
                designation_ = res.getString("designation");
                pu_ = res.getString("pu") + " Fc";
                list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
            }
        } catch (Exception e) {
        }
    }
}
