import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestionCitoyens {
    BaseDonnees baseDonnees;

    public GestionCitoyens(BaseDonnees db) {
        baseDonnees = db;
    }

    public boolean mariage(JFrame frame, int id1, int id2) {
        Personne p1 = baseDonnees.getPersonne(id1);
        Personne p2 = baseDonnees.getPersonne(id2);

        if (p1 == null || p2 == null) {
            JOptionPane.showMessageDialog(frame, "Erreur : L'une des 2 personne n'existe pas !");
            return false;
        }

        if (p1.getEtatCivil() == EtatCivil.MARIE || p2.getEtatCivil() == EtatCivil.MARIE) {
            JOptionPane.showMessageDialog(frame, "Erreur : L'une des personnes est deja marié !");
            return false;
        }
        p1.marier(p2);
        p2.marier(p1);
        JOptionPane.showMessageDialog(frame, "Mariage réussi !");
        return true;
    }

    public boolean divorce(JFrame frame, int id) {
        Personne p = baseDonnees.getPersonne(id);
        if (p == null) {
            JOptionPane.showMessageDialog(frame, "Erreur : Personne introuvable");
            return false;
        }
        if (p.getEtatCivil() != EtatCivil.MARIE || p.getConjoint() == null) {
            JOptionPane.showMessageDialog(frame, "Erreur : La personne n'est pas mariée");
            return false;
        }

        Personne conjoint = p.getConjoint();
        p.divorcer();
        conjoint.divorcer();
        JOptionPane.showMessageDialog(frame, "Divorce réussi !");
        return true;
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

    public boolean naissance(JFrame frame, int idParent1, int idParent2, Sexe sexe, JTextField[] textFields) {
        Personne parent1 = baseDonnees.getPersonne(idParent1);
        Personne parent2 = baseDonnees.getPersonne(idParent2);

        if (parent1 == null && parent2 == null) {
            JOptionPane.showMessageDialog(frame, "Erreur: Une personne n'existe pas !");
            return false;
        }

        Personne[] parents = new Personne[2];
        parents[0] = parent1;
        parents[1] = parent2;

        String nom = textFields[0].getText();
        String prenom = textFields[1].getText();
        String dateNaissance = textFields[2].getText();

        if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs");
            return false;
        }

        Personne p;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(dateNaissance);
            p = new Personne(parents, nom, prenom, date, sexe);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Format de date invalide. Utilisez JJ/MM/AAAA");
            return false;
        }
        baseDonnees.ajouterPersonne(p);
        JOptionPane.showMessageDialog(frame, "Nouvelle naissance ajoutée avec l'ID: " + baseDonnees.lastId);
        return true;
    }


}
