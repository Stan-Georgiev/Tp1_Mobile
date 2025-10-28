package cstjean.mobile;

import cstjean.mobile.Damier.TestAfficherDamier;
import cstjean.mobile.Damier.TestDamier;
import cstjean.mobile.Joueur.TestFinPartie;
import cstjean.mobile.Joueur.TestGestionTours;
import cstjean.mobile.Mouvement.TestMouvementDame;
import cstjean.mobile.Mouvement.TestMouvementPion;
import cstjean.mobile.Pion.TestDame;
import cstjean.mobile.Pion.TestPion;
import junit.framework.TestSuite;

public class TestComplet {
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
