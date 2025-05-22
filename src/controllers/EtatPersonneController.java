package src.controllers;

import src.enums.EtatCivil;
import src.models.Personne;
import src.utils.Utilitaire;

import javax.swing.*;

public final class EtatPersonneController {
    private final Mairie mairie;

    public EtatPersonneController(Mairie mairie) {
        this.mairie = mairie;
    }

    public void etatPersonne(JFrame frame, int id) {
        Personne p = this.mairie.getProvider().getPersonne(id);
        if (p != null) {
            String messageText = p.toString();

            if (p.getEtatCivil() == EtatCivil.DECES) {
                messageText += "\n\nStatut : Décédé(e)";
            } else {
                messageText += "\n\nStatut : Vivant(e)";
                if (p.getConjoint() != null) {
                    messageText += "\nMarié(e) avec : " + p.getConjoint().getNomPrenom();
                } else {
                    if (p.getEtatCivil() == EtatCivil.VEUF) {
                        messageText += "\nVeuf/Veuve";
                    }
                }
            }

            JOptionPane.showMessageDialog(frame, messageText,
                    "État de la personne", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Utilitaire.showError(frame, "Cette personne n'existe pas");
        }
    }
}
