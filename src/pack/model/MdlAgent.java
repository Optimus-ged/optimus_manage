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

/**
 *
 * @author Optimus
 */
public class MdlAgent {
    private cls_controller ctrl;
    private TttAgent ag;
    private TextField txtNom, txtPrenom, txtpostnom, txtPoste;
     private Label txtId;
    private ComboBox<String> cmbSexe;
    
     public MdlAgent(TextField txtNom, TextField txtPrenom,  TextField txtpostnom, ComboBox<String> cmbSexe, TextField txtPoste, Label txtId) {
        this.txtNom = txtNom;
        this.txtPrenom = txtPrenom;
        this.txtpostnom = txtpostnom;
        this.cmbSexe = cmbSexe;
        this.txtPoste = txtPoste;
        this.txtId = txtId;
        ctrl = new cls_controller();
        ag = new TttAgent();
    }
     
     public void agentIn(int btn) throws ClassNotFoundException, SQLException{
//         ctrl.alerteInformation("Information", "Attention champs vides !!!");    
         if(btn == 1){
            if(champs.champs_vide.isFieldsempty(txtNom, txtPrenom, txtpostnom, txtPoste)){
                ctrl.alerteInformation("Information", "Attention champs vides !!!");            
            }else{
                ag = new TttAgent(
                        txtNom.getText(), 
                        txtPrenom.getText(), 
                        txtpostnom.getText(), 
                        cmbSexe.getValue(),
                        txtPoste.getText(), 
                        Integer.parseInt(txtId.getText())
                );
                ag.agent(1);
                ctrl.alerteInformation("Information", "Enregistre !!!");            
            }
        }
          else if (btn == 2){
            if(champs.champs_vide.isFieldsempty(txtNom, txtPrenom, txtpostnom, txtPoste)){
                ctrl.alerteInformation("Information", "Veuillez selectionner une elements !!!");            
            }else{
                  ag = new TttAgent(
                        txtNom.getText(), 
                        txtPrenom.getText(), 
                        txtpostnom.getText(), 
                        cmbSexe.getValue(),
                        txtPoste.getText(), 
                        Integer.parseInt(txtId.getText())
                );
                ag.agent(2);
                ctrl.alerteInformation("Information", "Supprime !!!");            
            }
        }
     }
}
