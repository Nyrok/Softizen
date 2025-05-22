package src.controllers;

import src.enums.EtatCivil;
import src.models.Personne;
import src.utils.Utilitaire;

import javax.swing.*;
import java.util.Date;

public final class MariageController {
    private final Mairie mairie;

    public MariageController(Mairie mairie) {
        this.mairie = mairie;
    }

    public boolean mariage(JFrame frame, int id1, int id2) {
        Personne p1 = this.mairie.getProvider().getPersonne(id1);
        Personne p2 = this.mairie.getProvider().getPersonne(id2);

        if (p1 == null || p2 == null) {
            Utilitaire.showError(frame, "L'une des 2 personnes n'existe pas !");
            return false;
        }

        if (id1 == id2) {
            Utilitaire.showError(frame, "Vous devez marier deux personnes différentes !");
            return false;
        }

        if (p1.getEtatCivil() == EtatCivil.DECES || p2.getEtatCivil() == EtatCivil.DECES) {
            Utilitaire.showError(frame, "Vous ne pouvez pas marier une personne décédée.");
            return false;
        }
        if (p1.getEtatCivil() == EtatCivil.MARIE || p2.getEtatCivil() == EtatCivil.MARIE) {
            Utilitaire.showError(frame, "L'une des personnes est déjà mariée !");
            return false;
        }

        int currentYear = new Date().getYear();
        int age1 = currentYear - p1.getDateNaissance().getYear();
        int age2 = currentYear - p2.getDateNaissance().getYear();

        if (age1 < 18 || age2 < 18) {
            Utilitaire.showError(frame, "Les deux personnes doivent avoir au moins 18 ans !");
            return false;
        }

        p1.marier(p2);
        p2.marier(p1);
        JOptionPane.showMessageDialog(frame, "Mariage enregistré avec succès !");
        return true;
    }
}
