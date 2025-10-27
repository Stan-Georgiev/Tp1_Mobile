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
     * Vérifie la création correcte d'une dame blanche.
     * La couleur doit être {@link Couleur#Blanc} et
     * la représentation doit être le caractère 'd'.
     */
    @Test
    public void testDameCreationBlanc() {
        Dame dameBlanc = new Dame(Couleur.Blanc);

        // Vérifie la couleur
        assertEquals(Couleur.Blanc, dameBlanc.getCouleur());

        // Vérifie la représentation
        assertEquals('d', dameBlanc.getRepresentation());
    }

    /**
     * Vérifie la création correcte d'une dame noire.
     * La couleur doit être {@link Couleur#Noir} et
     * la représentation doit être le caractère 'D'.
     */
    @Test
    public void testDameCreationNoir() {
        Dame dameNoir = new Dame(Couleur.Noir);

        // Vérifie la couleur
        assertEquals(Couleur.Noir, dameNoir.getCouleur());

        // Vérifie la représentation
        assertEquals('D', dameNoir.getRepresentation());
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
