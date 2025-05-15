public class Main {
    public static void main(String[] args)
    {
        BaseDonnees baseDonnees = new BaseDonnees();
        GestionCitoyens gestionCitoyens = new GestionCitoyens(baseDonnees);
        Menu menu = new Menu(baseDonnees, gestionCitoyens);
        menu.menuPrincipal();
    }
}
