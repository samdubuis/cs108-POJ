/**
 * Enumeration concernant les différents blocks du jeu
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */
package ch.epfl.xblast.server;

import java.util.NoSuchElementException;

public enum Block {
    FREE, INDESTRUCTIBLE_WALL, DESTRUCTIBLE_WALL, CRUMBLING_WALL, BONUS_BOMB(Bonus.INC_BOMB), BONUS_RANGE(Bonus.INC_RANGE);

    private Bonus maybeAssociatedBonus;

    private Block(Bonus maybeAssociatedBonus){
        this.maybeAssociatedBonus = maybeAssociatedBonus;
    }

    private Block(){
        this.maybeAssociatedBonus = null;
    }

    /**
     * fonction retournant true seulement si le block est un block free
     * @return true ou false
     */
    public boolean isFree(){
        return this.equals(FREE);
    }

    /**
     * fonction retournant true seulement si le joueur peut y passer
     * @return true ou false
     */
    public boolean canHostPlayer(){
        return (this.isFree() || this.equals(BONUS_BOMB) || this.equals(BONUS_RANGE));
    }

    /**
     * fonction qui retourne true seulement si le block projette une ombre, cad les blocks murs
     * @return true ou false
     */
    public boolean castsShadow(){
        return (this.equals(INDESTRUCTIBLE_WALL) || this.equals(DESTRUCTIBLE_WALL) || this.equals(CRUMBLING_WALL));
    }

    /**
     * fonction retournant true seulement si le block en paramètre est un block bonus
     * @return true ou false
     */
    public boolean isBonus(){
        return (this.equals(BONUS_BOMB) || this.equals(BONUS_RANGE));
    }

    /**
     * fonction retournant le bonus associé si le block est un bonus, et null si le contraire
     * @return bonus associé ou null
     */
    public Bonus associatedBonus(){
        if (maybeAssociatedBonus==null) {
            throw new NoSuchElementException();
        }
        else {
            return this.maybeAssociatedBonus;

        }
        
    }

}
