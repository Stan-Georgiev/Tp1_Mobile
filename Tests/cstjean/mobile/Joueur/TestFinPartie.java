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
        //Les cases 4 et 5 sont les seules case où un pion blanc à la case 10 peut se déplacer vers,
        //c'est pour ça qu'on les encombre
        d.ajouterPion(4, new Pion(Couleur.Noir));
        d.ajouterPion(5, new Pion(Couleur.Noir));
        d.ajouterPion(10, new Pion(Couleur.Blanc));

        assertTrue(finPartie.aucunDeplacementPossible(Couleur.Blanc));
        assertTrue(!finPartie.aucunDeplacementPossible(Couleur.Noir));
    }
    public void testGetGagnant(){
        Damier d = new Damier();
        d.initialiser();
        FinPartie finPartie = new FinPartie(d);

        assertEquals("Aucun", finPartie.getGagnant());

        for (int i = 0; i < d.getNombrePions(); i++){
            if (d.getPion(i) != null && d.getPion(i).getCouleur() == Couleur.Blanc) {
                d.ajouterPion(i+1, null);
            }
        }
        assertEquals("Noir", finPartie.getGagnant());

        d.initialiser();
        finPartie = new FinPartie(d);
        for (int i = 0; i < d.getNombrePions(); i++){
            if (d.getPion(i) != null && d.getPion(i).getCouleur() == Couleur.Noir){
                d.ajouterPion(i+1, null);
            }
        }
        assertEquals("Blanc", finPartie.getGagnant());
    }
    public void testEstTerminee(){
        Damier d = new Damier();
        d.initialiser();
        FinPartie finPartie = new FinPartie(d);
        assertTrue(!finPartie.estTerminee());
        for (int i = 0; i < d.getNombrePions(); i++){
            if (d.getPion(i) != null && d.getPion(i).getCouleur() == Couleur.Blanc) {
                d.ajouterPion(i+1, null);
            }
        }
        assertTrue(finPartie.estTerminee());
    }
}
