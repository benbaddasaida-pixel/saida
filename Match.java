public class Match {
    private Equipe equipe1;
    private Equipe equipe2;
    private int scoreEquipe1;
    private int scoreEquipe2;
    private boolean estJoue;
    private String stade;
    private String date;
    private String typeMatch; // "Groupe", "Huitieme", "Quart", "Demi", "Finale"

    public Match(Equipe equipe1, Equipe equipe2, String typeMatch) {
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.scoreEquipe1 = -1;
        this.scoreEquipe2 = -1;
        this.estJoue = false;
        this.stade = "A definir";
        this.date = "A definir";
        this.typeMatch = typeMatch;
    }

    public void enregistrerResultat(int score1, int score2) {
        this.scoreEquipe1 = score1;
        this.scoreEquipe2 = score2;
        this.estJoue = true;

        if (score1 > score2) {
            equipe1.ajouterResultat(score1, score2, 3);
            equipe2.ajouterResultat(score2, score1, 0);
        } else if (score1 < score2) {
            equipe1.ajouterResultat(score1, score2, 0);
            equipe2.ajouterResultat(score2, score1, 3);
        } else {
            equipe1.ajouterResultat(score1, score2, 1);
            equipe2.ajouterResultat(score2, score1, 1);
        }
    }

    // Pour les phases finales (prolongation, penalties)
    public void enregistrerResultatFinale(int score1, int score2, boolean penalties) {
        this.scoreEquipe1 = score1;
        this.scoreEquipe2 = score2;
        this.estJoue = true;

        if (score1 > score2) {
            equipe1.ajouterResultat(score1, score2, 0); // 0 points en phase finale
            equipe2.ajouterResultat(score2, score1, 0);
        } else {
            equipe1.ajouterResultat(score1, score2, 0);
            equipe2.ajouterResultat(score2, score1, 0);
        }
    }

    public Equipe getVainqueur() {
        if (!estJoue)
            return null;
        if (scoreEquipe1 > scoreEquipe2)
            return equipe1;
        if (scoreEquipe1 < scoreEquipe2)
            return equipe2;
        return null; // Match nul (ou à déterminer par penalties)
    }

    public Equipe getPerdant() {
        if (!estJoue)
            return null;
        if (scoreEquipe1 > scoreEquipe2)
            return equipe2;
        if (scoreEquipe1 < scoreEquipe2)
            return equipe1;
        return null;
    }

    // Getters
    public Equipe getEquipe1() {
        return equipe1;
    }

    public Equipe getEquipe2() {
        return equipe2;
    }

    public int getScoreEquipe1() {
        return scoreEquipe1;
    }

    public int getScoreEquipe2() {
        return scoreEquipe2;
    }

    public boolean isEstJoue() {
        return estJoue;
    }

    public String getStade() {
        return stade;
    }

    public String getDate() {
        return date;
    }

    public String getTypeMatch() {
        return typeMatch;
    }

    // Setters
    public void setStade(String stade) {
        this.stade = stade;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        if (estJoue) {
            return String.format("%-15s %d - %d %-15s [%s] (Stade: %s, Date: %s)",
                    equipe1.getNom(), scoreEquipe1, scoreEquipe2, equipe2.getNom(),
                    typeMatch, stade, date);
        } else {
            return String.format("%-15s vs %-15s [%s] (A venir)",
                    equipe1.getNom(), equipe2.getNom(), typeMatch);
        }
    }
}