package cstjean.mobile.Mouvement;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class TestMouvementPion {

    @Test
    public void testDeplacementSimpleBlanc() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'p';
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, true, damier);

        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 2));
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 4));
    }

    @Test
    public void testDeplacementSimpleNoir() {
        Object[][] damier = new Object[10][10];
        damier[3][4] = 'P';
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(3, 4, false, damier);

        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 3));
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 5));
    }

    @Test
    public void testPasDePriseSiCaseArriereRemplie() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        damier[4][4] = 'p';
        damier[3][5] = 'P'; // destination occupée

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, true, damier);
        assertFalse(moves.stream().anyMatch(p -> p.ligne == 3 && p.colonne == 5));
    }

    @Test
    public void testPasDePriseSiPasAdversaire() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        damier[4][4] = 'P'; // même couleur

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);
        assertTrue(moves.stream().noneMatch(p -> p.ligne == 3 && p.colonne == 5));
    }

    @Test
    public void testLimiteDuDamier() {
        Object[][] damier = new Object[10][10];
        damier[0][0] = 'P';
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(0, 0, true, damier);
        assertTrue(moves.isEmpty());
    }

    @Test
    public void testToStringPosition() {
        MouvementPion.Position p = new MouvementPion.Position(2, 5, false);
        assertEquals("(2,5)", p.toString());
    }

    @Test
    public void testBougerPion() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        MouvementPion.Position target = null;
        for (MouvementPion.Position m : moves) {
            if (m.ligne == 6 && m.colonne == 4) {
                target = m;
                break;
            }
        }
        assertNotNull(target);

        MouvementPion.bougerPion(moves, target, damier, 5, 3);

        assertNull(damier[5][3]);
        assertEquals('P', damier[6][4]);
    }

    @Test
    public void testBougerPionMouvementInvalide() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        MouvementPion.Position falseMove = new MouvementPion.Position(9, 9, false);
        MouvementPion.bougerPion(moves, falseMove, damier, 5, 3);

        assertEquals('P', damier[5][3]);
        assertNull(damier[9][9]);
    }

    @Test
    public void testBougerPionAvecPrise() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        damier[6][4] = 'p'; // adversaire
        damier[7][5] = null;

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);
        MouvementPion.Position prise = null;
        for (MouvementPion.Position m : moves) {
            if (m.ligne == 7 && m.colonne == 5) {
                prise = m;
                break;
            }
        }

        assertNotNull(prise);
        assertTrue(prise.estPrise);

        MouvementPion.bougerPion(moves, prise, damier, 5, 3);

        assertNull(damier[5][3]);
        assertNull(damier[6][4]);
        assertEquals('P', damier[7][5]);
    }

    /** ✅ NEW: couvre le cas où ajouterMouvement sort des bornes (ligne + direction dépasse la limite) */
    @Test
    public void testAjoutMouvementOutOfBounds() {
        Object[][] damier = new Object[10][10];
        damier[9][9] = 'P';
        // Noir descend, direction = +1 -> ligne + direction = 10, out of bounds
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(9, 9, false, damier);
        assertTrue("Aucun mouvement valide attendu en dehors du damier", moves.isEmpty());
    }

    /** ✅ NEW: couvre le cas else-if (case occupée et case d’atterrissage libre) */
    @Test
    public void testAjoutMouvementAvecPrisePossible() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';  // noir
        damier[6][4] = 'p';  // adversaire
        damier[7][5] = null; // case libre après
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        assertTrue(moves.stream().anyMatch(p -> p.estPrise && p.ligne == 7 && p.colonne == 5));
    }
}
