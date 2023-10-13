package fr.cda.projet;

import java.util.*;

import fr.cda.util.*;

/**
 * Classe de définition du site de vente.
 * Cette classe gère les produits en stock, les commandes, et effectue diverses opérations sur ces données.
 */
public class Site {
    private ArrayList<Produit> stock;       // Les produits du stock
    private ArrayList<Commande> commandes;  // Les bons de commande
    private ArrayList<Commande> commandesLivres = new ArrayList<>();
    private List<Commande> commandesNonLivres = new ArrayList<>();

    /**
     * Obtient la liste de toutes les commandes.
     *
     * @return Une liste d'objets Commande.
     */
    public ArrayList<Commande> getCommandes() {
        return commandes;
    }

    public ArrayList<Produit> getStock() {
        return stock;
    }

    /**
     * Obtient la liste de toutes les commandes livrer.
     *
     * @return Une liste d'objets Commande.
     */
    public List<Commande> getCommandesLivres() {
        return commandesLivres;
    }

    /**
     * Obtient une commande par son numero.
     *
     * @return Une liste d'objets Commande.
     */
    public Commande getCommande(int num) {
        return commandes.get(num);
    }

    /**
     * Constructeur de la classe Site.
     * Initialise le stock de produits en lisant les données depuis le fichier "data/Produits.txt".
     * Initialise également la liste de commandes en lisant les données depuis le fichier "data/Commandes.txt".
     *
     * @see #initialiserStock(String)
     * @see #initialiserCommandes(String)
     */
    public Site() {
        stock = new ArrayList<Produit>();

        // lecture du fichier data/Produits.txt
        //  pour chaque ligne on cree un Produit que l'on ajoute a stock
        initialiserStock("data/Produits.txt");

        //  lecture du fichier data/Commandes.txt
        //  pour chaque ligne on cree une commande ou on ajoute une reference
        //  d'un produit a une commande existante.
        // AC AC

        commandes = new ArrayList<Commande>();
        initialiserCommandes("data/Commandes.txt");


    }
    /**
     * Initialise le stock de produits en lisant les données depuis le fichier spécifié.
     * Chaque ligne du fichier est analysée pour créer un nouveau Produit, qui est ajouté au stock.
     *
     * @param nomFichier Le chemin du fichier depuis lequel les données du stock sont lues.
     *
     * @see Produit
     */
    private void initialiserStock(String nomFichier) {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for (String ligne : lignes) {
            String[] champs = ligne.split("[;]", 4);
            String reference = champs[0];
            String nom = champs[1];
            double prix = Double.parseDouble(champs[2]);
            int quantite = Integer.parseInt(champs[3]);
            Produit p = new Produit(reference, nom, prix, quantite);
            stock.add(p);
        }
    }
    /**
     * Initialise les commandes en lisant les données depuis le fichier spécifié.
     * Chaque ligne du fichier est analysée pour créer une nouvelle commande, à laquelle des références
     * de produits sont ajoutées.
     *
     * @param nomFichier Le chemin du fichier depuis lequel les données des commandes sont lues.
     *
     * @see Commande
     */
    private void initialiserCommandes(String nomFichier) {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for (String ligne : lignes) {
            String[] champs = ligne.split("[;]", 4);
            int numero = Integer.parseInt(champs[0]);
            String date = champs[1];
            String client = champs[2];
            String[] referencesArray = champs[3].split(","); // Divisez la chaîne des références en un tableau de chaînes
            ArrayList<String> references = new ArrayList<>(Arrays.asList(referencesArray)); // Créez un ArrayList à partir du tableau de chaînes
            Commande c = new Commande(numero, date, client, references);
            commandes.add(c);
        }
    }
    /**
     * Convertit une liste de commandes en une carte de commandes indexée par leur numéro.
     * Chaque commande de la liste est ajoutée à la carte sous forme de paires (numéro de commande, commande).
     *
     * @param commandes La liste de commandes à convertir en carte.
     * @return Une carte de commandes indexée par leur numéro.
     *
     * @see Commande
     */
    public Map<Integer, Commande>  getMapCommandes(ArrayList<Commande> commandes){
        return convertToHashMap(commandes);
    }

    // Methode qui retourne sous la forme d'une chaine de caractere
    //  tous les produits du stock
    //
    public String listerTousProduits() {
        String res = "";
        for (Produit prod : stock)
            res = res + prod.toString() + "\n";

        return res;
    }
    /**
     * Convertit une liste de commandes en une carte de commandes indexée par leur numéro.
     * Chaque commande de la liste est ajoutée à la carte sous forme de paires (numéro de commande, commande).
     * Si une commande possède plusieurs références, elles sont incluses dans la carte sous forme de chaînes "référence=quantité".
     *
     * @param commandes La liste de commandes à convertir en carte.
     * @return Une carte de commandes indexée par leur numéro.
     *
     * @see Commande
     */
    public static Map<Integer, Commande> convertToHashMap(ArrayList<Commande> commandes) {
        Map<Integer, Commande> commandesMap = new HashMap<>();

        for (Commande commande : commandes) {
            int numero = commande.getNumero();

            if (!commandesMap.containsKey(numero)) {
                commandesMap.put(numero, new Commande(commande.getNumero(), commande.getDate(), commande.getClient(), new ArrayList<>()));
            }for (String reference : commande.getReferences()) {
                String[] parts = reference.split("=");
                if (parts.length == 2) {
                    String ref = parts[0];
                    try {
                        int quantity = Integer.parseInt(parts[1]);
                        commandesMap.get(numero).getReferences().add(ref + "=" + quantity);
                    } catch (NumberFormatException e) {
                        System.err.println("Error: Invalid type for quantity - " + parts[1]);                    }

                }
            }

        }

        return commandesMap;
    }


    /**
     * Liste toutes les commandes du site en une seule chaîne de caractères.
     * Chaque commande est affichée en utilisant la méthode {@link #listerCommande(int)}.
     *
     * @return Une chaîne de caractères représentant toutes les commandes du site.
     *
     * @see #listerCommande(int)
     */
    public String listerToutesCommandes() {

        String res = "";
        int compteur = 0;

        // Parcours toutes les commandes dans la liste en utilisant la methode listerCommande
        for (Commande comm : commandes) {
            if (comm.getNumero()!=compteur)
                res += listerCommande(comm.getNumero());
            compteur=comm.getNumero();
            ;
        }
        // Retourne la chaîne de caractères
        return res;
    }

    // Methode qui retourne sous la forme d'une chaine de caractere
    //  une commande
    public String listerCommande(int numero) {
        String res = "";
        String res1 = "";
        boolean trouver = false;

        for (Commande comm : commandes) {
            if (comm.getNumero() == numero) {
                res="Commande " + numero +" : " +"\n Date         : " + comm.getDate() + "\n" + " Client     :  " +comm.getClient()+ "\n" + " refProduits : " +"\n";
                for(int i=0; i<comm.getReferences().size();i++){
                    res1+="        " + comm.getReferences() + "\n";
                }
                trouver = true;
            }
        }
        if (!trouver) {
            res+="Cette commande n'existe pas";
        }

        return res + res1 + "\n" ;
    }
    /**
     * Livre une commande identifiée par son numéro en mettant à jour le stock de produits.
     *
     * @param mapDeCommandes Un mappage des commandes du site où la clé est le numéro de commande et la valeur est la commande correspondante.
     * @param numeroDeCommande Le numéro de la commande à livrer.
     *
     * @return Un tableau d'objets contenant :
     *   - Un booléen indiquant si la commande a été livrée avec succès.
     *   - La commande livrée (si livrable).
     *   - Une chaîne de caractères indiquant le résultat de la livraison.
     *
     * @see Commande
     * @see Produit
     */
    public Object[] livrable(Map<Integer, Commande> mapDeCommandes, int numeroDeCommande) {
        Commande commande = mapDeCommandes.get(numeroDeCommande);

        if (commande == null) {
            return new Object[]{false, "Commande pas trouvé"};
        }
        boolean commandeLivrable = true;

        for (String res : commande.getReferences()) {
            String reff = res.split("=")[0];
            int quantite = Integer.parseInt(res.split("=")[1]);

            for (Produit prod: stock) {

                boolean reffMatched = false;

                if (reff.equals(prod.getReference()) && quantite <= prod.getQuantite()) {
                    prod.setQuantite(prod.getQuantite() - quantite);
                    reffMatched = true;

                } else if (reff.equals(prod.getReference()) && quantite > prod.getQuantite()) {
                    int manque = quantite - prod.getQuantite();
                    commandeLivrable = false;
                    commande.setExplication("Il manque : " + manque + " " + reff + "\n");
                }
                if (reffMatched) {
                    break;
                }
            }
        }
        commande.setaEteLivre(commandeLivrable);
        if(commande.aEteLivre()){
            if (!commandesLivres.contains(commande)) {
                commandesLivres.add(commande);
            }
            ArrayList<Commande> newCommandes = new ArrayList<>();
            for (Commande comm : commandes) {
                if (comm.getNumero() != commande.getNumero()) {
                    newCommandes.add(comm);
                }
            }
            commandes = newCommandes;
        }
        else{
            if (!commandesNonLivres.contains(commande)) {
                commandesNonLivres.add(commande);
            }
        }

        String res = "Commande " + numeroDeCommande;

        return new Object[]{commande.aEteLivre(), commande, res};
    }

    /**
     * Livre toutes les commandes en utilisant les données du site, met à jour le stock de produits et renvoie les commandes non livrées.
     *
     * @return Une liste de commandes non livrées après l'exécution de la livraison.
     *
     * @see Commande
     * @see Produit
     */
    public List<Commande> livrerTous() {

        Map<Integer, Commande> commandesMap = convertToHashMap(commandes);

        for (int i = 1; i<=commandesMap.size(); i++) {
            livrable(commandesMap, i);

        }
        return commandesNonLivres;
    }
    public String calculerVentes(Commande commande) {
        String res = "";
        double somme = 0.0;
        ArrayList<String> references = commande.getReferences();

        for (String referances : references) {
            String reff = referances.split("=")[0];
            int quantite = Integer.parseInt(referances.split("=")[1]);

            for (Produit prod : stock) {
                if (reff.equals(prod.getReference())) {
                    double prix = prod.getPrix();
                    somme += prix * quantite;
                }
            }
        }

        res += somme;
        return res;
    }

}
