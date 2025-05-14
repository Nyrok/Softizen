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

    public void menuPrincipal() {
        this.reset();
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

    private void reset() {
        this.frame.getContentPane().removeAll();
        this.frame.repaint();
        this.frame.revalidate();
    }

    private void mariage() {
        this.reset();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        JLabel title = new JLabel("Sélectionner les personnes à marier");
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title);
        String[] options = {"Hamza Konte (1)", "Yassine Bakadir (2)", "Karine Bledarde (3)"};
        JLabel[] labels = new JLabel[2];
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(2, 2));
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("ID Personne " + (i + 1) + " :");
            subPanel.add(labels[i]);
            JComboBox<String> comboBox = new JComboBox<>(options);
            comboBox.addActionListener(_ -> {
                String selected = (String) comboBox.getSelectedItem();
                JOptionPane.showMessageDialog(frame, "Vous avez sélectionné : " + selected);
            });
            subPanel.add(comboBox);
        }
        panel.add(subPanel);
        JButton button = new JButton("Confirmer");
        button.setSize(this.frame.getWidth(), 20);
        panel.add(button);
        this.frame.add(panel);
        this.frame.pack();
    }

    private void divorce() {
        System.out.println("Ici on va déclarer un divorce");
    }

    private void naissance() {
        System.out.println("Ici on va déclarer une naissance");
    }

    private void etatPersonne() {
        System.out.println("Ici on va afficher l'état d'une personne");
    }

    private void affichageListePersonnes() {
        System.out.println("Ici on va afficher les personnes");
    }

    private void saisiePersonnes() {
        System.out.println("Ici on va saisir les personnes");
    }

    private void quitterProgramme() {
        System.exit(0);
    }

    private void buttonCallback(ActionEvent actionEvent) {
        String methodName = actionEvent.getActionCommand();
        try {
            Method method = this.getClass().getDeclaredMethod(methodName);
            method.invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
