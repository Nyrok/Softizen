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

public class Interface {
    public static final Color BACKGROUND_COLOR = new Color(15, 59, 94);

    JFrame frame;
    LinkedHashMap<String, String> buttonsMap;
    Database database;
    Mairie mairie;

    public Interface(Database db, Mairie gc) {
        this.database = db;
        this.mairie = gc;
        this.frame = new JFrame();
        this.frame.setTitle("Softizen");
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.buttonsMap = new LinkedHashMap<>();
        this.buttonsMap.put("mariage", "Mariage");
        this.buttonsMap.put("divorce", "Divorce");
        this.buttonsMap.put("naissance", "Naissance");
        this.buttonsMap.put("deces", "Décès");
        this.buttonsMap.put("etatPersonne", "État d'une personne");
        this.buttonsMap.put("affichageListePersonnes", "Affichage de la liste des personnes");
        this.buttonsMap.put("saisiePersonnes", "Saisie des personnes");
        this.buttonsMap.put("quitterProgramme", "Quitter le programme");
    }

    public static void main(String[] args) {
        Database database = new Database();
        Mairie mairie = new Mairie(database);
        Interface menu = new Interface(database, mairie);
        menu.splashScreen();
    }

    public void splashScreen() {
        ImageIcon logoIcon = new ImageIcon("splash.png");
        JWindow window = new JWindow();
        JLabel logo = new JLabel("", logoIcon,
                SwingConstants.CENTER);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setAlignmentY(Component.CENTER_ALIGNMENT);
        window.getContentPane().add(logo);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setBounds((screenSize.width - logoIcon.getIconWidth()) / 2,
                (screenSize.height - logoIcon.getIconHeight()) / 2,
                logoIcon.getIconWidth(),
                logoIcon.getIconHeight());
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        window.setVisible(false);
        this.menuPrincipal(true);
    }

    public void menuPrincipal(boolean initialLaunch) {
        int width = 300, height = 350;
        if (initialLaunch) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.frame.setBounds((screenSize.width - width) / 2,
                    (screenSize.height - height) / 2, 0, 0);
        }
        this.reset(width, height);
        int buttonsMapSize = buttonsMap.size();
        JButton[] buttons = new JButton[buttonsMapSize];
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
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

    private void reset(int width, int height) {
        ImageIcon logoIcon = new ImageIcon("logo.png");
        this.frame.getContentPane().removeAll();
        this.frame.repaint();
        this.frame.revalidate();
        this.frame.setPreferredSize(new Dimension(width, height + logoIcon.getIconHeight()));
        this.frame.getContentPane().setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));
        JLabel logo = new JLabel("", logoIcon,
                SwingConstants.CENTER);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.frame.getContentPane().add(logo);
    }

    private void mariage() {
        this.reset(275, 200);
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
            comboBoxes[i] = Utilitaire.createComboBox(this.database);
            subPanel.add(comboBoxes[i]);
        }
        panel.add(subPanel);
        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(actionEvent -> {
            int idP1 = comboBoxes[0].getSelectedIndex();
            int idP2 = comboBoxes[1].getSelectedIndex();
            if (this.mairie.mariage(this.frame, idP1, idP2))
                this.menuPrincipal(false);
        });
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(this.backButton());
        panel.add(buttonsPanel);
        this.frame.add(panel);
        this.frame.pack();
    }


    private void divorce() {
        this.reset(275, 150);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        panel.setLayout(new GridLayout(3, 1));
        JLabel title = Utilitaire.createTitle("Sélectionner la personne à divorcer");
        panel.add(title);
        JComboBox<String> comboBox = Utilitaire.createComboBox(this.database);
        panel.add(comboBox);
        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(actionEvent -> {
            int id = comboBox.getSelectedIndex();
            if (this.mairie.divorce(this.frame, id))
                this.menuPrincipal(false);
        });
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(this.backButton());
        panel.add(buttonsPanel);
        this.frame.add(panel);
        this.frame.pack();
    }

    private void naissance() {
        this.reset(300, 300);
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
            parentBoxes[i] = Utilitaire.createComboBox(this.database);
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

            if (this.mairie.naissance(this.frame, idP1, idP2, sexe, textFields))
                this.menuPrincipal(false);
        });

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(this.backButton());
        panel.add(buttonsPanel);

        this.frame.add(panel);
        this.frame.pack();
    }

    private void deces() {

    }

    private void etatPersonne() {
        this.reset(275, 150);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        panel.setLayout(new GridLayout(3, 1));

        JLabel title = Utilitaire.createTitle("Sélectionner une personne");
        panel.add(title);

        JComboBox<String> comboBox = Utilitaire.createComboBox(this.database);
        panel.add(comboBox);

        JPanel buttonsPanel = new JPanel();
        JButton confirmButton = new JButton("Afficher");
        confirmButton.addActionListener(actionEvent -> {
            int id = comboBox.getSelectedIndex();
            Personne p = this.database.getPersonne(id);
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
        this.reset(700, 400);

        String[] columnNames = {"Nom", "Prénom", "Sexe", "Date de naissance", "Conjoint", "Parent 1", "Parent 2"};
        Collection<Personne> personnes = database.listerPersonnes();
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

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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
        this.reset(300, 250);
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
            if (this.mairie.naissance(this.frame, -1, -1, sexe, textFields))
                this.menuPrincipal(false);
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
        button.addActionListener(actionEvent -> this.menuPrincipal(false));
        return button;
    }
}
