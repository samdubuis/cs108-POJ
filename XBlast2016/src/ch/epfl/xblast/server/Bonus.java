/**
 * Enumération concernant les bonus du jeu
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */
package ch.epfl.xblast.server;

public enum Bonus {
	
	/**
	 * augmente le nombre de bombres d'un joueur
	 */
    INC_BOMB {
        private static final int MAX_VALUE = 9;
        @Override
        public Player applyTo(Player player) { 
            if (player.maxBombs()<MAX_VALUE) {
                return player.withMaxBombs(player.maxBombs()+1);
            }
            else {
                return player;
            }
        }
    },
    
    /**
     * augmente la porte des bombres d'un joueur
     */
    INC_RANGE {
        private static final int MAX_VALUE = 9;
        @Override
        public Player applyTo(Player player) {
            if (player.bombRange()<MAX_VALUE) {
                return player.withBombRange(player.bombRange()+1);
            }
            else {
                return player;
            }
        }
    };

    /**
     * fonction appliquant le bonus au player sélectionné en paramètre
     * @param Player
     * @return Player
     */
    abstract public Player applyTo(Player player);
}
