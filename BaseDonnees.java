import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseDonnees {
    public AtomicInteger lastId;
    private final Map<Integer, Personne> personnes = new LinkedHashMap<>();

    public BaseDonnees() {
        this.lastId = new AtomicInteger(0);
    }

    public void ajouterPersonne(Personne p) {
        int id = lastId.incrementAndGet();
        personnes.put(id, p);
    }

    public Personne getPersonne(int id) {
        return personnes.get(id);
    }

    public Collection<Personne> listerPersonnes() {
        return personnes.values();
    }
}