/**
 * Classe concernant la SubCell et ses fonctions
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast;

public final class SubCell {
    public static final int SEIZE = 16;
    public static final int SUBCOLUMNS = SEIZE * Cell.COLUMNS;
    public static final int SUBROWS = SEIZE * Cell.ROWS;
    public static final int SUBCOUNT = SUBCOLUMNS * SUBROWS;

    private final int x;
    private final int y;

    /**
     * Constructeur de la SubCell
     * 
     * @param x	valeur des colonnes
     *            
     * @param y	valeurs des lignes
     *            
     */
    public SubCell(int x, int y) {
        this.x = Math.floorMod(x, SUBCOLUMNS);
        this.y = Math.floorMod(y, SUBROWS);
    }

    /**
     * Getter de la valeur de la colonne de la SubCell
     * 
     * @return int
     */
    public int x() {
        return x;
    }

    /**
     * Getter de la valeur de la ligne de la SubCell
     * 
     * @return int
     */
    public int y() {
        return y;
    }

    /**
     * Methode retournant la SubCell centrale de la Cell
     * 
     * @param Cell 
     * @return Subcell 
     */
    public static SubCell centralSubCellOf(Cell cell) {
        int tempx = cell.x() * SEIZE + SEIZE/2;
        int tempy = cell.y() * SEIZE + SEIZE/2;
        return new SubCell(tempx, tempy);
    }

    /**
     * Fonction retournant la cell dans laquelle se trouve la subcell
     * en parametre
     * 
     * @return Cell
     */
    public Cell containingCell() {
        int xtemp = this.x() / SEIZE;
        int ytemp = this.y() / SEIZE;

        return new Cell(xtemp, ytemp);
    }

    /**
     * Fonction retournant la distance de Manhattan entre la subcell et la
     * subcell centrale
     * 
     * @return int
     */
    public int distanceToCentral() {
        Cell cellContenante = this.containingCell();
        SubCell centralSubCell = centralSubCellOf(cellContenante);

        int distance = Math.abs(centralSubCell.x() - this.x())
                + Math.abs(centralSubCell.y() - this.y());
        return distance;
    }

    /**
     * Fonction booleen retournant si la subcell est centrale ou non
     * 
     * @return boolean
     */
    public boolean isCentral() {
        return (this.distanceToCentral()==0);
    }

    /**
     * Fonction retournant la subcell voisine selon le paramètre de direction
     * donné
     * 
     * @param Direction
     *           
     * @return Subcell
     */
    public SubCell neighbor(Direction d) {
        if (d == Direction.E) {
            return new SubCell(this.x() + 1, this.y());
        }
        if (d == Direction.N) {
            return new SubCell(this.x(), this.y() - 1);
        }
        if (d == Direction.S) {
            return new SubCell(this.x(), this.y() + 1);
        } 
        else return new SubCell(this.x() - 1, this.y());

    }

    @Override
    public String toString() {
        String coordonne = "(" + this.x() + "," + this.y() + ")";
        return coordonne;
    }

    @Override
    public boolean equals(Object that){
        if (that instanceof SubCell){
            SubCell comparedSubCell = (SubCell) that;
            return (this.x() == comparedSubCell.x() && this.y() == comparedSubCell.y());
        }
        else return false;
    }
    
    @Override
    public int hashCode(){
        return SUBCOLUMNS * this.y() + this.x();
    }

}
