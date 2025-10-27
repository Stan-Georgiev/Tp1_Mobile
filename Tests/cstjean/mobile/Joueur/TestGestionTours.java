package cstjean.mobile.Joueur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Damier.Damier;
import org.junit.Test;

/**
 * Tests unitaires pour la classe {@link GestionTours}.
 * Objectif : 100% de couverture des branches et lignes.
 */
public class TestGestionTours {

    /**
     * Classe factice pour simuler FinPartie sans dépendance réelle.
     */
    private static class FakeFinPartie extends FinPartie {

        private boolean terminee;
        private boolean aucunDeplacement;
        private String gagnant = "Aucun";

        public FakeFinPartie(Damier d) {
            super(d);
        }

        @Override
        public boolean estTerminee() {
            return terminee;
        }

        @Override
        public String getGagnant() {
            return gagnant;
        }

        @Override
        public boolean aucunDeplacementPossible(Couleur c) {
            return aucunDeplacement;
        }

        public void setTerminee(boolean terminee) {
            this.terminee = terminee;
        }

        public void setAucunDeplacement(boolean aucunDeplacement) {
            this.aucunDeplacement = aucunDeplacement;
        }

        public void setGagnant(String gagnant) {
            this.gagnant = gagnant;
        }
    }

    /**
     * Classe factice pour injecter un FinPartie personnalisé.
     */
    private static class GestionToursTestable extends GestionTours {

        public FakeFinPartie fakeFin;

        public GestionToursTestable(Damier d, FakeFinPartie f) {
            super(d);
            this.fakeFin = f;
            try {
                java.lang.reflect.Field finField =
                        GestionTours.class.getDeclaredField("finPartie");
                finField.setAccessible(true);
                finField.set(this, f);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /** Vérifie le constructeur et le joueur initial. */
    @Test
    public void testConstructeurEtGetJoueurActuel() {
        Damier d = new Damier();
        GestionTours gt = new GestionTours(d);
        assertEquals("Le joueur initial doit être Blanc",
                Couleur.Blanc, gt.getJoueurActuel());
    }

    /** Vérifie l’alternance normale des tours. */
    @Test
    public void testProchainTourAlternanceNormale() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        GestionToursTestable gt = new GestionToursTestable(d, f);

        gt.prochainTour(); // -> Noir
        assertEquals(Couleur.Noir, gt.getJoueurActuel());

        gt.prochainTour(); // -> Blanc
        assertEquals(Couleur.Blanc, gt.getJoueurActuel());
    }

    /** Vérifie qu’aucun changement de joueur si la partie est terminée. */
    @Test
    public void testProchainTourQuandPartieTerminee() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        f.setTerminee(true);
        f.setGagnant("Blanc");

        GestionToursTestable gt = new GestionToursTestable(d, f);
        gt.prochainTour();

        assertEquals("Ne doit pas changer de joueur si partie terminée",
                Couleur.Blanc, gt.getJoueurActuel());
    }

    /** Vérifie le comportement si aucun déplacement possible. */
    @Test
    public void testProchainTourAucunDeplacementPossible() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        f.setAucunDeplacement(true);
        f.setGagnant("Noir");

        GestionToursTestable gt = new GestionToursTestable(d, f);
        gt.prochainTour();

        assertEquals("Le tour doit quand même basculer avant de détecter aucun déplacement",
                Couleur.Noir, gt.getJoueurActuel());
    }

    /** Vérifie la réinitialisation du tour. */
    @Test
    public void testReinitialiser() {
        Damier d = new Damier();
        FakeFinPartie f = new FakeFinPartie(d);
        GestionToursTestable gt = new GestionToursTestable(d, f);

        gt.prochainTour(); // -> Noir
        gt.reinitialiser();

        assertEquals(Couleur.Blanc, gt.getJoueurActuel());
    }

    /** Vérifie la méthode {@code partieTerminee()}. */
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
