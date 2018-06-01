/**
 * Enumeration concernant les directions possibles dans le jeu
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */
package ch.epfl.xblast;

public enum Direction {
    N, E, S, W;

    /**
     * Fonction retournant l'opposé de la direction donnée
     * 
     * @return Direction
     */
    public Direction opposite() {
        switch (this) {
        case N:
            return S;

        case S:
            return N;

        case E:
            return W;

        case W:
            return E;

        default:
            return null;
        }
    }

    /**
     * fonction booleane retournant vrai si la direction donnée est horizontale
     * 
     * @return boolean
     */
    public boolean isHorizontal() {
        return (this == E || this == W);
    }

    /**
     * Fonction retournant si une direction est parallèle à la direction passée
     * en paramètre
     * 
     * @param Direction 
     *            
     * @return boolean
     */
    public boolean isParallelTo(Direction that) {
        return (this==that || this == that.opposite());
    }
}
