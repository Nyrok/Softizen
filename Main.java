import src.controllers.Provider;
import src.controllers.Mairie;
import src.views.Interface;

public class Main {
    public static void main(String[] args) {
        Provider provider = new Provider();
        provider.load();
        Mairie mairie = new Mairie(provider);
        Interface menu = new Interface(provider, mairie);
        menu.splashScreen();
    }
}
