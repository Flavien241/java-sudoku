/**
 * La classe {@code Case} représente une case individuelle dans une grille de Sudoku ou MultiDoku.
 * Chaque case est associée à un bloc, à des coordonnées spécifiques (ligne, colonne), et peut contenir une valeur.
 */
public class Case {
    private String valeur;
    private int sudokuid;
    private int colonne;
    private int ligne;
    private Bloc bloc;

    /**
     * Constructeur de la classe {@code Case}.
     *
     * @param sudokuid Identifiant de la grille de Sudoku à laquelle la case appartient.
     * @param valeur   La valeur initiale de la case (par défaut ".").
     * @param ligne    L'indice de la ligne de la case.
     * @param colonne  L'indice de la colonne de la case.
     * @param bloc     Le bloc auquel la case est associée.
     */
    public Case(int sudokuid, String valeur, int ligne, int colonne, Bloc bloc) {
        this.sudokuid = sudokuid;
        this.valeur = "."; // Valeur par défaut (case vide)
        this.ligne = ligne;
        this.colonne = colonne;
        this.bloc = bloc;
        this.valeur = valeur;
    }

    /**
     * Retourne la valeur de la case.
     *
     * @return La valeur de la case sous forme de chaîne de caractères.
     */
    public String getValeur() {
        return valeur;
    }

    /**
     * Définit la valeur de la case.
     *
     * @param valeur La nouvelle valeur à affecter à la case.
     * @throws IllegalArgumentException si la valeur est nulle ou ne contient pas exactement un caractère.
     */
    public void setValeur(String valeur) {
        if (valeur == null || valeur.length() != 1) {
            throw new IllegalArgumentException("La valeur doit être un seul caractère.");
        }
        this.valeur = valeur;
    }

    /**
     * Vérifie si la case est vide.
     *
     * @return {@code true} si la case est vide (représentée par "."), sinon {@code false}.
     */
    public boolean estVide() {
        return ".".equals(valeur);
    }

    /**
     * Retourne l'identifiant de la grille de Sudoku à laquelle la case appartient.
     *
     * @return L'identifiant de la grille de Sudoku.
     */
    public int getSudokuid() {
        return sudokuid;
    }

    /**
     * Retourne l'indice de la colonne de la case.
     *
     * @return L'indice de la colonne.
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Retourne l'indice de la ligne de la case.
     *
     * @return L'indice de la ligne.
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Retourne le bloc auquel la case appartient.
     *
     * @return Le bloc associé à la case.
     */
    public Bloc getBloc() {
        return bloc;
    }

    /**
     * Affecte un bloc à la case.
     *
     * @param bloc Le bloc à associer à la case.
     */
    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    /**
     * Représente la case sous forme de chaîne de caractères.
     *
     * @return La valeur de la case sous forme de chaîne.
     */
    @Override
    public String toString() {
        return valeur;
    }
}
