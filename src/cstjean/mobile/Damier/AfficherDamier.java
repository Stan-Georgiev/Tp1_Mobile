package cstjean.mobile.Damier;

import cstjean.mobile.Pion.Pion;

/** La classe de tests qui verifie le fonctionnement de la classe Damier. **/
public class AfficherDamier {
    /** La classe de tests qui verifie le fonctionnement de la classe Damier.
     *
     * @param damier l'instance de la classe damier.
     *
     * @return sa retourne le string builder en forme de string
     **/
    public static String generer(Damier damier) {
        StringBuilder sb = new StringBuilder();
        int posManoury = 1; // commence à 1 pour la notation Manoury

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int col = 0; col < 10; col++) {
                // cases jouables : alternance selon la ligne
                if ((ligne + col) % 2 == 1) {
                    // correspond à une case Manoury (1 à 50)
                    Pion pion = damier.getPion(posManoury - 1);
                    sb.append(pion == null ? '-' : pion.getRepresentation());
                    posManoury++;
                } else {
                    // case non jouable
                    sb.append('-');
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
