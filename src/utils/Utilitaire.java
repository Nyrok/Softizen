package src.utils;

import src.controllers.Provider;
import src.models.Personne;
import src.views.Interface;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public abstract class Utilitaire {
    public static JLabel createTitle(String title) {
        JLabel label = new JLabel("<html><div style='text-align: center;font-weight: bold;'>" + title.toUpperCase() + "</div></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        return label;
    }

    public static JComboBox<String> createComboBox(Provider provider) {
        Collection<Personne> listPersonnes = provider.listerPersonnes();
        Iterator<Personne> listPersonnesI = listPersonnes.iterator();
        String[] options = new String[listPersonnes.size() + 1];
        options[0] = "--";
        int i = 1;
        while (listPersonnesI.hasNext()) {
            Personne personne = listPersonnesI.next();
            options[i] = personne.getNomPrenom() + " (" + i + ")";
            i++;
        }
        return new JComboBox<String>(options);
    }

    public static void showError(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame,
                message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }

    public static JButton backButton(Interface interfaceView) {
        JButton button = new JButton("Retour");
        button.setForeground(new Color(242, 140, 40));
        button.addActionListener(actionEvent -> interfaceView.menuPrincipal(false));
        return button;
    }
}
