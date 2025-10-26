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
    public static List<MouvementDame.Position> getDeplacementsPossibles(int ligne, int colonne, Object[][] damier) {
        List<MouvementDame.Position> moves = new ArrayList<>();
        int taille = damier.length;
        //ex: 4,2 -> 8,6, pion adverse sur 7, 5
        // Déplacements simples (diagonales avant et arrière)
        int oppLigneCap = taille - ligne;
        int oppColonneCap = taille - colonne;
        for (int i = 0; i < taille; i++) {
            //Prises à ajouter
            if (i < (oppLigneCap < oppColonneCap ? oppLigneCap : oppColonneCap) && ligne < oppLigneCap && colonne < oppColonneCap) {
                ajouterMouvement(moves, damier, ligne - i, colonne - i, taille, -i, -i);
                if (i < ligne){
                    ajouterMouvement(moves, damier, ligne + i,  colonne - i, taille, i, -i);
                }
                if (i < colonne){
                    ajouterMouvement(moves, damier, ligne - i, colonne + i, taille, -i, i);
                }
            }
            else if (i < (ligne < colonne ? ligne : colonne) && oppLigneCap < ligne && colonne < oppColonneCap){
                ajouterMouvement(moves, damier, ligne + i, colonne + i, taille, i, i);
                if (i < oppLigneCap){
                    ajouterMouvement(moves, damier, ligne - i, colonne + i, taille, -i, i);
                }
                if (i < oppColonneCap){
                    ajouterMouvement(moves, damier, ligne + i, colonne - i, taille, i, -i);
                }
            }
        }

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

    public static void bougerDame(List<MouvementPion.Position> moves, MouvementPion.Position pos, Object[][] damier, int origineL, int origineC) {
        // Vérifie si la position de destination est valide
        if (!moves.contains(pos)) return;

        Object pion = damier[origineL][origineC];
        damier[origineL][origineC] = null; // enlève la dame de la case d’origine

        // Si le mouvement est une prise, on supprime le pion sauté
        if (pos.estPrise) {
            int lignePrise = pos.ligne > origineL ? pos.ligne - 1 : pos.ligne + 1;
            int colonnePrise = pos.colonne > origineC ? pos.colonne - 1 : pos.colonne + 1;
            damier[lignePrise][colonnePrise] = null;
        }

        // Place le pion sur la nouvelle case
        damier[pos.ligne][pos.colonne] = pion;
    }
}
