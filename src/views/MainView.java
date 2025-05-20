package src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            buttons[i].addActionListener(this::buttonCallback);
            if (i == buttonsSize - 1)
                buttons[i].setForeground(Color.RED);
            add(buttons[i]);
        }
    }

    private void buttonCallback(ActionEvent actionEvent) {
        String className = actionEvent.getActionCommand();
        try {
            Class<?> buttonClass = Class.forName(className);
            ParentView view = (ParentView) buttonClass.getDeclaredConstructor(Interface.class).newInstance(this.interfaceView);
            int width = buttonClass.getDeclaredField("VIEW_WIDTH").getInt(view);
            int height = buttonClass.getDeclaredField("VIEW_HEIGHT").getInt(view);
            Method method = this.interfaceView.getClass().getDeclaredMethod("view", int.class, int.class, JPanel.class);
            method.setAccessible(true);
            method.invoke(this.interfaceView, width, height, view);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException | NoSuchFieldException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
