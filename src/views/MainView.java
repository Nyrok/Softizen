package src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class MainView extends ParentView {
    public final int VIEW_WIDTH = 300;
    public final int VIEW_HEIGHT =  350;

    Interface interfaceView;

    public MainView(Interface interfaceView) {
        this.interfaceView = interfaceView;
        int buttonsSize = interfaceView.buttonsActions.size();
        JButton[] buttons = new JButton[buttonsSize];
        setLayout(new GridLayout(buttonsSize, 1));
        for (int i = 0; i < buttonsSize; i++) {
            Class<?> action = interfaceView.buttonsActions.get(i);
            String text = interfaceView.buttonsTexts.get(i);
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
            Field widthField = buttonClass.getDeclaredField("VIEW_WIDTH");
            Field heightField = buttonClass.getDeclaredField("VIEW_HEIGHT");
            Method method = this.interfaceView.getClass().getDeclaredMethod("view", int.class, int.class, JPanel.class);
            method.setAccessible(true);
            method.invoke(this.interfaceView, widthField.getInt(view), heightField.getInt(view), view);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException | NoSuchFieldException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
