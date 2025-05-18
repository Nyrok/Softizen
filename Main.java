import src.controllers.Database;
import src.controllers.Mairie;
import src.views.Interface;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        database.load();
        Mairie mairie = new Mairie(database);
        Interface menu = new Interface(database, mairie);
        menu.splashScreen();
    }
}
