/**
 * Classe gérant les exceptions diverses au travers du programme
 * Cette classe regroupe la plupart des gestions d'exceptions demandées au long du projet
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */
package ch.epfl.xblast;

public final class ArgumentChecker {
    private ArgumentChecker(){}

    /**
     * Test qui requiert une valeur non négative
     * @param int value
     * @return value si non négative, sinon lance l'exception IllegalArgumentException
     */
    public static int requireNonNegative(int value){
            if (value >=0) {
                return value;
            }
            else{
                throw new IllegalArgumentException();
            }
    }
}
