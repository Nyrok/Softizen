package src.views;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public final class MainView extends ParentView {
    public static final int VIEW_WIDTH = 300;
    public static final int VIEW_HEIGHT = 350;

    Interface interfaceView;

    public MainView(Interface interfaceView) {
        this.interfaceView = interfaceView;
        int buttonsSize = interfaceView.buttonsActions.size();
        JButton[] buttons = new JButton[buttonsSize];
        setLayout(new GridLayout(buttonsSize, 1));
        for (int i = 0; i < buttonsSize; i++) {
            Class<?> action = interfaceView.buttonsActions.get(i);
            String text;
            try {
                Field buttonTextField = action.getDeclaredField("BUTTON_TEXT");
                text = (String) buttonTextField.get(null);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                System.err.println(e.getMessage());
                return;
            }
            buttons[i] = new JButton(text);
            buttons[i].setSize(interfaceView.frame.getWidth(), 20);
            buttons[i].setActionCommand(action.getName());
            buttons[i].addActionListener(this.interfaceView.mairie::buttonCallback);
            if (i == buttonsSize - 1)
                buttons[i].setForeground(Color.RED);
            add(buttons[i]);
        }
    }
}
