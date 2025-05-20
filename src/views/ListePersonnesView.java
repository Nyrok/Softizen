package src.views;

import src.models.Personne;
import src.utils.Utilitaire;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collection;

public final class ListePersonnesView extends ParentView {
    public static final int VIEW_WIDTH = 800;
    public static final int VIEW_HEIGHT =  400;
    public static final String BUTTON_TEXT = "Affichage de la liste des personnes";

    public ListePersonnesView(Interface interfaceView) {
        setLayout(new BorderLayout());
        String[] columnNames = {"ID", "Nom", "Prénom", "Sexe", "Date de naissance", "Etat civil", "Conjoint", "Parent 1", "Parent 2"};
        Collection<Personne> personnes = interfaceView.database.listerPersonnes();
        Object[][] data = new Object[personnes.size()][9];

        int i = 0;
        for (Personne p : personnes) {
            data[i][0] = i + 1;
            data[i][1] = p.getNomPrenom().split(" ")[0];
            data[i][2] = p.getNomPrenom().split(" ")[1];
            data[i][3] = p.getSexe();
            data[i][4] = new SimpleDateFormat("dd/MM/yyyy").format(p.getDateNaissance());
            data[i][5] = p.getEtatCivil();
            data[i][6] = p.getConjoint() != null ? p.getConjoint().getNomPrenom() : "";
            data[i][7] = p.getParents() != null && p.getParents()[0] != null ? p.getParents()[0].getNomPrenom() : "";
            data[i][8] = p.getParents() != null && p.getParents()[1] != null ? p.getParents()[1].getNomPrenom() : "";
            i++;
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(Utilitaire.backButton(interfaceView));
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
