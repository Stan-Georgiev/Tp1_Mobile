package cstjean.mobile.Pion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Classe de tests unitaires pour la classe {@link Dame}.
 * <p>
 * Ces tests vérifient la création correcte des dames (blanches et noires)
 * ainsi que leur promotion à partir d’un pion.
 * </p>
 */
public class TestDame {

    /**
     * Vérifie la création d’une dame avec une couleur spécifiée.
     * La dame noir doit être représenté par 'D' et le blanc par 'd'.
     */
    @Test
    public void testCreerAvecCouleur() {
        Dame dameNoire = new Dame(Couleur.Noir);
        Dame dameBlanche = new Dame(Couleur.Blanc);

        assertEquals(Couleur.Noir, dameNoire.getCouleur());
        assertEquals('D', dameNoire.getRepresentation());

        assertEquals(Couleur.Blanc, dameBlanche.getCouleur());
        assertEquals('d', dameBlanche.getRepresentation());
    }

    /**
     * Vérifie la promotion d’un pion blanc en dame.
     * La dame résultante doit conserver la même couleur
     * et avoir la bonne représentation.
     */
    @Test
    public void testPromotionFromPion() {
        Pion p = new Pion(Couleur.Blanc);

        // Promotion en créant une nouvelle Dame
        Dame promoted = new Dame(p.getCouleur());

        // Vérifie la couleur et la représentation
        assertEquals(Couleur.Blanc, promoted.getCouleur());
        assertEquals('d', promoted.getRepresentation());
    }
}
