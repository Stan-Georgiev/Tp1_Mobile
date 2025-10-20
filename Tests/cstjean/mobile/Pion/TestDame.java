package cstjean.mobile.Pion;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestDame {

    @Test
    public void testDameCreationBlanc() {
        Dame dameBlanc = new Dame(Couleur.Blanc);

        // Check color
        assertEquals(Couleur.Blanc, dameBlanc.getCouleur());

        // Check representation
        assertEquals('d', dameBlanc.getRepresentation());
    }

    @Test
    public void testDameCreationNoir() {
        Dame dameNoir = new Dame(Couleur.Noir);

        // Check color
        assertEquals(Couleur.Noir, dameNoir.getCouleur());

        // Check representation
        assertEquals('D', dameNoir.getRepresentation());
    }

    @Test
    public void testPromotionFromPion() {
        Pion p = new Pion(Couleur.Blanc);

        // Promote by creating a Dame
        Dame promoted = new Dame(p.getCouleur());

        // Ensure it's a Dame and retains the color
        assertEquals(Couleur.Blanc, promoted.getCouleur());
        assertEquals('d', promoted.getRepresentation());
    }
}
