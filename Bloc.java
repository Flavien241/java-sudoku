import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Représente un bloc dans une grille de Sudoku ou MultiDoku.
 * Un bloc est composé de plusieurs cases et doit contenir des symboles uniques.
 */
public class Bloc {
    private int id;
    private List<Case> cases;
    private List<String> symbolesPossibles;

    /**
     * Constructeur de la classe Bloc.
     *
     * @param id                L'identifiant unique du bloc.
     * @param symbolesPossibles La liste des symboles autorisés dans le bloc.
     */
    public Bloc(int id, List<String> symbolesPossibles) {
        this.id = id;
        this.cases = new ArrayList<>();
        this.symbolesPossibles = symbolesPossibles;
    }

    /**
     * Ajoute une case au bloc.
     *
     * @param c La case à ajouter.
     */
    public void ajouterCase(Case c) {
        this.cases.add(c);
    }

    /**
     * Retourne la liste des cases du bloc.
     *
     * @return La liste des cases.
     */
    public List<Case> getCases() {
        return cases;
    }

    /**
     * Retourne l'identifiant du bloc.
     *
     * @return L'identifiant du bloc.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne la liste des symboles possibles dans le bloc.
     *
     * @return La liste des symboles autorisés.
     */
    public List<String> getSymbolesPossibles() {
        return symbolesPossibles;
    }

    /**
     * Vérifie si le bloc est valide, c'est-à-dire qu'il ne contient pas de doublons
     * parmi les valeurs des cases non vides.
     *
     * @return {@code true} si le bloc est valide, {@code false} sinon.
     */
    public boolean estValide() {
        Set<String> valeurs = new HashSet<>();
        for (Case c : cases) {
            if (!c.estVide()) {
                if (!valeurs.add(c.getValeur())) {
                    return false;
                }
            }
        }
        return true;
    }
}
