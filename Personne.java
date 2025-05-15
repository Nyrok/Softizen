public class Personne {
    private final Personne[] parents;
    private final String nom;
    private final String prenom;
    private final String dateNaissance;
    private final String sexe;
    private EtatCivil etatCivil;
    private Personne conjoint;

    public Personne(Personne[] parents, String nom, String prenom, String dateNaissance, String sexe) {
        this.parents = parents;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.etatCivil = EtatCivil.CELIBATAIRE;
        this.conjoint = null;
    }

    public Personne[] getParents() {
        return parents;
    }

    public String getNomPrenom() {
        return nom + " " + prenom;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public Personne getConjoint() {
        return conjoint;
    }

    public void marier(Personne conjoint) {
        this.etatCivil = EtatCivil.MARIE;
        this.conjoint = conjoint;
    }

    public void divorcer() {
        this.etatCivil = EtatCivil.DIVORCE;
        this.conjoint = null;
    }

    @Override

    public String toString() {
        return nom + " " + prenom + " | Né(e) le: " + dateNaissance +
                " | Sexe: " + sexe + " | État civil: " + etatCivil +
                (conjoint != null ? " | Conjoint: " + conjoint.getNomPrenom() : "");
    }
}
