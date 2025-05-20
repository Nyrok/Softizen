package src.views;

import src.utils.Utilitaire;

import javax.swing.*;
import java.awt.*;

public final class MariageView extends ParentView {
    public static final int VIEW_WIDTH = 275;
    public static final int VIEW_HEIGHT =  200;
    public static final String BUTTON_TEXT = "Mariage";

    public MariageView(Interface interfaceView) {
        setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        setLayout(new GridLayout(3, 1));
        JLabel title = Utilitaire.createTitle("Sélectionner les personnes à marier");
        add(title);
        JLabel[] labels = new JLabel[2];
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(2, 2));
        JComboBox<String>[] comboBoxes = new JComboBox[2];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("Personne " + (i + 1) + " :");
            subPanel.add(labels[i]);
            comboBoxes[i] = Utilitaire.createComboBox(interfaceView.database);
            subPanel.add(comboBoxes[i]);
        }
        add(subPanel);
        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.setForeground(new Color(46, 142, 95));
        confirmButton.addActionListener(actionEvent -> {
            int idP1 = comboBoxes[0].getSelectedIndex();
            int idP2 = comboBoxes[1].getSelectedIndex();
            if (interfaceView.mairie.mariage(interfaceView.frame, idP1, idP2))
                interfaceView.menuPrincipal(false);
        });
        buttonsPanel.add(Utilitaire.backButton(interfaceView));
        buttonsPanel.add(confirmButton);
        add(buttonsPanel);
    }
}
