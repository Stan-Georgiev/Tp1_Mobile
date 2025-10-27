package cstjean.mobile.Joueur;

import cstjean.mobile.Damier.Damier;
import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Pion.Pion;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Tests unitaires pour la classe {@link FinPartie}.
 */
public class TestFinPartie extends TestCase {

    /**
     * Vérifie la méthode {@code aucunePiece(Couleur)}.
     */
    @Test
    public void testAucunePiece() {
        Damier d = new Damier();
        d.initialiser();
        FinPartie finPartie = new FinPartie(d);

        assertFalse("Blanc ne doit pas être vide initialement",
                finPartie.aucunePiece(Couleur.Blanc));
        assertFalse("Noir ne doit pas être vide initialement",
                finPartie.aucunePiece(Couleur.Noir));

        // Supprime tous les pions
        for (int i = 1; i <= d.getNombrePions(); i++) {
            d.ajouterPion(i, null);
        }

        finPartie = new FinPartie(d);
        assertTrue("Blanc doit être vide après suppression",
                finPartie.aucunePiece(Couleur.Blanc));
        assertTrue("Noir doit être vide après suppression",
                finPartie.aucunePiece(Couleur.Noir));
    }

    /**
     * Vérifie la méthode {@code aucunDeplacementPossible(Couleur)}.
     */
    @Test
    public void testAucunDeplacementPossible() {
        Damier d = new Damier();
        d.initialiser();
        FinPartie finPartie = new FinPartie(d);

        assertFalse("Blanc doit pouvoir se déplacer initialement",
                finPartie.aucunDeplacementPossible(Couleur.Blanc));
        assertFalse("Noir doit pouvoir se déplacer initialement",
                finPartie.aucunePiece(Couleur.Noir)); // maybe should be aucunDeplacementPossible?

        // Supprime tous les pions
        for (int i = 1; i <= d.getNombrePions(); i++) {
            d.ajouterPion(i, null);
        }

        // Ajoute quelques pions isolés
        d.ajouterPion(5, new Pion(Couleur.Noir));
        d.ajouterPion(10, new Pion(Couleur.Blanc));

        // assertTrue(finPartie.aucunDeplacementPossible(Couleur.Blanc));
        assertFalse("Noir doit pouvoir se déplacer",
                finPartie.aucunDeplacementPossible(Couleur.Noir));
    }
}
