package cstjean.mobile.Mouvement;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class TestMouvementDame {

    @Test
    public void testToString() {
        MouvementDame.Position pos = new MouvementDame.Position(3, 4, false);
        assertEquals("(3,4)", pos.toString());
    }

    @Test
    public void testDeplacementsPossibles_CaseVideAutour() {
        Object[][] damier = new Object[10][10];
        damier[5][5] = 'd';

        List<MouvementDame.Position> moves = MouvementDame.getDeplacementsPossibles(5, 5, damier);
        assertNotNull(moves);
        assertFalse(moves.isEmpty());

        // Vérifie diagonales
        boolean diag1 = moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 4);
        boolean diag2 = moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 6);
        boolean diag3 = moves.stream().anyMatch(p -> p.ligne == 6 && p.colonne == 4);
        boolean diag4 = moves.stream().anyMatch(p -> p.ligne == 6 && p.colonne == 6);
        assertTrue(diag1 || diag2 || diag3 || diag4);
    }

    @Test
    public void testDeplacementsPossibles_AvecPrisePossible() {
        Object[][] damier = new Object[10][10];
        damier[4][4] = 'd';
        damier[3][3] = 'D';
        damier[2][2] = null;

        List<MouvementDame.Position> moves = MouvementDame.getDeplacementsPossibles(4, 4, damier);
        boolean prise = moves.stream().anyMatch(p -> p.estPrise && p.ligne == 2 && p.colonne == 2);
        assertTrue("Une prise devrait être possible", prise);
    }

    @Test
    public void testDeplacementsPossibles_BranchesElseIf() {
        Object[][] damier = new Object[10][10];
        damier[7][2] = 'd'; // position pour forcer else-if branch

        List<MouvementDame.Position> moves = MouvementDame.getDeplacementsPossibles(7, 2, damier);
        assertNotNull(moves);
        // On ne vérifie pas le nombre, seulement que la branche s'exécute sans erreur
        assertTrue(moves.size() > 0);
    }

    @Test
    public void testDeplacementsPossibles_AuCoin() {
        Object[][] damier = new Object[10][10];
        damier[0][0] = 'd';

        List<MouvementDame.Position> moves = MouvementDame.getDeplacementsPossibles(0, 0, damier);
        assertNotNull(moves);

        // en (0,0), seule (1,1) est valide
        boolean possible = moves.stream().anyMatch(p -> p.ligne == 1 && p.colonne == 1);
        assertTrue(possible);
    }

    @Test
    public void testBougerDame_DeplacementSimple() {
        Object[][] damier = new Object[10][10];
        damier[5][5] = 'd';
        MouvementPion.Position pos = new MouvementPion.Position(6, 6, false);

        MouvementDame.bougerDame(List.of(pos), pos, damier, 5, 5);

        assertNull(damier[5][5]);
        assertEquals('d', damier[6][6]);
    }

    @Test
    public void testBougerDame_PriseAdversaire() {
        Object[][] damier = new Object[10][10];
        damier[5][5] = 'd';
        damier[4][4] = 'D';
        damier[3][3] = null;

        MouvementPion.Position pos = new MouvementPion.Position(3, 3, true);
        MouvementDame.bougerDame(List.of(pos), pos, damier, 5, 5);

        assertNull("Origine doit être vide", damier[5][5]);
        assertNull("Pion adverse doit être capturé", damier[4][4]);
        assertEquals('d', damier[3][3]);
    }

    @Test
    public void testBougerDame_DeplacementInvalide() {
        Object[][] damier = new Object[10][10];
        damier[5][5] = 'd';

        // pos pas dans moves → ne bouge pas
        MouvementPion.Position pos = new MouvementPion.Position(8, 8, false);
        MouvementDame.bougerDame(List.of(), pos, damier, 5, 5);

        assertEquals('d', damier[5][5]);
        assertNull(damier[8][8]);
    }

    @Test
    public void testAjouterMouvement_CaseVideEtAvecPrise() {
        Object[][] damier = new Object[10][10];
        List<MouvementDame.Position> moves = new java.util.ArrayList<>();

        // Case vide
        callAjouterMouvement(moves, damier, 5, 5, 10, 1, 1);
        assertFalse(moves.isEmpty());
        assertFalse(moves.get(0).estPrise);

        // Case avec pion + case suivante vide
        damier[4][4] = 'D';
        callAjouterMouvement(moves, damier, 4, 4, 10, 1, 1);
        assertTrue(moves.stream().anyMatch(p -> p.estPrise));
    }

    @Test
    public void testAjouterMouvement_OutOfBounds() {
        Object[][] damier = new Object[10][10];
        List<MouvementDame.Position> moves = new java.util.ArrayList<>();

        // Case en dehors du damier, rien ne doit être ajouté
        callAjouterMouvement(moves, damier, -1, -1, 10, 1, 1);
        assertTrue("Aucun mouvement ne doit être ajouté", moves.isEmpty());
    }

    private static void callAjouterMouvement(List<MouvementDame.Position> moves, Object[][] damier,
                                             int l, int c, int taille, int cote, int direction) {
        try {
            var method = MouvementDame.class.getDeclaredMethod(
                    "ajouterMouvement", List.class, Object[][].class,
                    int.class, int.class, int.class, int.class, int.class);
            method.setAccessible(true);
            method.invoke(null, moves, damier, l, c, taille, cote, direction);
        } catch (Exception e) {
            fail("Erreur appel ajouterMouvement : " + e.getMessage());
        }
    }
}
