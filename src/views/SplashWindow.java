package src.views;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public final class SplashWindow extends JWindow {
    public SplashWindow(Interface interfaceView) {
        URL iconURL = this.getClass().getResource("/splash.png");
        if (iconURL == null)
            throw new IllegalArgumentException("Splash file is missing.");
        ImageIcon logoIcon = new ImageIcon(iconURL);
        JLabel logo = new JLabel("", logoIcon,
                SwingConstants.CENTER);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setAlignmentY(Component.CENTER_ALIGNMENT);
        getContentPane().add(logo);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - logoIcon.getIconWidth()) / 2,
                (screenSize.height - logoIcon.getIconHeight()) / 2,
                logoIcon.getIconWidth(),
                logoIcon.getIconHeight());
        setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        setVisible(false);
        dispose();
        interfaceView.menuPrincipal(true);
    }
}
