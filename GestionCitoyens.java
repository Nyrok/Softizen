import javax.swing.*;

public class GestionCitoyens {
    BaseDonnees baseDonnees;

    public GestionCitoyens(BaseDonnees db) {
        baseDonnees = db;
    }

    public void mariage(JFrame frame, int id1, int id2) {
        Personne p1 = baseDonnees.getPersonne(id1);
        Personne p2 = baseDonnees.getPersonne(id2);

        if (p1 == null || p2 == null) {
            JOptionPane.showMessageDialog(frame, "Erreur : L'une des 2 personne n'existe pas !");
            return;
        }

        if (p1.getEtatCivil() == EtatCivil.MARIE || p2.getEtatCivil() == EtatCivil.MARIE) {
            JOptionPane.showMessageDialog(frame, "Erreur : L'une des personnes est deja marié !");
            return;
        }
        p1.marier(p2);
        p2.marier(p1);
        JOptionPane.showMessageDialog(frame, "Mariage réussi !");
    }

    public void divorce(JFrame frame, int id) {
        Personne p = baseDonnees.getPersonne(id);
        if (p == null) {
            JOptionPane.showMessageDialog(frame, "Erreur : Personne introuvable");
            return;
        }
        if (p.getEtatCivil() != EtatCivil.MARIE || p.getConjoint() == null) {
            JOptionPane.showMessageDialog(frame, "Erreur : La personne n'est pas mariée");
            return;
        }

        Personne conjoint = p.getConjoint();
        p.divorcer();
        conjoint.divorcer();
        System.out.println("Divorce réussi !");
    }

    public void afficherEtat(int id) {
        Personne p = baseDonnees.getPersonne(id);
        if (p == null) {
            System.out.println("Personne introuvable");
            return;
        }
        System.out.println(p);
    }

    public void afficher() {
        for (Personne p : baseDonnees.listerPersonnes()) {
            System.out.println(p);
            System.out.println("--------------------------------");
        }
    }

    public void naissance(int idParent1, int idParent2) {
        Personne parent1 = baseDonnees.getPersonne(idParent1);
        Personne parent2 = baseDonnees.getPersonne(idParent2);

        if (parent1 == null && parent2 == null) {
            System.out.println("Une personne n'existe pas !");
            return;
        }

        String nom = Utilitaire.lire("Nom de l'enfant: ");
        String prenom = Utilitaire.lire("Prenom de l'enfant: ");
        String dateNaissance = Utilitaire.lire("Date de naissance (JJ/MM/AAAA): ");
        String sexe = Utilitaire.lire("Sexe (M/F): ");

        Personne p = new Personne(nom, prenom, dateNaissance, sexe);
        baseDonnees.ajouterPersonne(p);
        System.out.println("Personne ajoutée avec l'ID: " + baseDonnees.lastId);
    }


}
