import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Main {
    private static final String[] couleurs = {
        "\u001B[31m", // Rouge
        "\u001B[32m", // Vert
        "\u001B[33m", // Jaune
        "\u001B[34m", // Bleu
        "\u001B[35m", // Magenta
        "\u001B[36m", // Cyan
    };

    public static void main(String[] args) {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter("sudoku_solution.txt"))) {
            Grille sudoku = new Grille(1, 9, logWriter);
            List<String> symbolesPossibles = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");

            // Création des blocs (3x3)
            int blocId = 0;
            for (int blocRow = 0; blocRow < 3; blocRow++) {
                for (int blocCol = 0; blocCol < 3; blocCol++) {
                    Bloc bloc = new Bloc(blocId++,symbolesPossibles);
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            int row = blocRow * 3 + i;
                            int col = blocCol * 3 + j;
                            bloc.ajouterCase(new Case(1, ".", row, col, bloc)); // Initialisation vide
                        }
                    }
                    sudoku.ajouterBloc(bloc);
                }
            }

            // Ajout des valeurs initiales (selon l'image fournie)
            setCaseValue(sudoku, 0, 1, "6");
            setCaseValue(sudoku, 0, 7, "2");
            setCaseValue(sudoku, 1, 0, "7");
            setCaseValue(sudoku, 1, 4, "4");
            setCaseValue(sudoku, 1, 8, "8");
            setCaseValue(sudoku, 2, 2, "4");
            setCaseValue(sudoku, 2, 4, "7");
            setCaseValue(sudoku, 2, 7, "6");
            setCaseValue(sudoku, 3, 5, "1");
            setCaseValue(sudoku, 4, 4, "9");
            setCaseValue(sudoku, 4, 8, "7");
            setCaseValue(sudoku, 5, 2, "2");
            setCaseValue(sudoku, 5, 3, "7");
            setCaseValue(sudoku, 5, 4, "3");
            setCaseValue(sudoku, 5, 7, "9");
            setCaseValue(sudoku, 6, 0, "2");
            setCaseValue(sudoku, 6, 2, "9");
            setCaseValue(sudoku, 6, 7, "4");
            setCaseValue(sudoku, 7, 1, "4");
            setCaseValue(sudoku, 7, 2, "7");
            setCaseValue(sudoku, 8, 1, "1");
            setCaseValue(sudoku, 8, 4, "2");

            Grille sudoku2 = new Grille(2, 9, logWriter);
            // Création des blocs (3x3)
            for (int blocRow = 0; blocRow < 3; blocRow++) {
                for (int blocCol = 0; blocCol < 3; blocCol++) {
                    Bloc bloc = new Bloc(blocId++,symbolesPossibles);
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            int row = blocRow * 3 + i;
                            int col = blocCol * 3 + j;
                            bloc.ajouterCase(new Case(1, ".", row, col, bloc)); // Initialisation vide
                        }
                    }
                    sudoku2.ajouterBloc(bloc);
                }
            }

            // Ajout des valeurs initiales (selon l'image fournie)
            setCaseValue(sudoku2, 0, 1, "4");
            setCaseValue(sudoku2, 0, 6, "7");
            setCaseValue(sudoku2, 1, 3, "6");
            setCaseValue(sudoku2, 1, 4, "4");
            setCaseValue(sudoku2, 1, 7, "5");
            setCaseValue(sudoku2, 3, 1, "6");
            setCaseValue(sudoku2, 3, 5, "2");
            setCaseValue(sudoku2, 3, 6, "9");
            setCaseValue(sudoku2, 5, 0, "1");
            setCaseValue(sudoku2, 5, 3, "3");
            setCaseValue(sudoku2, 5, 5, "5");
            setCaseValue(sudoku2, 5, 6, "8");
            setCaseValue(sudoku2, 5, 8, "2");
            setCaseValue(sudoku2, 6, 2, "5");
            setCaseValue(sudoku2, 6, 5, "9");
            setCaseValue(sudoku2, 6, 7, "3");
            setCaseValue(sudoku2, 6, 8, "4");
            setCaseValue(sudoku2, 7, 4, "1");
            setCaseValue(sudoku2, 8, 0, "3");
            setCaseValue(sudoku2, 8, 8, "8");

            setCaseValue(sudoku, 6, 6, "."); 
            setCaseValue(sudoku2, 0, 0, "."); 
            setCaseValue(sudoku, 6, 8, "."); 
            setCaseValue(sudoku2, 0, 2, "."); 

            MultiDoku multidoku = new MultiDoku();
            multidoku.ajouterGrille(sudoku);
            multidoku.ajouterGrille(sudoku2);
            multidoku.ajouterCasesPartagees(sudoku.getCase(6, 6), sudoku2.getCase(0, 0));
            multidoku.ajouterCasesPartagees(sudoku.getCase(6, 7), sudoku2.getCase(0, 1)); 
            multidoku.ajouterCasesPartagees(sudoku.getCase(6, 8), sudoku2.getCase(0, 2));
            multidoku.ajouterCasesPartagees(sudoku.getCase(7, 6), sudoku2.getCase(1, 0));
            multidoku.ajouterCasesPartagees(sudoku.getCase(7, 7), sudoku2.getCase(1, 1)); 
            multidoku.ajouterCasesPartagees(sudoku.getCase(7, 8), sudoku2.getCase(1, 2)); 
            multidoku.ajouterCasesPartagees(sudoku.getCase(8, 6), sudoku2.getCase(2, 0));
            multidoku.ajouterCasesPartagees(sudoku.getCase(8, 7), sudoku2.getCase(2, 1)); 
            multidoku.ajouterCasesPartagees(sudoku.getCase(8, 8), sudoku2.getCase(2, 2));  
            multidoku.afficher();


            System.out.println("🔍 Vérification des cases partagées :");
            System.out.println("Grille 1 (6,6) : " + (sudoku.getCase(6, 6).estVide() ? "Vide" : sudoku.getCase(6, 6).getValeur()));
            System.out.println("Grille 2 (0,0) : " + (sudoku2.getCase(0, 0).estVide() ? "Vide" : sudoku2.getCase(0, 0).getValeur()));

            System.out.println("Grille 1 (6,8) : " + (sudoku.getCase(6, 8).estVide() ? "Vide" : sudoku.getCase(6, 8).getValeur()));
            System.out.println("Grille 2 (0,2) : " + (sudoku2.getCase(0, 2).estVide() ? "Vide" : sudoku2.getCase(0, 2).getValeur()));

            System.out.println("\nRésolution du Multidoku :");
            if (multidoku.resoudre(symbolesPossibles)) {
                System.out.println("Résolution réussie !");
            } else {
                System.out.println("Impossible de résoudre le Multidoku.");
            }

            multidoku.afficher();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setCaseValue(Grille grille, int ligne, int colonne, String valeur) {
        Case c = grille.getCase(ligne, colonne);
        if (c != null) {
            c.setValeur(valeur);
        }
    }

    private static void afficherGrille(Grille grille) {
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                Case c = grille.getCase(i, j);
                int blocId = c.getBloc().getId();
                String value = c.estVide() ? "." : c.getValeur();
                System.out.print(couleurs[blocId % couleurs.length] + value + " \u001B[0m ");
            }
            System.out.println();
        }
    }
}
