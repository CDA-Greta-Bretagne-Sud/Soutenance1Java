package fr.cda.projet;

import fr.cda.ihm.*;

import java.util.ArrayList;

/**
 * La classe `GUIModification` est utilisée pour la modification de la quantité de produits dans une commande.
 *
 * Elle implémente l'interface `FormulaireInt` pour gérer les interactions avec un formulaire.
 */
public class GUIModification implements FormulaireInt {
    private Formulaire formModification;  // Le formulaire de modification
    private Site site;                    // Le site de vente
    private Commande commande;            // La commande à modifier

    /**
     * Constructeur de la classe `GUIModification`.
     *
     * @param formModification Le formulaire de modification
     * @param site Le site de vente
     * @param commande La commande à modifier
     */

    public GUIModification(Formulaire formModification,
                           Site site,
                           Commande commande)
    {
        this.formModification   = formModification;
        this.site      = site;
        this.commande  = commande;


        // Crée un nouveau formulaire pour la modification de la quantité
        Formulaire form = new Formulaire("Modification quantite",this,400,400);

        ArrayList<String> references = commande.getReferences();

        // Ajoute des champs de saisie pour chaque référence de produit dans la commande
        for (int i = 0; i < references.size(); i++) {
            String reference = references.get(i);
            String[] tab = reference.split("=");
            String nomReference = tab[0];
            String valeur = tab[1];

            String inputFieldName = "QUANTITE_" + nomReference;
            form.addText(inputFieldName, nomReference, true, valeur);
        }
        // Ajoute un bouton de validation
        form.addButton("VALIDER","Valider");

        form.afficher();
    }

    /**
     * Méthode invoquée lors de la soumission du formulaire.
     *
     * @param form Le formulaire soumis
     * @param nomSubmit Le nom du bouton soumis
     */
    public void submit(Formulaire form,String nomSubmit)
    {

        if (nomSubmit.equals("VALIDER"))
        {
            // On reconstruit les références de produit de la commande
            //
            ArrayList<String> refProduits1 = new ArrayList<String>();
            ArrayList<String> refProduits = commande.getReferences();
            for(int i=0;i<refProduits.size();i++)
            {
                String[] tab = refProduits.get(i).split("=");
                String reff = tab[0];
                String quantite = form.getValeurChamp("QUANTITE_" + reff);
                try {
                    int quantity = Integer.parseInt(quantite);

                    // N'ajoute à refProduits1 que si l'entrée est un entier valide

                    String refProduit = reff + "=" + quantity;
                    refProduits1.add(refProduit);
                } catch (NumberFormatException e) {
                    System.out.println("Mauvais input quantite: " + quantite);
                }
            }
            // Met à jour la commande avec les nouvelles références de produit
            commande.setReferences(refProduits1);

            // Met à jour le champ "RESULTATS" du formulaire de modification
            formModification.setValeurChamp("RESULTATS",commande.toString());

            //  Ferme la fenêtre de modification
            form.fermer();
        }
    }
}
