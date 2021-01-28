/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.model;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pack.main.cls_controller;
import pack.traitement.TttClient;

/**
 *
 * @author Optimus
 */
public class MdlClient {

    private cls_controller ctrl;
    private TttClient cli;
    private TextField txtNom, txtPrenom, txtPoste;
    private Label txtId, lblInfo;
    private ComboBox<String> cmbSexe;
    private FontAwesomeIconView font;

    public MdlClient(TextField txtNom, TextField txtPrenom, TextField txtpostnom, ComboBox<String> cmbSexe, TextField txtPoste, Label txtId, Label lblInfo, FontAwesomeIconView font) {
        this.txtNom = txtNom;
        this.txtPrenom = txtPrenom;
        this.cmbSexe = cmbSexe;
        this.txtPoste = txtPoste;
        this.txtId = txtId;
        this.lblInfo = lblInfo;
        this.font = font;
        ctrl = new cls_controller();
        cli = new TttClient();
    }

    public void clientIn(int btn) throws ClassNotFoundException, SQLException {
        if (btn == 1) {
            if (champs.champs_vide.isFieldsempty(txtNom, txtPrenom, txtPoste)) {
                ctrl.showMssge(lblInfo, font, "Veuillez remplir tous les champs svp !", 0);
            } else {
                if (ctrl.isValideTel(txtPoste.getText())) {
                    cli = new TttClient(
                            txtNom.getText(),
                            txtPrenom.getText(),
                            cmbSexe.getValue(),
                            txtPoste.getText(),
                            Integer.parseInt(txtId.getText())
                    );
                    cli.client(1);
                    ctrl.alerteInformation("Information", "Enregistre !!!");
                }else{
                    ctrl.alerteInformation("Erreur", "Numero de telephone invalide !!!");
                }

            }
        } else if (btn == 2) {
            if (champs.champs_vide.isFieldsempty(txtNom, txtPrenom, txtPoste)) {
                ctrl.showMssge(lblInfo, font, "Veuillez selectionner un element !", 0);
            } else {
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
