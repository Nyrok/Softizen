package src.views;

import src.enums.EtatCivil;
import src.models.Personne;
import src.utils.Utilitaire;

import javax.swing.*;
import java.awt.*;

public final class EtatPersonneView extends ParentView {
    public static final int VIEW_WIDTH = 275;
    public static final int VIEW_HEIGHT =  150;
    public static final String BUTTON_TEXT = "État d'une personne";

    public EtatPersonneView(Interface interfaceView) {
        setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        setLayout(new GridLayout(3, 1));

        JLabel title = Utilitaire.createTitle("Sélectionner une personne");
        add(title);

        JComboBox<String> comboBox = Utilitaire.createComboBox(interfaceView.provider);
        add(comboBox);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Afficher");
        confirmButton.addActionListener(actionEvent -> {
            int id = comboBox.getSelectedIndex();
            Personne p = interfaceView.provider.getPersonne(id);
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

                JOptionPane.showMessageDialog(interfaceView.frame, messageText,
                        "État de la personne", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Utilitaire.showError(interfaceView.frame, "Cette personne n'existe pas");
            }
        });
        buttonsPanel.add(Utilitaire.backButton(interfaceView));
        buttonsPanel.add(confirmButton);
        add(buttonsPanel);
    }
}
