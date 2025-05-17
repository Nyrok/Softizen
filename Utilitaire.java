import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

public class Utilitaire {
    private static final Scanner scanner = new Scanner(System.in);

    public static String lire(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static JDialog lireInt(String title, String message) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setVisible(true);
        JLabel label = new JLabel(message);
        dialog.getContentPane().add(label);
        JTextField text = new JTextField(10);
        dialog.getContentPane().add(text);
        JButton confirm = new JButton("Entrer");
        dialog.getContentPane().add(confirm);
        return dialog;
    }

    public static JLabel createTitle(String title) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + title + "</div></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        return label;
    }

    public static JComboBox<String> createComboBox(BaseDonnees baseDonnees) {
        Collection<Personne> listPersonnes = baseDonnees.listerPersonnes();
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
}
