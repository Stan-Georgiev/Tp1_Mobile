package cstjean.mobile.Joueur;

import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Joueur.FinPartie;
import cstjean.mobile.Damier.Damier;

/**
 * Classe responsable de la gestion des tours de jeu.
 * Elle alterne les joueurs et vérifie si la partie est terminée après chaque tour.
 */
public class GestionTours {

    private Couleur joueurActuel;
    private final Damier damier;
    private final FinPartie finPartie;

    /**
     * Constructeur de la classe GestionTours.
     * @param damier Le damier du jeu.
     */
    public GestionTours(Damier damier) {
        this.damier = damier;
        this.finPartie = new FinPartie(damier);
        this.joueurActuel = Couleur.Blanc; // Par défaut, les blancs commencent.
    }

    /**
     * Retourne le joueur dont c’est le tour.
     * @return La couleur du joueur actuel.
     */
    public Couleur getJoueurActuel() {
        return joueurActuel;
    }

    /**
     * Passe au joueur suivant.
     * Si la partie est terminée, aucun changement de joueur n’est effectué.
     */
    public void prochainTour() {
        if (finPartie.estTerminee()) {
            System.out.println("La partie est terminée. Gagnant : " + finPartie.getGagnant());
            return;
        }

        joueurActuel = (joueurActuel == Couleur.Blanc) ? Couleur.Noir : Couleur.Blanc;

        // Vérifie si le nouveau joueur peut jouer
        if (finPartie.aucunDeplacementPossible(joueurActuel)) {
            System.out.println("Le joueur " + joueurActuel + " ne peut plus jouer !");
            System.out.println("Partie terminée. Gagnant : " + finPartie.getGagnant());
        } else {
            System.out.println("C’est au tour de " + joueurActuel + ".");
        }
    }

    /**
     * Réinitialise la gestion des tours.
     * Utile si on recommence une partie.
     */
    public void reinitialiser() {
        joueurActuel = Couleur.Blanc;
    }

    /**
     * Vérifie si la partie est terminée.
     * @return true si la partie est terminée, sinon false.
     */
    public boolean partieTerminee() {
        return finPartie.estTerminee();
    }
}
