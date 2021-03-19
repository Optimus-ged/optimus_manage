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
public class MdlDetailFacture {
    String desiProduit;
    float qte;
    float puDeVente;
    int id;
    
    public MdlDetailFacture(String desiProduit, float qte, int id,  Float puDeVente){
        this.desiProduit = desiProduit;
        this.qte = qte;
        this.id = id;
        this.puDeVente = puDeVente;
    }
    
    public void setDesiProduit(String desiProduit){
        this.desiProduit = desiProduit;
    }
    
    public void setPuDeVente(float puDeVente){
        this.puDeVente = puDeVente;
    }
     public float getPuDeVente(){
        return puDeVente;
    }
    
    public String getDesiProduit(){
        return desiProduit;
    }
    
    public void setQte(float qte){
        this.qte = qte;
    }
    
    public float getQte(){
        return qte;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
}
