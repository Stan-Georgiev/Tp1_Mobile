package cstjean.mobile.pion;

/** Classe représentant une Dame (reine) dans un jeu de dames. */
public class Dame extends Pion {
    /** Le constructeur de la classe Dame.
     *
     * @param couleur la couleur de la dame
     */
    public Dame(Couleur couleur) {
        super(couleur);
    }

    /** Méthode qui affiche les dames en correspondance avec leur couleur.
     *
     * @return cela retourne la representation d'une dame.
     */
    @Override
    public char getRepresentation() {
        if (getCouleur() == Couleur.Blanc) {
            return 'd';
        } else {
            return 'D';
        }
    }
}