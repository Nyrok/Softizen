package src.views;

import src.controllers.InterfaceController;

public final class QuitView extends ParentView {
    public static final String BUTTON_TEXT = "Sauvegarder et quitter";

    public QuitView(InterfaceController interfaceController) {
        interfaceController.mairie.provider.save();
        System.exit(0);
    }
}
