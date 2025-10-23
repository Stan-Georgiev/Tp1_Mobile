package cstjean.mobile.Mouvement;

import java.util.ArrayList;
import java.util.List;

public class MouvementDame {
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
     * Retourne la liste des déplacements possibles d’une dame.
     * Le damier est un tableau 10x10 contenant :
     *  - 'd' pour un dame blanche
     *  - 'D' pour une dame noire
     *  - null pour une case vide
     */
    public static List<MouvementDame.Position> getDeplacementsPossibles(int ligne, int colonne, boolean estBlanc, Object[][] damier) {
        List<MouvementDame.Position> moves = new ArrayList<>();
        int taille = damier.length;

        // Déplacements simples (diagonales avant et arrière)
        ajouterSiVide(moves, damier, ligne + 1, colonne - 1, taille);
        ajouterSiVide(moves, damier, ligne + 1, colonne + 1, taille);
        ajouterSiVide(moves, damier, ligne - 1, colonne + 1, taille);
        ajouterSiVide(moves, damier, ligne - 1, colonne - 1, taille);

        return moves;
    }

    private static void ajouterSiVide(List<MouvementDame.Position> moves, Object[][] damier, int l, int c, int taille) {
        if (l >= 0 && l < taille && c >= 0 && c < taille && damier[l][c] == null) {
            moves.add(new MouvementDame.Position(l, c));
        }
    }
}
