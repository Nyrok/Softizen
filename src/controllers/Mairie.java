package src.controllers;

import src.views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Mairie {
    private InterfaceController interfaceController;
    private final Provider provider;
    private final MariageController mariageController;
    private final DivorceController divorceController;
    private final DecesController decesController;
    private final NaissanceController naissanceController;

    public Mairie() {
        this.provider = new Provider();
        this.provider.load();
        this.mariageController = new MariageController(this);
        this.divorceController = new DivorceController(this);
        this.decesController = new DecesController(this);
        this.naissanceController = new NaissanceController(this);
        this.interfaceController = null;
    }

    public MariageController getMariageController() {
        return mariageController;
    }

    public DivorceController getDivorceController() {
        return divorceController;
    }

    public DecesController getDecesController() {
        return decesController;
    }

    public NaissanceController getNaissanceController() {
        return naissanceController;
    }

    public InterfaceController getInterfaceController() {
        return interfaceController;
    }

    public Provider getProvider() {
        return provider;
    }

    public void launchInterface() {
        InterfaceController interfaceController = new InterfaceController(this);
        setInterfaceController(interfaceController);
        interfaceController.splashScreen();
    }

    public void setInterfaceController(InterfaceController interfaceController) {
        this.interfaceController = interfaceController;
        this.interfaceController.addButtonAction(MariageView.class);
        this.interfaceController.addButtonAction(DivorceView.class);
        this.interfaceController.addButtonAction(NaissanceView.class);
        this.interfaceController.addButtonAction(DecesView.class);
        this.interfaceController.addButtonAction(EtatPersonneView.class);
        this.interfaceController.addButtonAction(ListePersonnesView.class);
        this.interfaceController.addButtonAction(SaisiePersonneView.class);
        this.interfaceController.addButtonAction(QuitView.class);
    }

    public void setButtonCallback(JButton button) {
        button.addActionListener(this::buttonCallback);
    }

    private void buttonCallback(ActionEvent actionEvent) {
        String className = actionEvent.getActionCommand();
        try {
            Class<?> buttonClass = Class.forName(className);
            ParentView view = (ParentView) buttonClass
                    .getDeclaredConstructor(InterfaceController.class)
                    .newInstance(this.interfaceController);
            int width = buttonClass.getDeclaredField("VIEW_WIDTH").getInt(view);
            int height = buttonClass.getDeclaredField("VIEW_HEIGHT").getInt(view);
            Method method = this.interfaceController.getClass()
                    .getDeclaredMethod("view", int.class, int.class, JPanel.class);
            method.invoke(this.interfaceController, width, height, view);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException | NoSuchFieldException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
