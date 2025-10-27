package cstjean.mobile.Mouvement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.ArrayList;
import org.junit.Test;

/**
 * Tests unitaires pour la classe {@link MouvementDame}.
 * Vérifie les déplacements de la dame, captures et mouvements invalides.
 */
public class TestMouvementDame {

    /** Vérifie la méthode {@code toString()} de la position. */
    @Test
    public void testToString() {
        MouvementDame.Position pos = new MouvementDame.Position(3, 4, false);
        assertEquals("(3,4)", pos.toString());
    }

    /** Teste les déplacements possibles autour d'une case vide. */
    @Test
    public void testDeplacementsPossibles_CaseVideAutour() {
        Object[][] damier = new Object[10][10];
        damier[5][5] = 'd';

        List<MouvementDame.Position> moves = MouvementDame.getDeplacementsPossibles(5, 5, damier);
        assertNotNull(moves);
        assertFalse(moves.isEmpty());

        // Vérifie au moins une diagonale valide
        boolean diag1 = moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 4);
        boolean diag2 = moves.stream().anyMatch(p -> p.ligne == 4 && p.colonne == 6);
        boolean diag3 = moves.stream().anyMatch(p -> p.ligne == 6 && p.colonne == 4);
        boolean diag4 = moves.stream().anyMatch(p -> p.ligne == 6 && p.colonne == 6);
        assertTrue(diag1 || diag2 || diag3 || diag4);
    }

    /** Vérifie un déplacement avec capture possible. */
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

    /** Teste la branche else-if pour couvrir le flux alternatif. */
    @Test
    public void testDeplacementsPossibles_BranchesElseIf() {
        Object[][] damier = new Object[10][10];
        damier[7][2] = 'd'; // position pour forcer else-if branch

        List<MouvementDame.Position> moves = MouvementDame.getDeplacementsPossibles(7, 2, damier);
        assertNotNull(moves);
        assertTrue(moves.size() > 0);
    }

    /** Vérifie le déplacement lorsqu'une dame est dans un coin. */
    @Test
    public void testDeplacementsPossibles_AuCoin() {
        Object[][] damier = new Object[10][10];
        damier[0][0] = 'd';

        List<MouvementDame.Position> moves = MouvementDame.getDeplacementsPossibles(0, 0, damier);
        assertNotNull(moves);

        boolean possible = moves.stream().anyMatch(p -> p.ligne == 1 && p.colonne == 1);
        assertTrue(possible);
    }

    /** Vérifie un déplacement simple d'une dame sur une case libre. */
    @Test
    public void testBougerDame_DeplacementSimple() {
        Object[][] damier = new Object[10][10];
        damier[5][5] = 'd';
        MouvementPion.Position pos = new MouvementPion.Position(6, 6, false);

        MouvementDame.bougerDame(List.of(pos), pos, damier, 5, 5);

        assertNull(damier[5][5]);
        assertEquals('d', damier[6][6]);
    }

    /** Vérifie un déplacement avec capture d'une pièce adverse. */
    @Test
    public void testBougerDame_PriseAdversaire() {
        Object[][] damier = new Object[10][10];
        damier[5][5] = 'd';
        damier[4][4] = 'D';
        damier[3][3] = null;

        MouvementPion.Position pos = new MouvementPion.Position(3, 3, true);
        MouvementDame.bougerDame(List.of(pos), pos, damier, 5, 5);

        assertNull(damier[5][5]);
        assertNull(damier[4][4]);
        assertEquals('d', damier[3][3]);
    }

    /** Vérifie qu'un déplacement invalide ne modifie pas le damier. */
    @Test
    public void testBougerDame_DeplacementInvalide() {
        Object[][] damier = new Object[10][10];
        damier[5][5] = 'd';

        MouvementPion.Position pos = new MouvementPion.Position(8, 8, false);
        MouvementDame.bougerDame(List.of(), pos, damier, 5, 5);

        assertEquals('d', damier[5][5]);
        assertNull(damier[8][8]);
    }

    /** Vérifie l'ajout de mouvements pour une case vide et avec capture. */
    @Test
    public void testAjouterMouvement_CaseVideEtAvecPrise() {
        Object[][] damier = new Object[10][10];
        List<MouvementDame.Position> moves = new ArrayList<>();

        // Case vide
        callAjouterMouvement(moves, damier, 5, 5, 10, 1, 1);
        assertFalse(moves.isEmpty());
        assertFalse(moves.get(0).estPrise);

        // Case avec pion + case suivante vide
        damier[4][4] = 'D';
        callAjouterMouvement(moves, damier, 4, 4, 10, 1, 1);
        assertTrue(moves.stream().anyMatch(p -> p.estPrise));
    }

    /** Vérifie qu'un mouvement hors du damier n'est pas ajouté. */
    @Test
    public void testAjouterMouvement_OutOfBounds() {
        Object[][] damier = new Object[10][10];
        List<MouvementDame.Position> moves = new ArrayList<>();

        callAjouterMouvement(moves, damier, -1, -1, 10, 1, 1);
        assertTrue("Aucun mouvement ne doit être ajouté", moves.isEmpty());
    }

    /**
     * Appel réfléchi de la méthode privée {@code ajouterMouvement}.
     *
     * @param moves liste des positions
     * @param damier le damier
     * @param l ligne
     * @param c colonne
     * @param taille taille du damier
     * @param cote côté
     * @param direction direction
     */
    private static void callAjouterMouvement(
            List<MouvementDame.Position> moves, Object[][] damier,
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
