package cstjean.mobile.Damier;

import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Pion.Dame;
import cstjean.mobile.Pion.Pion;
import junit.framework.TestCase;
import org.junit.Assert;

/** La classe de tests qui verifie le fonctionnement de la classe Damier. **/
public class TestDamier extends TestCase {
    /** Un test qui cree un damier et qui le remplie avec 1 pion. **/
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

    /** Un test qui verifie si le damier est initialiser avec 40 pions. **/
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

    /** Un test qui verifie l'affichage du damier. **/
    public void testAffichage() {
        Damier d = new Damier();
        d.initialiser();

        String affichage = AfficherDamier.generer(d).trim();
        assertTrue(affichage.contains("P")); // Contient des noirs
        assertTrue(affichage.contains("p")); // Contient des blancs

        System.out.println(affichage);
        System.out.println();
    }
    public void testCheckPromotion(){
        Damier d = new Damier();
        d.initialiser();


        d.ajouterPion(3, new Pion(Couleur.Blanc));
        String affichage = AfficherDamier.generer(d).trim();
        System.out.println(affichage);
        System.out.println();

        //Ce assert equals ne peut pas présentement fonctionner
        d.checkPromotion(3);
        assertEquals(Dame.class, d.getPion(2).getClass());
    }
}
