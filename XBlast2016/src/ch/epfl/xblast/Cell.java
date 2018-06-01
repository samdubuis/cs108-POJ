/**
 * Classe concernant la Cell et ses fonctions
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.lang.Math;

public final class Cell {
    
    public static final int COLUMNS = 15;
    public static final int ROWS = 13;
    public static final int COUNT = COLUMNS*ROWS;

    public static final List<Cell> ROW_MAJOR_ORDER = Collections.unmodifiableList(rowMajorOrder());
    public static final List<Cell> SPIRAL_ORDER = Collections.unmodifiableList(spiralOrder());

    
    private final int x;
    private final int y;
    
    /**
     * Constructeur de la Cell
     * @param x (valeur de la coordonné x)
     * @param y	(valeur de la coordonné y)
     */
    public Cell(int x, int y){                         
        this.x = Math.floorMod(x,COLUMNS);
        this.y = Math.floorMod(y,ROWS);
    }
   
    /**
     * Getter pour coordonnée x
     * @return int
     */
    public int x() {
        return x;
    }
    
    /**
     * Getter pour coordonnée y
     * @return int
     */
    public int y() {
        return y;
    }
    
    /**
     * Construction de la liste dans l'ordre de lecture (ROW_MAJOR_ORDER)
     * @return ArrayList<> 
     */
    private static ArrayList<Cell> rowMajorOrder(){
        ArrayList<Cell> listRowMajorOrder = new ArrayList<>();
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){
                Cell temp = new Cell(j,i);
                listRowMajorOrder.add(temp);
            }
        }
        return listRowMajorOrder;
    }
    
    /**
     * Construction de la dans l'ordre de lecture en spiral (SPIRAL_ORDER)
     * @return ArrayList<>
     */
    private static ArrayList<Cell> spiralOrder(){
        ArrayList<Integer> ix = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14));
        ArrayList<Integer> iy = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12));
       
        boolean horizontal = true;
        ArrayList<Cell> listSpiralOrder = new ArrayList<>();
                
        while (ix.isEmpty() == false && iy.isEmpty() == false){
            ArrayList<Integer> i1;
            ArrayList<Integer> i2;
            int c2;
            
            if (horizontal){
                i1 = ix;
                i2 = iy;
            }
            else{
                i1 = iy;
                i2 = ix;
            }
            
            c2 = i2.get(0);
            i2.remove(0);
         
            for (int i = 0; i < i1.size(); i++){
                if (horizontal){
                    Cell temp = new Cell(i1.get(i),c2);
                    listSpiralOrder.add(temp);
                }
                else {
                    Cell temp = new Cell(c2,i1.get(i));
                    listSpiralOrder.add(temp);
                }
            }
            Collections.reverse(i1);
            horizontal = !horizontal;
        }
        return listSpiralOrder;
    }
    
    /**
     * Methode retournant la position de la cell dans l'ordre de lecture
     * @return int
     */
    public int rowMajorIndex(){
        return this.x()+this.y()*COLUMNS;
    }
    
    /**
     * Compare deux cell et retourne vrai si les cells sont identiques faux sinon
     * @param 
     * @return boolean
     */
    @Override
    public boolean equals(Object that){
        if (that instanceof Cell){
            Cell comparedCell = (Cell) that;
            return (this.x() == comparedCell.x() && this.y() == comparedCell.y());
        }
        else return false;
    }
    
    
    /**
     * Retourne les cells voisines de la cell
     * @param Direction
     * @return Cell
     */
    public Cell neighbor(Direction dir){     
        if (dir == Direction.N){
            return new Cell(this.x(), this.y() -1);
        }

        if (dir == Direction.S){
            return new Cell(this.x(), this.y() +1);
        }

        if (dir == Direction.E){
            return new Cell(this.x() +1, this.y());
        }

        else return new Cell(this.x() -1, this.y());                  
    } 
    
    /**
     * Affiche les coordonnées x et y de la cell
     */
    @Override
    public String toString(){
        String coordonne = "(" + this.x() + "," + this.y() + ")";
        return coordonne;
    }
    
    @Override
    public int hashCode(){
        return this.rowMajorIndex();
    }
    

}
