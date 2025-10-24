package cstjean.mobile.Damier;

import junit.framework.TestCase;
public class TestAfficherDamier extends TestCase {
    AfficherDamier afficherDamier = new AfficherDamier();
    public void testGenerer() {
        Damier damier = new Damier();
        damier.initialiser();
        String damierStr = "";

        assertEquals(0, damierStr.length());
        damierStr = afficherDamier.generer(damier);
        //10 est soustrait à la longueur de damierStr pour ne pas prendre en compte les sauts de lignes
        //Le nombre de pions est multiplié par 2 puisque le damier contient 2 fois plus de cases que de pions
        assertEquals(damier.getNombrePions() * 2, damierStr.length() - 10);
    }
}
