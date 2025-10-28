package cstjean.mobile.Damier;

import cstjean.mobile.Mouvement.MouvementPion;
import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Pion.Dame;
import cstjean.mobile.Pion.Pion;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.LinkedList;
import java.util.List;

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
        Object[][] damier = new Object[10][10];
        for (int i = 0; i < d.getNombrePions(); i++) {
            d.ajouterPion(i+1, null);
        }
        d.ajouterPion(8, new Pion(Couleur.Blanc));
        damier[1][4] = Character.toString(d.getPion(7).getRepresentation());
        assertEquals(Pion.class, d.getPion(7).getClass());
        List<MouvementPion.Position> moves =
                MouvementPion.getDeplacementsPossibles(1, 4, true, damier);
        MouvementPion.bougerPion(
                moves,
                new MouvementPion.Position(0, 5, false),
                damier, 1, 4);

        d.checkPromotion(3);
        if (d.getPion(2) != null)
        assertEquals(Dame.class, d.getPion(2).getClass());
    }
    public void testHistorique(){
        Damier d = new Damier();
        d.initialiser();
        for (int i = 0; i < d.getNombrePions(); i++) {
            d.ajouterPion(i+1, null);
        }
        List<LinkedList<Pion>> historique = d.getHistorique();
        // -90 pour ignorer les actions de set le board up et d'enlever tous les pions
        assertEquals(0,  historique.size() - 90);

        d.ajouterPion(8, new Pion(Couleur.Blanc));
        d.ajouterPion(22, new Pion(Couleur.Noir));
        d.ajouterPion(44, new Pion(Couleur.Noir));
        String rep1 = AfficherDamier.generer(d);

        assertEquals(3, historique.size() - 90);
        d.annulerDernierDeplacement();
        assertEquals(2, historique.size() - 90);
        String rep2 = AfficherDamier.generer(d);
        assertTrue(!rep1.equals(rep2));
    }
}
