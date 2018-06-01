/**
 * Classe concernant les manipulations de liste
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public final class Lists {

    private Lists(){}

    /**
     * Methode retournant le miroire d'une liste d'elements quelconques
     * @param 
     * @return List<>
     */
    public static <T> List<T> mirrored(List<T> l){
        if (l.isEmpty()) {
            throw new IllegalArgumentException();
        }
        else{
            List<T> mirrored = new ArrayList<T>(l);
            List<T> mirroredSubList = new ArrayList<T>(mirrored.subList(0, mirrored.size() -1));
            Collections.reverse(mirroredSubList);
            for (int i = 0; i < mirroredSubList.size(); i++){
                mirrored.add(mirroredSubList.get(i));
            }
            return mirrored;
        }
    }



    /**
     * Methode retournant toutes les permutations d'une liste d'elements quelconques
     * @param 
     * @return List<>
     */
    public static <T> List<List<T>> permutations(List<T> l){
        List<List<T>> allPermutations = new ArrayList<List<T>>();
        allPermutations.add(new ArrayList<T>());

        for (int i = 0; i < l.size(); i++){
            List<List<T>> currentPermutations = new ArrayList<List<T>>();

            for (int j = 0; j < allPermutations.size(); j++){
                List<T> temp1 = new ArrayList<T>();
                temp1 = allPermutations.get(j);

                for (int k = 0; k < temp1.size() +1; k++){
                    temp1.add(k, l.get(i));                    
                    List<T> temp2 = new ArrayList<T>(temp1);
                    currentPermutations.add(temp2);                    
                    temp1.remove(k);
                }
            }
            allPermutations = new ArrayList<List<T>>(currentPermutations);
        }
        return allPermutations;
    }
}

