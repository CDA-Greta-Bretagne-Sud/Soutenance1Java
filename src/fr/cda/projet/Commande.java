package fr.cda.projet;

import java.util.*;

/**
 * La classe `Commande` représente une commande passée par un client.
 */
public class Commande
{
    // Les caracteristiques d'une commande
    //
    private int     numero;         // numero de la commande
    private String  date;           // date de la commande. Au format JJ/MM/AAAA
    private String  client;         // nom du client
    private boolean aEteLivre; // livré ou non
    private String explication ;     //précise les raisons pour lesquelles le bon de commande n'a pas être livré
    private ArrayList<String> references; // les references des produits de la commande

    /**
     * Constructeur de la classe `Commande`.
     *
     * @param numero Le numéro de la commande
     * @param date La date de la commande au format JJ/MM/AAAA
     * @param client Le nom du client
     * @param references Les références des produits de la commande
     */
    public Commande(int numero, String date, String client, ArrayList<String> references) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = references;
    }

    // Getters et setters pour les attributs de la classe
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean aEteLivre() {
        return aEteLivre;
    }

    public void setaEteLivre(boolean aEteLivre) {
        this.aEteLivre = aEteLivre;
    }

    public String getExplication() {
        if (explication != null) {
            return explication;
        } else {
            return "";
        }
    }

    public void setExplication(String explication) {
        this.explication = explication;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }

    @Override
    public String toString() {
        return " Commande : " + numero + "\n" +
                " date : " + date + "\n" +
                " client : " + client + "\n" +
                " refProduit : " + references +"\n" +
                explication + "\n" +
                '}';
    }





}