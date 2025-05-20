package src.views;

import src.controllers.Database;
import src.controllers.Mairie;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import java.util.Vector;

public class Interface {
    JFrame frame;
    Vector<String> buttonsTexts;
    Vector<Class<?>> buttonsActions;

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

        this.buttonsTexts = new Vector<>();
        this.buttonsActions = new Vector<>();

        buttonsTexts.add("Mariage");
        buttonsActions.add(MariageView.class);

        buttonsTexts.add("Divorce");
        buttonsActions.add(DivorceView.class);

        buttonsTexts.add("Naissance");
        buttonsActions.add(NaissanceView.class);

        buttonsTexts.add("Décès");
        buttonsActions.add(DecesView.class);

        buttonsTexts.add("État d'une personne");
        buttonsActions.add(EtatPersonneView.class);

        buttonsTexts.add("Affichage de la liste des personnes");
        buttonsActions.add(ListePersonnesView.class);

        buttonsTexts.add("Saisie des personnes");
        buttonsActions.add(SaisiePersonneView.class);

        buttonsTexts.add("Quitter le programme");
        buttonsActions.add(QuitView.class);
    }

    public void splashScreen() {
        new SplashWindow(this);
    }

    public void menuPrincipal(boolean initialLaunch) {
        MainView view = new MainView(this);
        if (initialLaunch) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.frame.setBounds((screenSize.width - view.VIEW_WIDTH) / 2,
                    (screenSize.height - view.VIEW_HEIGHT) / 2, 0, 0);
        }
        this.view(view.VIEW_WIDTH, view.VIEW_HEIGHT, view);
    }

    private void reset(int width, int height) {
        URL logoURL = this.getClass().getResource("/logo.png");
        if (logoURL == null)
            throw new IllegalArgumentException("Logo file is missing.");
        ImageIcon logoIcon = new ImageIcon(logoURL);
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

    private void view(int width, int height, JPanel view) {
        this.reset(width, height);
        this.frame.add(view);
        this.frame.pack();
    }
}