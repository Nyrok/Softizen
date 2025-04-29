import java.util.UUID;

public class Personne {
    private String id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String sexe;
    private EtatCivil etatCivil;
    private String idConjoint;

    public Personne(String nom, String prenom, String dateNaissance, String sexe) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.etatCivil = EtatCivil.CELIBATAIRE;
        this.idConjoint = null;
    }
    public String getId() {return id;}
    public String getNomPrenom() {return nom + " "+ prenom;}
    public EtatCivil getEtatCivil() {return etatCivil;}
    public String getIdConjoint() {return idConjoint;}

    public void marier(String idConjoint)
    {
        this.etatCivil = EtatCivil.MARIE;
        this.idConjoint = idConjoint;
    }

    public void divorcer(){
        this.etatCivil = EtatCivil.DIVORCE;
        this.idConjoint = null;
    }
    @Override

    public String toString() {
        return "ID: " + id + " | " + nom + " " + prenom + " | Né(e) le: " + dateNaissance +
                " | Sexe: " + sexe + " | État civil: " + etatCivil +
                (idConjoint != null ? " | Conjoint ID: " + idConjoint : "");
    }
}
