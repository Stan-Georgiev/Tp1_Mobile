package cstjean.mobile.Joueur;

import cstjean.mobile.Damier.Damier;
import cstjean.mobile.Pion.Pion;
import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Mouvement.MouvementPion;

import java.util.List;

/**
 * Classe pour vérifier la fin de la partie dans un jeu de Dames.
 * Conditions de fin :
 *  - Aucune pièce restante pour une couleur.
 *  - Aucun déplacement possible pour une couleur.
 */
public class FinPartie {

    private final Damier damier;

    public FinPartie(Damier damier) {
        this.damier = damier;
    }

    /**
     * Vérifie si la partie est terminée.
     * @return true si la partie est finie, sinon false.
     */
    public boolean estTerminee() {
        return aucunePiece(Couleur.Blanc) || aucunePiece(Couleur.Noir)
                || aucunDeplacementPossible(Couleur.Blanc)
                || aucunDeplacementPossible(Couleur.Noir);
    }

    /**
     * Vérifie s’il reste plus de pièce d’une couleur donnée.
     */
    private boolean aucunePiece(Couleur couleur) {
        for (int i = 0; i < damier.getNombrePions(); i++) {
            Pion p = damier.getPion(i);
            if (p != null && p.getCouleur() == couleur) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si un joueur ne peut plus bouger aucune pièce.
     */
    public boolean aucunDeplacementPossible(Couleur couleur) {
        Object[][] plateau = convertirDamier(damier);

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                Object caseCourante = plateau[ligne][colonne];
                if (caseCourante != null) {
                    boolean estBlanc = caseCourante.equals("p");
                    if ((estBlanc && couleur == Couleur.Blanc) ||
                            (!estBlanc && couleur == Couleur.Noir)) {

                        List<MouvementPion.Position> moves =
                                MouvementPion.getDeplacementsPossibles(ligne, colonne, estBlanc, plateau);

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
     * Convertit la LinkedList du Damier en tableau 10x10 pour l’analyse.
     * On n’utilise que les cases noires (50 cases valides).
     */
    private Object[][] convertirDamier(Damier damier) {
        Object[][] plateau = new Object[10][10];
        int posManoury = 1;

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                if ((ligne + colonne) % 2 == 1) { // case jouable
                    Pion p = damier.getPion(posManoury - 1);
                    if (p != null) {
                        plateau[ligne][colonne] =
                                (p.getCouleur() == Couleur.Blanc) ? "p" : "P";
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
     * @return "Blanc", "Noir" ou "Aucun" selon la situation.
     */
    public String getGagnant() {
        if (aucunePiece(Couleur.Noir) || aucunDeplacementPossible(Couleur.Noir)) {
            return "Blanc";
        }
        if (aucunePiece(Couleur.Blanc) || aucunDeplacementPossible(Couleur.Blanc)) {
            return "Noir";
        }
        return "Aucun";
    }
}
