public class Menu {
    public static void afficher(){
        while (true){
            System.out.println("\n--- MENU ---");
            System.out.println("1. Mariage");
            System.out.println("2. Divorce");
            System.out.println("3. Naissance");
            System.out.println("4. Etat d'une personne");
            System.out.println("5. Affichage de la liste des personnes");
            System.out.println("6. Saisie des personnes");
            System.out.println("7. Quitter");

            String choix = Utilitaire.lire("Choix: ");

            switch (choix){
                case "1":
                    String id1 = Utilitaire.lire("ID Personne 1: ");
                    String id2 = Utilitaire.lire("ID Personne 2: ");
                    GestionCitoyens.mariage(id1, id2);
                    break;
                case "2":
                    String idDiv = Utilitaire.lire("ID de la personne à divorcer: ");
                    GestionCitoyens.divorce(idDiv);
                    break;
                case "3":
                case "5":
                    GestionCitoyens.afficher();
                    break;
                case "7":
                    System.out.println("Au revoir");
                    return;
                default:
                    System.out.println("Choix invalide");

            }
        }
    }
}
