package src.controllers;

import src.enums.Sexe;
import src.models.Personne;
import src.utils.Utilitaire;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class NaissanceController {
    private final Mairie mairie;

    public NaissanceController(Mairie mairie) {
        this.mairie = mairie;
    }

    public boolean naissance(JFrame frame, int idParent1, int idParent2, Sexe sexe, JTextField[] textFields) {
        Personne[] parents = new Personne[2];
        if (idParent1 == -1 && idParent2 == -1) {
            parents[0] = null;
            parents[1] = null;
        } else {
            Personne parent1 = this.mairie.getProvider().getPersonne(idParent1);
            Personne parent2 = this.mairie.getProvider().getPersonne(idParent2);

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
            if (date.after(new Date())) {
                Utilitaire.showError(frame, "Vous ne pouvez pas déclarer de naissance après aujourd'hui !");
                return false;
            }
            p = new Personne(parents, nom, prenom, date, sexe);
        } catch (Exception e) {
            Utilitaire.showError(frame, "Format de date invalide. Utilisez JJ/MM/AAAA");
            return false;
        }
        this.mairie.getProvider().addPersonne(p);
        JOptionPane.showMessageDialog(frame, "ID: " + this.mairie.getProvider().lastId, "Nouvelle naissance ajoutée", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
