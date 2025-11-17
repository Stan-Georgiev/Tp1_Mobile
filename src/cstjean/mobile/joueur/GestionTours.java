package cstjean.mobile.joueur;

import cstjean.mobile.damier.Damier;
import cstjean.mobile.pion.Couleur;

/**
 * Classe responsable de la gestion des tours de jeu.
 *
 * <p>
 * Elle alterne les joueurs et vérifie si la partie est terminée
 * après chaque tour.
 * </p>
 */
public class GestionTours {

    /** Couleur du joueur actuel. */
    private Couleur joueurActuel;

    /** Référence vers le damier de la partie. */
    private final Damier damier;

    /** Objet responsable de la détection de fin de partie. */
    private final FinPartie finPartie;

    /**
     * Constructeur de la classe GestionTours.
     *
     * @param damier le damier du jeu
     */
    public GestionTours(Damier damier) {
        this.damier = damier;
        this.finPartie = new FinPartie(damier);
        this.joueurActuel = Couleur.Blanc; // Par défaut, les blancs commencent.
    }

    /**
     * Retourne la couleur du joueur dont c’est le tour.
     *
     * @return la couleur du joueur actuel
     */
    public Couleur getJoueurActuel() {
        return joueurActuel;
    }

    /**
     * Passe au joueur suivant.
     *
     * <p>
     * Si la partie est terminée, aucun changement de joueur n’est effectué.
     * </p>
     */
    public void prochainTour() {
        if (finPartie.estTerminee()) {
            System.out.println(
                    "La partie est terminée. Gagnant : " + finPartie.getGagnant());
            return;
        }

        joueurActuel = (joueurActuel == Couleur.Blanc) ? Couleur.Noir
                : Couleur.Blanc;

        // Vérifie si le nouveau joueur peut jouer
        if (finPartie.aucunDeplacementPossible(joueurActuel)) {
            System.out.println(
                    "Le joueur " + joueurActuel + " ne peut plus jouer !");
            System.out.println(
                    "Partie terminée. Gagnant : " + finPartie.getGagnant());
        } else {
            System.out.println("C’est au tour de " + joueurActuel + ".");
        }
    }

    /**
     * Réinitialise la gestion des tours.
     *
     * <p>
     * Utile lorsqu’une nouvelle partie commence.
     * </p>
     */
    public void reinitialiser() {
        joueurActuel = Couleur.Blanc;
    }

    /**
     * Vérifie si la partie est terminée.
     *
     * @return {@code true} si la partie est terminée, sinon {@code false}
     */
    public boolean partieTerminee() {
        return finPartie.estTerminee();
    }
}
