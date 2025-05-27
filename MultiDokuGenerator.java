import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiDokuGenerator {

    // Génère un MultiDoku à partir de grilles complètes existantes
    public MultiDoku genererMultiDoku(List<Grille> grillesCompletes, int zonesPartagees) {
        MultiDoku multidoku = new MultiDoku();
        List<Grille> grilles = new ArrayList<>(grillesCompletes);

        // Ajouter les grilles complètes au MultiDoku
        for (Grille grille : grilles) {
            multidoku.ajouterGrille(grille);
        }

        Random rand = new Random();

        // Définir des zones partagées sur les bords extérieurs
        for (int z = 0; z < zonesPartagees; z++) {
            int indexGrille1 = rand.nextInt(grilles.size());
            int indexGrille2 = rand.nextInt(grilles.size());

            while (indexGrille1 == indexGrille2) {
                indexGrille2 = rand.nextInt(grilles.size());
            }

            int choixZone = rand.nextInt(3); // 0: ligne, 1: colonne, 2: bloc extérieur
            int taille = grilles.get(0).getTaille();

            switch (choixZone) {
                case 0: // Partage d'une ligne extérieure
                    int ligne = rand.nextBoolean() ? 0 : taille - 1; // Haut ou bas
                    for (int c = 0; c < taille; c++) {
                        ajouterCasesPartageesSécurisées(multidoku, grilles, indexGrille1, indexGrille2, ligne, c);
                    }
                    break;

                case 1: // Partage d'une colonne extérieure
                    int colonne = rand.nextBoolean() ? 0 : taille - 1; // Gauche ou droite
                    for (int r = 0; r < taille; r++) {
                        ajouterCasesPartageesSécurisées(multidoku, grilles, indexGrille1, indexGrille2, r, colonne);
                    }
                    break;

                case 2: // Partage d'un bloc extérieur (coin)
                    int blocTaille = (int) Math.sqrt(taille);
                    int blocRow = rand.nextBoolean() ? 0 : (taille - blocTaille);
                    int blocCol = rand.nextBoolean() ? 0 : (taille - blocTaille);

                    for (int i = 0; i < blocTaille; i++) {
                        for (int j = 0; j < blocTaille; j++) {
                            if (blocRow + i < taille && blocCol + j < taille) {
                                ajouterCasesPartageesSécurisées(multidoku, grilles, indexGrille1, indexGrille2, blocRow + i, blocCol + j);
                            }
                        }
                    }
                    break;
            }
        }

        return multidoku;
    }

    // Assure un partage sécurisé des cases entre deux grilles
    private void ajouterCasesPartageesSécurisées(MultiDoku multidoku, List<Grille> grilles, int indexGrille1, int indexGrille2, int ligne, int colonne) {
        try {
            Case case1 = grilles.get(indexGrille1).getCase(ligne, colonne);
            Case case2 = grilles.get(indexGrille2).getCase(ligne, colonne);

            if (case1 != null && case2 != null) {
                multidoku.ajouterCasesPartagees(case1, case2);
            } else {
                System.err.println("❗ Case non trouvée à la position (" + ligne + ", " + colonne + ")");
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("🚨 Indices hors limites pour la grille : (" + ligne + ", " + colonne + ")");
        } catch (NullPointerException e) {
            System.err.println("⚠️ Problème d'initialisation des cases à (" + ligne + ", " + colonne + ")");
        }
    }
}
