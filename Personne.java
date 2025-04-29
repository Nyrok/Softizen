import java.util.UUID;

public class Personne {
    private String id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String sexe;
    private EtatCivil etatCivil;
    private Personne conjoint;

    public Personne(String nom, String prenom, String dateNaissance, String sexe) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.etatCivil = EtatCivil.CELIBATAIRE;
        this.conjoint = null;
    }

    public String getId() {
        return id;
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
        return "ID: " + id + " | " + nom + " " + prenom + " | Né(e) le: " + dateNaissance +
                " | Sexe: " + sexe + " | État civil: " + etatCivil +
                (conjoint != null ? " | Conjoint: " + conjoint.getNomPrenom() : "");
    }
}
