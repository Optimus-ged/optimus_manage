/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.model;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pack.main.cls_controller;
import pack.traitement.TttClient;
import pack.traitement.TttProduit;

/**
 *
 * @author Optimus
 */
public class MdlProduit {
    private cls_controller ctrl;
    private TttProduit pro;
    private TextField txtDesignation, txtPu;
    private Label txtId, lblInfo;
    private FontAwesomeIconView font;
    
    public MdlProduit(TextField txtDesignation, TextField txtPu, Label txtId, Label lblInfo, FontAwesomeIconView font){
        this.txtDesignation = txtDesignation;
        this.txtId = txtId;
        this.txtPu = txtPu;
        this.lblInfo = lblInfo;
        this.font = font;
        ctrl = new cls_controller();
        pro = new TttProduit();
    }
    
    public void produitIn(int btn) throws ClassNotFoundException, SQLException{
        if(btn == 1){
            if(champs.champs_vide.isFieldsempty(txtDesignation, txtPu)){
                ctrl.showMssge(lblInfo, font, "Veuillez remplir tous les champs svp !", 0);        
            }else{
                pro = new TttProduit(
                        txtDesignation.getText(), 
                        Float.parseFloat(txtPu.getText()) , 
                        Integer.parseInt(txtId.getText())
                );
                pro.produit(1);
                ctrl.alerteInformation("Information", "Enregistre !!!");            
            }
        }
    }
}
