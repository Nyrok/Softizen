package src.views;

import src.controllers.Database;
import src.controllers.Mairie;
import src.utils.Utilitaire;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.swing.*;

public class Interface {
    JFrame frame;
    LinkedHashMap<String, String> buttonsMap;
    Database database;
    Mairie mairie;

    public Interface(Database db, Mairie mairie) {
        this.database = db;
        this.mairie = mairie;
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

    public void splashScreen() {
        ImageIcon logoIcon = new ImageIcon("src/resources/splash.png");
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
        window.dispose();
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
            if (!valuesIterator.hasNext())
                buttons[i].setForeground(Color.RED);
            panel.add(buttons[i]);
            i++;
        }
        this.frame.add(panel);
        this.frame.pack();
    }

    private void reset(int width, int height) {
        ImageIcon logoIcon = new ImageIcon("src/resources/logo.png");
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
        JPanel view = new MariageView(this);
        this.frame.add(view);
        this.frame.pack();
    }

    private void divorce() {
        this.reset(275, 150);
        JPanel view = new DivorceView(this);
        this.frame.add(view);
        this.frame.pack();
    }

    private void naissance() {
        this.reset(300, 300);
        JPanel view = new NaissanceView(this);
        this.frame.add(view);
        this.frame.pack();
    }

    private void deces() {
        this.reset(275, 150);
        JPanel view = new DecesView(this);
        this.frame.add(view);
        this.frame.pack();
    }

    private void etatPersonne() {
        this.reset(275, 150);
        JPanel view = new EtatPersonneView(this);
        this.frame.add(view);
        this.frame.pack();
    }

    private void affichageListePersonnes() {
        this.reset(800, 400);
        JPanel view = new ListePersonnesView(this);
        this.frame.add(view);
        this.frame.pack();
    }

    private void saisiePersonnes() {
        this.reset(300, 250);
        JPanel view = new SaisiePersonneView(this);
        this.frame.add(view);
        this.frame.pack();
    }

    private void quitterProgramme() {
        this.database.save();
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
