package src.views;

public final class QuitView extends ParentView {
    public static final String BUTTON_TEXT = "Quitter le programme";

    public QuitView(Interface interfaceView) {
        interfaceView.provider.save();
        System.exit(0);
    }
}
