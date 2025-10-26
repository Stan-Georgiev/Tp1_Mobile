package cstjean.mobile.Joueur;

import cstjean.mobile.Damier.Damier;
import cstjean.mobile.Pion.Couleur;
import cstjean.mobile.Pion.Pion;
import junit.framework.TestCase;
import org.junit.Test;

public class TestFinPartie extends TestCase {
    @Test
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
    @Test
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

    @Test
    public void testGetGagnant() {
        Damier d = new Damier();
        d.initialiser();
        FinPartie finPartie = new FinPartie(d);

        // Partie en cours → aucun gagnant
        assertEquals("Aucun", finPartie.getGagnant());

        // Supprimer toutes les pièces blanches → Noir gagne
        for (int i = 0; i < d.getNombrePions(); i++) {
            Pion p = d.getPion(i);
            if (p != null && p.getCouleur() == Couleur.Blanc) {
                d.ajouterPion(i, null);
            }
        }
        finPartie = new FinPartie(d); // recréer FinPartie pour prendre en compte les changements
        assertEquals("Noir", finPartie.getGagnant());

        // Réinitialiser le damier
        d.initialiser();
        finPartie = new FinPartie(d);

        // Supprimer toutes les pièces noires → Blanc gagne
        for (int i = 0; i < d.getNombrePions(); i++) {
            Pion p = d.getPion(i);
            if (p != null && p.getCouleur() == Couleur.Noir) {
                d.ajouterPion(i, null);
            }
        }
        finPartie = new FinPartie(d); // recréer FinPartie
        assertEquals("Blanc", finPartie.getGagnant());
    }

}
