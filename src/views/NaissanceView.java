package src.views;

import src.controllers.InterfaceController;
import src.enums.Sexe;
import src.utils.Utilitaire;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class NaissanceView extends ParentView {
    public static final int VIEW_WIDTH = 300;
    public static final int VIEW_HEIGHT =  300;
    public static final String BUTTON_TEXT = "Naissance";

    public NaissanceView(InterfaceController interfaceController) {
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = Utilitaire.createTitle("Déclarer une naissance");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(title);

        JPanel parentsPanel = new JPanel();
        parentsPanel.setLayout(new GridLayout(6, 2));
        JLabel[] parentLabels = new JLabel[2];
        JComboBox<String>[] parentBoxes = new JComboBox[2];

        parentLabels[0] = new JLabel("Parent 1 :");
        parentLabels[1] = new JLabel("Parent 2 :");

        for (int i = 0; i < 2; i++) {
            parentsPanel.add(parentLabels[i]);
            parentBoxes[i] = Utilitaire.createComboBox(interfaceController.mairie.provider);
            parentsPanel.add(parentBoxes[i]);
        }
        add(parentsPanel);

        String[] labels = {"Nom :", "Prénom :", "Date (JJ/MM/AAAA) :"};
        JTextField[] textFields = new JTextField[3];

        for (int i = 0; i < 3; i++) {
            parentsPanel.add(new JLabel(labels[i]));
            textFields[i] = new JTextField();
            parentsPanel.add(textFields[i]);
        }
        parentsPanel.add(new JLabel("Sexe :"));
        JComboBox<Sexe> sexeBox = new JComboBox<>(Sexe.values());
        parentsPanel.add(sexeBox);
        add(parentsPanel);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.setForeground(new Color(46, 142, 95));
        confirmButton.addActionListener(actionEvent -> {
            Sexe sexe = (Sexe) Objects.requireNonNull(sexeBox.getSelectedItem());
            int idP1 = parentBoxes[0].getSelectedIndex();
            int idP2 = parentBoxes[1].getSelectedIndex();

            if (interfaceController.mairie.getNaissanceController().naissance(interfaceController.frame, idP1, idP2, sexe, textFields))
                interfaceController.menuPrincipal(false);
        });

        buttonsPanel.add(Utilitaire.backButton(interfaceController));
        buttonsPanel.add(confirmButton);
        add(buttonsPanel);
    }
}
