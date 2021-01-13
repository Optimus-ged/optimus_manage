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
import pack.traitement.TttFsseur;
import pack.traitement.TttProduit;

/**
 *
 * @author Optimus
 */
public class MdlFsseur {
    private cls_controller ctrl;
    private TttFsseur fss;
    private TextField txtNom, txtAddresse, txtPhone;
    private Label txtId, lblInfo;
    private FontAwesomeIconView font;
    
    public MdlFsseur(Label txtId, Label lblInfo, TextField txtNom, TextField txtAddresse, TextField txtPhone, FontAwesomeIconView font){
        this.txtId = txtId;
        this.txtNom = txtNom;
        this.txtAddresse = txtAddresse;
        this.txtPhone = txtPhone;
        this.txtNom = txtNom;
        this.lblInfo = lblInfo;
        ctrl = new cls_controller();
        fss = new TttFsseur();
    }
    
     public void produitIn(int btn) throws ClassNotFoundException, SQLException{
        if(btn == 1){
            if(champs.champs_vide.isFieldsempty(txtNom, txtAddresse, txtPhone)){
                ctrl.showMssge(lblInfo, font, "Veuillez remplir tous les champs svp !", 0);        
            }else{
                fss = new TttFsseur(
                        Integer.parseInt(txtId.getText()),
                        txtNom.getText(), 
                        txtAddresse.getText(), 
                        txtPhone.getText()
                        
                );
                fss.fournisseur(1);
                ctrl.alerteInformation("Information", "Enregistre !!!");            
            }
        }
    }
}
