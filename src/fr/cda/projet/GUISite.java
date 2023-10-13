package fr.cda.projet;

import fr.cda.ihm.*;
import fr.cda.util.Terminal;

// Classe de definition de l'IHM principale du compte
//
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

// Classe de definition de l'IHM principale du compte
//
public class GUISite implements FormulaireInt
{
    private Site site;  // Le site

    // Constructeur
    //
    public GUISite(Site site)
    {
        this.site = site;

        // Creation du formulaire
        Formulaire form = new Formulaire("Site de vente",this,1100,730);

        //  Creation des elements de l'IHM
        //
        form.addLabel("Afficher tous les produits du stock");
        form.addButton("AFF_STOCK","Tous le stock");
        form.addLabel("");
        form.addLabel("Afficher tous les bons de commande");
        form.addButton("AFF_COMMANDES","Toutes les commandes");
        form.addLabel("");
        form.addText("NUM_COMMANDE","Numero de commande",true,"");
        form.addButton("AFF_COMMANDE","Afficher");
        form.addLabel("");
        form.addButton("MODIFICATION_COMMANDE","Modifier");
        form.addLabel("");
        form.addButton("AFF_LIVRAISON","Livrer");
        form.addLabel("");
        form.addButton("CALCULER","Calculer ventes");
        form.addLabel("");
        form.addButton("SAUVEGARDER","Sauvegarde");
        form.addLabel("");
        form.addButton("FERMER_APP","Fermer");
        form.addLabel("");

        form.setPosition(400,0);
        form.addZoneText("RESULTATS","Resultats",
                true,
                "",
                600,700);

        // Affichage du formulaire
        form.afficher();
    }

    // Methode appellee quand on clique dans un bouton
    //
    public void submit(Formulaire form,String nomSubmit) {

        // Affichage de tous les produits du stock
        //
        if (nomSubmit.equals("AFF_STOCK")) {
            String res = site.listerTousProduits();
            form.setValeurChamp("RESULTATS", res);
        }

        // Affichage de toutes les commandes
        //
        if (nomSubmit.equals("AFF_COMMANDES")) {
            String res = site.listerToutesCommandes();
            form.setValeurChamp("RESULTATS", res);
        }

        // Affichage d'une commande
        //
        if (nomSubmit.equals("AFF_COMMANDE")) {
            String numStr = form.getValeurChamp("NUM_COMMANDE");
            try {
                int num = Integer.parseInt(numStr);
                String res = site.listerCommande(num);
                form.setValeurChamp("RESULTATS", res);
            } catch (NumberFormatException e) {
                form.setValeurChamp("RESULTATS", "Le numéro de commande n'est pas un entier valide");
            }
        }
        //affichage des commandes non livrees
        if (nomSubmit.equals("AFF_LIVRAISON")) {
            List<Commande> result = site.livrerTous();

            if (!result.isEmpty()) {
                String resultatString = "Les commandes non-livres :\n ================================== \n ";

                for (Commande commande : result) {
                    resultatString += commande;
                    resultatString += "\n";
                }
                form.setValeurChamp("RESULTATS", resultatString);
            }
        }
        //Modifications des commandes non livrees
        if (nomSubmit.equals("MODIFICATION_COMMANDE")) {
            // Récupérer le numéro de commande depuis le formulaire
            String numeroCommande = form.getValeurChamp("NUM_COMMANDE");
            try {
                int num = Integer.parseInt(numeroCommande);

                // Récupérer la liste des commandes du site
                ArrayList<Commande> commandes = site.getCommandes();

                // Convertir la liste des commandes en une structure de données de type Map
                Map<Integer, Commande> commandesMap = site.getMapCommandes(commandes);
                Commande commande = commandesMap.get(num);

                if (commande == null)
                    // Si la commande est introuvable, afficher un message d'erreur
                    form.setValeurChamp("RESULTATS", "Le numéro de commande n'existe pas");
                else {
                    // Si la commande a déjà été livrée, afficher un message indiquant son statut
                    if (commande.aEteLivre())
                        form.setValeurChamp("RESULTATS", "Commande a été livrée");
                    else {
                        // Si la commande n'a pas été livrée, ouvrir une interface de modification
                        GUIModification fenetreModif = new GUIModification(form, site, commande);
                    }
                }
            } catch (NumberFormatException e) {
                form.setValeurChamp("RESULTATS", "Le numéro de commande n'est pas un entier valide");
            }
        }
        if (nomSubmit.equals("CALCULER"))
        {
            // Création d'un objet StringBuilder pour construire la chaîne de résultat
            StringBuilder resultatString = new StringBuilder();

            // Parcours de toutes les commandes livrées dans le site
            for (Commande comm : site.getCommandesLivres()) {
                String somme = site.calculerVentes(comm);
                resultatString.append("Commande ").append(comm.getNumero()).append(" - Total des ventes : ").append(somme).append("\n");
            }
            form.setValeurChamp("RESULTATS", resultatString.toString());
        }


        //Bouton qui arrete le programme
        if (nomSubmit.equals("FERMER_APP")) {
            exit(0);
        }
        //Bouton de sauvegarde
        if (nomSubmit.equals("SAUVEGARDER")) {
            StringBuffer resCommandes = new StringBuffer();
            StringBuffer resProduits = new StringBuffer();

            ArrayList<Commande> commandes = site.getCommandes();
            System.out.println("Commandes : " + commandes);
            ArrayList<Produit> produits = site.getStock();
            System.out.println("Produits : " + produits);


            for (Commande commande : commandes) {
                String commandeString = String.format("%d;%s;%s;%s%n",
                        commande.getNumero(), commande.getDate(), commande.getClient(), commande.getReferences());
                resCommandes.append(commandeString);
            }

            for (Produit produit : produits) {
                String produitString = String.format("%s;%s;%s;%d%n",
                        produit.getReference(), produit.getNom(), produit.getPrix(), produit.getQuantite());
                resProduits.append(produitString);
            }

            Terminal.ecrireFichier("Commandes.txt", resCommandes);
            Terminal.ecrireFichier("Produits.txt", resProduits);
        }


    }

}