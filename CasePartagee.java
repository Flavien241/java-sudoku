import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code CasePartagee} représente une case partagée entre plusieurs grilles 
 * dans un MultiDoku. Lorsqu'une valeur est assignée à la case partagée, cette valeur 
 * est automatiquement synchronisée avec toutes les cases liées.
 */
public class CasePartagee {
    private String valeur;
    private List<Case> casesLiees;

    /**
     * Constructeur par défaut qui initialise la valeur de la case partagée à vide (".")
     * et crée une liste vide de cases liées.
     */
    public CasePartagee() {
        this.valeur = ".";
        this.casesLiees = new ArrayList<>();
    }

    /**
     * Ajoute une case à la liste des cases liées à cette case partagée.
     *
     * @param c La case à lier à cette case partagée.
     */
    public void ajouterCase(Case c) {
        casesLiees.add(c);
    }

    /**
     * Retourne la valeur actuelle de la case partagée.
     *
     * @return La valeur de la case partagée sous forme de chaîne de caractères.
     */
    public String getValeur() {
        return valeur;
    }

    /**
     * Définit une nouvelle valeur pour la case partagée et synchronise cette valeur
     * avec toutes les cases liées.
     *
     * @param valeur La nouvelle valeur à assigner à la case partagée.
     */
    public void setValeur(String valeur) {
        this.valeur = valeur;
        for (Case c : casesLiees) {
            c.setValeur(valeur);
        }
    }

    /**
     * Vérifie si la case partagée est vide.
     *
     * @return {@code true} si la valeur de la case est ".", sinon {@code false}.
     */
    public boolean estVide() {
        return ".".equals(valeur);
    }
}
