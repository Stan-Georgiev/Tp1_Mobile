package cstjean.mobile.Mouvement;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant les mouvements possibles d'une dame.
 */
public class MouvementDame {

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
         * @param ligne     la ligne du déplacement
         * @param colonne   la colonne du déplacement
         * @param estPrise  vrai si une pièce est prise
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
     * Retourne la liste des déplacements possibles d’une dame.
     * Le damier est un tableau 10x10 contenant :
     * <ul>
     *     <li>'d' pour une dame blanche</li>
     *     <li>'D' pour une dame noire</li>
     *     <li>null pour une case vide</li>
     * </ul>
     *
     * @param ligne   la ligne actuelle de la dame
     * @param colonne la colonne actuelle de la dame
     * @param damier  le tableau représentant le damier
     * @return la liste des positions possibles
     */
    public static List<MouvementDame.Position> getDeplacementsPossibles(
            int ligne, int colonne, Object[][] damier) {

        List<MouvementDame.Position> moves = new ArrayList<>();
        int taille = damier.length;

        int oppLigneCap = taille - ligne;
        int oppColonneCap = taille - colonne;

        for (int i = 0; i < taille; i++) {
            // Prises potentielles et mouvements diagonaux
            if (i < Math.min(oppLigneCap, oppColonneCap)
                    && ligne < oppLigneCap && colonne < oppColonneCap) {

                ajouterMouvement(moves, damier, ligne - i, colonne - i,
                        taille, -i, -i);

                if (i < ligne) {
                    ajouterMouvement(moves, damier, ligne + i, colonne - i,
                            taille, i, -i);
                }
                if (i < colonne) {
                    ajouterMouvement(moves, damier, ligne - i, colonne + i,
                            taille, -i, i);
                }
            } else if (i < Math.min(ligne, colonne)
                    && oppLigneCap < ligne && colonne < oppColonneCap) {

                ajouterMouvement(moves, damier, ligne + i, colonne + i,
                        taille, i, i);

                if (i < oppLigneCap) {
                    ajouterMouvement(moves, damier, ligne - i, colonne + i,
                            taille, -i, i);
                }
                if (i < oppColonneCap) {
                    ajouterMouvement(moves, damier, ligne + i, colonne - i,
                            taille, i, -i);
                }
            }
        }

        // Mouvements courts diagonaux
        ajouterMouvement(moves, damier, ligne + 1, colonne - 1, taille, -1, 1);
        ajouterMouvement(moves, damier, ligne + 1, colonne + 1, taille, 1, 1);
        ajouterMouvement(moves, damier, ligne - 1, colonne + 1, taille, -1, 1);
        ajouterMouvement(moves, damier, ligne - 1, colonne - 1, taille, -1, -1);

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
     * @param direction direction avant/arrière
     */
    private static void ajouterMouvement(
            List<MouvementDame.Position> moves,
            Object[][] damier,
            int l,
            int c,
            int taille,
            int cote,
            int direction) {

        if (l >= 0 && l < taille && c >= 0 && c < taille) {
            if (damier[l][c] == null) {
                moves.add(new MouvementDame.Position(l, c, false));
            } else if (l + direction >= 0 && l + direction < taille
                    && c + cote >= 0 && c + cote < taille
                    && damier[l + direction][c + cote] == null) {
                moves.add(new MouvementDame.Position(l + direction,
                        c + cote, true));
            }
        }
    }

    /**
     * Déplace une dame sur le damier selon une position donnée.
     *
     * @param moves     liste des mouvements possibles
     * @param pos       position de destination
     * @param damier    tableau du damier
     * @param origineL  ligne d’origine
     * @param origineC  colonne d’origine
     */
    public static void bougerDame(
            List<MouvementPion.Position> moves,
            MouvementPion.Position pos,
            Object[][] damier,
            int origineL,
            int origineC) {

        // Vérifie si la position de destination est valide
        boolean valide = moves.stream().anyMatch(m ->
                m.ligne == pos.ligne
                        && m.colonne == pos.colonne
                        && m.estPrise == pos.estPrise);

        if (!valide) {
            return;
        }

        Object pion = damier[origineL][origineC];
        damier[origineL][origineC] = null; // enlève la dame de la case d’origine

        // Si le mouvement est une prise, on supprime le pion sauté
        if (pos.estPrise) {
            int lignePrise = pos.ligne > origineL ? pos.ligne - 1 : pos.ligne + 1;
            int colonnePrise = pos.colonne > origineC ? pos.colonne - 1 : pos.colonne + 1;
            damier[lignePrise][colonnePrise] = null;
        }

        // Place la dame sur la nouvelle case
        damier[pos.ligne][pos.colonne] = pion;
    }
}
