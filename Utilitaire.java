import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
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
}
