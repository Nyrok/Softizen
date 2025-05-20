package src.views;

import src.enums.Sexe;
import src.utils.Utilitaire;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final  class SaisiePersonneView extends ParentView {
    public static final int VIEW_WIDTH = 300;
    public static final int VIEW_HEIGHT =  250;
    public static final String BUTTON_TEXT = "Saisie d'une personne";

    public SaisiePersonneView(Interface interfaceView) {
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = Utilitaire.createTitle("Saisir une nouvelle personne");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(title);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        String[] labels = {"Nom :", "Prénom :", "Date (JJ/MM/AAAA) :"};
        JTextField[] textFields = new JTextField[3];

        for (int i = 0; i < 3; i++) {
            inputPanel.add(new JLabel(labels[i]));
            textFields[i] = new JTextField();
            inputPanel.add(textFields[i]);
        }

        inputPanel.add(new JLabel("Sexe :"));
        JComboBox<Sexe> sexeBox = new JComboBox<>(Sexe.values());
        inputPanel.add(sexeBox);
        add(inputPanel);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.setForeground(new Color(46, 142, 95));
        confirmButton.addActionListener(actionEvent -> {
            Sexe sexe = (Sexe) Objects.requireNonNull(sexeBox.getSelectedItem());
            if (interfaceView.mairie.naissance(interfaceView.frame, -1, -1, sexe, textFields))
                interfaceView.menuPrincipal(false);
        });

        buttonsPanel.add(Utilitaire.backButton(interfaceView));
        buttonsPanel.add(confirmButton);
        add(buttonsPanel);
    }
}
