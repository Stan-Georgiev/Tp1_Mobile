package cstjean.mobile.pion;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Tests unitaires pour la classe {@link Pion}.
 *
 * <p>
 * Ces tests vérifient la création de pions et de dames
 * avec différentes couleurs et constructeurs.
 * </p>
 */
public class TestPion extends TestCase {

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
}
