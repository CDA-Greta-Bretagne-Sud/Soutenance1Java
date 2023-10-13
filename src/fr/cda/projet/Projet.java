// Projet 1 CDA
// 
// MIHAJLOVIC Miodrag
//
package fr.cda.projet;

import java.io.*;
import java.util.*;

import fr.cda.util.Terminal;

// Classe principale d'execution du projet
//
public class Projet
{
    public static void main(String a_args[])
    {
        // Affiche un message d'exécution
        Terminal.ecrireStringln("Execution du projet ");

        // Crée une instance de la classe Site pour gérer le site de vente
        Site site = new Site();

        // Crée une interface graphique utilisateur (GUI) pour interagir avec le site
        GUISite ihm = new GUISite(site);
    }
}
