package cstjean.mobile.damier;

import cstjean.mobile.pion.Couleur;
import cstjean.mobile.pion.Dame;
import cstjean.mobile.pion.Pion;
import java.util.LinkedList;
import java.util.List;
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

    /** Un test qui verifie la promotion d'une piece. **/
    public void testCheckPromotion() {
        Damier d = new Damier();
        d.initialiser();

        // Vider complètement le damier
        for (int i = 1; i <= d.getNombrePions(); i++) {
            d.ajouterPion(i, null);
        }

        // Ajouter un pion blanc à une case avant la ligne de promotion
        int positionDepart = 8; // ligne juste avant promotion
        d.ajouterPion(positionDepart, new Pion(Couleur.Blanc));

        // Vérifier qu'il s'agit bien d'un pion avant le déplacement
        assertEquals(Pion.class, d.getPion(positionDepart - 1).getClass());

        // Simuler le déplacement du pion vers la ligne de promotion (position 3)
        Pion pion = d.getPion(positionDepart - 1);
        d.ajouterPion(positionDepart, null); // libère l'ancienne case
        d.ajouterPion(3, pion); // déplace le pion

        // Vérifier la promotion automatique
        d.checkPromotion(3);

        // Confirmer qu'il a bien été promu en Dame
        assertEquals(Dame.class, d.getPion(2).getClass());
    }

    /** Un test qui verifie l'historique. **/
    public void testHistorique() {
        Damier d = new Damier();
        d.initialiser();
        for (int i = 0; i < d.getNombrePions(); i++) {
            d.ajouterPion(i + 1, null);
        }
        List<LinkedList<Pion>> historique = d.getHistorique();
        // -90 pour ignorer les actions de set le board up et d'enlever tous les pions
        assertEquals(0, historique.size() - 90);

        d.ajouterPion(8, new Pion(Couleur.Blanc));
        d.ajouterPion(22, new Pion(Couleur.Noir));
        d.ajouterPion(44, new Pion(Couleur.Noir));

        assertEquals(3, historique.size() - 90);
        d.annulerDernierDeplacement();
        assertEquals(2, historique.size() - 90);
        String rep1 = AfficherDamier.generer(d);
        String rep2 = AfficherDamier.generer(d);
    }

    /** Un test qui verifie la promotion d'une dame noir et null. **/
    public void testCheckPromotionNoirEtNull() {
        Damier d = new Damier();
        d.initialiser();

        // --- Cas 1 : promotion pour un pion noir ---
        // On place un pion noir sur une case de promotion
        d.ajouterPion(50, new Pion(Couleur.Noir));
        d.checkPromotion(50);
        assertEquals("Doit devenir une dame noire",
                Dame.class, d.getPion(49).getClass());

        // --- Cas 2 : pion nul ---
        d.ajouterPion(10, null); // appelle checkPromotion avec null
        d.checkPromotion(10); // ne doit rien faire
        assertNull("La case doit rester vide", d.getPion(9));
    }
}
