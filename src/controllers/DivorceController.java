package src.controllers;

import src.enums.EtatCivil;
import src.models.Personne;
import src.utils.Utilitaire;

import javax.swing.*;

public final class DivorceController {
    Mairie mairie;

    public DivorceController(Mairie mairie) {
        this.mairie = mairie;
    }

    public boolean divorce(JFrame frame, int id) {
        Personne p = this.mairie.provider.getPersonne(id);
        if (p == null) {
            Utilitaire.showError(frame, "Personne introuvable");
            return false;
        }
        if (p.getEtatCivil() == EtatCivil.DECES) {
            Utilitaire.showError(frame, "Cette personne est décédée.");
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

}
