package cstjean.mobile.Joueur;

import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Damier.Damier;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe GestionTours.
 * Objectif : 100% de couverture des branches et lignes.
 */
public class TestGestionTours {

    /** Classe factice pour simuler FinPartie sans dépendance réelle. */
    private static class FakeFinPartie extends FinPartie {
        private boolean terminee;
        private boolean aucunDeplacement;
        private String gagnant = "Aucun";

        public FakeFinPartie(Damier d) { super(d); }

        @Override
        public boolean estTerminee() { return terminee; }

        @Override
        public String getGagnant() { return gagnant; }

        @Override
        public boolean aucunDeplacementPossible(Couleur c) { return aucunDeplacement; }

        public void setTerminee(boolean terminee) { this.terminee = terminee; }

        public void setAucunDeplacement(boolean aucunDeplacement) {
            this.aucunDeplacement = aucunDeplacement;
        }

        public void setGagnant(String gagnant) { this.gagnant = gagnant; }
    }

    /** Classe factice pour injecter un FinPartie personnalisé. */
    private static class GestionToursTestable extends GestionTours {
        public FakeFinPartie fakeFin;
        public GestionToursTestable(Damier d, FakeFinPartie f) {
            super(d);
            this.fakeFin = f;
            try {
                java.lang.reflect.Field finField = GestionTours.class.getDeclaredField("finPartie");
                finField.setAccessible(true);
                finField.set(this, f);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testConstructeurEtGetJoueurActuel() {
        Damier d = new Damier();
        GestionTours gt = new GestionTours(d);
        assertEquals("Le joueur initial doit être Blanc", Couleur.Blanc, gt.getJoueurActuel());
    }

    @Test
    public void testProchainTourAlternanceNormale() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        GestionToursTestable gt = new GestionToursTestable(d, f);

        // 1er appel : doit passer de Blanc à Noir
        gt.prochainTour();
        assertEquals(Couleur.Noir, gt.getJoueurActuel());

        // 2e appel : retour à Blanc
        gt.prochainTour();
        assertEquals(Couleur.Blanc, gt.getJoueurActuel());
    }

    @Test
    public void testProchainTourQuandPartieTerminee() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        f.setTerminee(true);
        f.setGagnant("Blanc");

        GestionToursTestable gt = new GestionToursTestable(d, f);

        // Comme la partie est terminée, le joueur ne change pas
        gt.prochainTour();
        assertEquals("Ne doit pas changer de joueur si partie terminée",
                Couleur.Blanc, gt.getJoueurActuel());
    }

    @Test
    public void testProchainTourAucunDeplacementPossible() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        f.setAucunDeplacement(true);
        f.setGagnant("Noir");

        GestionToursTestable gt = new GestionToursTestable(d, f);

        // Passe à Noir mais sans déplacement possible
        gt.prochainTour();
        assertEquals("Le tour doit quand même basculer avant de détecter aucun déplacement",
                Couleur.Noir, gt.getJoueurActuel());
    }

    @Test
    public void testReinitialiser() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        GestionToursTestable gt = new GestionToursTestable(d, f);

        // Change de tour puis réinitialise
        gt.prochainTour(); // -> Noir
        gt.reinitialiser();
        assertEquals(Couleur.Blanc, gt.getJoueurActuel());
    }

    @Test
    public void testPartieTerminee() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        GestionToursTestable gt = new GestionToursTestable(d, f);

        f.setTerminee(false);
        assertFalse(gt.partieTerminee());

        f.setTerminee(true);
        assertTrue(gt.partieTerminee());
    }
}
