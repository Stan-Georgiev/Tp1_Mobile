package cstjean.mobile.Damier;

import cstjean.mobile.Pion.Pion;
import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Pion.Dame;

import java.util.LinkedList;
import java.util.Stack;

/** Classe pour le damier d'un jeu de Dames avec historique de déplacements. */
public class Damier {

    /** Liste contenant tous les pions présentement en jeu. */
    private final LinkedList<Pion> pions = new LinkedList<>();

    /** Historique des états précédents du damier. */
    private final Stack<LinkedList<Pion>> historique = new Stack<>();

    /** Constructeur de la classe Damier. */
    public Damier() {
        initListe();
    }

    /** Initialise la linked list en ajoutant 50 index avec valeur nulle. */
    public void initListe() {
        for (int i = 0; i < 50; i++) {
            pions.add(null);
        }
        historique.clear(); // vide l'historique
    }

    /**
     * Ajoute un pion à la position donnée en notation Manoury.
     * Sauvegarde l'état actuel avant modification pour l'historique.
     *
     * @param position position en notation Manoury (1 à 50)
     * @param pion     pion à ajouter
     */
    public void ajouterPion(int position, Pion pion) {
        sauvegarderHistorique();
        pions.set(position - 1, pion);
        checkPromotion(position);
    }

    /** Sauvegarde l'état actuel des pions dans l'historique. */
    private void sauvegarderHistorique() {
        LinkedList<Pion> copie = new LinkedList<>();
        for (Pion p : pions) {
            copie.add(p); // shallow copy, suffisant si on ne modifie pas les objets eux-mêmes
        }
        historique.push(copie);
    }

    /**
     * Reviens à l'état précédent du damier si disponible.
     */
    public void annulerDernierDeplacement() {
        if (!historique.isEmpty()) {
            LinkedList<Pion> etatPrecedent = historique.pop();
            pions.clear();
            pions.addAll(etatPrecedent);
        }
    }

    /** Retourne le pion à l'index donné (0 à 49). */
    public Pion getPion(int index) {
        return pions.get(index);
    }

    /** Retourne le nombre de pions dans la liste. */
    public int getNombrePions() {
        return pions.size();
    }

    /** Initialise le damier avec 4 rangées de pions pour chaque couleur. */
    public void initialiser() {
        for (int pos = 1; pos <= 20; pos++) {
            ajouterPion(pos, new Pion(Couleur.Noir));
        }
        for (int pos = 21; pos <= 30; pos++) {
            pions.set(pos - 1, null);
        }
        for (int pos = 31; pos <= 50; pos++) {
            ajouterPion(pos, new Pion(Couleur.Blanc));
        }
    }

    /**
     * Vérifie la promotion d'un pion à la dame si nécessaire.
     *
     * @param position position du pion à vérifier
     */
    public void checkPromotion(int position) {
        Pion p = pions.get(position - 1);
        if (p == null) return;

        if (p.getCouleur() == Couleur.Blanc && position <= 5) {
            pions.set(position - 1, new Dame(Couleur.Blanc));
        }

        if (p.getCouleur() == Couleur.Noir && position >= 46) {
            pions.set(position - 1, new Dame(Couleur.Noir));
        }
    }

    /** Retourne une copie de l’historique (utile pour debug ou tests). */
    public Stack<LinkedList<Pion>> getHistorique() {
        return historique;
    }
}
