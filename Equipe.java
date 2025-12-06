import java.util.Objects;

public class Equipe {
    private String nom;
    private String pays;
    private int points;
    private int butsMarques;
    private int butsEncaisses;
    private int differenceButs;
    private int matchsJoues;

    public Equipe(String nom) {
        this.nom = nom;
        this.pays = nom;
        this.points = 0;
        this.butsMarques = 0;
        this.butsEncaisses = 0;
        this.differenceButs = 0;
        this.matchsJoues = 0;
    }

    public Equipe(String nom, String pays) {
        this.nom = nom;
        this.pays = pays;
        this.points = 0;
        this.butsMarques = 0;
        this.butsEncaisses = 0;
        this.differenceButs = 0;
        this.matchsJoues = 0;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getPays() {
        return pays;
    }

    public int getPoints() {
        return points;
    }

    public int getButsMarques() {
        return butsMarques;
    }

    public int getButsEncaisses() {
        return butsEncaisses;
    }

    public int getDifferenceButs() {
        return differenceButs;
    }

    public int getMatchsJoues() {
        return matchsJoues;
    }

    public void ajouterResultat(int butsMarqués, int butsEncaissés, int pointsGagnés) {
        this.butsMarques += butsMarqués;
        this.butsEncaisses += butsEncaissés;
        this.differenceButs = this.butsMarques - this.butsEncaisses;
        this.points += pointsGagnés;
        this.matchsJoues++;
    }

    public void reinitialiser() {
        this.points = 0;
        this.butsMarques = 0;
        this.butsEncaisses = 0;
        this.differenceButs = 0;
        this.matchsJoues = 0;
    }

    @Override
    public String toString() {
        return String.format("%-15s | Pts: %2d | MJ: %1d | Diff: %+3d | Buts: %2d",
                nom, points, matchsJoues, differenceButs, butsMarques);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Equipe equipe = (Equipe) o;
        return Objects.equals(nom, equipe.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}