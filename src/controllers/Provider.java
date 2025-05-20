package src.controllers;

import src.enums.EtatCivil;
import src.enums.Sexe;
import src.models.Personne;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;

public class Provider {
    private final String FILE = "data.csv";
    public AtomicInteger lastId;
    private final Vector<Personne> personnes = new Vector<>();

    public Provider() {
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
        try (PrintWriter writer = new PrintWriter(FILE, StandardCharsets.UTF_8)) {
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
        Vector<Integer> mariagesP1 = new Vector<>();
        Vector<Integer> mariagesP2 = new Vector<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE, StandardCharsets.UTF_8))) {
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
                        mariagesP1.add(p.getId());
                        mariagesP2.add(Integer.parseInt(parts[5]));
                    }
                }
            }
            for (int i = 0; i < mariagesP1.size(); i++) {
                Personne p1 = this.getPersonne(mariagesP1.get(i));
                Personne p2 = this.getPersonne(mariagesP2.get(i));
                if (p1 != null && p2 != null) {
                    p1.setConjoint(p2);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
        }
    }
}