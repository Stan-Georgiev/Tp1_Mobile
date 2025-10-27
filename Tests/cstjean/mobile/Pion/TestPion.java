package cstjean.mobile.Pion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests unitaires pour la classe {@link Pion}.
 * <p>
 * Ces tests vérifient la création de pions et de dames
 * avec différentes couleurs et constructeurs.
 * </p>
 */
public class TestPion {

    /**
     * Vérifie la création d’un pion avec une couleur spécifiée.
     * Le pion noir doit être représenté par 'P' et le blanc par 'p'.
     */
    @Test
    public void testCreerAvecCouleur() {
        Pion pionNoir = new Pion(Couleur.Noir);
        Pion pionBlanc = new Pion(Couleur.Blanc);

        assertEquals(Couleur.Noir, pionNoir.getCouleur());
        assertEquals('P', pionNoir.getRepresentation());

        assertEquals(Couleur.Blanc, pionBlanc.getCouleur());
        assertEquals('p', pionBlanc.getRepresentation());
    }

    /**
     * Vérifie la création d’un pion sans paramètre.
     * Par défaut, un pion doit être blanc et représenté par 'p'.
     */
    @Test
    public void testCreerSansParametre() {
        Pion pion = new Pion(); // Constructeur par défaut

        assertEquals(Couleur.Blanc, pion.getCouleur());
        assertEquals('p', pion.getRepresentation());
    }

    /**
     * Vérifie la création correcte d’une dame blanche.
     * La représentation doit être le caractère 'd'.
     */
    @Test
    public void testDameBlanche() {
        Dame dameBlanche = new Dame(Couleur.Blanc);

        assertEquals(Couleur.Blanc, dameBlanche.getCouleur());
        assertEquals('d', dameBlanche.getRepresentation());
    }

    /**
     * Vérifie la création correcte d’une dame noire.
     * La représentation doit être le caractère 'D'.
     */
    @Test
    public void testDameNoire() {
        Dame dameNoire = new Dame(Couleur.Noir);

        assertEquals(Couleur.Noir, dameNoire.getCouleur());
        assertEquals('D', dameNoire.getRepresentation());
    }
}
