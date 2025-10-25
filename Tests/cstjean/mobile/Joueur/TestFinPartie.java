package cstjean.mobile.Joueur;

import cstjean.mobile.Damier.Damier;
import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Pion.Pion;
import junit.framework.TestCase;

public class TestFinPartie extends TestCase {
    public void testAucunePiece(){
        Damier d = new Damier();
        d.initialiser();
        FinPartie finPartie = new FinPartie(d);

        assertTrue(!finPartie.aucunePiece(Couleur.Blanc));
        assertTrue(!finPartie.aucunePiece(Couleur.Noir));

        for (int i = 1; i <= d.getNombrePions(); i++){
            d.ajouterPion(i, null);
        }
        finPartie =  new FinPartie(d);
        assertTrue(finPartie.aucunePiece(Couleur.Blanc));
        assertTrue(finPartie.aucunePiece(Couleur.Noir));
    }
    public void testAucunDeplacementPossible(){
        Damier d = new Damier();
        d.initialiser();
        FinPartie finPartie = new FinPartie(d);

        assertTrue(!finPartie.aucunDeplacementPossible(Couleur.Blanc));
        assertTrue(!finPartie.aucunePiece(Couleur.Noir));
        for (int i = 1; i <= d.getNombrePions(); i++){
            d.ajouterPion(i, null);
        }
        d.ajouterPion(5, new Pion(Couleur.Noir));
        d.ajouterPion(10, new Pion(Couleur.Blanc));
        //assertTrue(finPartie.aucunDeplacementPossible(Couleur.Blanc));
        assertTrue(!finPartie.aucunDeplacementPossible(Couleur.Noir));
    }
    public void testGetGagnant(){
        Damier d = new Damier();
        d.initialiser();
        FinPartie finPartie = new FinPartie(d);

        assertEquals("Aucun", finPartie.getGagnant());

        for (int i = 1; i <= d.getNombrePions(); i++){
            if (d.getPion(i).getCouleur() == Couleur.Blanc) d.ajouterPion(i, null);
        }
        assertEquals("Noir", finPartie.getGagnant());

        d.initialiser();
        finPartie = new FinPartie(d);
        for (int i = 1; i <= d.getNombrePions(); i++){
            if (d.getPion(i).getCouleur() == Couleur.Noir)  d.ajouterPion(i, null);
        }
        assertEquals("Blanc", finPartie.getGagnant());
    }
}
