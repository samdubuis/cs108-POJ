/**
 * classe permettant de faire le lien entre une bombe ou une explosion et son image
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */
package ch.epfl.xblast.server;

public final class ExplosionPainter {
    
    /**
     * constructeur privé de la classe car non instanciable
     */
    private  ExplosionPainter() { }
    
    public static byte BYTE_FOR_EMPTY = 0b10000; //=16
    
    /**
     * methode retournant le byte image correspondant à une bombe
     * @param b
     * @return
     */
    public static byte byteOfBomb(Bomb b){
        if (Integer.bitCount(b.fuseLength())==1) {
            return (byte)21;
        }
        else {
            return (byte)20;
        }
    }
    
    /**
     * methode retournant le byte image correspondant à une explosion
     * @param N
     * @param E
     * @param S
     * @param W
     * @return
     */
    public static byte byteOfBlast(boolean N, boolean E, boolean S, boolean W){
        byte temp = 0;
        if (N) {
            temp+=8;
        }
        if (E) {
            temp+=4;
        }
        if (S) {
            temp+=2;
        }
        if (W) {
            temp+=1;
        }
        return temp;
    }
    
    
    
}
