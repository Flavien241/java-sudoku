import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * La classe {@code SudokuGenerator} permet de générer des grilles de Sudoku jouables 
 * à partir de grilles complètes, ainsi que d'appliquer des transformations sur celles-ci 
 * telles que des permutations de lignes, de colonnes ou de symboles.
 */
public class SudokuGenerator {
    private Grille grille;
    private Random random = new Random();
    private static final int MAX_ATTEMPTS = 1000; // Limite d'essais pour éviter les boucles infinies

    /**
     * Constructeur de la classe {@code SudokuGenerator}.
     *
     * @param grille La grille de Sudoku sur laquelle les opérations seront effectuées.
     */
    public SudokuGenerator(Grille grille) {
        this.grille = grille;
    }

    /**
     * Génère une grille jouable à partir d'une grille complète en retirant un nombre donné de chiffres.
     * La méthode s'assure qu'il reste une solution unique après la suppression de chaque chiffre.
     *
     * @param nbChiffresARetirer Le nombre de chiffres à retirer pour générer la grille jouable.
     * @return La grille de Sudoku jouable générée.
     */
    public Grille genererGrilleJouable(int nbChiffresARetirer) {
        List<Case> cases = new ArrayList<>();
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                cases.add(grille.getCase(i, j));
            }
        }

        Collections.shuffle(cases, random);
        int tentatives = 0;

        for (Case c : cases) {
            if (nbChiffresARetirer <= 0 || tentatives >= MAX_ATTEMPTS) break;

            String ancienneValeur = c.getValeur();
            c.setValeur(".");

            if (compterSolutions() != 1) {
                c.setValeur(ancienneValeur); // Restauration si plusieurs solutions possibles
            } else {
                nbChiffresARetirer--;
            }

            tentatives++;
        }

        if (tentatives >= MAX_ATTEMPTS) {
            System.out.println("⚠️ La génération a été arrêtée après plusieurs tentatives.");
        }

        return grille;
    }

    /**
     * Permute deux lignes dans un même bloc de la grille de Sudoku.
     *
     * @param bloc   L'indice du bloc où les lignes doivent être permutées.
     * @param ligne1 L'indice de la première ligne à permuter.
     * @param ligne2 L'indice de la seconde ligne à permuter.
     */
    public void permuterLignes(int bloc, int ligne1, int ligne2) {
        int tailleBloc = (int) Math.sqrt(grille.getTaille());
        int baseLigne = bloc * tailleBloc;

        for (int col = 0; col < grille.getTaille(); col++) {
            Case case1 = grille.getCase(baseLigne + ligne1, col);
            Case case2 = grille.getCase(baseLigne + ligne2, col);

            String temp = case1.getValeur();
            case1.setValeur(case2.getValeur());
            case2.setValeur(temp);
        }
    }

    /**
     * Permute deux colonnes dans un même bloc de la grille de Sudoku.
     *
     * @param bloc L'indice du bloc où les colonnes doivent être permutées.
     * @param col1 L'indice de la première colonne à permuter.
     * @param col2 L'indice de la seconde colonne à permuter.
     */
    public void permuterColonnes(int bloc, int col1, int col2) {
        int tailleBloc = (int) Math.sqrt(grille.getTaille());
        int baseCol = bloc * tailleBloc;

        for (int row = 0; row < grille.getTaille(); row++) {
            Case case1 = grille.getCase(row, baseCol + col1);
            Case case2 = grille.getCase(row, baseCol + col2);

            String temp = case1.getValeur();
            case1.setValeur(case2.getValeur());
            case2.setValeur(temp);
        }
    }

    /**
     * Permute deux symboles dans toute la grille de Sudoku.
     * Cette méthode est utile pour générer des variantes visuelles d'une même grille.
     *
     * @param symbole1 Le premier symbole à permuter.
     * @param symbole2 Le second symbole à permuter.
     */
    public void permuterSymboles(String symbole1, String symbole2) {
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                Case c = grille.getCase(i, j);
                if (c.getValeur().equals(symbole1)) {
                    c.setValeur(symbole2);
                } else if (c.getValeur().equals(symbole2)) {
                    c.setValeur(symbole1);
                }
            }
        }
    }

    /**
     * Compte le nombre de solutions possibles pour la grille de Sudoku actuelle.
     * Cette méthode est utilisée pour vérifier l'unicité des solutions lors de la génération.
     *
     * @return Le nombre de solutions trouvées pour la grille actuelle.
     */
    public int compterSolutions() {
        return grille.compterSolutions();
    }
}

