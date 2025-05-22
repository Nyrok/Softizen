import src.controllers.Provider;
import src.controllers.Mairie;

public class Main {
    public static void main(String[] args) {
        Provider provider = new Provider();
        provider.load();
        Mairie mairie = new Mairie(provider);
        mairie.launchInterface();
    }
}
