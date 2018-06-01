/**
 * classe permettant de encoder et decoder une liste de byte
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RunLengthEncoder {
    
    /**
     * constructeur privé car non instanciable
     */
    private RunLengthEncoder(){}
    
    /**
     * methode encodant une liste de byte
     * @param l
     * @return
     */
    public static List<Byte> encode(List<Byte> l){
        for (Byte b : l) {
            if (b<0) {
                throw new IllegalArgumentException();
            }
        }
        
        Objects.requireNonNull(l);
        
        List<Byte> result = new ArrayList<Byte>();
        int taille = 1;
        if (!(l.size()<=0)) {
            for (int i = 0; i < l.size()-1; i++) {
                if (taille>129) {
                    result.add((byte)Math.negateExact(taille-2));
                    result.add(l.get(i));
                    taille = 0;
                }
                if (l.get(i)==l.get(i+1)) {
                    taille++;
                }
                else {
                    if (taille==1) {
                        result.add(l.get(i));
                    }
                    else if (taille ==2) {
                        result.add(l.get(i-1));
                        result.add(l.get(i));
                        taille=1;
                    }
                    else {
                        result.add((byte)Math.negateExact(taille-2));
                        result.add(l.get(i));
                        taille=1;
                    }
                }
            }
            if (taille==1) {
                result.add(l.get(l.size()-1));
            }
            else if (taille ==2) {
                result.add(l.get(l.size()-1-1));
                result.add(l.get(l.size()-1));
                taille=1;
            }
            else {
                result.add((byte)Math.negateExact(taille-2));
                result.add(l.get(l.size()-1));
                taille=1;
            }
        }
        
        return result;
    }

    /**
     * methode decodant une liste de byte
     * @param l
     * @return
     */
    public static List<Byte> decode(List<Byte> l){    
        if (l.get(l.size()-1)<0) {
            throw new IllegalArgumentException();
        }
        List<Byte> result = new ArrayList<>();
        int taille = 1;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i)<0) {
                taille = Math.negateExact(l.get(i))+2;
            }
            else {
                for (int j = 0; j < taille; j++) {
                    result.add(l.get(i));
                }
                taille=1;
            }
        }



        return result;
    }



}
