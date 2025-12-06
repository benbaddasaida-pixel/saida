import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("    TOURNOI DE FOOTBALL - DEMO         ");
        System.out.println("========================================");

        System.out.println("\nChoisissez une option :");
        System.out.println("1. Version complete avec menu");
        System.out.println("2. Version simple avec 4 equipes");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();

        if (choix == 1) {
            // Version complète avec menu
            Tournoi tournoi = new Tournoi();
            tournoi.demarrer();
        } else if (choix == 2) {
            // Version simple avec 4 équipes fixes
            versionSimple();
        } else {
            System.out.println("Choix invalide. Lancement de la version simple...");
            versionSimple();
        }

        scanner.close();
    }

    private static void versionSimple() {
        Scanner scanner = new Scanner(System.in);

        // Création des équipes
        Equipe e1 = new Equipe("France");
        Equipe e2 = new Equipe("Argentine");
        Equipe e3 = new Equipe("Bresil");
        Equipe e4 = new Equipe("Allemagne");

        // Création du groupe
        Groupe groupeA = new Groupe("Groupe A", Arrays.asList(e1, e2, e3, e4));

        System.out.println("\n" + "=".repeat(40));
        System.out.println("TOURNOI DEMO - GROUPE A");
        System.out.println("=".repeat(40));
        System.out.println("\nEquipes participantes :");
        System.out.println("1. " + e1.getNom());
        System.out.println("2. " + e2.getNom());
        System.out.println("3. " + e3.getNom());
        System.out.println("4. " + e4.getNom());

        // Saisie des résultats
        groupeA.saisirResultatsMatchs(scanner);

        // Affichage du classement
        groupeA.afficherClassement();

        // Affichage des matchs
        groupeA.afficherMatchs();

        scanner.close();
    }
}