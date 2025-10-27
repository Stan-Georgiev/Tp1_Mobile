package cstjean.mobile.Damier;

import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Pion.Dame;
import cstjean.mobile.Pion.Pion;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Tests unitaires pour la classe {@link Damier}.
 */
public class TestDamier extends TestCase {

    /**
     * Teste la création d'un damier et l'ajout d'un pion.
     */
    public void testDamier() {
        Damier d = new Damier();
        assertEquals(50, d.getNombrePions());

        Pion pion1 = new Pion(Couleur.Noir);
        d.ajouterPion(38, pion1);

        Assert.assertEquals(Couleur.Noir, pion1.getCouleur());
        Assert.assertEquals(d.getPion(37), pion1);

        String affichage = AfficherDamier.generer(d);
        assertNotNull(affichage);
    }

    /**
     * Teste l'initialisation du damier avec 40 pions.
     */
    public void testInitialiser() {
        Damier d = new Damier();
        d.initialiser();

        int count = 0;
        for (int i = 0; i < d.getNombrePions(); i++) {
            if (d.getPion(i) != null) {
                count++;
            }
        }
        Assert.assertEquals(40, count);
    }

    /**
     * Teste l'affichage du damier.
     */
    public void testAffichage() {
        Damier d = new Damier();
        d.initialiser();

        String affichage = AfficherDamier.generer(d).trim();
        assertTrue("Doit contenir des pions noirs", affichage.contains("P"));
        assertTrue("Doit contenir des pions blancs", affichage.contains("p"));
    }

    /**
     * Teste la promotion d'un pion blanc en dame.
     */
    public void testCheckPromotionBlanc() {
        Damier d = new Damier();
        d.initialiser();

        d.ajouterPion(3, new Pion(Couleur.Blanc));
        d.checkPromotion(3);

        assertEquals(Dame.class, d.getPion(2).getClass());
        assertEquals(Couleur.Blanc, d.getPion(2).getCouleur());
    }

    /**
     * Teste la promotion d'un pion noir en dame.
     */
    public void testCheckPromotionNoir() {
        Damier d = new Damier();
        d.initialiser();

        d.ajouterPion(48, new Pion(Couleur.Noir));
        d.checkPromotion(48);

        assertEquals(Dame.class, d.getPion(47).getClass());
        assertEquals(Couleur.Noir, d.getPion(47).getCouleur());
    }

    /**
     * Teste l'historique et l'annulation du dernier déplacement.
     */
    public void testHistoriqueEtAnnulerDernierDeplacement() {
        Damier d = new Damier();
        Pion p = new Pion(Couleur.Noir);

        d.ajouterPion(10, p);
        assertFalse("L'historique ne doit pas être vide", d.getHistorique().isEmpty());

        d.annulerDernierDeplacement();
        assertNull(d.getPion(9));
    }

    /**
     * Teste les getters getPion() et getNombrePions().
     */
    public void testGetPionEtNombrePions() {
        Damier d = new Damier();
        assertEquals(50, d.getNombrePions());

        Pion p1 = new Pion(Couleur.Blanc);
        Pion p2 = new Pion(Couleur.Noir);

        d.ajouterPion(25, p1);
        d.ajouterPion(30, p2);

        assertEquals(p1, d.getPion(24));
        assertEquals(p2, d.getPion(29));
    }
}
