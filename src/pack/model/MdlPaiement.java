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
import pack.traitement.TttPaiement;

/**
 *
 * @author Optimus
 */
public class MdlPaiement {

    private cls_controller ctrl;
    private TttPaiement pai;
    private TextField txtNomClient, txtMontant;
    private Label lblInfo;
    private FontAwesomeIconView font;

    MdlPaiement(TextField txtNomClient, TextField txtMontant) {
        txtNomClient = this.txtNomClient;
        txtMontant = this.txtMontant;
        ctrl = new cls_controller();
        pai = new TttPaiement();
    }

    public void paiementIn(int btn) throws ClassNotFoundException, SQLException {
        if (btn == 1) {
            if (champs.champs_vide.isFieldsempty(txtNomClient, txtMontant)) {
                ctrl.showMssge(lblInfo, font, "Veuillez remplir tous les champs svp !", 0);
            }else {
                if (ctrl.isNumber(txtMontant.getText()) && ctrl.isNumber(txtNomClient.getText()) ) {
                    pai = new TttPaiement(
                            Integer.parseInt(txtNomClient.getText()), 
                            Float.parseFloat(txtMontant.getText())
                    );
                    pai.paiement(1);
                    ctrl.alerteInformation("Information", "Enregistre !!!");
                }else{
                    ctrl.alerteInformation("Erreur", "Le montant et le numero de la\nfacture sont tous des entiers !!!");
                }

            }
        }
    }
}
