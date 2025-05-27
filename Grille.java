import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Représente une grille de Sudoku ou MultiDoku.
 * Gère les blocs, les cases, et propose des méthodes de validation.
 */
public class Grille {
    private int idSudoku;
    private int taille;
    private List<Bloc> blocs;
    private PrintWriter logWriter;
    private List<String> symbolesPossibles;

    /**
     * Constructeur de la grille avec logger.
     *
     * @param idSudoku Identifiant de la grille.
     * @param taille   Taille de la grille (N x N).
     * @param logWriter Logger pour le suivi des opérations.
     */
    public Grille(int idSudoku, int taille, PrintWriter logWriter) {
        this.idSudoku = idSudoku;
        this.taille = taille;
        this.blocs = new ArrayList<>();
        this.logWriter = logWriter;
        this.symbolesPossibles = new ArrayList<>();
    }

    /**
     * Constructeur de la grille avec des symboles définis.
     *
     * @param idSudoku Identifiant de la grille.
     * @param taille   Taille de la grille.
     * @param logWriter Logger pour les opérations.
     * @param symboles  Liste des symboles autorisés.
     */
    public Grille(int idSudoku, int taille, PrintWriter logWriter, List<String> symboles) {
        this(idSudoku, taille, logWriter);
        this.symbolesPossibles = new ArrayList<>(symboles);
    }

    /**
     * Constructeur sans logger.
     *
     * @param idSudoku Identifiant de la grille.
     * @param taille   Taille de la grille.
     */
    public Grille(int idSudoku, int taille) {
        this(idSudoku, taille, null);
    }

    /**
     * Récupère la liste des symboles possibles.
     *
     * @return Liste des symboles autorisés.
     */
    public List<String> getSymbolesPossibles() {
        return symbolesPossibles;
    }

    /**
     * Définit les symboles possibles pour la grille.
     *
     * @param symboles Liste des symboles.
     */
    public void setSymbolesPossibles(List<String> symboles) {
        this.symbolesPossibles = new ArrayList<>(symboles);
    }

    /**
     * Ajoute un symbole à la liste des symboles possibles s'il n'est pas déjà présent.
     *
     * @param symbole Le symbole à ajouter.
     * @return true si ajouté, false sinon.
     */
    public boolean ajouterSymbolePossible(String symbole) {
        if (!symbolesPossibles.contains(symbole)) {
            symbolesPossibles.add(symbole);
            return true;
        }
        return false;
    }

    /**
     * Ajoute un bloc à la grille.
     *
     * @param bloc Le bloc à ajouter.
     */
    public void ajouterBloc(Bloc bloc) {
        blocs.add(bloc);
    }

    /**
     * Récupère une case à partir de ses coordonnées.
     *
     * @param ligne   Ligne de la case.
     * @param colonne Colonne de la case.
     * @return La case correspondante ou null si non trouvée.
     */
    public Case getCase(int ligne, int colonne) {
        for (Bloc bloc : blocs) {
            for (Case c : bloc.getCases()) {
                if (c.getLigne() == ligne && c.getColonne() == colonne) {
                    return c;
                }
            }
        }
        System.err.println("⚠️ Case non trouvée à la position (" + ligne + ", " + colonne + ")");
        return null;
    }

    /**
     * Vérifie si la grille est valide.
     *
     * @return true si la grille est valide, sinon false.
     */
    public boolean estValide() {
        for (int i = 0; i < taille; i++) {
            if (!estLigneValide(i) || !estColonneValide(i)) return false;
        }
        for (Bloc bloc : blocs) {
            if (!bloc.estValide()) return false;
        }
        return true;
    }

    /**
     * Vérifie si une ligne est valide.
     *
     * @param ligne Numéro de la ligne à vérifier.
     * @return true si la ligne est valide, sinon false.
     */
    public boolean estLigneValide(int ligne) {
        Set<String> valeurs = new HashSet<>();
        for (int j = 0; j < taille; j++) {
            Case c = getCase(ligne, j);
            if (c != null && !c.estVide() && !valeurs.add(c.getValeur())) {
                return false;
            }
        }
        return true;
    }

        /**
     * Vérifie si une colonne est valide.
     *
     * @param colonne Numéro de la colonne à vérifier.
     * @return true si la colonne est valide (sans doublons), sinon false.
     */
    public boolean estColonneValide(int colonne) {
        Set<String> valeurs = new HashSet<>();
        for (int i = 0; i < taille; i++) {
            Case c = getCase(i, colonne);
            if (c != null && !c.estVide() && !valeurs.add(c.getValeur())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Résout la grille en complétant les valeurs manquantes par ligne.
     * Remplit les cases vides avec la valeur manquante lorsqu'une seule possibilité reste.
     */
    public void resoudreParLignes() {
        for (int i = 0; i < taille; i++) {
            Set<String> valeursPossibles = new HashSet<>();
            for (int j = 1; j <= taille; j++) valeursPossibles.add(String.valueOf(j));

            for (int j = 0; j < taille; j++) {
                Case c = getCase(i, j);
                if (!c.estVide()) {
                    valeursPossibles.remove(c.getValeur());
                }
            }

            for (int j = 0; j < taille; j++) {
                Case c = getCase(i, j);
                if (c.estVide() && !valeursPossibles.isEmpty()) {
                    c.setValeur(valeursPossibles.iterator().next());
                    break;
                }
            }
        }
    }

    /**
     * Résout la grille en complétant les valeurs manquantes par colonne.
     * Remplit les cases vides avec la valeur manquante lorsqu'une seule possibilité reste.
     */
    public void resoudreParColonnes() {
        for (int j = 0; j < taille; j++) {
            Set<String> valeursPossibles = new HashSet<>();
            for (int i = 1; i <= taille; i++) valeursPossibles.add(String.valueOf(i));

            for (int i = 0; i < taille; i++) {
                Case c = getCase(i, j);
                if (!c.estVide()) {
                    valeursPossibles.remove(c.getValeur());
                }
            }

            for (int i = 0; i < taille; i++) {
                Case c = getCase(i, j);
                if (c.estVide() && !valeursPossibles.isEmpty()) {
                    c.setValeur(valeursPossibles.iterator().next());
                    break;
                }
            }
        }
    }

    /**
     * Résout la grille en complétant les valeurs manquantes par bloc.
     * Remplit les cases vides avec la valeur manquante lorsqu'une seule possibilité reste.
     */
    public void resoudreParBlocs() {
        for (Bloc bloc : blocs) {
            Set<String> valeursPossibles = new HashSet<>();
            for (int i = 1; i <= taille; i++) valeursPossibles.add(String.valueOf(i));

            for (Case c : bloc.getCases()) {
                if (!c.estVide()) {
                    valeursPossibles.remove(c.getValeur());
                }
            }

            for (Case c : bloc.getCases()) {
                if (c.estVide() && !valeursPossibles.isEmpty()) {
                    c.setValeur(valeursPossibles.iterator().next());
                    break;
                }
            }
        }
    }

    /**
     * Résout la grille en utilisant l'algorithme de backtracking (retour arrière).
     *
     * @return true si la grille est résolue avec succès, sinon false.
     */
    public boolean resoudreParBacktracking() {
        return resoudreParBacktracking(false);
    }

    /**
     * Résout la grille en utilisant l'algorithme de backtracking (retour arrière) avec option de journalisation.
     *
     * @param enableLogs Active la journalisation des étapes si true.
     * @return true si la grille est résolue avec succès, sinon false.
     */
    public boolean resoudreParBacktracking(boolean enableLogs) {
        return backtrack(0, 0, enableLogs);
    }

    /**
     * Algorithme de backtracking pour remplir les cases vides de manière récursive.
     *
     * @param ligne      Indice de la ligne courante.
     * @param colonne    Indice de la colonne courante.
     * @param enableLogs Active la journalisation des tentatives et retours arrière si true.
     * @return true si la grille est résolue, sinon false.
     */
    private boolean backtrack(int ligne, int colonne, boolean enableLogs) {
        if (ligne == taille) {
            if (enableLogs && logWriter != null) {
                logWriter.println("Grille résolue avec succès !");
                logWriter.flush();
            }
            return true;
        }

        int nextCol = (colonne + 1) % taille;
        int nextRow = ligne + (nextCol == 0 ? 1 : 0);

        Case current = getCase(ligne, colonne);
        if (current == null) {
            return backtrack(nextRow, nextCol, enableLogs);
        }

        if (!current.estVide()) {
            return backtrack(nextRow, nextCol, enableLogs);
        }

        for (String valeur : current.getBloc().getSymbolesPossibles()) {
            if (enableLogs && logWriter != null) {
                logWriter.println("Tentative de placer " + valeur + " à la position (" + ligne + ", " + colonne + ")");
                logWriter.flush();
            }

            if (estValideAPlacer(ligne, colonne, valeur)) {
                current.setValeur(valeur);
                if (enableLogs && logWriter != null) {
                    logWriter.println("Placement réussi de " + valeur + " à (" + ligne + ", " + colonne + ")");
                    logWriter.flush();
                }

                if (backtrack(nextRow, nextCol, enableLogs)) {
                    return true;
                }

                current.setValeur(".");
                if (enableLogs && logWriter != null) {
                    logWriter.println("Retour arrière sur la case (" + ligne + ", " + colonne + ")");
                    logWriter.flush();
                }
            }
        }
        return false;
    }

        
        /**
     * Compte le nombre de solutions possibles pour la grille actuelle.
     *
     * @return Le nombre total de solutions valides.
     */
    public int compterSolutions() {
        return compterSolutionsRecursif(0, 0);
    }

    /**
     * Compte récursivement le nombre de solutions à partir d'une position donnée dans la grille.
     *
     * @param ligne   La ligne actuelle de la grille.
     * @param colonne La colonne actuelle de la grille.
     * @return Le nombre de solutions trouvées à partir de cette position.
     */
    private int compterSolutionsRecursif(int ligne, int colonne) {
        if (ligne == getTaille()) {
            return 1; // Solution trouvée
        }

        int nextCol = (colonne + 1) % getTaille();
        int nextRow = ligne + (nextCol == 0 ? 1 : 0);

        Case current = getCase(ligne, colonne);
        if (!current.estVide()) {
            return compterSolutionsRecursif(nextRow, nextCol);
        }

        int count = 0;
        List<String> symbolesPossibles = current.getBloc().getSymbolesPossibles();

        for (String symbole : symbolesPossibles) {
            if (estValideAPlacer(ligne, colonne, symbole)) {
                current.setValeur(symbole);
                count += compterSolutionsRecursif(nextRow, nextCol);
                current.setValeur("."); // Backtracking
            }
        }
        return count;
    }

    /**
     * Vérifie si une valeur peut être placée à une position spécifique de la grille.
     *
     * @param ligne   La ligne de la case à vérifier.
     * @param colonne La colonne de la case à vérifier.
     * @param valeur  La valeur à placer.
     * @return true si la valeur peut être placée sans violer les règles du Sudoku, sinon false.
     */
    public boolean estValideAPlacer(int ligne, int colonne, String valeur) {
        Case currentCase = getCase(ligne, colonne);
        String oldValue = currentCase.getValeur();
        currentCase.setValeur(valeur);

        boolean isValid = estLigneValide(ligne) && estColonneValide(colonne) && estBlocValide(currentCase.getBloc());

        currentCase.setValeur(oldValue);
        return isValid;
    }

    /**
     * Vérifie si un bloc est valide (pas de doublons dans les valeurs).
     *
     * @param bloc Le bloc à vérifier.
     * @return true si le bloc est valide, sinon false.
     */
    public boolean estBlocValide(Bloc bloc) {
        Set<String> seen = new HashSet<>();
        for (Case c : bloc.getCases()) {
            String val = c.getValeur();
            if (!val.equals(".") && !seen.add(val)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Récupère la taille de la grille.
     *
     * @return La taille de la grille.
     */
    public int getTaille() {
        return taille;
    }

    /**
     * Vérifie si un symbole peut être placé dans une case donnée en respectant les règles du Sudoku.
     *
     * @param c       La case à vérifier.
     * @param symbole Le symbole à tester.
     * @return true si le symbole peut être placé, sinon false.
     */
    public boolean peutPlacerSymbole(Case c, String symbole) {
        for (int col = 0; col < taille; col++) {
            Case caseCourante = getCase(c.getLigne(), col);
            if (caseCourante != null && symbole.equals(caseCourante.getValeur())) {
                return false;
            }
        }

        for (int row = 0; row < taille; row++) {
            Case caseCourante = getCase(row, c.getColonne());
            if (caseCourante != null && symbole.equals(caseCourante.getValeur())) {
                return false;
            }
        }

        if (c.getBloc() != null) {
            for (Case caseBloc : c.getBloc().getCases()) {
                if (symbole.equals(caseBloc.getValeur())) {
                    return false;
                }
            }
        }

        return true;
    }

    public static final String[] couleurs = {
        "\u001B[100m", // Gris foncé (arrière-plan gris foncé)
        "\u001B[42m", // Vert clair (arrière-plan vert)
        "\u001B[43m", // Jaune vif (arrière-plan jaune)
        "\u001B[44m", // Bleu vif (arrière-plan bleu)
        "\u001B[45m", // Magenta clair (arrière-plan magenta)
        "\u001B[46m", // Cyan clair (arrière-plan cyan)
        };

    /**
     * Affiche la grille dans la console avec des couleurs pour chaque bloc.
     * Les couleurs sont définies pour améliorer la lisibilité.
     */
    public void afficher() {
        for (int i = 0; i < this.getTaille(); i++) {
            for (int j = 0; j < this.getTaille(); j++) {
                Case c = this.getCase(i, j);
                int blocId = c.getBloc().getId();
                String value = c.estVide() ? "." : c.getValeur();

                System.out.print(couleurs[blocId % couleurs.length] + value + " \u001B[0m ");
            }
            System.out.println();
        }
    }
    }
