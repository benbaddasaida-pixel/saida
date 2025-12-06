import java.util.*;

public class Tournoi {
    private List<Groupe> groupes;
    private List<Match> phasesFinales;
    private List<Equipe> toutesEquipes;
    private Scanner scanner;

    public Tournoi() {
        this.groupes = new ArrayList<>();
        this.phasesFinales = new ArrayList<>();
        this.toutesEquipes = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void demarrer() {
        System.out.println("========================================================");
        System.out.println("       COUPE DU MONDE DE FOOTBALL - GESTIONNAIRE       ");
        System.out.println("========================================================");

        boolean quitter = false;
        while (!quitter) {
            afficherMenuPrincipal();
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerGroupes();
                    break;
                case 2:
                    saisirResultatsGroupes();
                    break;
                case 3:
                    afficherClassementsGroupes();
                    break;
                case 4:
                    genererPhasesFinales();
                    break;
                case 5:
                    saisirResultatsPhasesFinales();
                    break;
                case 6:
                    afficherArbrePhasesFinales();
                    break;
                case 7:
                    afficherStatistiques();
                    break;
                case 8:
                    quitter = true;
                    System.out.println("\nAu revoir !");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
        scanner.close();
    }

    private void afficherMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MENU PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. Creer les groupes");
        System.out.println("2. Saisir resultats des groupes");
        System.out.println("3. Afficher classements des groupes");
        System.out.println("4. Generer phases finales");
        System.out.println("5. Saisir resultats phases finales");
        System.out.println("6. Afficher arbre des phases finales");
        System.out.println("7. Statistiques du tournoi");
        System.out.println("8. Quitter");
        System.out.println("=".repeat(50));
        System.out.print("Votre choix : ");
    }

    private void creerGroupes() {
        System.out.println("\n=== CREATION DES GROUPES ===");
        System.out.print("Nombre de groupes (4 ou 8) : ");
        int nbGroupes = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre d'equipes par groupe (3 ou 4) : ");
        int equipesParGroupe = scanner.nextInt();
        scanner.nextLine();

        int totalEquipes = nbGroupes * equipesParGroupe;
        toutesEquipes.clear();

        System.out.println("\nSaisie des " + totalEquipes + " equipes :");
        for (int i = 0; i < totalEquipes; i++) {
            System.out.print("Equipe " + (i + 1) + " : ");
            String nom = scanner.nextLine();
            toutesEquipes.add(new Equipe(nom));
        }

        // Melanger et repartir dans les groupes
        Collections.shuffle(toutesEquipes);
        groupes.clear();

        for (int g = 0; g < nbGroupes; g++) {
            List<Equipe> equipesGroupe = new ArrayList<>();
            for (int e = 0; e < equipesParGroupe; e++) {
                int index = g * equipesParGroupe + e;
                equipesGroupe.add(toutesEquipes.get(index));
            }
            Groupe groupe = new Groupe("Groupe " + (char) ('A' + g), equipesGroupe);
            groupes.add(groupe);
            System.out.println("> " + groupe.getNom() + " cree avec " + equipesParGroupe + " equipes");
        }

        System.out.println("\n" + nbGroupes + " groupes crees avec succes !");
    }

    private void saisirResultatsGroupes() {
        if (groupes.isEmpty()) {
            System.out.println("Veuillez d'abord creer les groupes !");
            return;
        }

        for (Groupe groupe : groupes) {
            groupe.saisirResultatsMatchs(scanner);
        }
    }

    private void afficherClassementsGroupes() {
        if (groupes.isEmpty()) {
            System.out.println("Aucun groupe disponible !");
            return;
        }

        for (Groupe groupe : groupes) {
            groupe.afficherClassement();
        }
    }

    private void genererPhasesFinales() {
        if (groupes.isEmpty()) {
            System.out.println("Veuillez d'abord creer et completer les groupes !");
            return;
        }

        phasesFinales.clear();
        List<Equipe> qualifiees = new ArrayList<>();

        // Recuperer les 2 premieres de chaque groupe
        for (Groupe groupe : groupes) {
            qualifiees.addAll(groupe.getEquipesQualifiees());
        }

        System.out.println("\n=== PHASES FINALES ===");
        System.out.println("Equipes qualifiees : " + qualifiees.size());
        for (int i = 0; i < qualifiees.size(); i++) {
            System.out.println((i + 1) + ". " + qualifiees.get(i).getNom());
        }

        // Generer les huitiemes de finale
        if (qualifiees.size() == 16) {
            genererHuitiemes(qualifiees);
        } else if (qualifiees.size() == 8) {
            genererQuarts(qualifiees);
        }
    }

    private void genererHuitiemes(List<Equipe> qualifiees) {
        System.out.println("\n=== HUITIEMES DE FINALE ===");

        // Paire les matchs : 1er vs 2eme d'autre groupe
        for (int i = 0; i < 8; i++) {
            Equipe premier = qualifiees.get(i * 2);
            Equipe deuxieme = qualifiees.get((i * 2 + 9) % 16); // Formule pour appariement
            Match match = new Match(premier, deuxieme, "Huitieme");
            phasesFinales.add(match);
            System.out.println("Match " + (i + 1) + " : " + premier.getNom() + " vs " + deuxieme.getNom());
        }
    }

    private void genererQuarts(List<Equipe> qualifiees) {
        System.out.println("\n=== QUARTS DE FINALE ===");

        for (int i = 0; i < 4; i++) {
            Equipe equipe1 = qualifiees.get(i * 2);
            Equipe equipe2 = qualifiees.get(i * 2 + 1);
            Match match = new Match(equipe1, equipe2, "Quart");
            phasesFinales.add(match);
            System.out.println("Match " + (i + 1) + " : " + equipe1.getNom() + " vs " + equipe2.getNom());
        }
    }

    private void saisirResultatsPhasesFinales() {
        if (phasesFinales.isEmpty()) {
            System.out.println("Veuillez d'abord generer les phases finales !");
            return;
        }

        System.out.println("\n=== SAISIE DES RESULTATS PHASES FINALES ===");

        for (int i = 0; i < phasesFinales.size(); i++) {
            Match match = phasesFinales.get(i);
            System.out.println("\n--- " + match.getTypeMatch() + " " + (i + 1) + " ---");
            System.out.println(match.getEquipe1().getNom() + " vs " + match.getEquipe2().getNom());

            System.out.print("Score " + match.getEquipe1().getNom() + " : ");
            int score1 = scanner.nextInt();
            System.out.print("Score " + match.getEquipe2().getNom() + " : ");
            int score2 = scanner.nextInt();

            scanner.nextLine();
            System.out.print("Stade : ");
            String stade = scanner.nextLine();
            System.out.print("Date : ");
            String date = scanner.nextLine();

            match.setStade(stade);
            match.setDate(date);
            match.enregistrerResultat(score1, score2);

            System.out.println("> Resultat enregistre !");

            // Generer le match suivant si necessaire
            genererMatchSuivantSiPossible();
        }
    }

    private void genererMatchSuivantSiPossible() {
        // Compter les matchs de chaque type termines
        long huitiemesTermines = phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Huitieme") && m.isEstJoue())
                .count();

        long quartsTermines = phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Quart") && m.isEstJoue())
                .count();

        long demisTermines = phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Demi") && m.isEstJoue())
                .count();

        // Generer quarts si tous les huitiemes sont termines
        if (huitiemesTermines == 8 && quartsTermines == 0) {
            genererQuartsDepuisHuitiemes();
        }

        // Generer demis si tous les quarts sont termines
        if (quartsTermines == 4 && demisTermines == 0) {
            genererDemisDepuisQuarts();
        }

        // Generer finale si toutes les demis sont terminees
        if (demisTermines == 2 && phasesFinales.stream().noneMatch(m -> m.getTypeMatch().equals("Finale"))) {
            genererFinale();
        }
    }

    private void genererQuartsDepuisHuitiemes() {
        System.out.println("\n=== GENERATION DES QUARTS DE FINALE ===");

        List<Equipe> vainqueursHuitiemes = phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Huitieme") && m.isEstJoue())
                .map(Match::getVainqueur)
                .toList();

        for (int i = 0; i < 4; i++) {
            Equipe equipe1 = vainqueursHuitiemes.get(i * 2);
            Equipe equipe2 = vainqueursHuitiemes.get(i * 2 + 1);
            Match quart = new Match(equipe1, equipe2, "Quart");
            phasesFinales.add(quart);
            System.out.println("Quart " + (i + 1) + " : " + equipe1.getNom() + " vs " + equipe2.getNom());
        }
    }

    private void genererDemisDepuisQuarts() {
        System.out.println("\n=== GENERATION DES DEMI-FINALES ===");

        List<Equipe> vainqueursQuarts = phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Quart") && m.isEstJoue())
                .map(Match::getVainqueur)
                .toList();

        for (int i = 0; i < 2; i++) {
            Equipe equipe1 = vainqueursQuarts.get(i * 2);
            Equipe equipe2 = vainqueursQuarts.get(i * 2 + 1);
            Match demi = new Match(equipe1, equipe2, "Demi");
            phasesFinales.add(demi);
            System.out.println("Demi-finale " + (i + 1) + " : " + equipe1.getNom() + " vs " + equipe2.getNom());
        }
    }

    private void genererFinale() {
        System.out.println("\n=== GENERATION DE LA FINALE ===");

        List<Equipe> vainqueursDemis = phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Demi") && m.isEstJoue())
                .map(Match::getVainqueur)
                .toList();

        if (vainqueursDemis.size() == 2) {
            Equipe equipe1 = vainqueursDemis.get(0);
            Equipe equipe2 = vainqueursDemis.get(1);
            Match finale = new Match(equipe1, equipe2, "Finale");
            phasesFinales.add(finale);
            System.out.println("FINALE : " + equipe1.getNom() + " vs " + equipe2.getNom());
        }
    }

    private void afficherArbrePhasesFinales() {
        if (phasesFinales.isEmpty()) {
            System.out.println("Aucune phase finale generee !");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("ARBRE DES PHASES FINALES");
        System.out.println("=".repeat(80));

        // Huitiemes
        System.out.println("\n=== HUITIEMES DE FINALE ===");
        phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Huitieme"))
                .forEach(System.out::println);

        // Quarts
        System.out.println("\n=== QUARTS DE FINALE ===");
        phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Quart"))
                .forEach(System.out::println);

        // Demis
        System.out.println("\n=== DEMI-FINALES ===");
        phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Demi"))
                .forEach(System.out::println);

        // Finale
        System.out.println("\n=== FINALE ===");
        phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Finale"))
                .forEach(System.out::println);

        // Afficher le vainqueur si finale terminee
        phasesFinales.stream()
                .filter(m -> m.getTypeMatch().equals("Finale") && m.isEstJoue())
                .findFirst()
                .ifPresent(finale -> {
                    System.out.println("\n" + "=".repeat(40));
                    System.out.println("🏆 VAINQUEUR : " + finale.getVainqueur().getNom() + " 🏆");
                    System.out.println("=".repeat(40));
                });
    }

    private void afficherStatistiques() {
        System.out.println("\n=== STATISTIQUES DU TOURNOI ===");
        System.out.println("Nombre de groupes : " + groupes.size());
        System.out.println("Nombre total d'equipes : " + toutesEquipes.size());
        System.out.println("Nombre de matchs de groupe : " +
                groupes.stream().mapToInt(g -> g.getMatchs().size()).sum());
        System.out.println("Nombre de matchs en phases finales : " + phasesFinales.size());

        if (!toutesEquipes.isEmpty()) {
            System.out.println("\n--- Meilleures attaques ---");
            toutesEquipes.stream()
                    .sorted((e1, e2) -> Integer.compare(e2.getButsMarques(), e1.getButsMarques()))
                    .limit(5)
                    .forEach(e -> System.out.println(e.getNom() + " : " + e.getButsMarques() + " buts"));

            System.out.println("\n--- Meilleures defenses ---");
            toutesEquipes.stream()
                    .sorted((e1, e2) -> Integer.compare(e1.getButsEncaisses(), e2.getButsEncaisses()))
                    .limit(5)
                    .forEach(e -> System.out.println(e.getNom() + " : " + e.getButsEncaisses() + " buts encaisses"));
        }
    }

    // Main method pour lancer le programme
    public static void main(String[] args) {
        Tournoi tournoi = new Tournoi();
        tournoi.demarrer();
    }
}