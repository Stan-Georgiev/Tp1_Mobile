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
    public void testBougerPion(){
        Object[][] damier = new Object[10][10];
        damier[5][3] = 'P';
        MouvementPion.Position pos = new MouvementPion.Position(6, 4, false);

        assertEquals('P', damier[5][3]);

        List<MouvementPion.Position> moves = MouvementPion.getDeplacementsPossibles(5, 3, true, damier);
        MouvementPion.bougerPion(moves, pos, damier, 6, 4);

        for (int i = 0; i < moves.size(); i++) {
            System.out.println(moves.contains(pos));
        }
        assertEquals(null, damier[5][3]);
        assertEquals('P', damier[pos.ligne][pos.colonne]);
    }
}
