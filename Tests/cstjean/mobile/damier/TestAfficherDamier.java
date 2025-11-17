package cstjean.mobile.damier;

import junit.framework.TestCase;

/**
 * Tests unitaires pour la classe {@link AfficherDamier}.
 */
public class TestAfficherDamier extends TestCase {

    /**
     * instaniation de affierDamier {@link AfficherDamier}.
     */
    private final AfficherDamier afficherDamier = new AfficherDamier();

    /**
     * Test la génération de la représentation textuelle du damier.
     */
    public void testGenerer() {
        Damier damier = new Damier();
        damier.initialiser();

        String damierStr = afficherDamier.generer(damier);

        // 10 est soustrait à la longueur pour ne pas prendre en compte les sauts de lignes
        // Le nombre de pions est multiplié par 2 puisque le damier contient 2 fois plus de cases que de pions
        assertEquals(damier.getNombrePions() * 2, damierStr.length() - 10);
    }
}
