package src.views;

import src.controllers.InterfaceController;
import src.utils.Utilitaire;

import javax.swing.*;
import java.awt.*;

public final class EtatPersonneView extends ParentView {
    public static final int VIEW_WIDTH = 275;
    public static final int VIEW_HEIGHT = 150;
    public static final String BUTTON_TEXT = "État d'une personne";

    public EtatPersonneView(InterfaceController interfaceController) {
        setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        setLayout(new GridLayout(3, 1));

        JLabel title = Utilitaire.createTitle("Sélectionner une personne");
        add(title);

        JComboBox<String> comboBox = Utilitaire.createComboBox(interfaceController.getMairie().getProvider());
        add(comboBox);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Afficher");
        confirmButton.addActionListener(actionEvent -> {
            int id = comboBox.getSelectedIndex();
            interfaceController.getMairie().getEtatPersonneController().etatPersonne(interfaceController.getFrame(), id);
        });
        buttonsPanel.add(Utilitaire.backButton(interfaceController));
        buttonsPanel.add(confirmButton);
        add(buttonsPanel);
    }
}
