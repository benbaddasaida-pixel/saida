import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Groupe {
    private String nom;
    private List<Equipe> equipes;
    private List<Match> matchs;

    public Groupe(String nom, List<Equipe> equipes) {
        this.nom = nom;
        this.equipes = new ArrayList<>(equipes);
        this.matchs = new ArrayList<>();
        genererMatchs();
    }

    private void genererMatchs() {
        for (int i = 0; i < equipes.size(); i++) {
            for (int j = i + 1; j < equipes.size(); j++) {
                matchs.add(new Match(equipes.get(i), equipes.get(j), "Groupe " + nom));
            }
        }
    }

    public void saisirResultatsMatchs(Scanner scanner) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SAISIE DES RESULTATS - " + nom.toUpperCase());
        System.out.println("=".repeat(60));

        for (int i = 0; i < matchs.size(); i++) {
            Match match = matchs.get(i);
            System.out.println("\n--- Match " + (i + 1) + "/" + matchs.size() + " ---");
            System.out.println(match.getEquipe1().getNom() + " vs " + match.getEquipe2().getNom());

            System.out.print("Score " + match.getEquipe1().getNom() + ": ");
            int score1 = scanner.nextInt();
            System.out.print("Score " + match.getEquipe2().getNom() + ": ");
            int score2 = scanner.nextInt();

            scanner.nextLine();
            System.out.print("Stade: ");
            String stade = scanner.nextLine();
            System.out.print("Date (ex: 15/11/2023): ");
            String date = scanner.nextLine();

            match.setStade(stade);
            match.setDate(date);
            match.enregistrerResultat(score1, score2);

            System.out.println("> Resultat enregistre !");
        }
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TOUS LES MATCHS DU " + nom + " SONT TERMINES");
        System.out.println("=".repeat(60));
    }

    public void afficherClassement() {
        equipes.sort(Comparator
                .comparingInt(Equipe::getPoints).reversed()
                .thenComparingInt(Equipe::getDifferenceButs).reversed()
                .thenComparingInt(Equipe::getButsMarques).reversed());

        System.out.println("\n" + "=".repeat(70));
        System.out.println("CLASSEMENT - " + nom.toUpperCase());
        System.out.println("=".repeat(70));
        System.out.printf("%-4s | %-15s | %-3s | %-4s | %-6s | %-5s | %-3s%n",
                "Pos", "Equipe", "Pts", "MJ", "Diff", "Buts", "BM");
        System.out.println("-".repeat(70));

        for (int i = 0; i < equipes.size(); i++) {
            Equipe e = equipes.get(i);
            System.out.printf("%-4d | %-15s | %-3d | %-4d | %-+6d | %-5d | %-3d%n",
                    (i + 1), e.getNom(), e.getPoints(), e.getMatchsJoues(),
                    e.getDifferenceButs(), e.getButsMarques(), e.getButsEncaisses());
        }

        System.out.println("=".repeat(70));

        if (equipes.size() >= 2) {
            System.out.println("\nEQUIPES QUALIFIEES POUR LES HUITIEMES :");
            System.out.println("  1. " + equipes.get(0).getNom());
            System.out.println("  2. " + equipes.get(1).getNom());
        }
    }

    public List<Equipe> getEquipesQualifiees() {
        equipes.sort(Comparator
                .comparingInt(Equipe::getPoints).reversed()
                .thenComparingInt(Equipe::getDifferenceButs).reversed()
                .thenComparingInt(Equipe::getButsMarques).reversed());

        List<Equipe> qualifiees = new ArrayList<>();
        if (equipes.size() >= 2) {
            qualifiees.add(equipes.get(0));
            qualifiees.add(equipes.get(1));
        }
        return qualifiees;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public List<Equipe> getEquipes() {
        return new ArrayList<>(equipes);
    }

    public List<Match> getMatchs() {
        return new ArrayList<>(matchs);
    }

    public int getNombreEquipes() {
        return equipes.size();
    }

    public void afficherMatchs() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CALENDRIER DES MATCHS - " + nom.toUpperCase());
        System.out.println("=".repeat(80));

        for (int i = 0; i < matchs.size(); i++) {
            System.out.println((i + 1) + ". " + matchs.get(i));
        }
    }
}