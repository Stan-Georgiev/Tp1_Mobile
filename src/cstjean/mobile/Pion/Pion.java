package cstjean.mobile.Pion;

/** Classe pour un pion d'un jeu de dames. */
public class Pion {
    /** Couleur du pion, essentiellement son équipe. */
    private Couleur couleur;

    /** Position, notée en notation Manoury, du pion sur le damier. */
    private int position;

    /** Position, notée en notation Manoury, du pion sur le damier. */
    private char rep;

    /** Constructeur de la classe Pion.

     * @param couleur couleur du pion.
     */
    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    /** Constructeur de la classe Pion ne prenant aucuns paramètres et donnant une couleur par défaut, soit blanc. */
    public Pion() {
        this.couleur = Couleur.Blanc;
    }

    public Couleur getCouleur() {
        return this.couleur;
    }

    /** Methode qui affiche les pion en correspondance avec leur couleur.
     *
     * @return cela retourne la representation d'un pion.
     */
    public char getRepresentation() {
        if (couleur == Couleur.Blanc) {
            rep = 'p';
        } else {
            rep = 'P';
        }
        return rep;
    }

}
