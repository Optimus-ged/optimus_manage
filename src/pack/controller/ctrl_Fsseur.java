/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

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
import static pack.controller.ctrl_FsseurItem.idFsseur_;
import static pack.controller.ctrl_FsseurItem.nom_;
import static pack.controller.ctrl_FsseurItem.addresse_;
import static pack.controller.ctrl_FsseurItem.contact_;
import pack.main.cls_controller;
import pack.model.MdlClient;
import pack.model.MdlConnexion;
import pack.model.MdlFsseur;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Fsseur implements Initializable {
     private cls_controller ctrl;
    private MdlFsseur fss;
    
    @FXML
    private Label txtId;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtAddresse;
    @FXML
    private TextField txtPhone;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    private ResultSet res;
    
    @FXML
    private JFXListView<?> lstview_fsseur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        fss = new MdlFsseur(txtId, lblInfo, txtNom, txtAddresse, txtPhone, font);
         initList(
              lstview_fsseur,
              "SELECT * FROM fournisseur",
              "/pack/composants/ui_FsseurItem.fxml"
        );
    }    

    @FXML
    private void btnEnre(ActionEvent event) throws ClassNotFoundException, SQLException {
        fss.fsseurIn(1);
    }
    
    private void initList(JFXListView list, String requette, String uiFx){
        try {
            list.getItems().clear();
            res = MdlConnexion.getCnx().createStatement().executeQuery(requette);
            while(res.next()){
                idFsseur_ = res.getString("id");
                nom_ = res.getString("nom");
                addresse_ = res.getString("addresse");
                contact_ = res.getString("telephone");
                list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
            }
        } catch (Exception e) {
        }
    }
    
}
