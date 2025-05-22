package src.views;

import src.controllers.InterfaceController;
import src.utils.Utilitaire;

import javax.swing.*;
import java.awt.*;

public final class DecesView extends ParentView {
    public static final int VIEW_WIDTH = 275;
    public static final int VIEW_HEIGHT = 150;
    public static final String BUTTON_TEXT = "Décès";

    public DecesView(InterfaceController interfaceController) {
        setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        setLayout(new GridLayout(3, 1));

        JLabel title = Utilitaire.createTitle("Selectionner la personne décédée");
        add(title);

        JComboBox<String> comboBox = Utilitaire.createComboBox(interfaceController.mairie.provider);
        add(comboBox);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.setForeground(new Color(46, 142, 95));
        confirmButton.addActionListener(actionEvent -> {
            int id = comboBox.getSelectedIndex();
            if (interfaceController.mairie.getDecesController().deces(interfaceController.frame, id))
                interfaceController.menuPrincipal(false);
        });

        buttonsPanel.add(Utilitaire.backButton(interfaceController));
        buttonsPanel.add(confirmButton);
        add(buttonsPanel);
    }
}
