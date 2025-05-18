import java.util.Date;
import java.time.LocalDate;

public class Personne {
    private final Personne[] parents;
    private final String nom;
    private final String prenom;
    private final Date dateNaissance;
    private final Sexe sexe;
    private int id;
    private EtatCivil etatCivil;
    private Personne conjoint;
    private LocalDate dateDeces;

    public Personne(Personne[] parents, String nom, String prenom, Date dateNaissance, Sexe sexe) {
        this.parents = parents;
        this.nom = nom.toUpperCase().trim();
        this.prenom = prenom.trim();
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.etatCivil = EtatCivil.CELIBATAIRE;
        this.conjoint = null;
        this.dateDeces = null;
    }

    public Personne[] getParents() {
        return parents;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
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

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }

    public Personne getConjoint() {
        return conjoint;
    }

    public void setConjoint(Personne conjoint) {
        if (this.conjoint != null && conjoint == null) {
            this.etatCivil = EtatCivil.VEUF;
        }
        this.conjoint = conjoint;
    }

    public void deces() {
        this.dateDeces = LocalDate.now();
        this.etatCivil = EtatCivil.DECES;
    }

    // Méthode pour obtenir la date de décès
    public LocalDate getDateDeces() {
        return dateDeces;
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
        return "ID:" + id + " | " + nom + " " + prenom + " | Né(e) le: " + dateNaissance +
                " | Sexe: " + sexe + " | État civil: " + etatCivil +
                (conjoint != null ? " | Conjoint: " + conjoint.getNomPrenom() : "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
