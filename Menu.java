import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static PrintWriter logWriter;

    public static void main(String[] args) {
        try {
            // Initialize the log writer once and use it throughout the application run.
            logWriter = new PrintWriter(new FileWriter("sudoku_log1.txt", true)); // Append mode
            log("Starting the application.");

            while (true) {
                System.out.println("\n🎯 MENU PRINCIPAL 🎯");
                System.out.println("1️⃣ Résoudre une grille (Sudoku ou MultiDoku)");
                System.out.println("2️⃣ Générer une nouvelle grille à partir d'une solution complète");
                System.out.println("3️⃣ Quitter");
                System.out.print("Votre choix : ");

                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1 -> choisirTypeGrille();
                    case 2 -> genererGrilleAvecDifficulte();
                    case 3 -> {
                        log("👋 Au revoir !");
                        System.out.println("👋 Au revoir !");
                        return;
                    }
                    default -> {
                        log("❌ Choix invalide. Veuillez réessayer.");
                        System.out.println("❌ Choix invalide. Veuillez réessayer.");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("\"Erreur initialisation log writer: " + e.getMessage());
            log("Erreur initialisation log writer: " + e.getMessage());
        } finally {
            if (logWriter != null) {
                logWriter.close();
            }
        }
    }

    private static void log(String message) {
        if (logWriter != null) {
            logWriter.println(message);
            logWriter.flush();
        }
    }

    private static void choisirTypeGrille() {
        System.out.println("\nQuel type de grille souhaitez-vous résoudre ?");
        System.out.println("1️⃣ Sudoku classique");
        System.out.println("2️⃣ MultiDoku");
        System.out.print("Votre choix : ");

        int type = scanner.nextInt();
        scanner.nextLine();

        if (type == 1) {
            entrerGrilleSudoku();
        } else if (type == 2) {
            entrerGrilleMultiDoku();
        } else {
            System.out.println("❌ Choix invalide.");
        }
    }

    private static List<String> demanderSymboles(int taille) {
        System.out.println("Entrez les symboles possibles séparés par des espaces (ex : 1 2 3 4 5 6 7 8 9) :");
        String[] symbolesArray = scanner.nextLine().trim().split(" ");
        List<String> symbolesPossibles = new ArrayList<>(Arrays.asList(symbolesArray));

        if (symbolesPossibles.size() != taille) {
            System.out.println("❌ Le nombre de symboles ne correspond pas à la taille de la grille !");
            return demanderSymboles(taille);
        }
        return symbolesPossibles;
    }

    private static void entrerGrilleSudoku() {
        System.out.print("Taille de la grille (ex : 4, 9) : ");
        int taille = scanner.nextInt();
        scanner.nextLine();

        List<String> symbolesPossibles = demanderSymboles(taille);
        Grille grille = new Grille(1, taille, logWriter);
        grille.setSymbolesPossibles(symbolesPossibles);

        System.out.println("Entrez la grille (format : valeur ligne colonne bloc_id). Tapez 'fin' pour terminer :");

        while (true) {
            String ligne = scanner.nextLine();
            if (ligne.equalsIgnoreCase("fin")) break;

            String[] parts = ligne.split(" ");
            if (parts.length == 4) {
                String valeur = parts[0];  // "." ou chiffre
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                int blocId = Integer.parseInt(parts[3]);
                Bloc bloc = new Bloc(blocId, symbolesPossibles);
                grille.ajouterBloc(bloc);
                bloc.ajouterCase(new Case(1, valeur, row, col, bloc));
            } else {
                System.out.println("⚠️ Format invalide. Essayez encore.");
            }
        }

        System.out.println("\n📋 Voici la grille avant résolution :");
        grille.afficher();

        System.out.print("Souhaitez-vous activer les logs de la résolution ? (oui/non) : ");
    boolean enableLogs = scanner.nextLine().equalsIgnoreCase("oui");

    System.out.print("Souhaitez-vous continuer la résolution ? (oui/non) : ");
    if (scanner.nextLine().equalsIgnoreCase("oui")) {
        choisirAlgorithmeDeResolution(grille, enableLogs);
        System.out.println("✅ Grille résolue :");
        grille.afficher();
    } else {
        System.out.println("❌ Résolution annulée.");
    }
}


    private static void entrerGrilleMultiDoku() {
        System.out.print("Nombre de grilles MultiDoku à entrer : ");
        int nbGrilles = scanner.nextInt();
        scanner.nextLine();

        MultiDoku multiDoku = new MultiDoku();

        for (int i = 1; i <= nbGrilles; i++) {
            System.out.print("Taille de la grille " + i + " : ");
            int taille = scanner.nextInt();
            scanner.nextLine();

            List<String> symbolesPossibles = demanderSymboles(taille);
            Grille grille = new Grille(i, taille);
            grille.setSymbolesPossibles(symbolesPossibles);

            multiDoku.ajouterGrille(grille);
        }

        System.out.println("\n📋 Voici les grilles MultiDoku avant résolution :");
        multiDoku.afficher();
    }

    private static void genererGrilleAvecDifficulte() {
        System.out.print("Taille de la grille (ex : 4, 9) : ");
        int taille = scanner.nextInt();
        scanner.nextLine();
    
        List<String> symbolesPossibles = demanderSymboles(taille);
        Grille grille = new Grille(1, taille);
        grille.setSymbolesPossibles(symbolesPossibles);
    
        System.out.println("Entrez la grille complète (format : valeur ligne colonne bloc_id). Tapez 'fin' pour terminer :");
        while (true) {
            String ligne = scanner.nextLine();
            if (ligne.equalsIgnoreCase("fin")) break;
    
            String[] parts = ligne.split(" ");
            if (parts.length == 4) {
                String valeur = parts[0];
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                int blocId = Integer.parseInt(parts[3]);
    
                if (!symbolesPossibles.contains(valeur) && !valeur.equals(".")) {
                    System.out.println("❌ Valeur invalide : " + valeur);
                    continue;
                }
    
                Bloc bloc = new Bloc(blocId, symbolesPossibles);
                grille.ajouterBloc(bloc);
                bloc.ajouterCase(new Case(1, valeur, row, col, bloc));
            } else {
                System.out.println("⚠️ Format invalide. Essayez encore.");
            }
        }
    
        System.out.println("Choisissez le niveau de difficulté :");
        System.out.println("1️⃣ Facile");
        System.out.println("2️⃣ Moyen");
        System.out.println("3️⃣ Difficile");
        int niveau = scanner.nextInt();
        scanner.nextLine();
    
        int casesASupprimer = calculateCellsToRemove(taille, niveau);
    
        System.out.println("🔄 Suppression de " + casesASupprimer + " cases pour générer une grille jouable...");
    
        SudokuGenerator generator = new SudokuGenerator(grille);
        generator.genererGrilleJouable(casesASupprimer);
    
        System.out.println("\n🎯 Grille générée avec difficulté :");
        grille.afficher();
    }
    
    private static int calculateCellsToRemove(int taille, int niveau) {
        int baseCells;
        switch (niveau) {
            case 1: // Facile
                baseCells = 5 + (taille - 9); // Supprime moins de cases pour les niveaux faciles
                break;
            case 2: // Moyen
                baseCells = 10 + (taille - 9) * 2; // La difficulté moyenne supprime un nombre modéré de cases
                break;
            case 3: // Difficile
                baseCells = 15 + (taille - 9) * 3; // La difficulté élevée vise à être plus exigeante
                break;
            default:
                baseCells = 10; // Cas par défaut
                break;
        }
        // Assure que le nombre de cases supprimées ne dépasse pas le minimum logique requis pour résoudre le puzzle
        return Math.min(baseCells, (taille * taille) - (taille * 2));
    }        
    
    

    /**
 * Permet à l'utilisateur de choisir un algorithme de résolution pour le Sudoku.
 */
private static void choisirAlgorithmeDeResolution(Grille grille, boolean enableLogs) {
    System.out.println("\n📌 Choisissez l'algorithme de résolution :");
    System.out.println("1️⃣ Résolution par lignes");
    System.out.println("2️⃣ Résolution par colonnes");
    System.out.println("3️⃣ Résolution par blocs");
    System.out.println("4️⃣ Résolution par backtracking");
    System.out.print("Votre choix : ");

    int algoChoix = scanner.nextInt();
    scanner.nextLine();

    switch (algoChoix) {
        case 1 -> {
            grille.resoudreParLignes();
            System.out.println("✅ Résolution par lignes effectuée !");
        }
        case 2 -> {
            grille.resoudreParColonnes();
            System.out.println("✅ Résolution par colonnes effectuée !");
        }
        case 3 -> {
            grille.resoudreParBlocs();
            System.out.println("✅ Résolution par blocs effectuée !");
        }
        case 4 -> {
            grille.resoudreParBacktracking(enableLogs);
            System.out.println("✅ Résolution par Backtracking effectuée !");
            }
        default -> System.out.println("❌ Choix invalide. Annulation de la résolution.");
    }
}
}

