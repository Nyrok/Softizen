package src.controllers;

import src.enums.EtatCivil;
import src.enums.Sexe;
import src.models.Personne;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;

public class Database {
    private final String DATABASE_FILE = "db.csv";
    public AtomicInteger lastId;
    private final Vector<Personne> personnes = new Vector<>();

    public Database() {
        this.lastId = new AtomicInteger(0);
    }

    public void ajouterPersonne(Personne p) {
        int id = lastId.incrementAndGet();
        p.setId(id);
        personnes.add(p);
    }

    public Personne getPersonne(int id) {
        return personnes.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Collection<Personne> listerPersonnes() {
        return personnes;
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(DATABASE_FILE, StandardCharsets.UTF_8)) {
            writer.println("Nom,Prenom,DateNaissance,Sexe,EtatCivil,IDConjoint");
            for (Personne p : personnes) {
                writer.println(p.getNom() + "," +
                        p.getPrenom() + "," +
                        new SimpleDateFormat("dd/MM/yyyy").format(p.getDateNaissance()) + "," +
                        p.getSexe() + "," +
                        p.getEtatCivil() + "," +
                        (p.getEtatCivil() == EtatCivil.MARIE ? p.getConjoint().getId() : "-1")
                );
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du fichier : " + e.getMessage());
        }
    }

    public void load() {
        Map<Integer, Integer> mariages = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILE, StandardCharsets.UTF_8))) {
            String line;
            reader.readLine();
            personnes.removeAllElements();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Personne p = new Personne(
                            null,
                            parts[0],
                            parts[1],
                            dateFormat.parse(parts[2]),
                            Sexe.valueOf(parts[3])
                    );
                    p.setEtatCivil(EtatCivil.valueOf(parts[4]));
                    this.ajouterPersonne(p);
                    int conjointId = Integer.parseInt(parts[5]);
                    if (conjointId != -1) {
                        mariages.put(p.getId(), Integer.parseInt(parts[5]));
                    }
                }
            }
            for (Map.Entry<Integer, Integer> m : mariages.entrySet()) {
                Personne p1 = this.getPersonne(m.getKey());
                Personne p2 = this.getPersonne(m.getValue());
                if (p1 != null && p2 != null) {
                    p1.setConjoint(p2);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
        }
    }
}