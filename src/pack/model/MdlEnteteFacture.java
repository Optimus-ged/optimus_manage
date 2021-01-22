/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.model;

/**
 *
 * @author Optimus
 */
public class MdlEnteteFacture {
     private String nom;
     private int typeVente;

    public MdlEnteteFacture(String nom, int typeVente) {
        this.nom = nom;
        this.typeVente = typeVente;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setType(int typeVente) {
        this.typeVente = typeVente;
    }

    public String getNom() {
        return nom;
    }
    
    public int getType() {
        return typeVente;
    }
}
