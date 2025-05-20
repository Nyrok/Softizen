package src.controllers;

import src.enums.EtatCivil;
import src.enums.Sexe;
import src.models.Personne;
import src.utils.Utilitaire;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mairie {
    Provider provider;

    public Mairie(Provider db) {
        provider = db;
    }

    public boolean mariage(JFrame frame, int id1, int id2) {
        Personne p1 = this.provider.getPersonne(id1);
        Personne p2 = this.provider.getPersonne(id2);

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
        JOptionPane.showMessageDialog(frame, "Mariage enregistré avec succès !");
        return true;
    }

    public boolean divorce(JFrame frame, int id) {
        Personne p = this.provider.getPersonne(id);
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
        JOptionPane.showMessageDialog(frame, "Divorce enregistré avec succès !");
        return true;
    }

    public boolean deces(JFrame frame, int id) {
        Personne p = this.provider.getPersonne(id);
        if (p == null) {
            Utilitaire.showError(frame, "La personne n'existe pas.");
            return false;
        }
        if (p.getEtatCivil() == EtatCivil.DECES) {
            Utilitaire.showError(frame, "Cette personne est deja décedée");
            return false;
        }
        if (p.getEtatCivil() == EtatCivil.MARIE) {
            Personne conjoint = p.getConjoint();
            conjoint.setConjoint(null);
            conjoint.setEtatCivil(EtatCivil.VEUF);
        }
        p.deces();
        JOptionPane.showMessageDialog(frame, "Décès enregistré avec succès !");
        return true;
    }

    public boolean naissance(JFrame frame, int idParent1, int idParent2, Sexe sexe, JTextField[] textFields) {
        Personne[] parents = new Personne[2];
        if (idParent1 == -1 && idParent2 == -1) {
            parents[0] = null;
            parents[1] = null;
        } else {
            Personne parent1 = this.provider.getPersonne(idParent1);
            Personne parent2 = this.provider.getPersonne(idParent2);

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
        this.provider.ajouterPersonne(p);
        JOptionPane.showMessageDialog(frame, "ID: " + this.provider.lastId, "Nouvelle naissance ajoutée", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
