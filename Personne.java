import java.util.Date;
import java.time.LocalDate;

public class Personne {
    private final Personne[] parents;
    private final String nom;
    private final String prenom;
    private final Date dateNaissance;
    private final Sexe sexe;
    private EtatCivil etatCivil;
    private Personne conjoint;
    private boolean veuf;
    private LocalDate dateDeces;

    public Personne(Personne[] parents, String nom, String prenom, Date dateNaissance, Sexe sexe) {
        this.parents = parents;
        this.nom = nom.toUpperCase();
        this.prenom = prenom;
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

    public void setConjoint(Personne conjoint){
        if (this.conjoint != null && conjoint == null) {
        this.veuf = true;
    }
        this.conjoint = conjoint;
    }

    public boolean isDecedes() {return dateDeces != null;}

    public boolean isVeuf() {return this.veuf;}

    public void deces() {
        if (!isDecedes()) {  // On vérifie que la personne n'est pas déjà décédée
            this.dateDeces = LocalDate.now();

            // Si la personne était mariée, son conjoint devient veuf/veuve
            if (this.conjoint != null) {
                Personne conjointTemp = this.conjoint;
                this.conjoint = null;            // La personne décédée n'est plus mariée
                conjointTemp.setConjoint(null);  // Le conjoint devient veuf/veuve
            }
        }
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

    public void veuf() {
        this.etatCivil = EtatCivil.VEUF;
    }


    @Override

    public String toString() {
        return nom + " " + prenom + " | Né(e) le: " + dateNaissance +
                " | Sexe: " + sexe + " | État civil: " + etatCivil +
                (conjoint != null ? " | Conjoint: " + conjoint.getNomPrenom() : "");
    }
}
