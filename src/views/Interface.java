package src.views;

import src.controllers.Provider;
import src.controllers.Mairie;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import java.util.Vector;

public class Interface {
    JFrame frame;
    Vector<Class<?>> buttonsActions;

    Provider provider;
    Mairie mairie;

    public Interface(Provider provider, Mairie mairie) {
        this.provider = provider;
        this.mairie = mairie;
        this.frame = new JFrame();
        this.frame.setTitle("Softizen");
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.buttonsActions = new Vector<>();
        buttonsActions.add(MariageView.class);
        buttonsActions.add(DivorceView.class);
        buttonsActions.add(NaissanceView.class);
        buttonsActions.add(DecesView.class);
        buttonsActions.add(EtatPersonneView.class);
        buttonsActions.add(ListePersonnesView.class);
        buttonsActions.add(SaisiePersonneView.class);
        buttonsActions.add(QuitView.class);
    }

    public void splashScreen() {
        new SplashWindow(this);
    }

    public void menuPrincipal(boolean initialLaunch) {
        MainView view = new MainView(this);
        if (initialLaunch) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.frame.setBounds((screenSize.width - MainView.VIEW_WIDTH) / 2,
                    (screenSize.height - MainView.VIEW_HEIGHT) / 2, 0, 0);
        }
        this.view(MainView.VIEW_WIDTH, MainView.VIEW_HEIGHT, view);
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

    public void view(int width, int height, JPanel view) {
        this.reset(width, height);
        this.frame.add(view);
        this.frame.pack();
    }
}