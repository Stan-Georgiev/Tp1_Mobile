package cstjean.mobile.Pion;

import junit.framework.TestCase;
import org.junit.Assert;

/** Classe de Test pour la classe Pion. **/
public class TestPion extends TestCase {
    /** Test le constructeur de pion avec Parametre de couleur. **/
    public void testCreerAvecCouleur() {
        Pion p1 = new Pion(Couleur.Noir);
        Pion p2 = new Pion(Couleur.Blanc);

        assertEquals(Couleur.Noir, p1.getCouleur());
        assertEquals('P', p1.getRepresentation());
        assertEquals(Couleur.Blanc, p2.getCouleur());
        assertEquals('p', p2.getRepresentation());
    }

    /** Test le constructeur de pion sans Parametre. **/
    public void testCreerSansParametre() {
        Pion p = new Pion(); // uses default constructor
        assertEquals(Couleur.Blanc, p.getCouleur());
        assertEquals('p', p.getRepresentation());
    }

    /** Test la creation d'une dame blanc. **/
    public void testDameBlanche() {
        Dame dameBlanche = new Dame(Couleur.Blanc);
        assertEquals(Couleur.Blanc, dameBlanche.getCouleur());
        assertEquals('d', dameBlanche.getRepresentation()); // covers if branch
    }

    /** Test la creation d'une dame noir. **/
    public void testDameNoire() {
        Dame dameNoire = new Dame(Couleur.Noir);
        assertEquals(Couleur.Noir, dameNoire.getCouleur());
        assertEquals('D', dameNoire.getRepresentation()); // covers else branch
    }
}
