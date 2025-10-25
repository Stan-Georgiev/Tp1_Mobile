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
        public boolean estPrise;

        public Position(int ligne, int colonne,  boolean estPrise) {
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
        ajouterMouvement(moves, damier, ligne + 1, colonne - 1, taille, -1, 1);
        ajouterMouvement(moves, damier, ligne + 1, colonne + 1, taille, 1, 1);
        ajouterMouvement(moves, damier, ligne - 1, colonne + 1, taille, -1, 1);
        ajouterMouvement(moves, damier, ligne - 1, colonne - 1, taille, -1, -1);

        return moves;
    }

    private static void ajouterMouvement(List<MouvementDame.Position> moves, Object[][] damier, int l, int c, int taille, int cote, int direction) {
        if (l >= 0 && l < taille && c >= 0 && c < taille) {
            if (damier[l][c] == null) {
                moves.add(new MouvementDame.Position(l, c, false));
            }
            else if (damier[l + direction][c + cote] == null){
                moves.add(new MouvementDame.Position(l + direction, c + cote, true));
            }
        }
    }
}
