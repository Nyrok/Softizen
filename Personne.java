import java.util.Date;

public class Personne {
    private final Personne[] parents;
    private final String nom;
    private final String prenom;
    private final Date dateNaissance;
    private final Sexe sexe;
    private EtatCivil etatCivil;
    private Personne conjoint;

    public Personne(Personne[] parents, String nom, String prenom, Date dateNaissance, Sexe sexe) {
        this.parents = parents;
        this.nom = nom.toUpperCase();
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

    public Sexe getSexe() {
        return sexe;
    }

    public Date getDateNaissance() {
        return dateNaissance;
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

    public void deces(){
        this.etatCivil = EtatCivil.DECES;
    }

    public void veuf(){
        this.etatCivil = EtatCivil.VEUF;
    }



    @Override

    public String toString() {
        return nom + " " + prenom + " | Né(e) le: " + dateNaissance +
                " | Sexe: " + sexe + " | État civil: " + etatCivil +
                (conjoint != null ? " | Conjoint: " + conjoint.getNomPrenom() : "");
    }
}
