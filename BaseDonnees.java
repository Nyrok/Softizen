import java.util.*;

public class BaseDonnees {
    private static Map<String, Personne> personnes = new HashMap<>();

    public static void ajouterPersonne(Personne p) {
        personnes.put(p.getId(), p);
    }

    public static Personne getPersonne(String id) {
        return personnes.get(id);
    }
    public static Collection<Personne> listerPersonnes()
    {
        return personnes.values();
    }
}
