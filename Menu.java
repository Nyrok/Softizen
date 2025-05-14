import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.swing.*;

public class Menu {
    JFrame frame;
    LinkedHashMap<String, String> buttonsMap;

    public Menu() {
        this.frame = new JFrame();
        this.frame.setTitle("Softizen");
        this.frame.setPreferredSize(new Dimension(300, 300));
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.buttonsMap = new LinkedHashMap<>();
        this.buttonsMap.put("mariage", "Mariage");
        this.buttonsMap.put("divorce", "Divorce");
        this.buttonsMap.put("naissance", "Naissance");
        this.buttonsMap.put("etatPersonne", "État d'une personne");
        this.buttonsMap.put("affichageListePersonnes", "Affichage de la liste des personnes");
        this.buttonsMap.put("saisiePersonnes", "Saisie des personnes");
        this.buttonsMap.put("quitterProgramme", "Quitter le programme");
    }

    public void afficher() {
        int buttonsMapSize = buttonsMap.size();
        JButton[] buttons = new JButton[buttonsMapSize];
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(buttonsMapSize, 1));
        Iterator<String> keysIterator = this.buttonsMap.keySet().iterator();
        Iterator<String> valuesIterator = this.buttonsMap.values().iterator();
        int i = 0;
        String key, value;
        while (valuesIterator.hasNext()) {
            key = keysIterator.next();
            value = valuesIterator.next();
            buttons[i] = new JButton(value);
            buttons[i].setSize(this.frame.getWidth(), 20);
            buttons[i].setActionCommand(key);
            buttons[i].addActionListener(this::buttonCallback);
            panel.add(buttons[i]);
            i++;
        }
        this.frame.add(panel);
        this.frame.pack();
    }

    private static void mariage() {
        System.out.println("Ici on va déclarer un mariage");
    }

    private static void divorce() {
        System.out.println("Ici on va déclarer un divorce");
    }

    private static void naissance() {
        System.out.println("Ici on va déclarer une naissance");
    }

    private static void etatPersonne() {
        System.out.println("Ici on va afficher l'état d'une personne");
    }

    private static void affichageListePersonnes() {
        System.out.println("Ici on va afficher les personnes");
    }

    private static void saisiePersonnes() {
        System.out.println("Ici on va saisir les personnes");
    }

    private static void quitterProgramme() {
        System.out.println("Au revoir");
    }

    private void buttonCallback(ActionEvent actionEvent) {
        String methodName = actionEvent.getActionCommand();
        try {
            Method method = this.getClass().getDeclaredMethod(methodName);
            method.invoke(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
