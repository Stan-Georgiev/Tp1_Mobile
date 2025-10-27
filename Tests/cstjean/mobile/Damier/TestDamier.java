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
     * Test la création d'un damier et l'ajout d'un pion.
     */
    public void testDamier() {
        Damier d = new Damier();

        assertEquals(50, d.getNombrePions());

        Pion pion1 = new Pion(Couleur.Noir);
        d.ajouterPion(38, pion1);

        Assert.assertEquals(Couleur.Noir, pion1.getCouleur());
        Assert.assertEquals(d.getPion(37), pion1);

        String affichage = AfficherDamier.generer(d);
        System.out.println(affichage);
        System.out.println();
    }

    /**
     * Test l'initialisation du damier avec 40 pions.
     */
    public void testInitialiser() {
        Damier d = new Damier();
        d.initialiser();

        // Vérifie qu'il y a bien 40 pions
        int count = 0;
        for (int i = 0; i < d.getNombrePions(); i++) {
            if (d.getPion(i) != null) {
                count++;
            }
        }
        Assert.assertEquals(40, count);
    }

    /**
     * Test l'affichage du damier.
     */
    public void testAffichage() {
        Damier d = new Damier();
        d.initialiser();

        String affichage = AfficherDamier.generer(d).trim();
        assertTrue("Doit contenir des pions noirs", affichage.contains("P"));
        assertTrue("Doit contenir des pions blancs", affichage.contains("p"));

        System.out.println(affichage);
        System.out.println();
    }

    /**
     * Test la promotion d'un pion en dame.
     */
    public void testCheckPromotion() {
        Damier d = new Damier();
        d.initialiser();

        d.ajouterPion(3, new Pion(Couleur.Blanc));
        String affichage = AfficherDamier.generer(d).trim();
        System.out.println(affichage);
        System.out.println();

        // Vérifie que la promotion fonctionne
        d.checkPromotion(3);
        assertEquals("Le pion doit être promu en dame",
                Dame.class, d.getPion(2).getClass());
    }
}
