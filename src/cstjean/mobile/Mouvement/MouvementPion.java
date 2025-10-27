package cstjean.mobile.Mouvement;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable des mouvements possibles d'un pion.
 */
public class MouvementPion {

    /**
     * Représente une position sur le damier.
     */
    public static class Position {
        /** Ligne sur le damier. */
        public int ligne;

        /** Colonne sur le damier. */
        public int colonne;

        /** Indique si le mouvement implique une prise. */
        public boolean estPrise;

        /**
         * Constructeur d'une position.
         *
         * @param ligne la ligne du pion
         * @param colonne la colonne du pion
         * @param estPrise indique si le mouvement est une prise
         */
        public Position(int ligne, int colonne, boolean estPrise) {
            this.ligne = ligne;
            this.colonne = colonne;
            this.estPrise = estPrise;
        }

        @Override
        public String toString() {
            return "(" + ligne + "," + colonne + ")";
        }
    }

    /**
     * Retourne la liste des déplacements possibles d’un pion.
     * Le damier est un tableau 10x10 contenant :
     * <ul>
     *     <li>'p' pour un pion blanc</li>
     *     <li>'P' pour un pion noir</li>
     *     <li>null pour une case vide</li>
     * </ul>
     *
     * @param ligne     la ligne actuelle du pion
     * @param colonne   la colonne actuelle du pion
     * @param estBlanc  vrai si le pion est blanc
     * @param damier    le tableau représentant le damier
     * @return la liste des positions accessibles
     */
    public static List<Position> getDeplacementsPossibles(
            int ligne, int colonne, boolean estBlanc, Object[][] damier) {

        List<Position> moves = new ArrayList<>();
        int direction = estBlanc ? -1 : 1; // les blancs montent, les noirs descendent
        int taille = damier.length;

        // Déplacements simples (diagonales avant)
        ajouterMouvement(moves, damier, ligne + direction, colonne - 1, taille, -1, direction);
        ajouterMouvement(moves, damier, ligne + direction, colonne + 1, taille, 1, direction);

        return moves;
    }

    /**
     * Ajoute un mouvement valide à la liste.
     *
     * @param moves     liste des mouvements possibles
     * @param damier    tableau du damier
     * @param l         ligne cible
     * @param c         colonne cible
     * @param taille    taille du damier
     * @param cote      direction latérale (-1 ou +1)
     * @param direction direction avant (1 ou -1)
     */
    private static void ajouterMouvement(
            List<Position> moves, Object[][] damier, int l, int c,
            int taille, int cote, int direction) {

        if (l >= 0 && l < taille && c >= 0 && c < taille) {
            if (damier[l][c] == null) {
                moves.add(new Position(l, c, false));
            } else if (l + direction >= 0 && l + direction < taille
                    && c + cote >= 0 && c + cote < taille
                    && damier[l + direction][c + cote] == null) {
                moves.add(new Position(l + direction, c + cote, true));
            }
        }
    }

    /**
     * Déplace un pion sur le damier selon une position donnée.
     *
     * @param moves     liste des mouvements possibles
     * @param pos       position de destination
     * @param damier    tableau du damier
     * @param origineL  ligne d’origine
     * @param origineC  colonne d’origine
     */
    public static void bougerPion(
            List<Position> moves, Position pos, Object[][] damier,
            int origineL, int origineC) {

        // Vérifie si la position de destination est valide
        if (!moves.contains(pos)) {
            return;
        }

        Object pion = damier[origineL][origineC];
        damier[origineL][origineC] = null; // enlève le pion de la case d’origine

        // Si le mouvement est une prise, on supprime le pion sauté
        if (pos.estPrise) {
            int lignePrise = (origineL + pos.ligne) / 2;
            int colonnePrise = (origineC + pos.colonne) / 2;
            damier[lignePrise][colonnePrise] = null;
        }

        // Place le pion sur la nouvelle case
        damier[pos.ligne][pos.colonne] = pion;
    }
}
