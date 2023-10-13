package fr.cda.projet;

import java.util.*;

/**
 * Classe de définition d'un produit du stock.
 */
public class Produit
{
    // Les caracteristiques d'un Produit
    //
    private String  reference;      // reference du produit
    private String  nom;            // nom du produit
    private double  prix;           // prix du produit
    private int     quantite;       // quantité du produit

    /**
     * Constructeur de la classe Produit.
     *
     * @param reference La référence du produit.
     * @param nom Le nom du produit.
     * @param prix Le prix du produit.
     * @param quantite La quantité du produit.
     */
    public Produit(String reference,
                   String nom,
                   double prix,
                   int quantite)
    {
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    /**
     * Convertit l'objet Produit en une chaîne de caractères.
     *
     * @return Une représentation textuelle du produit.
     */
    public String toString()
    {
        return String.format("%-50s %-1s %-50s %-1s %-10.2f %-10s %2d\n",reference," ",nom," ",prix," ",quantite);
    }


    // Getters et setters pour les attributs du Produit
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}