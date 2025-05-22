package src.controllers;

import src.enums.EtatCivil;
import src.models.Personne;
import src.utils.Utilitaire;

import javax.swing.*;

public final class DecesController {
    Provider provider;

    public DecesController(Provider provider) {
        this.provider = provider;
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
}
