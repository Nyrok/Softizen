package src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public final class MainView extends JPanel {
    Interface interfaceView;

    public MainView(Interface interfaceView) {
        this.interfaceView = interfaceView;
        int buttonsMapSize = interfaceView.buttonsMap.size();
        JButton[] buttons = new JButton[buttonsMapSize];
        setLayout(new GridLayout(buttonsMapSize, 1));
        Iterator<String> keysIterator = interfaceView.buttonsMap.keySet().iterator();
        Iterator<String> valuesIterator = interfaceView.buttonsMap.values().iterator();
        int i = 0;
        String key, value;
        while (valuesIterator.hasNext()) {
            key = keysIterator.next();
            value = valuesIterator.next();
            buttons[i] = new JButton(value);
            buttons[i].setSize(interfaceView.frame.getWidth(), 20);
            buttons[i].setActionCommand(key);
            buttons[i].addActionListener(this::buttonCallback);
            if (!valuesIterator.hasNext())
                buttons[i].setForeground(Color.RED);
            add(buttons[i]);
            i++;
        }
    }

    private void buttonCallback(ActionEvent actionEvent) {
        String methodName = actionEvent.getActionCommand();
        try {
            Method method = this.interfaceView.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(this.interfaceView);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
