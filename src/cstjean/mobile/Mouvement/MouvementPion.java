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

        public Position(int ligne, int colonne) {
            this.ligne = ligne;
            this.colonne = colonne;
        }

        @Override
        public String toString() {
            return "(" + ligne + "," + colonne + ")";
        }
    }

    /**
     * Retourne la liste des déplacements possibles d’un pion.
     * Le damier est un tableau 10x10 contenant :
     *  - 'B' pour un pion blanc
     *  - 'N' pour un pion noir
     *  - null pour une case vide
     */
    public static List<Position> getDeplacementsPossibles(int ligne, int colonne, boolean estBlanc, Object[][] damier) {
        List<Position> moves = new ArrayList<>();
        int direction = estBlanc ? -1 : 1; // les blancs montent, les noirs descendent
        int taille = damier.length;

        // Déplacements simples (diagonales avant)
        ajouterSiVide(moves, damier, ligne + direction, colonne - 1, taille);
        ajouterSiVide(moves, damier, ligne + direction, colonne + 1, taille);

        return moves;
    }

    private static void ajouterSiVide(List<Position> moves, Object[][] damier, int l, int c, int taille) {
        if (l >= 0 && l < taille && c >= 0 && c < taille && damier[l][c] == null) {
            moves.add(new Position(l, c));
        }
    }
}
