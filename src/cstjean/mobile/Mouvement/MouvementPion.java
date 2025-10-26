package cstjean.mobile.Mouvement;

import java.util.ArrayList;
import java.util.List;

public class MouvementPion {

    /**
     * Représente une position sur le damier.
     */
    public static class Position {
        public int ligne;
        public int colonne;

        public boolean estPrise;

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
     *  - 'p' pour un pion blanc
     *  - 'P' pour un pion noir
     *  - null pour une case vide
     */
    public static List<Position> getDeplacementsPossibles(int ligne, int colonne, boolean estBlanc, Object[][] damier) {
        List<Position> moves = new ArrayList<>();
        int direction = estBlanc ? -1 : 1; // les blancs montent, les noirs descendent
        int taille = damier.length;

        // Déplacements simples (diagonales avant)
        ajouterMouvement(moves, damier, ligne + direction, colonne - 1, taille, -1, direction);
        ajouterMouvement(moves, damier, ligne + direction, colonne + 1, taille, 1, direction);

        return moves;
    }

    private static void ajouterMouvement(List<Position> moves, Object[][] damier, int l, int c, int taille, int cote, int direction) {
        if (l >= 0 && l < taille && c >= 0 && c < taille) {
            if (damier[l][c] == null) {
                moves.add(new Position(l, c, false));
            }
            else if (damier[l + direction][c + cote] == null){
                moves.add(new Position(l + direction, c + cote, true));
            }
        }
    }
    public static void bougerPion(List<Position> moves, Position pos, Object[][] damier, int origineL, int origineC) {
        // Vérifie si la position de destination est valide
        if (!moves.contains(pos)) return;

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
