/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.model;

import java.sql.SQLException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pack.main.cls_controller;
import pack.traitement.TttAgent;
import pack.traitement.TttClient;

/**
 *
 * @author Optimus
 */
public class MdlClient {

    private cls_controller ctrl;
    private TttClient cli;
    private TextField txtNom, txtPrenom, txtPoste;
    private Label txtId;
    private ComboBox<String> cmbSexe;
    
    public MdlClient(TextField txtNom, TextField txtPrenom,  TextField txtpostnom, ComboBox<String> cmbSexe, TextField txtPoste, Label txtId) {
        this.txtNom = txtNom;
        this.txtPrenom = txtPrenom;
        this.cmbSexe = cmbSexe;
        this.txtPoste = txtPoste;
        this.txtId = txtId;
        ctrl = new cls_controller();
        cli = new TttClient();
    }
    
    public void clientIn(int btn) throws ClassNotFoundException, SQLException{
         if(btn == 1){
            if(champs.champs_vide.isFieldsempty(txtNom, txtPrenom, txtPoste)){
                ctrl.alerteInformation("Information", "Attention champs vides !!!");            
            }else{
                cli = new TttClient(
                        txtNom.getText(), 
                        txtPrenom.getText(), 
                        cmbSexe.getValue(),
                        txtPoste.getText(), 
                        Integer.parseInt(txtId.getText())
                );
                cli.client(1);
                ctrl.alerteInformation("Information", "Enregistre !!!");            
            }
        }
          else if (btn == 2){
            if(champs.champs_vide.isFieldsempty(txtNom, txtPrenom,  txtPoste)){
                ctrl.alerteInformation("Information", "Veuillez selectionner une elements !!!");            
            }else{
                  cli = new TttClient(
                        txtNom.getText(), 
                        txtPrenom.getText(), 
                        cmbSexe.getValue(),
                        txtPoste.getText(), 
                        Integer.parseInt(txtId.getText())
                );
                cli.client(2);
                ctrl.alerteInformation("Information", "Supprime !!!");            
            }
        }
     }
}
