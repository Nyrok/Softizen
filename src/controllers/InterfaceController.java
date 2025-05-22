package src.controllers;

import src.views.MainView;
import src.views.SplashWindow;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Vector;

public final class InterfaceController {
    public JFrame frame;
    public Vector<Class<?>> buttonsActions;
    public Mairie mairie;

    public InterfaceController(Mairie mairie) {
        this.mairie = mairie;
        this.frame = new JFrame();
        this.frame.setTitle("Softizen");
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.buttonsActions = new Vector<>();
    }

    public void addButtonAction(Class<?> action) {
        buttonsActions.add(action);
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