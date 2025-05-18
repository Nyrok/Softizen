package src.views;

import src.controllers.Database;
import src.controllers.Mairie;

import java.awt.*;
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
        new SplashWindow(this);
    }

    public void menuPrincipal(boolean initialLaunch) {
        int width = 300, height = 350;
        if (initialLaunch) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.frame.setBounds((screenSize.width - width) / 2,
                    (screenSize.height - height) / 2, 0, 0);
        }
        this.reset(width, height);
        JPanel view = new MainView(this);
        this.frame.add(view);
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
}
