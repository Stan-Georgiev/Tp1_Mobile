package cstjean.mobile.Joueur;

import cstjean.mobile.Damier.Damier;
import cstjean.mobile.Pion.Pion;
import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Mouvement.MouvementPion;

import java.util.List;

/**
 * Classe pour vérifier la fin de la partie dans un jeu de Dames.
 * <p>
 * Conditions de fin :
 * <ul>
 *     <li>Aucune pièce restante pour une couleur.</li>
 *     <li>Aucun déplacement possible pour une couleur.</li>
 * </ul>
 * </p>
 */
public class FinPartie {

    /** Référence vers le damier actuel. */
    private final Damier damier;

    /**
     * Constructeur de la classe FinPartie.
     *
     * @param damier le damier du jeu
     */
    public FinPartie(Damier damier) {
        this.damier = damier;
    }

    /**
     * Vérifie si la partie est terminée.
     *
     * @return {@code true} si la partie est finie, sinon {@code false}
     */
    public boolean estTerminee() {
        return aucunePiece(Couleur.Blanc)
                || aucunePiece(Couleur.Noir)
                || aucunDeplacementPossible(Couleur.Blanc)
                || aucunDeplacementPossible(Couleur.Noir);
    }

    /**
     * Vérifie s’il reste encore des pièces d’une couleur donnée.
     *
     * @param couleur la couleur à vérifier
     * @return {@code true} si aucune pièce de cette couleur n’est présente
     */
    public boolean aucunePiece(Couleur couleur) {
        for (int i = 0; i < damier.getNombrePions(); i++) {
            Pion pion = damier.getPion(i);
            if (pion != null && pion.getCouleur() == couleur) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si un joueur ne peut plus bouger aucune pièce.
     *
     * @param couleur la couleur du joueur
     * @return {@code true} si aucun déplacement n’est possible
     */
    public boolean aucunDeplacementPossible(Couleur couleur) {
        Object[][] plateau = convertirDamier(damier);

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                Object caseCourante = plateau[ligne][colonne];
                if (caseCourante != null) {
                    boolean estBlanc = caseCourante.equals("p");

                    if ((estBlanc && couleur == Couleur.Blanc)
                            || (!estBlanc && couleur == Couleur.Noir)) {

                        List<MouvementPion.Position> moves =
                                MouvementPion.getDeplacementsPossibles(
                                        ligne, colonne, estBlanc, plateau);

                        if (!moves.isEmpty()) {
                            return false; // au moins un mouvement possible
                        }
                    }
                }
            }
        }
        return true; // aucun mouvement trouvé
    }

    /**
     * Convertit la LinkedList du damier en tableau 10x10 pour l’analyse.
     * <p>
     * Seules les cases noires sont utilisées (50 cases valides).
     * </p>
     *
     * @param damier le damier à convertir
     * @return un tableau 10x10 représentant le damier
     */
    private Object[][] convertirDamier(Damier damier) {
        Object[][] plateau = new Object[10][10];
        int posManoury = 1;

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                if ((ligne + colonne) % 2 == 1) {
                    Pion pion = damier.getPion(posManoury - 1);

                    if (pion != null) {
                        plateau[ligne][colonne] =
                                (pion.getCouleur() == Couleur.Blanc) ? "p" : "P";
                    } else {
                        plateau[ligne][colonne] = null;
                    }
                    posManoury++;
                } else {
                    plateau[ligne][colonne] = null; // case non jouable
                }
            }
        }
        return plateau;
    }

    /**
     * Retourne le gagnant si la partie est terminée.
     *
     * @return "Blanc", "Noir" ou "Aucun" selon la situation
     */
    public String getGagnant() {
        if (aucunePiece(Couleur.Noir)
                || aucunDeplacementPossible(Couleur.Noir)) {
            return "Blanc";
        }
        if (aucunePiece(Couleur.Blanc)
                || aucunDeplacementPossible(Couleur.Blanc)) {
            return "Noir";
        }
        return "Aucun";
    }
}
