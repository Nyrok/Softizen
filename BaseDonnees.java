import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseDonnees {
    public AtomicInteger lastId;
    private final Map<AtomicInteger, Personne> personnes = new LinkedHashMap<>();

    public void ajouterPersonne(Personne p) {
        AtomicInteger id = new AtomicInteger(lastId.incrementAndGet());
        personnes.put(id, p);
    }

    public Personne getPersonne(int id) {
        AtomicInteger idAtomicInteger = new AtomicInteger(id);
        return personnes.get(idAtomicInteger);
    }

    public Collection<Personne> listerPersonnes() {
        return personnes.values();
    }
}
