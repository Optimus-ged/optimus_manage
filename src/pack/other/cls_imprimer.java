/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.other;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import pack.model.MdlConnexion;

/**
 *
 * @author Optimus
 */
public class cls_imprimer {
    
    public static void _impresion(String requete,StackPane container, String cheminJasper) throws ClassNotFoundException, SQLException {
        int y = 2;
        try {
            JasperDesign g = JRXmlLoader.load(cheminJasper);
            JRDesignQuery gn = new JRDesignQuery();
            gn.setText(requete);
            g.setQuery(gn);
            JasperReport f1 = JasperCompileManager.compileReport(g);
            JasperPrint a = JasperFillManager.fillReport(f1, null, MdlConnexion.getCnx());

            JFXButton b1 = new JFXButton("OK");
            JFXButton b2 = new JFXButton("Annuler");

            JFXDialogLayout Layout = new JFXDialogLayout();

            Layout.setHeading(new Label("Voulez-vous visualiser ?"));

            JFXDialog dialog = new JFXDialog(container, Layout, JFXDialog.DialogTransition.CENTER);

            b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

                JasperViewer.viewReport(a, false);
                dialog.close();
            });

            b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                if (y == 2) {
                    try {
                        JasperPrintManager.printReport(a, true);
                        dialog.close();
                    } catch (JRException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else if (y == 2) {
                    try {
                        JasperPrintManager.printReport(a, false);
                        dialog.close();
                    } catch (JRException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            });
            Layout.setActions(b1, b2);
            dialog.show();

        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
