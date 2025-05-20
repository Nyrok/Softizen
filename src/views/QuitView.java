package src.views;

public final class QuitView extends ParentView {
    public QuitView(Interface interfaceView) {
        interfaceView.database.save();
        System.exit(0);
    }
}
