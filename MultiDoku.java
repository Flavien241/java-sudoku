import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code MultiDoku} gère plusieurs grilles de Sudoku, appelées MultiDoku,
 * qui peuvent avoir des cases partagées entre elles. Elle fournit des méthodes
 * pour résoudre ces grilles de manière synchrone tout en respectant les contraintes des cases partagées.
 */
public class MultiDoku {
    private List<Grille> grilles;
    private List<Case[]> casesPartagees;

    /**
     * Constructeur par défaut initialisant les listes de grilles et de cases partagées.
     */
    public MultiDoku() {
        this.grilles = new ArrayList<>();
        this.casesPartagees = new ArrayList<>();
    }

    /**
     * Ajoute une grille à la liste des grilles du MultiDoku.
     *
     * @param grille La grille à ajouter.
     */
    public void ajouterGrille(Grille grille) {
        grilles.add(grille);
    }

    /**
     * Ajoute une paire de cases partagées entre deux grilles.
     *
     * @param case1 La première case partagée.
     * @param case2 La deuxième case partagée.
     */
    public void ajouterCasesPartagees(Case case1, Case case2) {
        casesPartagees.add(new Case[]{case1, case2});
    }

    /**
     * Synchronise les valeurs des cases partagées pour s'assurer qu'elles restent cohérentes.
     *
     * @return {@code true} si la synchronisation est réussie sans conflits, sinon {@code false}.
     */
    public boolean synchroniserCasesPartagees() {
        for (Case[] paire : casesPartagees) {
            Case case1 = paire[0];
            Case case2 = paire[1];

            if (!case1.estVide() && case2.estVide()) {
                case2.setValeur(case1.getValeur());
            } else if (case1.estVide() && !case2.estVide()) {
                case1.setValeur(case2.getValeur());
            } else if (!case1.estVide() && !case2.estVide() && !case1.getValeur().equals(case2.getValeur())) {
                System.out.println("❌ Conflit détecté entre les cases partagées : (" +
                    case1.getLigne() + "," + case1.getColonne() + ") [" + case1.getValeur() + "] et (" +
                    case2.getLigne() + "," + case2.getColonne() + ") [" + case2.getValeur() + "]");
                return false;
            }
        }
        return true;
    }

    /**
     * Résout toutes les grilles du MultiDoku en respectant les cases partagées.
     *
     * @param symbolesPossibles La liste des symboles possibles à utiliser pour la résolution.
     * @return {@code true} si la résolution est réussie, sinon {@code false}.
     */
    public boolean resoudre(List<String> symbolesPossibles) {
        return resoudreRecursif(0, symbolesPossibles);
    }

    /**
     * Résout récursivement les grilles à partir d'un index donné.
     *
     * @param indexGrille Index de la grille à résoudre.
     * @param symbolesPossibles Liste des symboles possibles.
     * @return {@code true} si la grille est résolue avec succès, sinon {@code false}.
     */
    public boolean resoudreRecursif(int indexGrille, List<String> symbolesPossibles) {
        if (indexGrille == grilles.size()) {
            return true;
        }

        Grille grille = grilles.get(indexGrille);

        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                Case currentCase = grille.getCase(i, j);

                if (currentCase.estVide()) {
                    for (String valeur : symbolesPossibles) {
                        currentCase.setValeur(valeur);

                        if (synchroniserCasesPartagees() && grille.estValide()) {
                            if (resoudreRecursif(indexGrille, symbolesPossibles)) {
                                return true;
                            }
                        }

                        currentCase.setValeur(".");
                        resetSharedCases(currentCase);
                        synchroniserCasesPartagees();
                    }
                    return false;
                }
            }
        }
        return resoudreRecursif(indexGrille + 1, symbolesPossibles);
    }

    /**
     * Réinitialise les valeurs des cases partagées lorsqu'une tentative de résolution échoue.
     *
     * @param c La case qui a échoué lors de la tentative de résolution.
     */
    public void resetSharedCases(Case c) {
        for (Case[] paire : casesPartagees) {
            if (paire[0] == c) {
                paire[1].setValeur(".");
            } else if (paire[1] == c) {
                paire[0].setValeur(".");
            }
        }
    }

    /**
     * Affiche toutes les grilles du MultiDoku avec des couleurs distinctes pour chaque bloc.
     */
    public void afficher() {
        for (int g = 0; g < grilles.size(); g++) {
            Grille grille = grilles.get(g);
            System.out.println("\nGrille " + (g + 1) + " :");
            afficherGrille(grille);
        }
    }

    /**
     * Récupère une grille spécifique par son index.
     *
     * @param index L'index de la grille à récupérer.
     * @return La grille correspondante si l'index est valide, sinon {@code null}.
     */
    public Grille getGrille(int index) {
        if (index >= 0 && index < grilles.size()) {
            return grilles.get(index);
        } else {
            System.out.println("❌ Indice de grille invalide : " + index);
            return null;
        }
    }

    /**
     * Retourne la liste de toutes les grilles du MultiDoku.
     *
     * @return Liste des grilles.
     */
    public List<Grille> getGrilles() {
        return grilles;
    }

    /**
     * Affiche une grille spécifique avec des séparateurs visuels et des couleurs par bloc.
     *
     * @param grille La grille à afficher.
     */
    public void afficherGrille(Grille grille) {
        System.out.println("╔═══╦═══╦═══╗");
        for (int i = 0; i < grille.getTaille(); i++) {
            System.out.print("║ ");
            for (int j = 0; j < grille.getTaille(); j++) {
                Case c = grille.getCase(i, j);
                int blocId = c.getBloc().getId();
                String value = c.estVide() ? "." : c.getValeur();
                System.out.print(couleurs[blocId % couleurs.length] + value + " \u001B[0m");

                if ((j + 1) % 3 == 0 && j != grille.getTaille() - 1) {
                    System.out.print("║ ");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("║");

            if ((i + 1) % 3 == 0 && i != grille.getTaille() - 1) {
                System.out.println("╠═══╬═══╬═══╣");
            }
        }
        System.out.println("╚═══╩═══╩═══╝");
    }

    private static final String[] couleurs = {
        "\u001B[31m", // Rouge
        "\u001B[32m", // Vert
        "\u001B[33m", // Jaune
        "\u001B[34m", // Bleu
        "\u001B[35m", // Magenta
        "\u001B[36m"  // Cyan
    };
}
