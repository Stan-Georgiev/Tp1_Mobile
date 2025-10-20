package cstjean.mobile.Pion;

/** Classe représentant une Dame (reine) dans un jeu de dames. */
public class Dame extends Pion {
    /** Le constructeur de la classe Dame.
     *
     * @param couleur la couleur de la dame
     */
    public Dame(Couleur couleur) {
        super(couleur);
    }

    @Override
    public char getRepresentation() {
        if (getCouleur() == Couleur.Blanc) {
            return 'd';
        } else {
            return 'D';
        }
    }
}