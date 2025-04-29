public class GestionCitoyens {
    public static void mariage(String id1, String id2){
        Personne p1 = BaseDonnees.getPersonne(id1);
        Personne p2 = BaseDonnees.getPersonne(id2);

        if (p1 == null || p2 == null){
            System.out.println("Une des 2 personne n'existe pas !");
            return;
        }

        if (p1.getEtatCivil() == EtatCivil.MARIE || p2.getEtatCivil() == EtatCivil.MARIE ){
            System.out.println("Erreur : l'une des personnes est deja marié !");
            return;
        }
        p1.marier(p2);
        p2.marier(p1);
        System.out.println("Mariage réussi !");
    }

    public static void divorce(String id) {
        Personne p = BaseDonnees.getPersonne(id);
        if (p == null){
            System.out.println("Personne introuvable");
            return;
        }
        if (p.getEtatCivil() != EtatCivil.MARIE || p.getConjoint() == null){
            System.out.println("Erreur : La personne n'est pas mariée");
            return;
        }

        Personne conjoint = p.getConjoint();
        p.divorcer();
        conjoint.divorcer();
        System.out.println("Divorcer réussi !");
    }

    public static void afficherEtat(String id){
        Personne p = BaseDonnees.getPersonne(id);
        if (p == null){
            System.out.println("Personne introuvable");
            return;
        }
        System.out.println(p);
    }

    public static void afficher(){
        for (Personne p : BaseDonnees.listerPersonnes()){
            System.out.println(p);
            System.out.println("--------------------------------");
        }
    }

    public static void naissance(String idParent1, String idParent2){
        Personne parent1 = BaseDonnees.getPersonne(idParent1);
        Personne parent2 = BaseDonnees.getPersonne(idParent2);

        if (parent1 == null && parent2 == null){
            System.out.println("Une personne n'existe pas !");
            return;
        }

        String nom = Utilitaire.lire("Nom de l'enfant: ");
        String prenom = Utilitaire.lire("Prenom de l'enfant: ");
        String dateNaissance = Utilitaire.lire("Date de naissance (JJ/MM/AAAA): ");
        String sexe = Utilitaire.lire("Sexe (M/F): ");

        Personne p = new Personne(nom, prenom, dateNaissance, sexe);
        BaseDonnees.ajouterPersonne(p);
        System.out.println("Personne ajoutée avec l'ID: "+ p.getId());
    }


}
