package cstjean.mobile;

import cstjean.mobile.damier.TestAfficherDamier;
import cstjean.mobile.damier.TestDamier;
import cstjean.mobile.joueur.TestFinPartie;
import cstjean.mobile.joueur.TestGestionTours;
import cstjean.mobile.mouvement.TestMouvementDame;
import cstjean.mobile.mouvement.TestMouvementPion;
import cstjean.mobile.pion.TestDame;
import cstjean.mobile.pion.TestPion;
import junit.framework.TestSuite;

/** La suite de test dans la solution.*/
public class TestComplet {
    /** La suite de test dans la solution.
     *
     *@return  retourne les tests pour chaque classe de test */
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestAfficherDamier.class);
        suite.addTestSuite(TestDamier.class);
        suite.addTestSuite(TestFinPartie.class);
        suite.addTestSuite(TestGestionTours.class);
        suite.addTestSuite(TestMouvementPion.class);
        suite.addTestSuite(TestMouvementDame.class);
        suite.addTestSuite(TestDame.class);
        suite.addTestSuite(TestPion.class);
        return suite;
    }
}
