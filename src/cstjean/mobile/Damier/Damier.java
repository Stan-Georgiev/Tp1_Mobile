package cstjean.mobile.Damier;

import cstjean.mobile.Pion.Pion;
import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Pion.Dame;
import java.util.LinkedList;

/** Classe pour le damier d'un jeu de Dames. */
public class Damier {
    /** Liste contenant tous les pions présentement en jeu. */
    private final LinkedList<Pion> pions = new LinkedList<>();

    /** Constructeur de la classe Damier. */
    public Damier() {
        initListe();
    }

    /** Initialise la linked list en ajoutant 50 index ayant une valeur nulle. */
    public void initListe() {
        for (int i = 0; i < 50; i++) {
            pions.add(null);
        }
    }

    /** Ajoute un pion à la position donnée en notation Manoury dans la liste de pion.

     * @param position position en notation Manoury du pion à ajouter.
     *
     * @param pion pion à ajouter.
     */
    public void ajouterPion(int position, Pion pion) {
        pions.set(position - 1, pion);
        checkPromotion(position); // automatically promote if needed
    }

    /** Trouve le pion a l'index specifier dans la liste de pion.
     *
     * @param index position en notation Manoury du pion à ajouter.
     * @return sa retourne le pion a l'index specifier
     **/
    public Pion getPion(int index) {
        return pions.get(index);
    }

    /** Retourne le nombre de pions.
     *
     * @return sa retourne le nombre de pions dans la liste
     **/
    public int getNombrePions() {
        return pions.size();
    }

    /** Initialise le damier avec 4 rangées de pions pour chaque couleur. */
    public void initialiser() {
        // Pions noirs sur les rangées 1 à 4
        for (int pos = 1; pos <= 20; pos++) {
            ajouterPion(pos, new Pion(Couleur.Noir));
        }

        // Rangées 5 et 6 (21 à 30) → vides
        for (int pos = 21; pos <= 30; pos++) {
            pions.set(pos - 1, null);
        }

        // Pions blancs sur les rangées 7 à 10
        for (int pos = 31; pos <= 50; pos++) {
            ajouterPion(pos, new Pion(Couleur.Blanc));
        }
    }

    private void checkPromotion(int position) {
        Pion p = pions.get(position - 1);
        if (p == null) return;

        // Blanc atteint la première ligne (positions 1–5)
        if (p.getCouleur() == Couleur.Blanc && position <= 5) {
            pions.set(position - 1, new Dame(Couleur.Blanc));
        }

        // Noir atteint la dernière ligne (positions 46–50)
        if (p.getCouleur() == Couleur.Noir && position >= 46) {
            pions.set(position - 1, new Dame(Couleur.Noir));
        }
    }

}
