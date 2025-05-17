import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GestionCitoyens {
    BaseDonnees baseDonnees;

    public GestionCitoyens(BaseDonnees db) {
        baseDonnees = db;
    }

    public boolean mariage(JFrame frame, int id1, int id2) {
        System.out.println("id1: " + id1 + " id2: " + id2);
        Personne p1 = baseDonnees.getPersonne(id1);
        Personne p2 = baseDonnees.getPersonne(id2);

        if (p1 == null || p2 == null) {
            Utilitaire.showError(frame, "L'une des 2 personnes n'existe pas !");
            return false;
        }

        if (id1 == id2) {
            Utilitaire.showError(frame, "Vous devez marier deux personnes différentes !");
            return false;
        }

        if (p1.getEtatCivil() == EtatCivil.MARIE || p2.getEtatCivil() == EtatCivil.MARIE) {
            Utilitaire.showError(frame, "L'une des personnes est déjà mariée !");
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
            Utilitaire.showError(frame, "Personne introuvable");
            return false;
        }
        if (p.getEtatCivil() != EtatCivil.MARIE || p.getConjoint() == null) {
            Utilitaire.showError(frame, "La personne n'est pas mariée");
            return false;
        }

        Personne conjoint = p.getConjoint();
        p.divorcer();
        conjoint.divorcer();
        JOptionPane.showMessageDialog(frame, "Divorce réussi !");
        return true;
    }

    public void deces(int id){
        Personne p = baseDonnees.getPersonne(id);
        if (p.getEtatCivil() == EtatCivil.MARIE){
            Personne conjoint = baseDonnees.getPersonne(id);
            p.deces();
            conjoint.veuf();
        }else if(p.getEtatCivil() != EtatCivil.MARIE){
            p.deces();
        }
    }

    public static void suiteDeces(JButton valider, JTextField champParent1, JTextField champParent2, GestionCitoyens gestion, JFrame fnais) {
        valider.addActionListener(e -> {
            try {

                String parent1 = champParent1.getText();
                String parent2 = champParent2.getText();

                int idParent1 = Integer.parseInt(parent1);
                int idParent2 = Integer.parseInt(parent2);


                Personne p1 = gestion.baseDonnees.getPersonne(idParent1);
                Personne p2 = gestion.baseDonnees.getPersonne(idParent2);

                if (p1 == null || p2 == null) {
                    Utilitaire.showError(fnais, "L'une des 2 personnes n'existe pas !");
                    return;
                }

                if (idParent1 == idParent2) {
                    Utilitaire.showError(fnais, "Vous devez déclarer deux parents différents !");
                    return;
                }

                if (p1.getEtatCivil() == EtatCivil.DECES || p2.getEtatCivil() == EtatCivil.DECES) {
                    Utilitaire.showError(fnais, "Une des personnes est décédée");
                    return;
                }

            } catch (NumberFormatException ex) {
                Utilitaire.showError(fnais, "Les IDs doivent être des nombres");
            } catch (Exception ex) {
                Utilitaire.showError(fnais, "Erreur : " + ex.getMessage());
            }
        });
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
        Personne[] parents = new Personne[2];
        if (idParent1 == -1 && idParent2 == -1) {
            parents[0] = null;
            parents[1] = null;
        } else {
            Personne parent1 = baseDonnees.getPersonne(idParent1);
            Personne parent2 = baseDonnees.getPersonne(idParent2);

            if (parent1 == null || parent2 == null) {
                Utilitaire.showError(frame, "L'une des 2 personne n'existe pas");
                return false;
            }

            if (idParent1 == idParent2) {
                Utilitaire.showError(frame, "Vous devez déclarer deux parents différents");
                return false;
            }

            parents[0] = parent1;
            parents[1] = parent2;
        }

        String nom = textFields[0].getText();
        String prenom = textFields[1].getText();
        String dateNaissance = textFields[2].getText();

        if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty()) {
            Utilitaire.showError(frame, "Veuillez remplir tous les champs");
            return false;
        }

        Personne p;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(dateNaissance);
            p = new Personne(parents, nom, prenom, date, sexe);
        } catch (Exception e) {
            Utilitaire.showError(frame, "Format de date invalide. Utilisez JJ/MM/AAAA");
            return false;
        }
        baseDonnees.ajouterPersonne(p);
        JOptionPane.showMessageDialog(frame, "ID: " + baseDonnees.lastId, "Nouvelle naissance ajoutée", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }


}
