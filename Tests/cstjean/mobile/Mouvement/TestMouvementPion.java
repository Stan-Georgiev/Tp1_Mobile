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

        // Les blancs montent, donc doivent pouvoir aller (4,2) et (4,4)
        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 2));
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 4));
    }

    @Test
    public void testDeplacementSimpleNoir() {
        Object[][] damier = new Object[10][10];
        damier[3][4] = 'P';

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(3, 4, false, damier);

        // Les noirs descendent, donc doivent pouvoir aller (4,3) et (4,5)
        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 3));
        assertTrue(moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 5));
    }

    @Test
    public void testPasDePriseSiCaseArriereRemplie() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        damier[4][4] = 'p';
        damier[3][5] = 'P'; // case d'atterrissage occupée

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, true, damier);

        // Aucune prise valide vers (3,5)
        assertFalse(moves.stream().anyMatch(p -> p.ligne == 3 && p.colonne == 5));
    }

    @Test
    public void testPasDePriseSiPasAdversaire() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        damier[4][4] = 'P'; // même couleur

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);
        // Aucune prise ne doit être détectée
        assertTrue(moves.stream().noneMatch(p -> p.ligne == 3 && p.colonne == 5));
    }

    @Test
    public void testLimiteDuDamier() {
        Object[][] damier = new Object[10][10];
        damier[0][0] = 'P';

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(0, 0, true, damier);

        // Aucun déplacement possible (bord supérieur)
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

        // Get possible moves for the pion
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        // Find the move that goes to (6, 4) instead of creating a new Position
        MouvementPion.Position target = null;
        for (MouvementPion.Position m : moves) {
            if (m.ligne == 6 && m.colonne == 4) {
                target = m;
                break;
            }
        }

        // Make sure the move exists
        assertNotNull("Expected move (6,4) not found", target);

        // Perform the move
        MouvementPion.bougerPion(moves, target, damier, 5, 3);

        // Debug output
        System.out.println("Moves: " + moves);
        System.out.println("Selected: " + target);
        System.out.println("After move: damier[5][3]=" + damier[5][3] + " damier[6][4]=" + damier[6][4]);

        // Verify the pion moved
        assertNull("Old position should be empty", damier[5][3]);
        assertEquals("Pion should move to (6,4)", 'P', damier[6][4]);
    }

    @Test
    public void testBougerPionMouvementInvalide() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        // Crée une position qui n’est PAS dans la liste des mouvements
        MouvementPion.Position falseMove = new MouvementPion.Position(9, 9, false);

        // Appel de bougerPion avec un mouvement invalide — ne doit rien changer
        MouvementPion.bougerPion(moves, falseMove, damier, 5, 3);

        // Rien n’a bougé
        assertEquals('P', damier[5][3]);
        assertNull(damier[9][9]);
    }

    @Test
    public void testBougerPionAvecPrise() {
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P'; // noir
        damier[6][4] = 'p'; // blanc (adversaire à capturer)

        // le noir descend, donc direction = +1
        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, false, damier);

        // Chercher la position de prise (7,5)
        MouvementPion.Position prise = null;
        for (MouvementPion.Position m : moves) {
            if (m.ligne == 7 && m.colonne == 5) {
                prise = m;
                break;
            }
        }

        assertNotNull("Aucune position de prise trouvée", prise);
        assertTrue("La position doit être une prise", prise.estPrise);

        MouvementPion.bougerPion(moves, prise, damier, 5, 3);

        // Après la prise : le pion noir doit être à (7,5),
        // l'adversaire (6,4) doit être supprimé, et (5,3) vide.
        assertNull(damier[5][3]);
        assertNull(damier[6][4]);
        assertEquals('P', damier[7][5]);
    }
}
