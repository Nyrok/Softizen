package src.views;

import src.utils.Utilitaire;

import javax.swing.*;
import java.awt.*;

public final class DecesView extends JPanel {
    public DecesView(Interface interfaceView) {
        setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        setLayout(new GridLayout(3, 1));

        JLabel title = Utilitaire.createTitle("Selectionner la personne décédée");
        add(title);

        JComboBox<String> comboBox = Utilitaire.createComboBox(interfaceView.database);
        add(comboBox);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.setForeground(new Color(46, 142, 95));
        confirmButton.addActionListener(actionEvent -> {
            int id = comboBox.getSelectedIndex();
            if (interfaceView.mairie.deces(interfaceView.frame, id))
                interfaceView.menuPrincipal(false);
        });

        buttonsPanel.add(Utilitaire.backButton(interfaceView));
        buttonsPanel.add(confirmButton);
        add(buttonsPanel);
    }
}
