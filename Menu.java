import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import javax.swing.*;

public class Menu {
    JFrame frame;
    LinkedHashMap<String, String> buttonsMap;
    BaseDonnees baseDonnees;
    GestionCitoyens gestionCitoyens;

    public Menu(BaseDonnees db, GestionCitoyens gc) {
        this.baseDonnees = db;
        this.gestionCitoyens = gc;
        this.frame = new JFrame();
        this.frame.setTitle("Softizen");
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        this.frame.setPreferredSize(new Dimension(300, 300));
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
        this.frame.setPreferredSize(new Dimension(275, 200));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        panel.setLayout(new GridLayout(3, 1));
        JLabel title = Utilitaire.createTitle("Sélectionner les personnes à marier");
        panel.add(title);
        JLabel[] labels = new JLabel[2];
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(2, 2));
        JComboBox<String>[] comboBoxes = new JComboBox[2];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("Personne " + (i + 1) + " :");
            subPanel.add(labels[i]);
            comboBoxes[i] = Utilitaire.createComboBox(this.baseDonnees);
            subPanel.add(comboBoxes[i]);
        }
        panel.add(subPanel);
        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(actionEvent -> {
            int idP1 = comboBoxes[0].getSelectedIndex();
            int idP2 = comboBoxes[1].getSelectedIndex();
            if (this.gestionCitoyens.mariage(this.frame, idP1, idP2))
                this.menuPrincipal();
        });
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(this.backButton());
        panel.add(buttonsPanel);
        this.frame.add(panel);
        this.frame.pack();
    }

    private void divorce() {
        this.reset();
        this.frame.setPreferredSize(new Dimension(275, 150));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        panel.setLayout(new GridLayout(3, 1));
        JLabel title = Utilitaire.createTitle("Sélectionner la personne à divorcer");
        panel.add(title);
        JComboBox<String> comboBox = Utilitaire.createComboBox(this.baseDonnees);
        panel.add(comboBox);
        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(actionEvent -> {
            int id = comboBox.getSelectedIndex();
            if (this.gestionCitoyens.divorce(this.frame, id))
                this.menuPrincipal();
        });
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(this.backButton());
        panel.add(buttonsPanel);
        this.frame.add(panel);
        this.frame.pack();
    }

    private void naissance() {
        this.reset();
        this.frame.setPreferredSize(new Dimension(275, 300));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = Utilitaire.createTitle("Déclarer une naissance");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(title);

        JPanel parentsPanel = new JPanel();
        parentsPanel.setLayout(new GridLayout(6, 2));
        JLabel[] parentLabels = new JLabel[2];
        JComboBox<String>[] parentBoxes = new JComboBox[2];

        parentLabels[0] = new JLabel("Parent 1 :");
        parentLabels[1] = new JLabel("Parent 2 :");

        for (int i = 0; i < 2; i++) {
            parentsPanel.add(parentLabels[i]);
            parentBoxes[i] = Utilitaire.createComboBox(this.baseDonnees);
            parentsPanel.add(parentBoxes[i]);
        }
        panel.add(parentsPanel);

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
        panel.add(parentsPanel);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(actionEvent -> {
            Sexe sexe = (Sexe) Objects.requireNonNull(sexeBox.getSelectedItem());
            int idP1 = parentBoxes[0].getSelectedIndex();
            int idP2 = parentBoxes[1].getSelectedIndex();

            if (this.gestionCitoyens.naissance(this.frame, idP1, idP2, sexe, textFields))
                this.menuPrincipal();
        });

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(this.backButton());
        panel.add(buttonsPanel);

        this.frame.add(panel);
        this.frame.pack();
    }

    private void etatPersonne() {
        this.reset();
        this.frame.setPreferredSize(new Dimension(275, 150));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        panel.setLayout(new GridLayout(3, 1));

        JLabel title = Utilitaire.createTitle("Sélectionner une personne");
        panel.add(title);

        JComboBox<String> comboBox = Utilitaire.createComboBox(this.baseDonnees);
        panel.add(comboBox);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Afficher");
        confirmButton.addActionListener(actionEvent -> {
            int id = comboBox.getSelectedIndex();
            Personne p = this.baseDonnees.getPersonne(id);
            if (p != null) {
                JOptionPane.showMessageDialog(this.frame, p.toString(),
                        "État de la personne", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Utilitaire.showError(this.frame, "Cette personne n'existe pas");
            }
        });
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(this.backButton());
        panel.add(buttonsPanel);

        this.frame.add(panel);
        this.frame.pack();
    }

    private void affichageListePersonnes() {
        this.reset();
        this.frame.setPreferredSize(new Dimension(700, 400));

        String[] columnNames = {"Nom", "Prénom", "Sexe", "Date de naissance", "Conjoint", "Parent 1", "Parent 2"};
        Collection<Personne> personnes = baseDonnees.listerPersonnes();
        Object[][] data = new Object[personnes.size()][7];

        int i = 0;
        for (Personne p : personnes) {
            data[i][0] = p.getNomPrenom().split(" ")[0];
            data[i][1] = p.getNomPrenom().split(" ")[1];
            data[i][2] = p.getSexe();
            data[i][3] = new SimpleDateFormat("dd/MM/yyyy").format(p.getDateNaissance());
            data[i][4] = p.getConjoint() != null ? p.getConjoint().getNomPrenom() : "";
            data[i][5] = p.getParents() != null && p.getParents()[0] != null ? p.getParents()[0].getNomPrenom() : "";
            data[i][6] = p.getParents() != null && p.getParents()[1] != null ? p.getParents()[1].getNomPrenom() : "";
            i++;
        }

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.backButton());
        panel.add(buttonPanel, BorderLayout.SOUTH);

        this.frame.add(panel);
        this.frame.pack();
    }

    private void saisiePersonnes() {
        this.reset();
        this.frame.setPreferredSize(new Dimension(275, 250));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = Utilitaire.createTitle("Saisir une nouvelle personne");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(title);

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
        panel.add(inputPanel);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(actionEvent -> {
            Sexe sexe = (Sexe) Objects.requireNonNull(sexeBox.getSelectedItem());
            if (this.gestionCitoyens.naissance(this.frame, -1, -1, sexe, textFields))
                this.menuPrincipal();
        });

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(this.backButton());
        panel.add(buttonsPanel);

        this.frame.add(panel);
        this.frame.pack();
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

    private JButton backButton() {
        JButton button = new JButton("Retour");
        button.addActionListener(actionEvent -> this.menuPrincipal());
        return button;
    }
}
