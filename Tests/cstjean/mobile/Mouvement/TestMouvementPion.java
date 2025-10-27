package cstjean.mobile.Mouvement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

/**
 * Classe de tests unitaires pour la classe {@link MouvementPion}.
 * Vérifie les déplacements valides, invalides et les prises sur un damier 10x10.
 */
public class TestMouvementPion {

    /** Teste les déplacements simples pour un pion blanc. */
    @Test
    public void testDeplacementSimpleBlanc() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'p';

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(5, 3, true, damier);

        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 2));
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 4));
    }

    /** Teste les déplacements simples pour un pion noir. */
    @Test
    public void testDeplacementSimpleNoir() {
        Object[][] damier = new Object[10][10];
        damier[3][4] = 'P';

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(3, 4, false, damier);

        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 3));
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 5));
    }

    /** Vérifie qu'aucune prise n'est possible si la case arrière est occupée. */
    @Test
    public void testPasDePriseSiCaseArriereRemplie() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        damier[4][4] = 'p';
        damier[3][5] = 'P'; // destination occupée

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(5, 3, true, damier);

        assertFalse(moves.stream().anyMatch(p -> p.ligne == 3 && p.colonne == 5));
    }

    /** Vérifie qu'aucune prise n'est faite si la pièce adverse n'existe pas. */
    @Test
    public void testPasDePriseSiPasAdversaire() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        damier[4][4] = 'P'; // même couleur

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        assertTrue(moves.stream().noneMatch(p -> p.ligne == 3 && p.colonne == 5));
    }

    /** Vérifie qu'un pion ne peut pas sortir des limites du damier. */
    @Test
    public void testLimiteDuDamier() {
        Object[][] damier = new Object[10][10];
        damier[0][0] = 'P';

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(0, 0, true, damier);

        assertTrue(moves.isEmpty());
    }

    /** Teste la méthode {@code toString()} de la classe interne Position. */
    @Test
    public void testToStringPosition() {
        MouvementPion.Position p = new MouvementPion.Position(2, 5, false);
        assertEquals("(2,5)", p.toString());
    }

    /** Vérifie qu'un pion peut bouger correctement sur une case valide. */
    @Test
    public void testBougerPion() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

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

    /** Vérifie qu'un déplacement invalide ne modifie pas le damier. */
    @Test
    public void testBougerPionMouvementInvalide() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        MouvementPion.Position falseMove = new MouvementPion.Position(9, 9, false);
        MouvementPion.bougerPion(moves, falseMove, damier, 5, 3);

        assertEquals('P', damier[5][3]);
        assertNull(damier[9][9]);
    }

    /** Vérifie le déplacement avec une prise valide d'un adversaire. */
    @Test
    public void testBougerPionAvecPrise() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        damier[6][4] = 'p'; // adversaire
        damier[7][5] = null;

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

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

    /** Vérifie que le déplacement hors des bornes n'est pas possible. */
    @Test
    public void testAjoutMouvementOutOfBounds() {
        Object[][] damier = new Object[10][10];
        damier[9][9] = 'P'; // noir descend, direction = +1 -> ligne + direction = 10 (out of bounds)

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(9, 9, false, damier);

        assertTrue("Aucun mouvement valide attendu en dehors du damier", moves.isEmpty());
    }

    /** Vérifie la détection d'une prise possible lors du calcul des mouvements. */
    @Test
    public void testAjoutMouvementAvecPrisePossible() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';  // noir
        damier[6][4] = 'p';  // adversaire
        damier[7][5] = null; // case libre après

        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        assertTrue(moves.stream().anyMatch(p -> p.estPrise && p.ligne == 7 && p.colonne == 5));
    }
}
